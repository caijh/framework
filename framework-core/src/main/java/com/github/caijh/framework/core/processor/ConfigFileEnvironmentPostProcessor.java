package com.github.caijh.framework.core.processor;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

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

    private static final Map<ConfigurableEnvironment, AtomicInteger> INDEX_MAP = new ConcurrentHashMap<>();
    private static final Map<ConfigurableEnvironment, String> LAST_PROPERTY_SOURCE_NAME_MAP = new ConcurrentHashMap<>();

    private static synchronized AtomicInteger getIndex(ConfigurableEnvironment environment) {
        return INDEX_MAP.computeIfAbsent(environment, env -> new AtomicInteger(1));
    }

    private static synchronized void setLastPropertySourceName(ConfigurableEnvironment environment, String propertySourceName) {
        LAST_PROPERTY_SOURCE_NAME_MAP.put(environment, propertySourceName);
    }

    private static synchronized String getLastPropertySourceName(ConfigurableEnvironment environment) {
        return LAST_PROPERTY_SOURCE_NAME_MAP.computeIfAbsent(environment, env -> DEFAULT_PROPERTIES);
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Resource resource = getResource();

        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(resource);

        Properties properties = yaml.getObject();
        if (properties != null) {
            MutablePropertySources propertySources = environment.getPropertySources();
            PropertiesPropertySource propertySource;
            if (propertySources.contains(DEFAULT_PROPERTIES)) {
                String propertySourceName = DEFAULT_PROPERTIES + "-" + getIndex(environment).getAndIncrement();
                propertySource = new PropertiesPropertySource(propertySourceName, properties);
                propertySources.addAfter(getLastPropertySourceName(environment), propertySource);
                setLastPropertySourceName(environment, propertySourceName);
            } else {
                propertySource = new PropertiesPropertySource(DEFAULT_PROPERTIES, properties);
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
