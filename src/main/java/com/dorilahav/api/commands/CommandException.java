package com.dorilahav.api.commands;

import java.util.Arrays;
import java.util.List;

import com.dorilahav.api.chat.AEmbed;
import com.dorilahav.api.chat.Usages;
import com.dorilahav.api.exception.EmbedException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public abstract class CommandException extends EmbedException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1209814625856814911L;

	public CommandException(List<AEmbed> response) {
		super(response);
		
	}
	
	public CommandException() {
		super(Arrays.asList());
		
	}
	
	public abstract void handle(CommandContext context);
	
	@NoArgsConstructor @AllArgsConstructor
	public static class UsageException extends CommandException {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 4380497133730361720L;
		
		@Getter
		private String
				type;
		
		@Override
		public void handle(CommandContext context) {
			
			if (type == null) {
				Usages.get(context.getLabel()).send(context.getMessage().getChannel());
				return;
			}
			
			Usages.get(context.getLabel(), type).send(context.getMessage().getChannel());
		}
				
	}
}
