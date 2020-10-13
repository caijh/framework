package com.github.caijh.framework.web.autoconfigure;

import com.github.caijh.framework.web.filter.ThreadLocalStoreFilter;
import com.github.caijh.framework.web.init.DocInitializer;
import com.github.caijh.framework.web.interceptor.ThreadLocalStoreInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableConfigurationProperties(ServerProperties.class)
public class FrameworkWebAutoConfiguration implements WebMvcConfigurer {

    /**
     * use threadLocalStoreFilter to clean thread local store.
     *
     * @return {@code FilterRegistrationBean<ThreadLocalStoreFilter>}
     * @see ThreadLocalStoreFilter
     */
    @Bean
    public FilterRegistrationBean<ThreadLocalStoreFilter> threadLocalStoreFilterRegistration() {
        FilterRegistrationBean<ThreadLocalStoreFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new ThreadLocalStoreFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(Integer.MIN_VALUE);
        return registration;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ThreadLocalStoreInterceptor());
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public DocInitializer docInitializer() {
        return new DocInitializer();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties(prefix = "server")
    public ServerProperties serverProperties() {
        return new ServerProperties();
    }

}
