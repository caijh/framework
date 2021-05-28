package com.github.caijh.framework.demo.web.service.impl;

import com.github.caijh.framework.core.annotations.Retry;
import com.github.caijh.framework.core.exception.BizException;
import com.github.caijh.framework.demo.web.service.DemoWebService;
import org.springframework.stereotype.Service;

@Service
public class DemoWebServiceImpl implements DemoWebService {

    @Retry(times = 2)
    @Override
    public String hello(String word) {
        if (word == null) {
            throw new BizException("word is null");
        }

        return word;
    }

}
