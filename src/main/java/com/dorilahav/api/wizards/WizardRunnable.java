package com.dorilahav.api.wizards;

import net.dv8tion.jda.core.events.message.GenericMessageEvent;

@FunctionalInterface
public interface WizardRunnable<E extends GenericMessageEvent, V> {
	
	V run(E event);

}
