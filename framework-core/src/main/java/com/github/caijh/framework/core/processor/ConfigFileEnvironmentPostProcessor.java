package com.github.caijh.framework.core.processor;

import java.io.FileNotFoundException;
import java.util.Properties;

import com.github.caijh.framework.core.exception.ConfigFileNotFoundException;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.ResourceUtils;

import static org.springframework.util.ResourceUtils.JAR_URL_PREFIX;
import static org.springframework.util.ResourceUtils.JAR_URL_SEPARATOR;

public abstract class ConfigFileEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    public static final int DEFAULT_ORDER = ConfigFileApplicationListener.DEFAULT_ORDER - 1;
    private static final String CONFIG_FILE_LOCATION = "META-INF/application.yml";
    /**
     * defaultProperties keep same value of {@code ConfigFileApplicationListener.DEFAULT_PROPERTIES}.
     */
    private static final String DEFAULT_PROPERTIES = "defaultProperties";

    private static final String PROPERTIES_PROPERTY_SOURCE_NAME = "starterProperties";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Resource resource = getResource();

        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(resource);

        Properties properties = yaml.getObject();
        if (properties != null) {
            MutablePropertySources propertySources = environment.getPropertySources();
            PropertiesPropertySource propertySource = new PropertiesPropertySource(DEFAULT_PROPERTIES, properties);
            if (propertySources.contains(DEFAULT_PROPERTIES)) {
                propertySource = new PropertiesPropertySource(PROPERTIES_PROPERTY_SOURCE_NAME, properties);
                propertySources.addAfter(DEFAULT_PROPERTIES, propertySource);
            } else {
                propertySources.addLast(propertySource);
            }
        }
    }

    public Resource getResource() {
        String url = getClass().getResource(getClass().getSimpleName() + ".class").toString();
        String location = getLocation();

        Resource resource;
        if (url.startsWith(JAR_URL_PREFIX)) {
            location = url.substring(0, url.lastIndexOf("!")) + JAR_URL_SEPARATOR + location;
            try {
                resource = new UrlResource(ResourceUtils.getURL(location));
            } catch (FileNotFoundException e) {
                throw new ConfigFileNotFoundException(location);
            }
        } else {
            resource = new ClassPathResource(location);
        }
        return resource;
    }

    @Override
    public int getOrder() {
        return DEFAULT_ORDER;
    }

    public String getLocation() {
        return CONFIG_FILE_LOCATION;
    }

}
