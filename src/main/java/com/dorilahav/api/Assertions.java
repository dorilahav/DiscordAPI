package com.dorilahav.api;

import java.util.Collection;
import java.util.Map;

import com.dorilahav.api.chat.AEmbed;
import com.dorilahav.api.exception.EmbedException;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

public class Assertions {
	
	public static void assertHasPermission(Member member, TextChannel channel, Permission... permission) {
		
	}
	
	public static void assertNotEquals(Object arg0, Object arg1, AEmbed message) {
		
	}
	
	public static void assertEquals(Object arg0, Object arg1, AEmbed message) {
		assertTrue(arg0.equals(arg1), message);
		
	}
	
	public static void assertNotNull(Object value, AEmbed message) {
		assertTrue(value == null, message);
	}
	
	
	public static void assertNotEmpty(Collection<?> collection, AEmbed message) {
		
	}
	
	public static void assertNotEmpty(String string, AEmbed message) {
		
	}
	
	public static void assertNotEmpty(Map<?, ?> map, AEmbed message) {
		
	}
	
	public static <T> void assertNotEmpty(T[] array, AEmbed message) {
		
	}

	public static void assertTrue(boolean value, AEmbed message) {
		if (!value)
			throw EmbedException.of(message);
		
	}
	
}
