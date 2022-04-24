package com.github.caijh.framework.microservice.trace;

import java.io.IOException;

import com.github.caijh.framework.core.constant.TraceConstants;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StringUtils;

public class RestTemplateTraceInterceptor implements ClientHttpRequestInterceptor {

    @NotNull
    @Override
    public ClientHttpResponse intercept(@NotNull HttpRequest request, @NotNull byte[] body, @NotNull ClientHttpRequestExecution execution) throws IOException {
        String traceId = MDC.get(TraceConstants.TRACE_ID);
        if (StringUtils.hasLength(traceId)) {
            request.getHeaders().add(TraceConstants.HTTP_HEADER_TRACE_ID, traceId);
        }
        return execution.execute(request, body);
    }

}
