package org.training.upskilling.onlineshop.view;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ViewGenerator {

	private static final String TEMPLATE_FOLDER = "/templates";

	private final Configuration cfg;

	public ViewGenerator() {
		cfg = new Configuration(new Version(2, 3, 31));
		cfg.setClassForTemplateLoading(getClass(), TEMPLATE_FOLDER);
	}

	public String getView(String templateFilename, Map<String, Object> parameters) {
		try {
			Writer stream = new StringWriter();
			cfg.getTemplate(templateFilename).process(parameters, stream);
			return stream.toString();
		} catch (IOException | TemplateException e) {
			log.error(e.getMessage());
			throw new ViewException(e);
		}
	}

}
