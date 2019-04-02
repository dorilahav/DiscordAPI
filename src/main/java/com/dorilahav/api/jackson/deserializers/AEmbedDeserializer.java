package com.dorilahav.api.jackson.deserializers;

import java.io.IOException;

import com.dorilahav.api.chat.AEmbed;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class AEmbedDeserializer extends StdDeserializer<AEmbed> {

	private static final long serialVersionUID = -3489820023373718124L;

	public AEmbedDeserializer() {
		super(AEmbed.class);
	}

	@Override
	public AEmbed deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		
		JsonNode node = p.readValueAsTree();
		
		AEmbed embed = new AEmbed();
		
		if (node.get("title") != null)
			embed.setTitle(node.get("title").asText());
		
		if (node.get("description") != null)
			embed.setDescription(node.get("description").asText());
		
		if (node.get("color") != null)
			embed.setColor(node.get("color").asText());
		
		if (node.get("timestamp") != null)
			embed.setTimestamp(node.get("timestamp").asLong());
		
		return embed;
	}

}
