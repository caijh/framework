package com.github.caijh.framework.core.processor;

import java.util.Properties;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;

public class ConfigFileEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    public static final int DEFAULT_ORDER = ConfigFileApplicationListener.DEFAULT_ORDER - 1;
    private static final String CONFIG_FILE_LOCATION = "META-INF/application.yml";
    /**
     * defaultProperties keep same value of {@code ConfigFileApplicationListener.DEFAULT_PROPERTIES}.
     */
    private static final String DEFAULT_PROPERTIES = "defaultProperties";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        MutablePropertySources propertySources = environment.getPropertySources();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();

        ClassPathResource classPathResource = new ClassPathResource(getLocation());
        yaml.setResources(classPathResource);
        Properties properties = yaml.getObject();
        if (properties != null) {
            PropertiesPropertySource propertySource = new PropertiesPropertySource(DEFAULT_PROPERTIES, properties);
            if (propertySources.contains(DEFAULT_PROPERTIES)) {
                propertySources.addAfter(DEFAULT_PROPERTIES, propertySource);
            } else {
                propertySources.addLast(propertySource);
            }
        }
    }

    @Override
    public int getOrder() {
        return DEFAULT_ORDER;
    }

    public String getLocation() {
        return CONFIG_FILE_LOCATION;
    }

}
