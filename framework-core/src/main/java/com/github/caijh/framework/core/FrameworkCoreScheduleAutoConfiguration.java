package com.github.caijh.framework.core;

import com.github.caijh.framework.core.scheduling.ScheduleThreadPoolExecutorProperties;
import jakarta.inject.Inject;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@AutoConfiguration
@EnableScheduling
@EnableConfigurationProperties({ScheduleThreadPoolExecutorProperties.class})
public class FrameworkCoreScheduleAutoConfiguration implements SchedulingConfigurer {

    private ScheduleThreadPoolExecutorProperties scheduleThreadPoolExecutorProperties;

    @Inject
    public void setScheduleThreadPoolExecutorProperties(ScheduleThreadPoolExecutorProperties scheduleThreadPoolExecutorProperties) {
        this.scheduleThreadPoolExecutorProperties = scheduleThreadPoolExecutorProperties;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

        threadPoolTaskScheduler.setPoolSize(this.scheduleThreadPoolExecutorProperties.getPoolSize());
        threadPoolTaskScheduler.setThreadNamePrefix(this.scheduleThreadPoolExecutorProperties.getNamePrefix());
        threadPoolTaskScheduler.initialize();

        taskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
    }

}
