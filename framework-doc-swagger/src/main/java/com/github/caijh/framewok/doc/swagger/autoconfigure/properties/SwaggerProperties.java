package com.github.caijh.framewok.doc.swagger.autoconfigure.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(SwaggerProperties.PREFIX)
@Data
public class SwaggerProperties {

    public static final String PREFIX = "framework.doc.swagger";

    private boolean enabled;

    private String title;

    private String description;

}
