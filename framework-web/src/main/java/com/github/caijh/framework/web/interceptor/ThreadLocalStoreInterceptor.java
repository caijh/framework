package com.github.caijh.framework.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.caijh.framework.web.threadlocal.ThreadLocalStore;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ThreadLocalStoreInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
        ThreadLocalStore.reset();
    }

}
