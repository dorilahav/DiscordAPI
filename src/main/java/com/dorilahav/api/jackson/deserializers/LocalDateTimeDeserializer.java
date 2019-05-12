package com.dorilahav.api.jackson.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class LocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {
	
	private static final long serialVersionUID = -8054939543669093941L;
	
	public LocalDateTimeDeserializer() {
		super(LocalDateTime.class);
	}

	@Override
	public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(p.getValueAsLong()), ZoneOffset.UTC);
	}
	
}
