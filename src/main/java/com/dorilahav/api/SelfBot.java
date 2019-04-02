package com.dorilahav.api;

import lombok.NonNull;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.OnlineStatus;

public class SelfBot extends Bot {
	
	public SelfBot(@NonNull String token) {
		super(token, AccountType.CLIENT, OnlineStatus.ONLINE, null);
	}
	
}
