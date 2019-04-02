package com.dorilahav.api.data;

import java.util.Map;

import com.dorilahav.api.utils.MapBuilder;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import lombok.NonNull;

public class MongoDatabaseBuilder extends DatabaseBuilder<MongoDatabase> {
	
	@NonNull
	private final String
			hostname;
	
	private final int
			port;
	
	private final String
			username,
			password;
	
	@NonNull
	private final String
			database;
	
	public MongoDatabaseBuilder(Map<String, Object> details) {
		super(details);
		this.hostname = (@NonNull String) details.get("hostname");
		this.port = (int) details.get("port");
		this.username = (String) details.get("username");
		this.password = (String) details.get("password");
		this.database = (@NonNull String) details.get("database");
	}
	
	@SuppressWarnings("resource")
	public MongoDatabase build() {
		return new MongoClient(new MongoClientURI("mongodb://" + (username != null && !username.isEmpty() && password != null && !password.isEmpty() ? username + ":" + password + "@" : "") + hostname + ":" + port + "/" + database)).getDatabase(database);
	}
	
	public static final Map<String, Object> constructDetails(String hostname, int port, String username, String password, String database) {
		return new MapBuilder<String, Object>().put("hostname", hostname).put("port", port).put("username", username).put("password", password).put("database", database).build();
	}
	
}
