package com.github.caijh.framework.web.interceptor;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.caijh.framework.core.constant.TraceConstants;
import com.github.caijh.framework.core.util.LoggerUtils;
import com.github.caijh.framework.web.util.TraceLogUtils;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

public class TraceIdInterceptor implements AsyncHandlerInterceptor {

    private final Logger logger = LoggerUtils.getLogger(getClass());

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) {
        String traceId = TraceLogUtils.getTraceId(request);
        logger.debug("TraceId is {}", traceId);
        MDC.put(TraceConstants.TRACE_ID, traceId);
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
