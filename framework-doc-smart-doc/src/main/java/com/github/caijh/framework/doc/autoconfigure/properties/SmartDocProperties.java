package com.github.caijh.framework.doc.autoconfigure.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(SmartDocProperties.PREFIX)
@Data
public class SmartDocProperties {

    public static final String PREFIX = "framework.doc.smart-doc";

    private boolean enabled;

}
