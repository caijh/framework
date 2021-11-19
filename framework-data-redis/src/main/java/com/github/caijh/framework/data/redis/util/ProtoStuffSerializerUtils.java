package com.github.caijh.framework.data.redis.util;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * ProtoStuffSerializer工具类
 */
public class ProtoStuffSerializerUtils {

    private static final Schema<Wrapper> SCHEMA = RuntimeSchema.getSchema(Wrapper.class);

    private ProtoStuffSerializerUtils() {
    }

    /**
     * 序列化对象
     *
     * @param t   对象
     * @param <T> 对象类型
     * @return 对象的字节数组
     */
    public static <T> byte[] serialize(T t) {
        LinkedBuffer buffer = LinkedBuffer.allocate(512);
        final byte[] protoStuff;
        try {
            protoStuff = ProtostuffIOUtil.toByteArray(new Wrapper<>(t), SCHEMA, buffer);
        } finally {
            buffer.clear();
        }
        return protoStuff;
    }

    /**
     * 反序列化对象
     *
     * @param bytes 字节数组
     * @param <T>   期望的对象类型
     * @return 反序列化的对象
     */
    public static <T> T deserialize(byte[] bytes) {
        Wrapper<T> wrapper = new Wrapper<>();
        ProtostuffIOUtil.mergeFrom(bytes, wrapper, SCHEMA);
        return wrapper.getData();
    }

    private static class Wrapper<T> {

        private T data;

        public Wrapper() {
        }

        public Wrapper(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

    }

}
