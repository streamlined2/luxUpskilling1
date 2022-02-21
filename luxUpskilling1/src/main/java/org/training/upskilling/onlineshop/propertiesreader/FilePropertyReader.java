package org.training.upskilling.onlineshop.propertiesreader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

public class FilePropertyReader extends AbstractPropertyReader {
	
	private final Reader reader;
	
	public FilePropertyReader(String propsFileName) throws IOException {
		InputStream is = getClass().getResourceAsStream(propsFileName);		
		reader = new InputStreamReader(Objects.requireNonNull(is, String.format("properties file %s not found", propsFileName)));
		properties.load(reader);
	}
	
	@Override
	public void close() throws Exception {
		reader.close();
	}

}
