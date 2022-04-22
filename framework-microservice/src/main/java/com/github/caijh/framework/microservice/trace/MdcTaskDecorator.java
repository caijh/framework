package com.github.caijh.framework.microservice.trace;

import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import static com.github.caijh.framework.core.constant.TraceIdConstants.TRACE_ID;

/**
 * 线程池中任务获取trace_id
 */
public class MdcTaskDecorator implements TaskDecorator {

    @NotNull
    @Override
    public Runnable decorate(@NotNull Runnable runnable) {
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
