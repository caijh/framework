package com.github.caijh.framework.microservice;

import com.github.caijh.framework.microservice.client.TraceRestTemplate;
import com.github.caijh.framework.microservice.constant.Constants;
import com.github.caijh.framework.microservice.interceptor.TraceIdInterceptor;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import feign.Logger;
import feign.RequestInterceptor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.HystrixCircuitBreakerFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
public class FrameworkMicroserviceAutoConfiguration implements WebMvcConfigurer {

    @ConditionalOnBean(RestTemplate.class)
    @Bean
    public TraceRestTemplate traceRestTemplate(RestTemplate restTemplate) {
        return new TraceRestTemplate(restTemplate);
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            String traceId = MDC.get(Constants.TRACE_ID);
            if (traceId != null) {
                template.header(Constants.HTTP_HEADER_TRACE_ID, traceId);
            }
        };
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        //这里记录所有，根据实际情况选择合适的日志level
        return Logger.Level.FULL;
    }

    @Bean
    public Customizer<HystrixCircuitBreakerFactory> defaultConfig() {
        return factory -> factory.configureDefault(id -> HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey(id))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                                .withExecutionTimeoutInMilliseconds(5000)
                                                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                )

        );
    }

    @Override
    public void addInterceptors(@NotNull InterceptorRegistry registry) {
        registry.addInterceptor(new TraceIdInterceptor());
    }

}
