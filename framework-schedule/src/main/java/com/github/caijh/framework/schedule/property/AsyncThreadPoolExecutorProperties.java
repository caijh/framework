package com.github.caijh.framework.schedule.property;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = AsyncThreadPoolExecutorProperties.PREFIX)
@Data
public class AsyncThreadPoolExecutorProperties {

    public static final String PREFIX = "aysnc.executor";

    private int corePoolSize;
    private int maxPoolSize;
    private int queueCapacity;
    private String namePrefix;

}
