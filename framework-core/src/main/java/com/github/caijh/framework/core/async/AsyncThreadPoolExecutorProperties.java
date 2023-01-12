package com.github.caijh.framework.core.async;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = AsyncThreadPoolExecutorProperties.PREFIX)
@Data
public class AsyncThreadPoolExecutorProperties {

    public static final String PREFIX = "framework.async.executor";

    private int corePoolSize = Runtime.getRuntime().availableProcessors();
    private int maxPoolSize = Runtime.getRuntime().availableProcessors() * 2;
    private int queueCapacity = 1000;
    private String namePrefix = "async-task-pool-";

}
