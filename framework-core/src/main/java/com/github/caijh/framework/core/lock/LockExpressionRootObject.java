

package com.github.caijh.framework.core.lock;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;


/**
 * class to
 */
public record LockExpressionRootObject(Method method, Object[] args, Object target, Class<?> targetClass) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LockExpressionRootObject that = (LockExpressionRootObject) o;
        return Objects.equals(method, that.method) && Arrays.equals(args, that.args) && Objects.equals(target, that.target) && Objects.equals(targetClass, that.targetClass);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(method, target, targetClass);
        result = 31 * result + Arrays.hashCode(args);
        return result;
    }

    @Override
    public String toString() {
        return "LockExpressionRootObject{" +
            "method=" + method +
            ", args=" + Arrays.toString(args) +
            ", target=" + target +
            ", targetClass=" + targetClass +
            '}';
    }
}
