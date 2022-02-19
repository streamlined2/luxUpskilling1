package org.training.upskilling.onlineshop.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.training.upskilling.onlineshop.dao.DataAccessException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JdbcConnectionFactory {

	private final String url;
	private final String user;
	private final String password;

	public Connection getConnection() {
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			log.error("can't connect to database");
			throw new DataAccessException("can't connect to database", e);
		}
	}

}
