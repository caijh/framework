package com.github.caijh.framework.doc.autoconfigure;

import com.github.caijh.framework.doc.autoconfigure.properties.SmartDocProperties;
import com.github.caijh.framework.doc.autoconfigure.properties.SwaggerProperties;
import com.github.caijh.framework.doc.init.DocInitializer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 默认使用smart-doc作为文档生成工具.
 * <p>
 * 要使用swagger, 自行配置swagger.enabled=true. SpringApplication类上加{@code @EnableOpenApi}.
 * </p>
 */
@Configuration
@EnableConfigurationProperties({SwaggerProperties.class, SmartDocProperties.class})
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class FrameworkDocAutoConfig {

    @Autowired(required = false)
    private BuildProperties buildProperties;

    @Bean
    @ConditionalOnProperty(prefix = SwaggerProperties.PREFIX, value = "enabled")
    @ConditionalOnMissingBean
    public Docket createRestApi(SwaggerProperties swaggerProperties) {
        return new Docket(DocumentationType.OAS_30).apiInfo(apiInfo(swaggerProperties))
                                                   // 是否开启
                                                   .enable(swaggerProperties.isEnabled()).select()
                                                   // 只扫描注解了@Api并且@ApiOperation，或者@Endpoint
                                                   .apis(RequestHandlerSelectors.withClassAnnotation(Api.class)
                                                                                .and(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                                                                                .or(RequestHandlerSelectors.withClassAnnotation(Endpoint.class)))
                                                   // 指定路径处理PathSelectors.any()代表所有的路径
                                                   .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
        String version = "1.0.0";
        if (buildProperties != null) {
            version = buildProperties.getVersion();
        }
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .contact(new Contact("", "", "caiqizhe@gmail.com"))
                .version(version)
                .build();
    }


    @Bean
    @ConditionalOnProperty(prefix = SmartDocProperties.PREFIX, value = "enabled")
    public DocInitializer docInitializer() {
        return new DocInitializer();
    }

}
