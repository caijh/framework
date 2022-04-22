package com.github.caijh.framework.microservice;

import com.github.caijh.framework.core.constant.TraceIdConstants;
import com.github.caijh.framework.microservice.client.TraceRestTemplate;
import com.github.caijh.framework.microservice.trace.TraceIdThreadPoolTaskExecutorDecorator;
import feign.Logger;
import feign.RequestInterceptor;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableDiscoveryClient
@EnableFeignClients
public class FrameworkMicroserviceAutoConfiguration {

    /**
     * 通过TraceRestTemplate对restTemplate interceptors加入RestTemplateTraceInterceptor.
     *
     * @param restTemplate restTemplate
     * @return TraceRestTemplate
     */
    @ConditionalOnBean(RestTemplate.class)
    @Bean
    public TraceRestTemplate traceRestTemplate(RestTemplate restTemplate) {
        return new TraceRestTemplate(restTemplate);
    }

    /**
     * feign的请求头中加入X_TRACE_ID.
     *
     * @return feign请求拦截器
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            String traceId = MDC.get(TraceIdConstants.TRACE_ID);
            if (traceId != null) {
                template.header(TraceIdConstants.HTTP_HEADER_TRACE_ID, traceId);
            }
        };
    }

    @Bean
    @ConditionalOnBean(ThreadPoolTaskExecutor.class)
    public TraceIdThreadPoolTaskExecutorDecorator traceIdThreadPoolTaskExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        return new TraceIdThreadPoolTaskExecutorDecorator(threadPoolTaskExecutor);
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        //这里记录所有，根据实际情况选择合适的日志level
        return Logger.Level.FULL;
    }

}
