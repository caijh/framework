package com.github.caijh.framework.microservice.client;

import com.github.caijh.framework.microservice.interceptor.RestTemplateTraceInterceptor;
import org.springframework.web.client.RestTemplate;

public class TraceRestTemplate {

    private final RestTemplate restTemplate;

    public TraceRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.restTemplate.getInterceptors().add(new RestTemplateTraceInterceptor()); // 加入traceId
    }

    public RestTemplate getRestTemplate() {
        return this.restTemplate;
    }

}
