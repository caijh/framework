package com.github.caijh.framework.microservice;

import com.github.caijh.framework.microservice.client.TraceRestTemplate;
import com.github.caijh.framework.microservice.constant.Constants;
import com.github.caijh.framework.microservice.trace.TraceIdInterceptor;
import com.github.caijh.framework.microservice.trace.TraceIdThreadPoolTaskExecutorDecorator;
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
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@EnableHystrixDashboard
public class FrameworkMicroserviceAutoConfiguration implements WebMvcConfigurer {

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
            String traceId = MDC.get(Constants.TRACE_ID);
            if (traceId != null) {
                template.header(Constants.HTTP_HEADER_TRACE_ID, traceId);
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

    @Bean
    public Customizer<HystrixCircuitBreakerFactory> defaultConfig() {
        return factory -> factory.configureDefault(id -> HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey(id))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                                .withExecutionTimeoutEnabled(true)
                                                .withExecutionTimeoutInMilliseconds(5000)
                                                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                                                .withCircuitBreakerSleepWindowInMilliseconds(10000) // 把断路器的休眠窗时间设为10秒，默认为5秒
                )

        );
    }

    @Override
    public void addInterceptors(@NotNull InterceptorRegistry registry) {
        registry.addInterceptor(new TraceIdInterceptor());
    }

}
