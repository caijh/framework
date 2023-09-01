package com.github.caijh.framework.core.lock.aspect;

import java.lang.reflect.Method;

import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.ParameterNameDiscoverer;

public class LockEvaluationContext extends MethodBasedEvaluationContext {


    public LockEvaluationContext(Object rootObject, Method method, Object[] arguments, ParameterNameDiscoverer parameterNameDiscoverer) {
        super(rootObject, method, arguments, parameterNameDiscoverer);
    }
}
