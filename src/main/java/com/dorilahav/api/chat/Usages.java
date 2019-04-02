package com.dorilahav.api.chat;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public class Usages {
	
	@Getter
	private static Map<String, Usage>
		 usages = new HashMap<>();
	
	private static String
		prefix = "-";
	
	private static AEmbed
		embed = new AEmbed().setDescription("**Unknown Usage! Try using:** {usage}").setColor(Color.RED);
	
	public static AEmbed getEmbed() {
		return embed;
	}
	
	public static void setEmbed(AEmbed aEmbed) {
		Usages.embed = aEmbed;
	}
	
	public static void setPrefix(String prefix) {
		Usages.prefix = prefix;
	}
	
	public static String getUsage(String label) {
		return Chat.processString(usages.get(label.toLowerCase()).getMainUsage(), "{label}", label.toLowerCase(), "{prefix}", prefix, "{cmd}", prefix + label.toLowerCase());
	}
	
	public static String getUsage(String label, String type) {
		return Chat.processString(usages.get(label.toLowerCase()).getUsages().get(type), "{label}", label.toLowerCase(), "{prefix}", prefix, "{cmd}", prefix + label.toLowerCase());
	}
	
	public static AEmbed get(String label) {
		return embed.setArguments("{usage}", getUsage(label));
	}
	
	public static AEmbed get(String label,String type) {
		return embed.addArgument("{usage}", getUsage(label, type));
	}
	
	public static void set(String label, String type, String usage) {
		
		Usage use = null;
		
		if (!usages.containsKey(label.toLowerCase()))
			use = new Usage();
		else 
			use = usages.get(label.toLowerCase());
		
		use.getUsages().put(type, usage);
		usages.put(label.toLowerCase(), use);
		
	}
	
	public static void set(String label, String usage) {
		
		if (!usages.containsKey(label.toLowerCase()))
			usages.put(label.toLowerCase(), new Usage(usage));
		else {
			Usage use = usages.get(label.toLowerCase());
			use.setMainUsage(usage);
			usages.put(label.toLowerCase(), use);
		}
		
	}
	
	public static void set(String[] labels, String usage) {
		for (String label : labels)
			set(label, usage);
	}
	
	public static void set(String[] labels, String type, String usage) {
		
		for (String label : labels)
			set(label, type, usage);
		
	}

}
