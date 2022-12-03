package com.github.caijh.framework.doc;

import com.github.caijh.framework.doc.autoconfigure.properties.SmartDocProperties;
import com.github.caijh.framework.doc.initializer.SmartDocInitializer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration(after = WebMvcAutoConfiguration.class)
@EnableConfigurationProperties(SmartDocProperties.class)
public class FrameworkDocSmartDocAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = SmartDocProperties.PREFIX, value = "enabled")
    public SmartDocInitializer docInitializer() {
        return new SmartDocInitializer();
    }

}
