package com.dorilahav.api.wizards;

import java.beans.ConstructorProperties;

import com.dorilahav.api.chat.AEmbed;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.GenericMessageEvent;

public class WizardStep<E extends GenericMessageEvent, V> {
	
	@Getter
	private final String
			name;
	
	private final AEmbed
			embed;
	
	@Getter
	private final Class<E>
			eventClass;
	
	@Getter
	private final Class<V>
			valueClass;
	
	@Getter(value = AccessLevel.PROTECTED)
	private final WizardPointer<WizardRunnable<E, V>>
			pointer;
	
	@Getter @Setter
	private Message
			message;
	
	@Getter @Setter
	private V
			value;

	@ConstructorProperties(value = { "name", "embed", "eventClass", "valueClass", "pointer" })
	public WizardStep(String name, AEmbed embed, Class<E> eventClass, Class<V> valueClass, WizardPointer<WizardRunnable<E, V>> pointer) {
		this.name = name;
		this.embed = embed;
		this.eventClass = eventClass;
		this.valueClass = valueClass;
		this.pointer = pointer;
	}
	
	protected void message(MessageChannel channel) {
		message = embed.send(channel);
	}
	
	@SuppressWarnings("unchecked")
	protected void handle(GenericMessageEvent e) {
		value = pointer.get().run((E) e);
	}
	
}
