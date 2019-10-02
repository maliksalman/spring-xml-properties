package com.smalik.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.web.context.support.StandardServletEnvironment;

import java.util.Properties;

public class MyEnvironment extends StandardServletEnvironment {

    private static Logger LOGGER = LoggerFactory.getLogger(MyEnvironment.class);

    @Override
    protected void customizePropertySources(MutablePropertySources propertySources) {

        super.customizePropertySources(propertySources);

        Properties props = new Properties();
        props.setProperty("message.a", "custom");
        props.setProperty("message.b", "custom");
        props.setProperty("message.c", "custom");
        props.setProperty("message.d", "custom");

        propertySources.addLast(new PropertiesPropertySource("custom", props));

        for (PropertySource ps: propertySources) {
            LOGGER.info("PropertySource: " + ps);
        }
    }
}
