package org.training.upskilling.onlineshop.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.training.upskilling.onlineshop.dao.DataAccessException;
import org.training.upskilling.onlineshop.propertiesreader.AbstractPropertyReader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JdbcConnectionFactory implements AutoCloseable {

	private static final String URL = "url";
	private static final String USER = "user";
	private static final String PASSWORD = "password";
	private static final String MIN_IDLE = "minIdle";
	private static final String MAX_IDLE = "maxIdle";
	private static final String MAX_OPEN_PREPARED_STATEMENTS = "maxOpenPreparedStatements";

	private final BasicDataSource dataSource;
	
	public static JdbcConnectionFactory getConnectionFactory(AbstractPropertyReader reader) {
		return new JdbcConnectionFactory(
				reader.getProperty(URL), 
				reader.getProperty(USER), 
				reader.getProperty(PASSWORD))
				.setMinIdle(reader.getIntegerProperty(MIN_IDLE, 5))
				.setMaxIdle(reader.getIntegerProperty(MAX_IDLE, 10))
				.setMaxOpenPreparedStatements(reader.getIntegerProperty(MAX_OPEN_PREPARED_STATEMENTS, 100));
	}

	public JdbcConnectionFactory(String url, String user, String password) {
		dataSource = new BasicDataSource();
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(password);
	}
	
	public JdbcConnectionFactory setMinIdle(int minIdle) {
		dataSource.setMinIdle(minIdle);
		return this;
	}

	public JdbcConnectionFactory setMaxIdle(int maxIdle) {
		dataSource.setMaxIdle(maxIdle);
		return this;
	}

	public JdbcConnectionFactory setMaxOpenPreparedStatements(int maxOpenPreparedStatements) {
		dataSource.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
		return this;
	}

	public Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			log.error("can't obtain connection");
			throw new DataAccessException("can't obtain connection", e);
		}
	}

	@Override
	public void close() throws Exception {
		dataSource.close();
	}
	
}
