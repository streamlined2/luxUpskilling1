package org.training.upskilling.onlineshop;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

public class PropertyReader implements AutoCloseable {
	
	private final Properties properties;
	private final Reader reader;
	
	public PropertyReader(File file) throws IOException {
		reader = new FileReader(file);
		properties = new Properties();
		properties.load(reader);
	}
	
	public String getProperty(String name) {
		return properties.getProperty(name);
	}
	
	public Properties getProperties() {
		return properties;
	}

	@Override
	public void close() throws Exception {
		reader.close();
	}

}
