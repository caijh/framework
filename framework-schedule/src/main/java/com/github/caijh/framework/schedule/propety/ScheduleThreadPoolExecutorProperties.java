package com.github.caijh.framework.schedule.propety;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(value = ScheduleThreadPoolExecutorProperties.PREFIX)
@Data
public class ScheduleThreadPoolExecutorProperties {

    public static final String PREFIX = "schedule.executor";

    private int poolSize;
    private String namePrefix;

}
