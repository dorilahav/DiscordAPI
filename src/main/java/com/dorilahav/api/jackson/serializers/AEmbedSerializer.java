package com.dorilahav.api.jackson.serializers;

import java.io.IOException;

import com.dorilahav.api.chat.AEmbed;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import net.dv8tion.jda.core.entities.Role;

public class AEmbedSerializer extends StdSerializer<AEmbed> {

    private static final long serialVersionUID = 8791245370442265700L;

    public AEmbedSerializer() {
        super(AEmbed.class);
    }

    @Override
    public void serialize(AEmbed value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();

        if (value.getTitle() != null && !value.getTitle().isEmpty())
            gen.writeStringField("title", value.getTitle());

        if (value.getDescription() != null && !value.getDescription().isEmpty())
            gen.writeStringField("description", value.getDescription());

        if (value.getColor() != Role.DEFAULT_COLOR_RAW)
            gen.writeStringField("color", "#" + Integer.toHexString(value.getColor()).substring(2));

        if (value.getTimestamp() != null)
            gen.writeNumberField("timestamp", value.getTimestamp());

        gen.writeEndObject();
    }

}
