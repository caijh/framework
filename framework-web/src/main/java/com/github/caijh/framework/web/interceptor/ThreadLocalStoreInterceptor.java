package com.github.caijh.framework.web.interceptor;


import com.github.caijh.framework.core.threadlocal.ThreadLocalStore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

public class ThreadLocalStoreInterceptor implements AsyncHandlerInterceptor {

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) {
        ThreadLocalStore.reset();
    }

}
