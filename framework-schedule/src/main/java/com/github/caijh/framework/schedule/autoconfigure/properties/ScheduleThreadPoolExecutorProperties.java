package com.github.caijh.framework.schedule.autoconfigure.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(value = ScheduleThreadPoolExecutorProperties.PREFIX)
@Data
public class ScheduleThreadPoolExecutorProperties {

    public static final String PREFIX = "framework.schedule.executor";

    private int poolSize;
    private String namePrefix;

}
