package com.github.caijh.framework.data.redis.producer;

public interface StreamProducer {

    <T> void send(String queue, T payload);

}
