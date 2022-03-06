package org.training.upskilling.onlineshop.propertiesreader;

import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractPropertyReader implements AutoCloseable {

	protected final Properties properties;

	protected AbstractPropertyReader() {
		properties = new Properties();
	}

	public String getProperty(String name) {
		String propertyValue = properties.getProperty(name);
		if (propertyValue == null) {
			throw new PropertyException(String.format("value for required property %s not found", name));
		}
		return propertyValue;
	}

	public int getIntegerProperty(String name) {
		try {
			return Integer.parseInt(getProperty(name));
		} catch (NumberFormatException e) {
			log.error(String.format("wrong numeric value for property %s", name));
			throw new PropertyException(String.format("wrong numeric value for property %s", name), e);
		}
	}

	public int getIntegerProperty(String name, int defaultValue) {
		try {
			return getIntegerProperty(name);
		} catch (PropertyException e) {
			return defaultValue;
		}
	}

	public void close() throws Exception {
	}

}
