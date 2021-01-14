package com.github.caijh.framework.web.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {

    private RequestUtils() {}

    /**
     * @param request HttpServletRequest
     * @param param   参数
     * @return 查找请求是否有参数，有返回；无则查找header。
     */
    public static String extractHeader(HttpServletRequest request, String param) {
        String headValue = request.getParameter(param);
        if (null != headValue) {
            return headValue;
        }
        return request.getHeader(param);
    }

}
