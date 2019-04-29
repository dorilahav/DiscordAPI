package com.dorilahav.api.chat;

import java.awt.Color;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.EmbedType;
import net.dv8tion.jda.core.entities.EntityBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.MessageEmbed.AuthorInfo;
import net.dv8tion.jda.core.entities.MessageEmbed.Field;
import net.dv8tion.jda.core.entities.MessageEmbed.Footer;
import net.dv8tion.jda.core.entities.MessageEmbed.ImageInfo;
import net.dv8tion.jda.core.entities.MessageEmbed.Thumbnail;
import net.dv8tion.jda.core.entities.MessageEmbed.VideoInfo;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.requests.restaction.MessageAction;
import net.dv8tion.jda.core.utils.Checks;

@Accessors(chain = true)
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class AEmbed implements Cloneable {
	
	public static AEmbed of(MessageEmbed embed) {		
		return new AEmbed(
				embed.getUrl(),
				embed.getTitle(),
				embed.getDescription(),
				embed.getColorRaw(),
				0L,
				embed.getThumbnail(),
				embed.getFields(),
				embed.getFooter(),
				embed.getAuthor(),
				embed.getImage(),
				embed.getVideoInfo(),
				null,
				null,
				null);
	}
	
	@Getter
    private String
    		url,
    		title,
    		description;
    
    @Getter
    private int
    		color = Role.DEFAULT_COLOR_RAW;
    
    @Getter
    private Long
    		timestamp;
    
    @Getter
    private Thumbnail
    		thumbnail;
    
    @Getter
    private List<Field>
    		fields = new ArrayList<>();
    
    @Getter
    private Footer
    		footer;
    
    @Getter
    private AuthorInfo
    		author;
    
    @Getter
    private ImageInfo
    		image;
    
    @Getter
    private VideoInfo
    		video;
    
    @Getter
    private Map<String, String>
    		arguments = new HashMap<>();
    
    @Getter
    private List<String>
    		reactions = new ArrayList<>();
    
    @Getter @Setter
    private String
    		text;
    
    @JsonCreator
    public AEmbed(
    		@JsonProperty(value = "title") String title, 
    		@JsonProperty(value = "description") String description, 
    		@JsonProperty(value = "args") String... args) {
    	this.setTitle(title);
    	this.setDescription(description);
    	this.setArguments(args);
    	
    }
    
    @JsonIgnore
    public AEmbed() {}
    
    @JsonIgnore
    public AEmbed setUrl(@NonNull String url) {
    	urlCheck(url);
    	this.url = url;
    	return this;
    }
    
    @JsonIgnore
    public AEmbed setTitle(@NonNull String title) {
    	lengthCheck(title, "title", MessageEmbed.TITLE_MAX_LENGTH);
    	this.title = title;
    	return this;
    }
    
    @JsonIgnore
    public AEmbed setDescription(@NonNull String description) {
    	lengthCheck(description, "description", MessageEmbed.TEXT_MAX_LENGTH);
    	this.description = description;
    	return this;
    }
    
    @JsonIgnore
    public AEmbed addDescription(@NonNull String description) {
    	lengthCheck(this.description + description, "description", MessageEmbed.TEXT_MAX_LENGTH);
    	this.description += description;
    	return this;
    }
    
    @JsonIgnore
    public AEmbed emptyDescription() {
    	this.description = "";
    	return this;
    }
    
    @JsonIgnore
    public AEmbed setColor(@NonNull Color color) {
    	this.color = color.getRGB();
    	return this;
    }
    
    @JsonIgnore
    public AEmbed setColor(int r, int g, int b) {
    	this.color = new Color(r, g, b).getRGB();
    	return this;
    }
    
    @JsonIgnore
    public AEmbed setColor(@NonNull String color) {
    	this.color = Color.decode(color).getRGB();
    	return this;
    }
        
    @JsonIgnore
    public AEmbed setThumbnail(@NonNull String url) {
    	urlCheck(url);
    	
    	this.thumbnail = new Thumbnail(url, null, 0, 0);
    	return this;
    }
    
    @JsonIgnore
    public AEmbed addField(@NonNull Field field) {    	
    	this.fields.add(field);
    	return this;
    }
    
    @JsonIgnore
    public AEmbed addField(@NonNull String name, @NonNull String value, boolean inline) {
    	lengthCheck(name, "name", MessageEmbed.TITLE_MAX_LENGTH);
    	lengthCheck(value, "value", MessageEmbed.VALUE_MAX_LENGTH);
    	return addField(new Field(name, value, inline));
    }
    
    @JsonIgnore
    public AEmbed addField(@NonNull String name, @NonNull String value) {
    	return addField(name, value, false);
    }
    
    @JsonIgnore
    public AEmbed addBlankField(boolean inline) {
    	return addField(new Field(EmbedBuilder.ZERO_WIDTH_SPACE, EmbedBuilder.ZERO_WIDTH_SPACE, inline));
    }
    
    @JsonIgnore
    public AEmbed clearFields() {
    	this.fields.clear();
    	return this;
    }
    
    @JsonIgnore
    public AEmbed setFooter(String text, String iconUrl) {
    	urlCheck(iconUrl);
    	lengthCheck(text, "text", MessageEmbed.TEXT_MAX_LENGTH);
    	lengthCheck(iconUrl, "iconUrl", MessageEmbed.URL_MAX_LENGTH);
    	this.footer = new Footer(text, iconUrl, null);
    	return this;
    }
    
    @JsonIgnore
    public AEmbed setFooter(@NonNull String text) {
    	return setFooter(text, null);
    }
    
    @JsonIgnore
    public AEmbed setAuthor(@NonNull String name, String url, String iconUrl) {
    	urlCheck(url);
    	urlCheck(iconUrl);
    	lengthCheck(url, "url", MessageEmbed.URL_MAX_LENGTH);
    	lengthCheck(iconUrl, "iconUrl", MessageEmbed.URL_MAX_LENGTH);
    	this.author = new AuthorInfo(name, url, iconUrl, null);
    	return this;
    }
    
    @JsonIgnore
    public AEmbed setAuthor(@NonNull String name, String url) {
    	lengthCheck(url, "url", MessageEmbed.URL_MAX_LENGTH);
    	return setAuthor(name, url, null);
    }
    
    @JsonIgnore
    public AEmbed setAuthor(@NonNull String name) {
    	return setAuthor(name, null, null);
    }
    
    @JsonIgnore
    public AEmbed setImage(@NonNull String url) {
    	urlCheck(url);
    	lengthCheck(url, "url", MessageEmbed.URL_MAX_LENGTH);
    	this.image = new ImageInfo(url, null, 0, 0);
    	return this;
    }
    
    @JsonIgnore
    public AEmbed setVideo(@NonNull String url) {
    	urlCheck(url);
    	lengthCheck(url, "url", MessageEmbed.URL_MAX_LENGTH);
    	this.video = new VideoInfo(url, 0, 0);
    	return this;
    }
    
    @JsonIgnore
    public AEmbed setArguments(@NonNull Map<String, String> arguments) {
    	this.arguments = arguments;
    	return this;
    }
    
    @JsonIgnore
    public AEmbed setArguments(String... args) {
    	return setArguments(parseArguments(args));
    }
    
    @JsonIgnore
    public AEmbed addArguments(@NonNull Map<String, String> arguments) {
    	this.arguments.putAll(arguments);
    	return this;
    }
    
    @JsonIgnore
    public AEmbed addArguments(String... args) {
    	return addArguments(parseArguments(args));
    }
    
    @JsonIgnore
    public AEmbed addArgument(String key, String value) {
    	this.arguments.put(key, value);
    	return this;
    }
    
    @JsonIgnore
    public AEmbed clearArguments() {
    	this.arguments.clear();
    	return this;
    }
    
    @JsonIgnore
    public AEmbed addReaction(String reaction) {    	
    	this.reactions.add(reaction);
    	return this;
    }
    
    @JsonIgnore
    public AEmbed addReactions(String... reactions) {
    	this.reactions.addAll(Arrays.asList(reactions));
    	return this;
    }
    
    @JsonIgnore
    public AEmbed setTimestamp(Duration duration) {
    	this.timestamp = duration.toMillis();
    	return this;
    }
    
    @JsonIgnore
    public AEmbed setTimestamp(Long timestamp) {
    	this.timestamp = timestamp;
    	return this;
    }
    
	public MessageEmbed build() {
		String title = this.title;
		String description = this.description;
		Footer footer = this.footer;
		List<Field> flds = new LinkedList<>();
		
		if (arguments != null && !arguments.isEmpty()) {
			
			for (Entry<String, String> entry : arguments.entrySet()) {
				
				String key = entry.getKey();
				
				if (!key.endsWith("}"))
					key += "}";
				if (!key.startsWith("{"))
					key = "{" + key;
				
				
				if (title != null)
					title = title.replace(key, entry.getValue());
				
				if (description != null)
					description = description.replace(key, entry.getValue());
				
				if (footer != null)
					footer = new Footer(footer.getText().replace(key, entry.getValue()), footer.getIconUrl(), null);
				
			}
			
			if (fields != null && !fields.isEmpty()) {
				
				for (Field field : this.fields) {
					
					String
						name = field.getName(),
						value = field.getValue();
					
					for (Entry<String, String> entry : arguments.entrySet()) {
						
						String key = entry.getKey();
						
						if (!key.endsWith("}"))
							key += "}";
						if (!key.startsWith("{"))
							key = "{" + key;
						
						name = name.replace(key, entry.getValue());
						value = value.replace(key, entry.getValue());
					}
					
					flds.add(new Field(name, value, field.isInline()));
				}
			}
			
		}
		
		return EntityBuilder.createMessageEmbed(url, title, description, EmbedType.RICH, timestamp != null ? Instant.now().plus(timestamp, ChronoUnit.MILLIS).atOffset(ZoneOffset.UTC) : null,
                color, thumbnail, null, author, video, footer, image, flds.isEmpty() ? this.fields : new LinkedList<>(flds));
	}
	
	public Message send(User user) {
		return send(user.openPrivateChannel().complete());
	}
	
	public Message send(MessageChannel channel) {
		return send(channel, false);
	}
	
	public Message send(User user, boolean react) {
		return send(user.openPrivateChannel().complete(), react);
	}
	
	public Message send(MessageChannel channel, boolean react) {
		return send(channel, reactions);
	}
	
	public Message send(User user, List<String> reactions) {
		return send(user.openPrivateChannel().complete(), reactions);
	}
	
	public Message send(MessageChannel channel, List<String> reactions) {
		
		MessageAction action = null;
		
		if (text == null)
			action = channel.sendMessage(build());
		else
			action = channel.sendMessage(text).embed(build());
		
		Message message = null;
		try {
			message = action.complete();
		} catch (Exception e) {
		}
		
		if (message != null && reactions != null)
			for (String reaction : reactions)
				message.addReaction(reaction).complete();
		
		return message;
	}
	
	public AEmbed clone() {
		try {
			return (AEmbed) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	Map<String, String> parseArguments(String... args) {
		if (args == null)
			return new HashMap<>();
		
		if (args.length % 2 != 0)
    		throw new IllegalArgumentException("args has to be even.");
    	
		Map<String, String> arguments = new HashMap<>();
    	for (int i = 0; i < args.length; i+=2)
    		arguments.put(args[i], args[i+1]);
    	
    	return arguments;
	}
	
	void urlCheck(String url) {
        if (url != null) {
            lengthCheck(url, "url", MessageEmbed.URL_MAX_LENGTH);
            Checks.check(EmbedBuilder.URL_PATTERN.matcher(url).matches(), "url must be a valid http(s) or attachment url.");
        }
    }
	
	void lengthCheck(String string, String name, int maxLength) {
		
		if (string == null || string.isEmpty())
			return;
		
		if (string.length() > maxLength)
			throw new IllegalArgumentException(name + " cannot be longer than " + maxLength + " characters!");
	}

}
