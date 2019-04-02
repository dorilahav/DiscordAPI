package com.dorilahav.api.utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.dorilahav.api.jackson.JacksonModule;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class JacksonUtils {
	
	@Getter
	@Setter
	private static ObjectMapper
			mapper;
	
	static {
		mapper = new ObjectMapper()
				.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
	}
	
	public static Object getSerialized(@NonNull Object obj) {
		JsonNode node = mapper.convertValue(obj, JsonNode.class);
		Object target = null;
		
		switch (node.getNodeType()) {
			case STRING:
				target = node.textValue();
				break;
				
			case ARRAY:
				target = mapper.convertValue(obj, new TypeReference<List<Object>>() {});
				break;
				
			case POJO:
				target = mapper.convertValue(obj, new TypeReference<Map<String, Object>>() {});
				break;
				
			case OBJECT:
				target = mapper.convertValue(obj, new TypeReference<Map<String, Object>>() {});
				break;
				
			case NUMBER:
				target = node.numberValue();
				break;
			
			case BINARY:
				try {
					target = node.binaryValue();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				break;
			
			case BOOLEAN:
				target = node.booleanValue();
				break;
			
			case NULL:
				break;
			
			case MISSING:
				break;
			
			default:
				break;
			
		}
		
		return target;
	}
	
	public static ObjectMapper constructMapper() {
		return new ObjectMapper().setVisibility(PropertyAccessor.ALL, Visibility.ANY).registerModule(new JacksonModule());
	}
	
	public static ObjectMapper constructYamlMapper() {
		return new ObjectMapper(new YAMLFactory().configure(Feature.SPLIT_LINES, false).configure(Feature.WRITE_DOC_START_MARKER, false)).setVisibility(PropertyAccessor.ALL, Visibility.ANY).registerModule(new JacksonModule());
	}

}
