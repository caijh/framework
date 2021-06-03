package com.github.caijh.framework.demo.web.service.impl;

import com.github.caijh.framework.core.annotations.Retry;
import com.github.caijh.framework.core.exception.BizException;
import com.github.caijh.framework.demo.web.service.DemoWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class DemoWebServiceImpl implements DemoWebService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Retry(times = 2)
    @Override
    public String hello(String word) {
        if (word == null) {
            throw new BizException("word is null");
        }

        return word;
    }

    @Async
    @Override
    public void asyncHello(String world) {
        this.logger.info(world);
    }

}
