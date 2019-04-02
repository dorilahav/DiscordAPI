package com.dorilahav.api;

import java.util.Collection;
import java.util.Map.Entry;

import javax.security.auth.login.LoginException;

import com.dorilahav.api.commands.CommandHandler;
import com.dorilahav.api.listeners.Listener;
import com.google.common.collect.MultimapBuilder.SetMultimapBuilder;
import com.google.common.collect.SetMultimap;

import lombok.Getter;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDA.Status;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;

public class Bot {
	
	private JDABuilder
			jdaBuilder;
	
	@Getter
	private JDA
			jda;
	
	@Getter
	private CommandHandler
			commandHandler = new CommandHandler();
	
	private SetMultimap<Class<? extends Event>, Listener<? extends Event>>
			events = SetMultimapBuilder.hashKeys().hashSetValues().build();
	
	public Bot(String token) {
		this(token, OnlineStatus.ONLINE);
	}
	
	
	public Bot(String token, OnlineStatus status) {
		this(token, AccountType.BOT, status, null);
	}
	
	public Bot(String token, Game game) {
		this(token, AccountType.BOT, OnlineStatus.ONLINE, game);
	}
	
	
	protected Bot(String token, AccountType type, OnlineStatus status, Game game) {
		
		jdaBuilder = new JDABuilder(type)
				.setToken(token)
				.setStatus(status);
		
		if (game != null)
			jdaBuilder.setGame(game);
		
		jdaBuilder.addEventListener((EventListener) (event) -> {
			for (Entry<Class<? extends Event>, Collection<Listener<? extends Event>>> entry : 
				events.asMap().entrySet()) {
				
				if (!entry.getKey().isInstance(event)) continue;
				
				for (Listener<? extends Event> listener : entry.getValue())
					listener.run(event);
			}

		});
		
	}
	
	
	public void start() throws LoginException {
		
		jda = jdaBuilder.build();
		
		while (!jda.getStatus().isInit() || jda.getStatus().ordinal() < Status.CONNECTED.ordinal()) {
			
            if (jda.getStatus() == Status.SHUTDOWN)
                throw new IllegalStateException("Bot is shutdown.");
            
            try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
            
        }
			
	}
	
	
	public void stop() {
		
		onlineCheck();
		
		jda.shutdownNow();
	}
	
	public Bot useDefaultCommandHandler(String prefix) {
		addListener(new Listener<MessageReceivedEvent>() {

			@Override
			public void run(MessageReceivedEvent event) {
				if (getCommandHandler().handle(event.getMessage(), prefix))
					return;
			}
			
		});
		
		return this;
	}
	
	public void addCommand(Object object) {
		commandHandler.registerCommand(object);
		
	}
	
	/**
	 * Add a listener to this bot
	 * @param listener the listener to add
	 */
	public <T extends Event> void addListener(Listener<T> listener) {
		addListener(listener.getEventType(), listener);
		
	}
	
	@Deprecated
	public <T extends Event> void addListener(Class<T> clazz, Listener<T> listener) {
		events.put(clazz, listener);
		
	}
	
	/**
	 * @return The amount of listeners registered.
	 */
	public int countListeners() {
		return events.values().size();
	}
	
	/**
	 * @return The amount of listeners registered.
	 */
	@Deprecated
	public int getListenersAmount() {
		return countListeners();
	}
	
	/**
	 * Set Discord status for this bot. 
	 * @param game OnlineStatus of JDA
	 * @return this Bot instance
	 */
	public Bot setStatus(OnlineStatus status) {
		return setStatus(status, false);
	}
	
	/**
	 * Set Discord status for this bot.
	 * @param game OnlineStatus of JDA
	 * @param setBuilder should the game be updated in the builder
	 * @return this Bot instance
	 */
	public Bot setStatus(OnlineStatus status, boolean setBuilder) {
		if (setBuilder)
			jdaBuilder.setStatus(status);
		else
			onlineCheck();
		
		if (jda != null)
			jda.getPresence().setStatus(status);
		
		return this;
	}
	
	/**
	 * Set game for this bot. 
	 * @param game Game of JDA
	 * @return this Bot instance
	 */
	public Bot setGame(Game game) {
		return setGame(game, true);
	}
	
	/**
	 * Set game for this bot. 
	 * @param game Game of JDA
	 * @param setBuilder should the game be updated in the builder
	 * @return this Bot instance
	 */
	public Bot setGame(Game game, boolean setBuilder) {
		if (setBuilder)
			jdaBuilder.setGame(game);
		else
			onlineCheck();
		
		if (jda != null)
			jda.getPresence().setGame(game);
		
		return this;
	}
	
	/**
	 * Check whether or not this bot is online. 
	 * @return true if online, else false
	 */
	public boolean isOnline() {
		return jda != null && jda.getStatus() == Status.CONNECTED; 
	}
	
	
	private void onlineCheck() {
		if (!isOnline())
			throw new IllegalStateException("Bot is down.");
	}
	
	/**
	 * Opens a {@link PrivateChannel}. Returns null if an exception was thrown.
	 * @param user the user to open the {@link PrivateChannel} with.
	 * @return the opened {@link PrivateChannel}.
	 */
	public PrivateChannel getPrivateChannel(User user) {
		try {
			return user.openPrivateChannel().complete();
		} catch (Exception e) {
			return null;
		}
	}

}
