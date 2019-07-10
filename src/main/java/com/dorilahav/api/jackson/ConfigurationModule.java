package com.dorilahav.api.jackson;

import com.dorilahav.api.chat.AEmbed;
import com.dorilahav.api.jackson.deserializers.AEmbedDeserializer;
import com.dorilahav.api.jackson.serializers.AEmbedSerializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class ConfigurationModule extends SimpleModule {

    private static final long serialVersionUID = 883682977724049141L;

    public ConfigurationModule() {
        addDeserializer(AEmbed.class, new AEmbedDeserializer());
        addSerializer(new AEmbedSerializer());
    }

}
