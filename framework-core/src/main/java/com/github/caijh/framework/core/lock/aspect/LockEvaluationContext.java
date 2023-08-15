package com.github.caijh.framework.core.lock.aspect;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.ParameterNameDiscoverer;

public class LockEvaluationContext extends MethodBasedEvaluationContext {

    private final Set<String> unavailableVariables = new HashSet<>(1);


    public LockEvaluationContext(Object rootObject, Method method, Object[] arguments, ParameterNameDiscoverer parameterNameDiscoverer) {
        super(rootObject, method, arguments, parameterNameDiscoverer);
    }

    public void addUnavailableVariable(Object parametersUnavailable) {
        unavailableVariables.add(parametersUnavailable.toString());
    }
}
