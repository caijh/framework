package com.github.caijh.framework.data.redis.producer;

import javax.inject.Inject;

import com.github.caijh.framework.data.redis.support.Redis;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.stereotype.Component;

@Component
public class StreamProducerImpl implements StreamProducer {

    @Inject
    private Redis redis;

    @Override
    public <T> void send(String queue, T payload) {
        ObjectRecord<String, T> objectRecord = StreamRecords.newRecord().ofObject(payload).withStreamKey(queue);
        redis.getRedisTemplate().opsForStream().add(objectRecord);
    }

}
