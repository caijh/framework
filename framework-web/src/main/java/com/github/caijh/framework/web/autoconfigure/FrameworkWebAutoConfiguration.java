package com.github.caijh.framework.web.autoconfigure;

import com.github.caijh.framework.web.init.DocInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class FrameworkWebAutoConfiguration {


    @Bean
    public DocInitializer docInitializer() {
        return new DocInitializer();
    }

}
