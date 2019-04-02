package com.dorilahav.api.commands;

import java.lang.reflect.Method;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.dorilahav.api.chat.Usages;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;

public class CommandHandler {
	
	private Map<String, Entry<CommandExecutor, CommandType>>
			commands = new HashMap<>();
	
		
	public void registerCommand(Object object) {
		Class<?> clazz = object.getClass();

		for (Method method : Arrays.asList(clazz.getDeclaredMethods()).stream().filter(method -> method.isAnnotationPresent(Command.class)).collect(Collectors.toSet())) {
			
			Command command = method.getDeclaredAnnotation(Command.class);
			CommandExecutor executor = new CommandExecutor(method, object, command.processFlags());
			
			Entry<CommandExecutor, CommandType> cmd = new SimpleEntry<>(executor, command.type());
			commands.put(command.label().toLowerCase(), cmd);
			
			if (!command.usage().isEmpty())
				Usages.set(command.label(), command.usage());
			
			for (String alias : command.aliases()) {
				commands.put(alias.toLowerCase(), cmd);
				if (!command.usage().isEmpty())
					Usages.set(alias, command.usage());
			}
			
		}
	}
	
	public int getCommandsAmount() {
		return commands.size();
	}

	public boolean isCommand(Message message, String prefix) {
		return isCommand(getLabel(message, prefix));
	}
	
	public boolean isCommand(String label) {
		return commands.containsKey(label);
	}

	public String getLabel(Message message, String prefix) {

		String content = message.getContentRaw();

		if (!content.toLowerCase().startsWith(prefix.toLowerCase()))
			return null;

		return content.split(" ")[0].toLowerCase().replaceFirst("\\" + prefix.toLowerCase(), "");
	}


	public boolean handle(Message message, String prefix) {

		String content = message.getContentRaw();
		String label = getLabel(message, prefix);
		
		if (!isCommand(label))
			return false;
		
		Entry<CommandExecutor, CommandType> cmd = commands.get(label);
		boolean execute = false;
		
		switch (cmd.getValue()) {
		
			case PRIVATE:
				if (message.isFromType(ChannelType.PRIVATE))
					execute = true;
				break;
			
			case PUBLIC:
				if (message.isFromType(ChannelType.TEXT))
					execute = true;
				break;
			
			case BOTH:
				execute = true;
				break;
			
			default:
				break;
		
		}
		
		if (!execute)
			return false;
		
		content = content.replaceFirst("(?i)\\" + prefix + Pattern.quote(label), "");

		if (content.startsWith(" "))
			content = content.replaceFirst(" ", "");
		
		String[] args = content.split(" ");
		
		if (content.isEmpty() || args == null)
			args = new String[0];
		
		return cmd.getKey().run(message, prefix, label, args);
	}

}
