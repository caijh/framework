package com.github.caijh.framework.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.caijh.framework.web.threadlocal.GlobalUserContext;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import static com.github.caijh.framework.web.threadlocal.GlobalUserContext.X_USER_ID;

public class UserContextInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        String userId = request.getHeader(X_USER_ID);
        if (userId != null) {
            GlobalUserContext.setCurrentUserId(Long.valueOf(userId));
        }
        return AsyncHandlerInterceptor.super.preHandle(request, response, handler);
    }

}
