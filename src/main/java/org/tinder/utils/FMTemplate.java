package org.tinder.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FMTemplate {
    private static Configuration configuration;

    private static void config() throws IOException {
        configuration = new Configuration(Configuration.VERSION_2_3_32);
        configuration.setDefaultEncoding(String.valueOf(StandardCharsets.UTF_8));
        configuration.setDirectoryForTemplateLoading(new File(ResourcesOps.dir(Constants.TEMPLATE_PAGES_DIR)));
    }

    public static Template getTemplate(String name) throws IOException {
        if (configuration == null) {
            config();
            return getTemplate(name);
        }

        return configuration.getTemplate(name);
    }

}
