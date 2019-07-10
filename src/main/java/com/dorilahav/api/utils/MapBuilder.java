package com.dorilahav.api.utils;

import java.util.HashMap;
import java.util.Map;

import lombok.NonNull;
import net.dv8tion.jda.core.utils.Checks;

public class MapBuilder<K, V> {

    private Map<K, V>
            map;

    public MapBuilder() {
        this(new HashMap<>());
    }

    public MapBuilder(MapBuilder<K, V> builder) {
        this(builder.build());
    }

    public MapBuilder(Map<K, V> map) {
        this.map = map;
    }

    public MapBuilder<K, V> put(@NonNull K key, V value) {
        map.put(key, value);
        return this;
    }

    public MapBuilder<K, V> putAll(@NonNull Map<K, V> map) {
        this.map.putAll(map);
        return this;
    }

    public MapBuilder<K, V> remove(@NonNull K key) {
        map.remove(key);
        return this;
    }

    public Map<K, V> build() {
        return map;
    }

    @SafeVarargs
    public static <K, V> Map<K, V> combine(Map<K, V>... maps) {
        Checks.notEmpty(maps, "maps");

        if (maps.length == 1)
            return maps[0];

        MapBuilder<K, V> builder = new MapBuilder<>(maps[0]);
        for (int i = 1; i < maps.length; i++)
            builder.putAll(maps[i]);

        return builder.build();
    }

}
