package com.github.caijh.framework.doc.autoconfigure.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(SwaggerProperties.PREFIX)
@Data
public class SwaggerProperties {

    public static final String PREFIX = "swagger";

    private boolean enabled;

    private String title;

    private String description;

}
