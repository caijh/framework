package com.github.caijh.framework.microservice.trace;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.caijh.framework.microservice.constant.Constants;
import com.github.caijh.framework.microservice.util.TraceLogUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class TraceIdInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        MDC.put(Constants.TRACE_ID, TraceLogUtils.getTraceId(request));
        return true;
    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request,
                                @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
        MDC.clear();
    }

}
