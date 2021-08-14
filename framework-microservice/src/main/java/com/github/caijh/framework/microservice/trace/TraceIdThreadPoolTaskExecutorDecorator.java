package com.github.caijh.framework.microservice.trace;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class TraceIdThreadPoolTaskExecutorDecorator {

    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public TraceIdThreadPoolTaskExecutorDecorator(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.threadPoolTaskExecutor.setTaskDecorator(new MdcTaskDecorator());
    }

}
