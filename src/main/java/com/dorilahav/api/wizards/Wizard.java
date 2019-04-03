package com.dorilahav.api.wizards;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.dorilahav.api.data.Identifiable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.GenericMessageEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;

@JsonTypeInfo(use = Id.CLASS)
@RequiredArgsConstructor(onConstructor = @_({ @ConstructorProperties(value = { "user", "channel", "pointer" }) }))
public class Wizard implements Identifiable {
	
	private final String
			id = UUID.randomUUID().toString();
	
	@Getter @NonNull
	private User
			user;
	
	@Getter @NonNull
	private MessageChannel
			channel;
	
	@JsonIgnore
	private final WizardPointer<? extends WizardEndRunnable>
			pointer;
	
	@Getter
	private List<WizardStep<?, ?>>
			steps = new ArrayList<>();
	
	@Override
	public String getId() {
		return id;
	}
	
	public void addStep(@NonNull WizardStep<?, ?> step) {
		
		if (getStep(step.getName()) != null)
			throw new IllegalArgumentException("A step with the name \"" + step.getName() + "\" already exists!");
		
		this.steps.add(step);
	}
	
	public void start() {
		if (steps.isEmpty())
			throw new IllegalStateException("No steps found.");
		
		WizardStep<?, ?> step = steps.get(0);
		step.message(channel);
	}
	
	public boolean handle(@NonNull GenericMessageEvent e) {
		
		WizardStep<?, ?> step = next();
		
		if (step == null) {
			pointer.get().run(this);
			return false;
		}
		
		if (!step.getEventClass().isInstance(e))
			return false;
		
		if (e instanceof MessageReactionAddEvent && !step.getMessage().getId().equals(e.getMessageId()))
			return false;
		
		step.handle(e);
		
		if (step.getValue() == null)
			return false;
		
		step = next();
		
		if (step == null) {
			pointer.get().run(this);
			return false;
		}
		
		step.message(e.getChannel());
		
		return true;
	}
	
	public WizardStep<?, ?> getStep(@NonNull String name) {
		return this.steps.stream().filter(step -> step.getName().equals(name)).findFirst().orElse(null);
	}
	
	public WizardStep<?, ?> next() {
		return steps.stream().filter(s -> s.getValue() == null).findFirst().orElse(null);
	}
	
	public boolean check(@NonNull User user, @NonNull MessageChannel channel) {
		return this.user.equals(user) && this.channel.equals(channel);
	}

}
