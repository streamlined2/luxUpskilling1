package org.training.upskilling.onlineshop.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.training.upskilling.onlineshop.dao.DataAccessException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JdbcConnectionFactory {

	private static final String URL = "url";

	private final Properties props;

	public Connection getConnection() {
		try {
			String url = props.getProperty(URL);
			return DriverManager.getConnection(url, props);
		} catch (SQLException e) {
			log.error("can't connect to database");
			throw new DataAccessException("can't connect to database", e);
		}
	}

}
