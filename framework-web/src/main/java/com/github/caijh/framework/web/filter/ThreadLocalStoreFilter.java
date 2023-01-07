package com.github.caijh.framework.web.filter;

import java.io.IOException;

import com.github.caijh.framework.core.threadlocal.ThreadLocalStore;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class ThreadLocalStoreFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ThreadLocalStore.reset();
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
