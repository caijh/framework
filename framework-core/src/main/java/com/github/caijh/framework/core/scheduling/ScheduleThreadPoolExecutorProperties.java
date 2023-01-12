package com.github.caijh.framework.core.scheduling;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = ScheduleThreadPoolExecutorProperties.PREFIX)
@Data
public class ScheduleThreadPoolExecutorProperties {

    public static final String PREFIX = "framework.core.schedule.executor";

    private int poolSize = Runtime.getRuntime().availableProcessors();
    private String namePrefix = "schedule-task-pool-";

}
