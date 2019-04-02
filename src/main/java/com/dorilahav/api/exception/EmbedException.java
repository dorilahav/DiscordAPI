package com.dorilahav.api.exception;

import java.util.Arrays;
import java.util.List;

import com.dorilahav.api.chat.AEmbed;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmbedException extends RuntimeException {

	public static EmbedException of(AEmbed embed) {
		return new EmbedException(Arrays.asList(embed));
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5028581548945806820L;
	
	@NonNull @Getter
	List<AEmbed>
			response;
	
	public EmbedException(AEmbed... embeds) {
		this.response = Arrays.asList(embeds);
	}
	
	public EmbedException(AEmbed embed) {
		this.response = Arrays.asList(embed);
	}
	
}
