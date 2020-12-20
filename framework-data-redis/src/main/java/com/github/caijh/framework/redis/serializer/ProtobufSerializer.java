package com.github.caijh.framework.redis.serializer;

import com.github.caijh.framework.redis.util.ProtoStuffSerializerUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class ProtobufSerializer implements RedisSerializer<Object> {

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        if (o == null) {
            return new byte[]{};
        }
        return ProtoStuffSerializerUtils.serialize(o);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return ProtoStuffSerializerUtils.deserialize(bytes);
    }

}
