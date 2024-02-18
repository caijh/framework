package com.github.caijh.framework.core;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import com.github.caijh.framework.core.async.AsyncThreadPoolExecutorProperties;
import jakarta.inject.Inject;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@AutoConfiguration
@EnableConfigurationProperties({AsyncThreadPoolExecutorProperties.class})
@EnableAsync(proxyTargetClass = true)
public class FrameworkCoreAsyncAutoConfiguration implements AsyncConfigurer {

    private AsyncThreadPoolExecutorProperties asyncThreadPoolExecutorProperties;

    @Inject
    public void setAsyncThreadPoolExecutorProperties(AsyncThreadPoolExecutorProperties asyncThreadPoolExecutorProperties) {
        this.asyncThreadPoolExecutorProperties = asyncThreadPoolExecutorProperties;
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(this.asyncThreadPoolExecutorProperties.getCorePoolSize());
        //配置最大线程数
        executor.setMaxPoolSize(this.asyncThreadPoolExecutorProperties.getMaxPoolSize());
        //配置队列大小
        executor.setQueueCapacity(this.asyncThreadPoolExecutorProperties.getQueueCapacity());
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix(this.asyncThreadPoolExecutorProperties.getNamePrefix());

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }

}
