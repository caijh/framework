package com.github.caijh.framework.data.mybatis;

import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.github.caijh.framework.data.mybatis.support.InsertFillFieldProvider;
import com.github.caijh.framework.data.mybatis.support.MybatisMetaObjectHandler;
import com.github.caijh.framework.data.mybatis.support.UpdateFillFieldProvider;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(markerInterface = Mapper.class)
public class FrameworkMybatisAutoConfiguration {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

    @ConditionalOnBean(value = {InsertFillFieldProvider.class, UpdateFillFieldProvider.class})
    @Bean
    public MybatisMetaObjectHandler mybatisMetaObjectHandler(InsertFillFieldProvider insertFillFieldProvider, UpdateFillFieldProvider updateFillFieldProvider) {
        return new MybatisMetaObjectHandler(insertFillFieldProvider, updateFillFieldProvider);
    }

    @Bean
    public OptimisticLockerInnerInterceptor opLocker() {
        return new OptimisticLockerInnerInterceptor();
    }

}
