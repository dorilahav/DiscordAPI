package com.dorilahav.api.chat;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor @NoArgsConstructor
public class Usage {
	
	@Getter @Setter @NonNull
	private String
		mainUsage;
	
	@Getter
	private Map<String, String>
		usages = new HashMap<>();
		
}
