package com.github.caijh.framework.core.lock.aspect;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.lang.Nullable;

public class LockOperationExpressionEvaluator extends CachedExpressionEvaluator {

    private final Map<ExpressionKey, Expression> keyCache = new ConcurrentHashMap<>(64);


    @Nullable
    public Object key(String keyExpression, AnnotatedElementKey methodKey, EvaluationContext evalContext) {
        return getExpression(this.keyCache, methodKey, keyExpression).getValue(evalContext);
    }

}
