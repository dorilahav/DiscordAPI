package com.dorilahav.api.jackson;

import java.time.Instant;
import java.time.OffsetDateTime;

import com.dorilahav.api.jackson.deserializers.InstantDeserializer;
import com.dorilahav.api.jackson.deserializers.OffsetDateTimeDeserializer;
import com.dorilahav.api.jackson.serializers.InstantSerializer;
import com.dorilahav.api.jackson.serializers.OffsetDateTimeSerializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class JacksonModule extends SimpleModule {

	private static final long serialVersionUID = 6537931591338424693L;
	
	public JacksonModule() {
		addDeserializer(Instant.class, new InstantDeserializer());
		addSerializer(new InstantSerializer());
		
		addDeserializer(OffsetDateTime.class, new OffsetDateTimeDeserializer());
		addSerializer(new OffsetDateTimeSerializer());
	}

}
