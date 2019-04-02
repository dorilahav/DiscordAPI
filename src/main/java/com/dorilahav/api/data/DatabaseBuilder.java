package com.dorilahav.api.data;

import java.util.Map;

public abstract class DatabaseBuilder<T> {
	
	protected Map<String, Object>
			details;
	
	public abstract T build();
	
	public DatabaseBuilder(Map<String, Object> details) {
		this.details = details;
	}
	
}
