package com.github.caijh.framework.redis.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import com.github.caijh.framework.redis.exception.DeserializeException;
import com.github.caijh.framework.redis.exception.SerializeException;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

public class ProtoStuffSerializerUtils {

    private ProtoStuffSerializerUtils() {
    }

    /**
     * serialize object.
     *
     * @param t         the object
     * @param typeClass the class of object
     * @param <T>       type parameter name
     * @return byte[]
     */
    public static <T> byte[] serialize(T t, Class<T> typeClass) {
        Schema<T> schema = RuntimeSchema.getSchema(typeClass);
        LinkedBuffer buffer = LinkedBuffer.allocate(512);
        final byte[] protoStuff;
        try {
            protoStuff = ProtostuffIOUtil.toByteArray(t, schema, buffer);
        } finally {
            buffer.clear();
        }
        return protoStuff;
    }

    /**
     * serialize list.
     *
     * @param list      list
     * @param typeClass the class of element in list
     * @param <T>       parameter type name
     * @return byte[]
     */
    public static <T> byte[] serialize(List<T> list, Class<T> typeClass) {
        Schema<T> schema = RuntimeSchema.getSchema(typeClass);
        LinkedBuffer buffer = LinkedBuffer.allocate(1024 * 1024);
        byte[] protoStuff;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ProtostuffIOUtil.writeListTo(bos, list, schema, buffer);
            protoStuff = bos.toByteArray();
        } catch (IOException e) {
            throw new SerializeException(e);
        } finally {
            buffer.clear();
        }

        return protoStuff;
    }

    /**
     * deserialize obj.
     *
     * @param bytes the bytes to deserialize
     * @param clazz the class of obj
     * @param <T>   parameter type name
     * @return object of class T
     */
    public static <T> T deserialize(byte[] bytes, Class<T> clazz) {
        Schema<T> schema = RuntimeSchema.getSchema(clazz);
        T t = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes, t, schema);
        return t;
    }

    /**
     * deserialize list.
     *
     * @param bytes the bytes to deserialize
     * @param clazz the class of obj
     * @param <T>   parameter type name
     * @return list contains element, which class is clazz, parameter type name is T
     */
    public static <T> List<T> deserializeList(byte[] bytes, Class<T> clazz) {
        Schema<T> schema = RuntimeSchema.getSchema(clazz);
        try {
            return ProtostuffIOUtil.parseListFrom(new ByteArrayInputStream(bytes), schema);
        } catch (IOException e) {
            throw new DeserializeException(e);
        }
    }

}
