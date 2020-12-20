package com.github.caijh.framework.data.redis.serializer;

import com.github.caijh.framework.data.redis.util.ProtoStuffSerializerUtils;
import org.springframework.data.redis.serializer.RedisSerializer;

public class ProtobufSerializer implements RedisSerializer<Object> {

    @Override
    public byte[] serialize(Object o) {
        if (o == null) {
            return new byte[]{};
        }
        return ProtoStuffSerializerUtils.serialize(o);
    }

    @Override
    public Object deserialize(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return ProtoStuffSerializerUtils.deserialize(bytes);
    }

}
