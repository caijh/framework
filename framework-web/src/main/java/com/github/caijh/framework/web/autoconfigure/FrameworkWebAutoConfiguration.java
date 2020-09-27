package com.github.caijh.framework.web.autoconfigure;

import com.github.caijh.framework.web.init.DocInitializer;
import com.github.caijh.framework.web.threadlocal.ThreadLocalStore;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.target.ThreadLocalTargetSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableConfigurationProperties(ServerProperties.class)
public class FrameworkWebAutoConfiguration {

    @Bean
    public DocInitializer docInitializer() {
        return new DocInitializer();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties(prefix = "server")
    public ServerProperties serverProperties() {
        return new ServerProperties();
    }

    @Bean(name = "threadLocalStore")
    @Scope(scopeName = "prototype")
    public ThreadLocalStore threadLocalStore() {
        return new ThreadLocalStore();
    }

    @Bean(destroyMethod = "destroy")
    public ThreadLocalTargetSource threadLocalTargetStore() {
        ThreadLocalTargetSource threadLocalTargetSource = new ThreadLocalTargetSource();
        threadLocalTargetSource.setTargetBeanName("threadLocalStore");
        return threadLocalTargetSource;
    }

    @Bean(name = "proxiedThreadLocalTargetSource")
    public ProxyFactoryBean proxiedThreadLocalTargetSource(ThreadLocalTargetSource threadLocalTargetSource) {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTargetSource(threadLocalTargetSource);
        return proxyFactoryBean;
    }

}
