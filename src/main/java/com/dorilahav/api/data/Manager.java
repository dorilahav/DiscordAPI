package com.dorilahav.api.data;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Queue;
import java.util.function.Predicate;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.bson.conversions.Bson;

import com.dorilahav.api.utils.JacksonUtils;
import com.dorilahav.api.utils.SimpleStream;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import lombok.Getter;
import lombok.NonNull;
import net.voigon.jackson.mongo.JacksonMongoCollection;

public class Manager<T extends Identifiable> {

    @Getter
    protected JacksonMongoCollection<T>
            collection;

    @Getter
    protected Queue<T>
            cache;

    @Getter
    protected ObjectMapper
            mapper;

    protected String
            idName;

    public Manager(MongoDatabase database, ObjectMapper mapper, Class<T> clazz, String collectionName, int cacheSize) {
        if (!database.listCollectionNames().into(new HashSet<>()).contains(collectionName))
            database.createCollection(collectionName);

        this.mapper = mapper;
        this.collection = new JacksonMongoCollection<>(database.getCollection(collectionName), mapper, clazz);
        this.cache = cacheSize > 0 ? new CircularFifoQueue<>(cacheSize) : null;
        this.idName = "id";
    }


    public T getById(@NonNull String id) {
        return getByFilter(t -> t.getId().equals(id), Filters.eq(idName, id));
    }

    protected T getByFilter(@NonNull String fieldName, Object value) {

        return getByFilter(t -> {
            try {

                Field field = t.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);

                return field.get(t).equals(value);
            } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
                e.printStackTrace();
                return false;
            }
        }, Filters.eq(fieldName, JacksonUtils.getSerialized(value)));
    }


    protected T getByFilter(@NonNull Predicate<T> predicate, @NonNull Bson filter) {

        T item = null;
        if (cache != null)
            item = new SimpleStream<>(cache).getFirstOrNull(predicate);

        if (item == null) {
            item = collection.findFirstOrNull(filter);

            if (cache != null && item != null)
                cache.add(item);

        }

        return item;
    }


    public long count() {
        return collection.getCollection().countDocuments();
    }


    public long count(@NonNull Bson filter) {
        return collection.getCollection().countDocuments(filter);
    }

    public void insert(@NonNull T item) {
        if (cache != null)
            cache.add(item);

        collection.insertOne(item);
    }

    public void insertMany(@NonNull T... items) {

        if (items.length == 0)
            throw new IllegalArgumentException("Nothing provided to insert.");

        if (cache != null)
            cache.addAll(Arrays.asList(items));

        collection.insertMany(items);
    }

    public void save(@NonNull T item) {
        if (cache != null && !cache.contains(item))
            cache.add(item);

        collection.findOneAndReplace(Filters.eq(idName, item.getId()), item);
    }

    public void delete(@NonNull T item) {
        if (cache != null && cache.contains(item))
            cache.remove(item);

        collection.findOneAndDelete(Filters.eq(idName, item.getId()));
    }

    public void deleteMany(@NonNull Bson filter) {
        collection.getCollection().deleteMany(filter);
    }

    public void replace(T item, T newItem) {
        collection.findOneAndReplace(Filters.eq(idName, item.getId()), newItem);
        cache.remove(item);
        cache.add(newItem);
    }

}
