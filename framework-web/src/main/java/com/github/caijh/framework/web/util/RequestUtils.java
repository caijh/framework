package com.github.caijh.framework.web.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {

    private RequestUtils() {}

    public static String extractHeader(HttpServletRequest request, String headerName) {
        String headValue = request.getParameter(headerName);
        if (null != headValue) {
            return headValue;
        }
        return request.getHeader(headerName);
    }

}
