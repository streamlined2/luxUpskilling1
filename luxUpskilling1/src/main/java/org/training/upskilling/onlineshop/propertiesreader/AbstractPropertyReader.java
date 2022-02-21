package org.training.upskilling.onlineshop.propertiesreader;

import java.util.Objects;
import java.util.Properties;

public abstract class AbstractPropertyReader implements AutoCloseable {

	protected final Properties properties;

	protected AbstractPropertyReader() {
		properties = new Properties();
	}

	public String getProperty(String name) {
		return Objects.requireNonNull(properties.getProperty(name),
				String.format("value for required property %s not found", name));
	}

	public int getIntegerProperty(String name) {
		return Integer.parseInt(getProperty(name));
	}

	public int getIntegerProperty(String name, int defaultValue) {
		try {
			return getIntegerProperty(name);
		} catch (NullPointerException | NumberFormatException e) {
			return defaultValue;
		}
	}

	public Properties getProperties() {
		return new Properties(properties);
	}

	public void close() throws Exception {
	}

}
