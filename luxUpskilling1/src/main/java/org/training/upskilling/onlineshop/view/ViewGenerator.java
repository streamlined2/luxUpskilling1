package org.training.upskilling.onlineshop.view;

import java.io.IOException;
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

	public void writeView(String templateFilename, Map<String, Object> parameters, Writer destination) {
		try {
			cfg.getTemplate(templateFilename).process(parameters, destination);
		} catch (IOException | TemplateException e) {
			log.error(e.getMessage());
			throw new ViewException(e);
		}
	}

}
