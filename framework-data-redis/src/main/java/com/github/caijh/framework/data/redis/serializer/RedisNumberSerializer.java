package com.github.caijh.framework.data.redis.serializer;

import java.lang.reflect.ParameterizedType;
import java.nio.charset.StandardCharsets;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class RedisNumberSerializer<T extends Number> implements RedisSerializer<T> {

    private final Class<T> clazz;

    @SuppressWarnings("unchecked")
    public RedisNumberSerializer() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    @Override
    public byte[] serialize(T number) throws SerializationException {
        if (number == null) {
            return new byte[0];
        }
        return number.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        return null;
    }

}
