package com.github.caijh.framework.web.autoconfigure;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class FrameworkWebAutoConfiguration {

}
