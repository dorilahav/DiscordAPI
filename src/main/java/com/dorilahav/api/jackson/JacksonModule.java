package com.dorilahav.api.jackson;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import com.dorilahav.api.jackson.deserializers.InstantDeserializer;
import com.dorilahav.api.jackson.deserializers.LocalDateTimeDeserializer;
import com.dorilahav.api.jackson.deserializers.OffsetDateTimeDeserializer;
import com.dorilahav.api.jackson.serializers.InstantSerializer;
import com.dorilahav.api.jackson.serializers.LocalDateTimeSerializer;
import com.dorilahav.api.jackson.serializers.OffsetDateTimeSerializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class JacksonModule extends SimpleModule {

    private static final long serialVersionUID = 6537931591338424693L;

    public JacksonModule() {
        addDeserializer(Instant.class, new InstantDeserializer());
        addSerializer(new InstantSerializer());

        addDeserializer(OffsetDateTime.class, new OffsetDateTimeDeserializer());
        addSerializer(new OffsetDateTimeSerializer());

        addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        addSerializer(new LocalDateTimeSerializer());
    }

}
