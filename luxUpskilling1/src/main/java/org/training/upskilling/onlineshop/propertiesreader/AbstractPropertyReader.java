package org.training.upskilling.onlineshop.propertiesreader;

import java.util.Objects;
import java.util.Properties;

public abstract class AbstractPropertyReader {

	protected final Properties properties;

	protected AbstractPropertyReader() {
		properties = new Properties();
	}

	public String getProperty(String name) {
		return Objects.requireNonNull(properties.getProperty(name),
				String.format("value for required property %s not found", name));
	}

	public Properties getProperties() {
		return new Properties(properties);
	}

}
