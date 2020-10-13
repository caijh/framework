package com.github.caijh.framework.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.github.caijh.framework.web.threadlocal.ThreadLocalStore;

public class ThreadLocalStoreFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ThreadLocalStore.reset();
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
