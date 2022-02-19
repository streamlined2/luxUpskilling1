package org.training.upskilling.onlineshop.propertiesreader;

import java.util.Properties;

public abstract class AbstractPropertyReader {

	protected final Properties properties;
	
	protected AbstractPropertyReader() {
		properties = new Properties();
	}

	public String getProperty(String name) {
		return properties.getProperty(name);
	}
	
	public Properties getProperties() {
		return new Properties(properties);
	}

}
