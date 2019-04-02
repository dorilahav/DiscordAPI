package com.dorilahav.api.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.dorilahav.api.chat.AEmbed;
import com.dorilahav.api.exception.EmbedException;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class CommandExecutor {
	
	private Method
			method;
	
	private Object
			instance;
	
	private boolean
			processFlags;
	
	public CommandExecutor(Method method, Object instance, boolean processFlags) {
		if (!method.getReturnType().equals(Boolean.class) && !method.getReturnType().equals(boolean.class))
			throw new InvalidParameterException("Command must return a boolean!");
		
		this.method = method;
		this.instance = instance;
		this.processFlags = processFlags;
	}
	
	public boolean run(Message message, String prefix, String label, String[] args) {
		
		Flags flags = new Flags(new ArrayList<>());
		if (processFlags) {
			args = Arrays.stream(args).filter(arg -> {
				
				if (arg.startsWith("-")) {
					flags.add(arg.replaceFirst("-", "").toLowerCase());
					return true;
				}
				return false;
				
			}).toArray(String[]::new);
		}
		
		Map<Class<?>, Object> parameters = new HashMap<>();
		CommandContext context = createContext(message, prefix, label, args, flags);
		fillParameters(parameters, context);
		
		Class<?>[] classes = method.getParameterTypes();
		
		Object[] params = new Object[classes.length];

		for (int i = 0; i < classes.length; i++)
			params[i] = parameters.get(classes[i]);
		
		boolean delete = invoke(method, params, context);
		
		if (message.getChannelType().equals(ChannelType.TEXT) && delete)
			message.delete().queueAfter(100, TimeUnit.MILLISECONDS);
		
		return true;
	}
	
	CommandContext createContext(Message message, String prefix, String label, String args[], Flags flags) {
		return new CommandContext(method, label, processFlags, false, message, prefix, label, args, flags);
	}
	
	void fillParameters(Map<Class<?>, Object> parameters, CommandContext context) {
		parameters.put(String.class, context.getLabel());
		parameters.put(String[].class, context.getArgs());
		parameters.put(TextChannel.class, context.getMessage().getTextChannel());
		parameters.put(PrivateChannel.class, context.getMessage().getPrivateChannel());
		parameters.put(MessageChannel.class, context.getMessage().getChannel());
		parameters.put(Member.class, context.getMessage().getMember());
		parameters.put(User.class, context.getMessage().getAuthor());
		parameters.put(Guild.class, context.getMessage().getGuild());
		parameters.put(Message.class, context.getMessage());
		parameters.put(Prefix.class, new Prefix(context.getPrefix().toLowerCase()));
		parameters.put(Flags.class, context.getFlags());
		parameters.put(CommandContext.class, context);
	}
	
	boolean invoke(Method method, Object[] params, CommandContext context) {
		try {
			return (boolean) method.invoke(instance, params);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			if (e.getCause() instanceof CommandException) 
				((CommandException)e.getCause()).handle(context);
			else if (e.getCause() instanceof EmbedException) 
				for (AEmbed embed : ((EmbedException)e.getCause()).getResponse()) 
					embed.send(context.getMessage().getChannel());
			else
				e.printStackTrace();
			
			return false;
		}
	}
	
}
