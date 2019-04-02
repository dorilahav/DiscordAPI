package com.dorilahav.api.commands;

import java.lang.reflect.Method;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.core.entities.Message;

@AllArgsConstructor
public class CommandContext {

	@Getter
	final Method 
			method;
	
	@Getter
	final Object
			instance;
	
	@Getter
	final boolean
			processFlags;
	
	@Getter @Setter
	boolean
			delete;
	
	@Getter
	final Message
			message;
	
	@Getter
	final String
			prefix,
			label,
			args[];
	
	@Getter
	final Flags
			flags;
}
