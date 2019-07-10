package com.dorilahav.api.utils;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
public class SimpleStream<T> {

    @Getter
    @NonNull
    private final Stream<T>
            stream;

    public SimpleStream(Collection<T> collection) {
        this(collection.stream());

    }

    public T getFirstOrNull(@NonNull Predicate<T> predicate) {
        return stream.filter(predicate).limit(1).findFirst().orElse(null);
    }

}
