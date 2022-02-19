package org.training.upskilling.onlineshop.propertiesreader;

public class EnvironmentPropertyReader extends AbstractPropertyReader {
	
	public EnvironmentPropertyReader() {
		for(var entry:System.getenv().entrySet()) {
			properties.put(entry.getKey(), entry.getValue());
		}
		for(var entry:System.getProperties().entrySet()) {
			properties.put(entry.getKey(), entry.getValue());
		}
	}
		
}
