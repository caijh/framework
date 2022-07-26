package com.github.caijh.framework.web.interceptor;

import java.util.Date;
import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.caijh.framework.web.threadlocal.GlobalApplicationContext;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import static com.github.caijh.framework.web.threadlocal.GlobalApplicationContext.X_USER_ID;

public class GlobalApplicationContextInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) throws Exception {
        String userId = request.getHeader(X_USER_ID);
        if (userId != null) {
            GlobalApplicationContext.setCurrentUserId(Long.valueOf(userId));
        }
        GlobalApplicationContext.setIp(request);
        GlobalApplicationContext.setControllerEnterTime(new Date());
        return AsyncHandlerInterceptor.super.preHandle(request, response, handler);
    }

}
