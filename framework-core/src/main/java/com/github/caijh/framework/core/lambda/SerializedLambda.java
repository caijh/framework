package com.github.caijh.framework.core.lambda;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.util.Objects;

import com.github.caijh.framework.core.exception.BizException;
import com.github.caijh.framework.core.utils.ClassUtils;
import org.springframework.util.SerializationUtils;

/**
 * Copy from mybatis-plus.
 */
@SuppressWarnings("unused")
public class SerializedLambda implements Serializable {

    private static final long serialVersionUID = 8025925345765570181L;

    private Class<?> capturingClass;
    private String functionalInterfaceClass;
    private String functionalInterfaceMethodName;
    private String functionalInterfaceMethodSignature;
    private String implClass;
    private String implMethodName;
    private String implMethodSignature;
    private int implMethodKind;
    private String instantiatedMethodType;
    private Object[] capturedArgs;

    /**
     * 通过反序列化转换 lambda 表达式，该方法只能序列化 lambda 表达式，不能序列化接口实现或者正常非 lambda 写法的对象
     *
     * @param lambda lambda对象
     * @return 返回解析后的 SerializedLambda
     * @throws BizException if resolve fail
     */
    public static SerializedLambda resolve(SFunction<?, ?> lambda) {
        if (!lambda.getClass().isSynthetic()) {
            throw new BizException("该方法仅能传入 lambda 表达式产生的合成类");
        }
        try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(Objects.requireNonNull(SerializationUtils.serialize(lambda)))) {
            @Override
            protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
                Class<?> clazz;
                try {
                    clazz = ClassUtils.toClassConfident(objectStreamClass.getName());
                } catch (Exception ex) {
                    clazz = super.resolveClass(objectStreamClass);
                }
                return clazz == java.lang.invoke.SerializedLambda.class ? SerializedLambda.class : clazz;
            }
        }) {
            return (SerializedLambda) objIn.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new BizException("This is impossible to happen", e);
        }
    }

    /**
     * 获取接口 class
     *
     * @return 返回 class 名称
     */
    public String getFunctionalInterfaceClassName() {
        return this.normalizedName(this.functionalInterfaceClass);
    }

    /**
     * 获取实现的 class
     *
     * @return 实现类
     */
    public Class<?> getImplClass() {
        return ClassUtils.toClassConfident(this.getImplClassName());
    }

    /**
     * 获取 class 的名称
     *
     * @return 类名
     */
    public String getImplClassName() {
        return this.normalizedName(this.implClass);
    }

    /**
     * 获取实现者的方法名称
     *
     * @return 方法名称
     */
    public String getImplMethodName() {
        return this.implMethodName;
    }

    /**
     * 正常化类名称，将类名称中的 / 替换为 .
     *
     * @param name 名称
     * @return 正常的类名
     */
    private String normalizedName(String name) {
        return name.replace('/', '.');
    }

    /**
     * 获取实例化方法的类型.
     *
     * @return 获取实例化方法的类型
     */
    public Class<?> getInstantiatedType() {
        String instantiatedTypeName = this.normalizedName(this.instantiatedMethodType.substring(2, this.instantiatedMethodType.indexOf(';')));
        return ClassUtils.toClassConfident(instantiatedTypeName);
    }

    /**
     * toString.
     *
     * @return 字符串形式
     */
    @Override
    public String toString() {
        String interfaceName = this.getFunctionalInterfaceClassName();
        String implName = this.getImplClassName();
        return String.format("%s -> %s::%s",
                interfaceName.substring(interfaceName.lastIndexOf('.') + 1),
                implName.substring(implName.lastIndexOf('.') + 1),
                this.implMethodName);
    }

}
