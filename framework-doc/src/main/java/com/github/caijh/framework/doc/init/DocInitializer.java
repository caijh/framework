package com.github.caijh.framework.doc.init;

import java.util.ArrayList;
import java.util.List;

import com.power.common.enums.HttpCodeEnum;
import com.power.doc.builder.HtmlApiDocBuilder;
import com.power.doc.model.ApiConfig;
import com.power.doc.model.ApiErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.ApplicationListener;

public class DocInitializer implements ApplicationListener<WebServerInitializedEvent> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.logger.info("Initializing smart-doc");

        this.init(event.getApplicationContext());

        this.logger.info("Initialized smart-doc");
    }

    public void init(WebServerApplicationContext applicationContext) {
        ApiConfig config = new ApiConfig();
        config.setAllInOne(true);
        config.setCoverOld(true);
        config.setAdoc(true);
        config.setStrict(true);
        config.setProjectName(applicationContext.getApplicationName());
        WebServer webServer = applicationContext.getWebServer();
        config.setServerUrl("http://127.0.0.1:" + webServer.getPort());
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
