package org.training.upskilling.onlineshop.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.PreDestroy;

import org.apache.commons.dbcp2.BasicDataSource;
import org.training.upskilling.onlineshop.dao.DataAccessException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JdbcConnectionFactory implements AutoCloseable {

	private final BasicDataSource dataSource;

	public JdbcConnectionFactory(String url, String user, String password, int minIdle, int maxIdle,
			int maxOpenPreparedStatements) {
		dataSource = new BasicDataSource();
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(password);
		dataSource.setMinIdle(minIdle);
		dataSource.setMaxIdle(maxIdle);
		dataSource.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
	}

	public Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			log.error("can't obtain connection");
			throw new DataAccessException("can't obtain connection", e);
		}
	}

	@Override @PreDestroy
	public void close() throws Exception {
		dataSource.close();
	}

}
