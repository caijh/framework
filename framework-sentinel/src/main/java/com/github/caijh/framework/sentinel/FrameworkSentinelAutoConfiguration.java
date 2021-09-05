package com.github.caijh.framework.sentinel;

import com.github.caijh.framework.sentinel.init.SentinelInit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FrameworkSentinelAutoConfiguration {

    @Bean
    public SentinelInit sentinelInit() {
        return new SentinelInit();
    }

}
