package com.github.caijh.framework.core;

import com.github.caijh.framework.core.lock.annotation.EnableLocking;
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
@EnableLocking
public class FrameworkCoreAutoConfiguration {


}
