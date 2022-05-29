package com.github.caijh.framework.web.interceptor;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.caijh.framework.core.constant.TraceConstants;
import com.github.caijh.framework.web.util.TraceLogUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

public class TraceIdInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) {
        MDC.put(TraceConstants.TRACE_ID, TraceLogUtils.getTraceId(request));
        return true;
    }

    @Override
    public void afterCompletion(@Nonnull HttpServletRequest request,
                                @Nonnull HttpServletResponse response,
                                @Nonnull Object handler,
                                Exception ex) {
        MDC.clear();
    }

}
