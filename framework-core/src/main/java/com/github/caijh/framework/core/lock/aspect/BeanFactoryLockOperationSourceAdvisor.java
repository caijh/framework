package com.github.caijh.framework.core.lock.aspect;

import lombok.EqualsAndHashCode;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.lang.NonNull;

@EqualsAndHashCode(callSuper = true)
public class BeanFactoryLockOperationSourceAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    private final LockOperationSourcePointcut pointcut = new LockOperationSourcePointcut();

    @NonNull
    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    public void setLockOperationSource(LockOperationSource lockOperationSource) {
        pointcut.setLockOperationSource(lockOperationSource);
    }
}
