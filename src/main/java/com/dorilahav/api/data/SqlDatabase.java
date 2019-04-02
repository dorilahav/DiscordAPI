package com.dorilahav.api.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

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

	public SqlDatabase(Map<String, String> information) {
		this(information.get("hostname"), information.get("port"), information.get("database"), information.get("username"), information.get("password"));
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
