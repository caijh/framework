package com.github.caijh.framework.microservice.trace;

import java.util.Map;
import java.util.UUID;
import javax.annotation.Nonnull;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import static com.github.caijh.framework.core.constant.TraceConstants.TRACE_ID;

/**
 * 线程池中任务获取trace_id
 */
public class MdcTaskDecorator implements TaskDecorator {

    @Nonnull
    @Override
    public Runnable decorate(@Nonnull Runnable runnable) {
        Map<String, String> map = MDC.getCopyOfContextMap();
        return () -> {
            try {
                MDC.setContextMap(map);
                String traceId = MDC.get(TRACE_ID);
                if (Strings.isBlank(traceId)) {
                    traceId = UUID.randomUUID().toString();
                    MDC.put(TRACE_ID, traceId);
                }
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }

}
