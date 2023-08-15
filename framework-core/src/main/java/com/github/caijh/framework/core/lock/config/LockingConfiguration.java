package com.github.caijh.framework.core.lock.config;

import com.github.caijh.framework.core.lock.annotation.EnableLocking;
import com.github.caijh.framework.core.lock.aspect.AnnotationLockOperationSource;
import com.github.caijh.framework.core.lock.aspect.BeanFactoryLockOperationSourceAdvisor;
import com.github.caijh.framework.core.lock.aspect.LockInterceptor;
import com.github.caijh.framework.core.lock.aspect.LockKeyGenerator;
import com.github.caijh.framework.core.lock.aspect.LockManager;
import com.github.caijh.framework.core.lock.aspect.LockOperationSource;
import com.github.caijh.framework.core.lock.aspect.ReentrantLockManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.Nullable;

@Configuration
public class LockingConfiguration implements ImportAware {

    @Nullable
    protected AnnotationAttributes enableLocking;

    @Bean
    public BeanFactoryLockOperationSourceAdvisor beanFactoryLockOperationSourceAdvisor(LockOperationSource lockOperationSource,
                                                                                       LockInterceptor lockInterceptor) {
        BeanFactoryLockOperationSourceAdvisor advisor = new BeanFactoryLockOperationSourceAdvisor();
        advisor.setLockOperationSource(lockOperationSource);
        advisor.setAdvice(lockInterceptor);
        if (this.enableLocking != null) {
            advisor.setOrder(this.enableLocking.<Integer>getNumber("order"));
        }
        return advisor;
    }

    @Bean
    public LockOperationSource lockOperationSource() {
        return new AnnotationLockOperationSource();
    }

    @Bean
    @ConditionalOnMissingBean
    public LockManager lockManager() {
        return new ReentrantLockManager();
    }


    @Bean
    @ConditionalOnMissingBean
    public LockKeyGenerator lockKeyGenerator() {
        return new LockKeyGenerator();
    }

    @Bean
    public LockInterceptor lockInterceptor(LockOperationSource lockOperationSource, LockKeyGenerator keyGenerator, LockManager lockManager) {
        LockInterceptor interceptor = new LockInterceptor();
        interceptor.configure(lockOperationSource, keyGenerator, lockManager);
        return interceptor;
    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        this.enableLocking = AnnotationAttributes.fromMap(
                importMetadata.getAnnotationAttributes(EnableLocking.class.getName()));
        if (this.enableLocking == null) {
            throw new IllegalArgumentException(
                    "@EnableLocking is not present on importing class " + importMetadata.getClassName());
        }
    }
}
