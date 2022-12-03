package com.github.caijh.framework.web;

import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.annotation.Nonnull;

import com.github.caijh.framework.web.autoconfigure.CorsAutoConfiguration;
import com.github.caijh.framework.web.autoconfigure.JacksonAutoConfiguration;
import com.github.caijh.framework.web.filter.ThreadLocalStoreFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

@AutoConfiguration
@ComponentScan
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableConfigurationProperties(ServerProperties.class)
@ImportAutoConfiguration(value = {CorsAutoConfiguration.class, JacksonAutoConfiguration.class})
public class FrameworkWebAutoConfiguration implements WebMvcConfigurer {

    @Bean
    public HttpMessageConverter<String> charsetConverter() {
        return new StringHttpMessageConverter(StandardCharsets.UTF_8);
    }

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
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        urlPathHelper.setDefaultEncoding(StandardCharsets.UTF_8.name());
        urlPathHelper.setUrlDecode(false);
        configurer.setUrlPathHelper(urlPathHelper);
    }

    @Override
    public void configureMessageConverters(@Nonnull List<HttpMessageConverter<?>> converters) {
        converters.add(charsetConverter());
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties(prefix = "server")
    public ServerProperties serverProperties() {
        return new ServerProperties();
    }

}
