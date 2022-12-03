package com.github.caijh.framework.data;

import com.github.caijh.framework.data.jpa.BaseService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

@AutoConfiguration
@EnableJpaRepositories(
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Service.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = BaseService.class)
    })
public class FrameworkJpaAutoConfiguration {
}
