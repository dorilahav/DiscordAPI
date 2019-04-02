package com.dorilahav.api.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
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
	
	@Getter @NonNull
	final ObjectMapper
			mapper;
	
	@Override
	public void initialize(@NonNull Class<?> clazz, Object instance) {
		Map<String, Object> content = cache.get(clazz);
		
		if (content == null)
			content = loadContent(clazz);
		
		for (Field field : clazz.getDeclaredFields()) 
			processField(field, content, instance);
		
		writeContent(clazz, content);
		
	}
	
	
	@Override
	public void save(@NonNull Class<?> clazz, Object instance) {
		Map<String, Object> content = new HashMap<>();
		
		for (Field field : clazz.getDeclaredFields()) {
			
			String key = getKey(field);
			
			if (key == null)
				continue;
			
			field.setAccessible(true);
			
			try {
				content.put(key, field.get(instance));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				continue;
			}
			
			field.setAccessible(false);
			
		}
		
		writeContent(clazz, content);
	}

	@Override
	public void load(@NonNull Class<?> clazz, Object instance) {
		
		Map<String, Object> content = loadContent(clazz);
		
		for (Field field : clazz.getDeclaredFields()) {
			
			String key = getKey(field);
			
			if (key == null)
				continue;
			
			if (!content.containsKey(key))
				continue;
			
			field.setAccessible(true);
			
			try {
				field.set(instance, content.get(key));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				continue;
			}
			
			field.setAccessible(false);
			
		}
	}

	@Override
	public void saveField(@NonNull Field field, Object instance) {
		
		Class<?> clazz = field.getDeclaringClass();
		
		Map<String, Object> content = cache.get(clazz);
		
		if (content == null)
			content = loadContent(clazz);
		
		String key = getKey(field);
		
		if (key == null)
			return;
		
		field.setAccessible(true);
		
		try {
			content.put(key, field.get(instance));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		field.setAccessible(false);
		
		writeContent(clazz, content);
	}

	@Override
	public void loadField(@NonNull Field field, Object instance) {
		
		Map<String, Object> content = loadContent(field.getDeclaringClass());
		
		String key = getKey(field);
		
		if (key == null)
			return;
		
		field.setAccessible(true);
		
		if (content.containsKey(key))
			try {
				field.set(instance, content.get(key));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		
		field.setAccessible(false);
		
	}
	
	@Override
	public void initializeField(@NonNull Field field, Object instance) {
		
		Class<?> clazz = field.getDeclaringClass();
		Map<String, Object> content = cache.get(clazz);
		
		if (content == null)
			content = loadContent(clazz);
		
		String key = getKey(field);
		
		if (key == null)
			return;
		
		field.setAccessible(true);
		
		if (content.containsKey(key))
			try {
				field.set(instance, content.get(key));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				return;
			}
		else
			try {
				content.put(key, field.get(instance));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				return;
			}
		
		field.setAccessible(false);
		
		writeContent(clazz, content);
		
	}

	@Override
	public void initializeFile(@NonNull Class<?> clazz) {
		initializeFile(clazz, false);
	}
	
	protected void processField(@NonNull Field field, @NonNull Map<String, Object> content, Object instance) {
		String key = getKey(field);
		
		if (key == null || key.isEmpty())
			return;
		
		boolean accessible = field.isAccessible();
		
		if (!accessible)
			field.setAccessible(true);
		
		if (content.containsKey(key))
			try {
				field.set(instance, mapper.convertValue(content.get(key), field.getType()));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				return;
			}
		else
			try {
				content.put(key, field.get(instance));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				return;
			}
		
		if (!accessible)
			field.setAccessible(accessible);
		
	}
	
	String getKey(@NonNull Field field) {
		
		if (!field.isAnnotationPresent(Configurable.class))
			return null;
		
		Configurable configurable = field.getDeclaredAnnotation(Configurable.class);
		
		String key = configurable.key();
		if (key != null && !key.isEmpty())
			return key;
		
		return field.getName();
	}
	
	void writeContent(@NonNull Class<?> clazz, @NonNull Map<String, Object> content) {
		
		File file = getFile(clazz);
		
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8), content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		cache.put(clazz, content);
	}

	Map<String, Object> loadContent(@NonNull Class<?> clazz) {
		
		File file = getFile(clazz);
		
		Map<String, Object> content = new HashMap<>();
		try {
			 content = mapper.readValue(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8), new TypeReference<Map<String, Object>>() {});
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
		
		return content;
	}
	
	File getFile(@NonNull Class<?> clazz) {
		if (files.containsKey(clazz))
			return files.get(clazz);
		
		return initializeFile(clazz, true);
	}
	
	File initializeFile(@NonNull Class<?> clazz, boolean returnFile) {
		
		if (!clazz.isAnnotationPresent(ConfigFile.class))
			throw new IllegalArgumentException("A ConfigFile annotation wasn't found on clazz.");
		
		ConfigFile configFile = clazz.getDeclaredAnnotation(ConfigFile.class);
		
		File file = null;
		
		if (configFile.path().isEmpty())
			file = new File(configFile.name());
		else
			file = new File(configFile.path(), configFile.name());
		
		files.put(clazz, file);
		
		if (!file.exists()) {
			
			if (file.getParentFile() != null && !file.getParentFile().exists())
				file.getParentFile().mkdirs();
				
				try {
					file.createNewFile();
					mapper.writerWithDefaultPrettyPrinter().writeValue(file, new HashMap<>());
				} catch (IOException e2) {
					e2.printStackTrace();
					return null;
				}
		}
		
		if (returnFile)
			return file;
		
		return null;
	}

}
