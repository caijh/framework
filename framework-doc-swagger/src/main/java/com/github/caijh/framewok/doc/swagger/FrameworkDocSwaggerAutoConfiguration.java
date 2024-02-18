package com.github.caijh.framewok.doc.swagger;

import com.github.caijh.framewok.doc.swagger.autoconfigure.properties.SwaggerProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@AutoConfiguration(after = WebMvcAutoConfiguration.class)
@EnableConfigurationProperties({SwaggerProperties.class})
public class FrameworkDocSwaggerAutoConfiguration {

    private BuildProperties buildProperties;

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    @ConditionalOnProperty(prefix = SwaggerProperties.PREFIX, value = "enabled")
    @ConditionalOnMissingBean
    public Docket createRestApi(SwaggerProperties swaggerProperties) {
        return new Docket(DocumentationType.OAS_30).apiInfo(this.apiInfo(swaggerProperties))
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
        if (this.buildProperties != null) {
            version = this.buildProperties.getVersion();
        }
        return new ApiInfoBuilder()
            .title(swaggerProperties.getTitle())
            .description(swaggerProperties.getDescription())
            .contact(new Contact("", "", "caiqizhe@gmail.com"))
            .version(version)
            .build();
    }

}
