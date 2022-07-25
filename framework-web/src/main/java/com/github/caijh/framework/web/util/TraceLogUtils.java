package com.github.caijh.framework.web.util;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import com.github.caijh.framework.core.constant.TraceConstants;
import org.apache.logging.log4j.util.Strings;

public class TraceLogUtils {

    private TraceLogUtils() {
    }

    public static String getTraceId(HttpServletRequest request) {
        String traceId = request.getHeader(TraceConstants.X_TRACE_ID);
        if (Strings.isNotBlank(traceId)) {
            return traceId;
        }
        return UUID.randomUUID().toString();
    }

}
