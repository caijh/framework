package com.github.caijh.framework.core.processor;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.github.caijh.framework.core.exception.ConfigFileNotFoundException;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.config.ConfigDataEnvironmentPostProcessor;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

public class ConfigFileEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    public static final int DEFAULT_ORDER = ConfigDataEnvironmentPostProcessor.ORDER + 1;
    private static final String CONFIG_FILE_LOCATION = "META-INF/application.yml";
    /**
     * defaultProperties keep same value of {@code ConfigFileApplicationListener.DEFAULT_PROPERTIES}.
     */
    private static final String DEFAULT_PROPERTIES = "defaultProperties";

    private static final Map<ConfigurableEnvironment, AtomicInteger> INDEX_MAP = new ConcurrentHashMap<>();
    private static final Map<ConfigurableEnvironment, String> LAST_PROPERTY_SOURCE_NAME_MAP = new ConcurrentHashMap<>();

    private static synchronized AtomicInteger getIndex(ConfigurableEnvironment environment) {
        return ConfigFileEnvironmentPostProcessor.INDEX_MAP.computeIfAbsent(environment, env -> new AtomicInteger(1));
    }

    private static synchronized void setLastPropertySourceName(ConfigurableEnvironment environment, String propertySourceName) {
        ConfigFileEnvironmentPostProcessor.LAST_PROPERTY_SOURCE_NAME_MAP.put(environment, propertySourceName);
    }

    private static synchronized String getLastPropertySourceName(ConfigurableEnvironment environment) {
        return ConfigFileEnvironmentPostProcessor.LAST_PROPERTY_SOURCE_NAME_MAP
                .computeIfAbsent(environment, env -> ConfigFileEnvironmentPostProcessor.DEFAULT_PROPERTIES);
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        try {
            Enumeration<URL> urls = this.getClass().getClassLoader().getResources(ConfigFileEnvironmentPostProcessor.CONFIG_FILE_LOCATION);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                Resource resource = new UrlResource(url);
                YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
                yaml.setResources(resource);
                Properties properties = yaml.getObject();
                if (properties != null) {
                    MutablePropertySources propertySources = environment.getPropertySources();
                    PropertiesPropertySource propertySource;
                    if (propertySources.contains(ConfigFileEnvironmentPostProcessor.DEFAULT_PROPERTIES)) {
                        String propertySourceName = ConfigFileEnvironmentPostProcessor.DEFAULT_PROPERTIES + "-" + ConfigFileEnvironmentPostProcessor
                                .getIndex(environment).getAndIncrement();
                        propertySource = new PropertiesPropertySource(propertySourceName, properties);
                        propertySources.addAfter(ConfigFileEnvironmentPostProcessor.getLastPropertySourceName(environment), propertySource);
                        ConfigFileEnvironmentPostProcessor.setLastPropertySourceName(environment, propertySourceName);
                    } else {
                        propertySource = new PropertiesPropertySource(ConfigFileEnvironmentPostProcessor.DEFAULT_PROPERTIES, properties);
                        propertySources.addLast(propertySource);
                    }
                }
            }
        } catch (IOException e) {
            throw new ConfigFileNotFoundException(e.getMessage());
        }
    }

    @Override
    public int getOrder() {
        return ConfigFileEnvironmentPostProcessor.DEFAULT_ORDER;
    }

}
