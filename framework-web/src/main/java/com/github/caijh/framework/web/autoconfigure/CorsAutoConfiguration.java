package com.github.caijh.framework.web.autoconfigure;

import javax.inject.Inject;

import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Cors配置
 */
@Configuration
public class CorsAutoConfiguration {

    @Inject
    private CorsEndpointProperties properties;

    private CorsConfiguration corsConfiguration() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        this.properties.getAllowedMethods().forEach(corsConfiguration::addAllowedOrigin); // 添加允许跨域的请求origin
        this.properties.getAllowedHeaders().forEach(corsConfiguration::addAllowedHeader); // 添加允许跨域的请求header
        this.properties.getAllowedMethods().forEach(corsConfiguration::addAllowedMethod); // 添加允许跨域的请求method
        corsConfiguration.setAllowCredentials(this.properties.getAllowCredentials()); // 是否发送 Cookie
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", this.corsConfiguration());
        return new CorsFilter(source);
    }

}
