package com.dorilahav.api.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.gson.JsonObject;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
public class SqlDatabase {
	
	@Getter
	@Delegate
	private Connection
		connection;

	private final String
		ip,
		port,
		dbName,
		username,
		password;

	/*public Database(String ip, String port, String dbName, String username, String password) {
		this.ip = ip;
		this.port = port;
		this.dbName = dbName;
		this.username = username;
		this.password = password;
	} */
	
	public SqlDatabase(JsonObject information) {
		this(information.get("hostname").getAsString(), information.get("port").getAsString(), information.get("database").getAsString(), information.get("username").getAsString(), information.get("password").getAsString());
	}
	
	public void connect() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			connection = null;
			return;
		}
		connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + dbName + "?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", username, password);
	}
	
	public void disconnect() throws SQLException {
		if (connection != null && !connection.isClosed())
			connection.close();
	}
	
}
