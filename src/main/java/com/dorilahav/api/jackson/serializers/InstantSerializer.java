package com.dorilahav.api.jackson.serializers;

import java.io.IOException;
import java.time.Instant;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class InstantSerializer extends StdSerializer<Instant> {

	private static final long serialVersionUID = 5191653172894008036L;
	
	public InstantSerializer() {
		super(Instant.class);
	}

	@Override
	public void serialize(Instant value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeObject(value.toEpochMilli());
	}

}
