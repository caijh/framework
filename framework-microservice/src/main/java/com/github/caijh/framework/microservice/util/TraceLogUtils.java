package com.github.caijh.framework.microservice.util;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import com.github.caijh.framework.microservice.constant.Constants;
import org.apache.logging.log4j.util.Strings;

public class TraceLogUtils {

    private TraceLogUtils() {
    }

    public static String getTraceId(HttpServletRequest request) {
        String traceId = request.getHeader(Constants.HTTP_HEADER_TRACE_ID);
        if (Strings.isNotBlank(traceId)) {
            return traceId;
        }
        return UUID.randomUUID().toString();
    }

}
