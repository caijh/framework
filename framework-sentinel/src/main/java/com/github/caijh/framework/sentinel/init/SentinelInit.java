package com.github.caijh.framework.sentinel.init;

import com.alibaba.csp.sentinel.init.InitExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;

public class SentinelInit implements ApplicationListener<WebServerInitializedEvent> {

    private final Logger logger = LoggerFactory.getLogger(SentinelInit.class);

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.logger.info("Trigger to init sentinel {}", event.getApplicationContext());
        InitExecutor.doInit();
    }

}
