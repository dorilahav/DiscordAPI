package com.dorilahav.api.jackson.serializers;

import java.io.IOException;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class OffsetDateTimeSerializer extends StdSerializer<OffsetDateTime> {

	private static final long serialVersionUID = 5191653172894008036L;
	
	public OffsetDateTimeSerializer() {
		super(OffsetDateTime.class);
	}

	@Override
	public void serialize(OffsetDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeNumber(value.toInstant().toEpochMilli());
	}

}
