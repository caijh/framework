package com.github.caijh.framework.doc.init;

import java.util.ArrayList;
import java.util.List;

import com.power.common.enums.HttpCodeEnum;
import com.power.doc.builder.HtmlApiDocBuilder;
import com.power.doc.model.ApiConfig;
import com.power.doc.model.ApiErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

public class DocInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (WebApplicationType.NONE == event.getSpringApplication().getWebApplicationType()) {
            return;
        }
        logger.info("Initializing smart-doc");

        init(event.getApplicationContext());

        logger.info("Initialized smart-doc");
    }

    public void init(ConfigurableApplicationContext applicationContext) {
        ApiConfig config = new ApiConfig();
        config.setAllInOne(true);
        config.setCoverOld(true);
        config.setAdoc(true);
        config.setStrict(true);
        config.setProjectName(applicationContext.getApplicationName());
        ServerProperties serverProperties = applicationContext.getBean(ServerProperties.class);
        config.setServerUrl("http://127.0.0.1:" + serverProperties.getPort());
        config.setOutPath("doc/api");
        List<ApiErrorCode> errorCodeList = new ArrayList<>();
        for (HttpCodeEnum codeEnum : HttpCodeEnum.values()) {
            ApiErrorCode errorCode = new ApiErrorCode();
            errorCode.setValue(codeEnum.getCode()).setDesc(codeEnum.getMessage());
            errorCodeList.add(errorCode);
        }
        config.setErrorCodes(errorCodeList);
        HtmlApiDocBuilder.buildApiDoc(config);
    }

}
