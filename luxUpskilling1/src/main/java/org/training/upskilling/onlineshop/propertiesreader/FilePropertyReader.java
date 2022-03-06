package org.training.upskilling.onlineshop.propertiesreader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class FilePropertyReader extends AbstractPropertyReader {

	private final Reader reader;

	public FilePropertyReader(String propsFileName) throws IOException {
		InputStream is = getClass().getResourceAsStream(propsFileName);
		if (is == null) {
			throw new PropertyException(String.format("properties file %s not found", propsFileName));
		}
		reader = new InputStreamReader(is);
		properties.load(reader);
	}

	@Override
	public void close() throws Exception {
		reader.close();
	}

}
