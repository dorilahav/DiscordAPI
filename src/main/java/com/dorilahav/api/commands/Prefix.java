package com.dorilahav.api.commands;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
public class Prefix implements CharSequence {

	@JsonValue @Getter @Delegate
    final String
        prefix;
	
    public String get() {
        return prefix;
    }
    
}
