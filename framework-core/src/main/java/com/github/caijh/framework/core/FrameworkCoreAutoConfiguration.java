package com.github.caijh.framework.core;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@AutoConfiguration
@ImportAutoConfiguration({
    FrameworkCoreRetryAutoConfiguration.class,
    FrameworkCoreScheduleAutoConfiguration.class,
    FrameworkCoreAsyncAutoConfiguration.class
})
@EnableAsync
public class FrameworkCoreAutoConfiguration {


}
