package com.dorilahav.api.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JacksonConfiguration implements Configuration {

    private Map<Class<?>, File>
            files = new HashMap<>();

    private Map<Class<?>, Map<String, Object>>
            cache = new HashMap<>();

    @Getter
    @NonNull
    final ObjectMapper
            mapper;


    @Override
    public void initialize(@NonNull Class<?> clazz, Object instance) {

    }

    @Override
    public void save(@NonNull Class<?> clazz, @NonNull Object instance) {

    }

    @Override
    public void load(@NonNull Class<?> clazz, Object instance) {

    }

    @Override
    public void initializeField(@NonNull Field field, Object instance) {

    }

    @Override
    public void saveField(@NonNull Field field, Object instance) {

    }

    @Override
    public void loadField(@NonNull Field field, Object instance) {



    }

    /**
     * Creates the file based on the class, if needed and enters an empty HashMap as it's value.
     * @param clazz the class the take the file from.
     */
    @Override
    public void initializeFile(@NonNull Class<?> clazz) {

        File file = getFile(clazz);

        if (file.getParent() != null && !file.getParentFile().exists())
            file.getParentFile().mkdirs();

        if (!file.exists()) {
            try {
                file.createNewFile();
                mapper.writeValue(file, new HashMap<>());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Gets the fle details from the specified class using the @ConfigFile annotation.
     * @param clazz the class to get the file details from.
     * @return Possibly-null the file processed using the @ConfigFile annotation.
     */
    private File getFile(Class<?> clazz) {

        if (files.containsKey(clazz))
            return files.get(clazz);

        if (!clazz.isAnnotationPresent(ConfigFile.class))
            throw new IllegalArgumentException("Class " + clazz.getCanonicalName() + " is not annotated with @ConfigFile.");

        ConfigFile configFile = clazz.getDeclaredAnnotation(ConfigFile.class);

        if (configFile.name().isEmpty())
            return null;

        Path path = Paths.get(configFile.name());
        if (!configFile.path().isEmpty())
            path = Paths.get(configFile.path(), configFile.name());

        return path.toFile();
    }

}