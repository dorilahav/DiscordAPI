package com.dorilahav.api.configuration;

import java.lang.reflect.Field;

import lombok.NonNull;

public interface Configuration {

    default void initialize(@NonNull Class<?> clazz) {
        initialize(clazz, null);
    }

    void initialize(@NonNull Class<?> clazz, Object instance);

    default void save(@NonNull Class<?> clazz) {
        save(clazz, null);
    }

    void save(@NonNull Class<?> clazz, @NonNull Object instance);

    default void load(@NonNull Class<?> clazz) {
        load(clazz, null);
    }

    void load(@NonNull Class<?> clazz, Object instance);

    default void initializeField(@NonNull Class<?> clazz, String fieldName) {
        try {
            initializeField(clazz.getDeclaredField(fieldName));
        } catch (SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    default void initializeField(@NonNull Class<?> clazz, @NonNull String fieldName, Object instance) {
        try {
            initializeField(clazz.getDeclaredField(fieldName), instance);
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
    }

    default void initializeField(@NonNull Field field) {
        try {
            initializeField(field, null);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    void initializeField(@NonNull Field field, Object instance);

    default void saveField(@NonNull Class<?> clazz, @NonNull String fieldName) {
        try {
            saveField(clazz.getDeclaredField(fieldName));
        } catch (SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    default void saveField(@NonNull Class<?> clazz, @NonNull String fieldName, Object instance) {
        try {
            saveField(clazz.getDeclaredField(fieldName), instance);
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
    }

    default void saveField(@NonNull Field field) {
        try {
            saveField(field, null);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    void saveField(@NonNull Field field, Object instance);

    default void loadField(@NonNull Class<?> clazz, @NonNull String fieldName) {
        try {
            loadField(clazz.getDeclaredField(fieldName));
        } catch (SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    default void loadField(@NonNull Class<?> clazz, @NonNull String fieldName, Object instance) {
        try {
            loadField(clazz.getDeclaredField(fieldName), instance);
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
    }

    default void loadField(@NonNull Field field) {
        try {
            loadField(field, null);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    void loadField(@NonNull Field field, Object instance);

    void initializeFile(@NonNull Class<?> clazz);

}
