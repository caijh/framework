package com.github.caijh.framework.core.context;

import com.github.caijh.framework.core.util.SpringContextUtils;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;

public class SpringBeanUtilsApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
        SpringContextUtils.setApplicationContext(applicationContext);
    }

}
