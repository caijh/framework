package com.github.caijh.framework.data.redis.serializer;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.nio.charset.StandardCharsets;

import com.github.caijh.framework.data.redis.exception.RedisSerializeException;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class RedisNumberSerializer<T extends Number> implements RedisSerializer<T> {

    public static final RedisNumberSerializer<Long> STRING_LONG_SERIALIZER = new RedisNumberSerializer<>(Long.class);

    private final Class<T> clazz;

    public RedisNumberSerializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T number) throws SerializationException {
        if (number == null) {
            return new byte[0];
        }
        return number.toString().getBytes(StandardCharsets.UTF_8);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType mt = MethodType.methodType(clazz, String.class);
        try {
            MethodHandle valueOf = lookup.findStatic(clazz, "valueOf", mt);
            return (T) valueOf.invoke(new String(bytes, StandardCharsets.UTF_8));
        } catch (Throwable e) {
            throw new RedisSerializeException(e);
        }
    }

}
