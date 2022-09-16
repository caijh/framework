package com.github.caijh.framework.data.redis.listener;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.github.caijh.framework.data.redis.support.Redis;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamInfo;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

public abstract class AbstractStreamListener<T> implements StreamListener<String, ObjectRecord<String, T>>, Closeable, DisposableBean {

    private final Class<T> clazz;
    private StreamMessageListenerContainer<String, ObjectRecord<String, T>> container;

    @Inject
    private Redis redis;

    @SuppressWarnings("unchecked")
    protected AbstractStreamListener() {
        clazz = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), AbstractStreamListener.class);
    }

    public abstract String getQueueName();

    public abstract String getGroupName();

    public abstract String getConsumerName();

    @PostConstruct
    private void registerConsumerListener() {
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, T>> options = StreamMessageListenerContainer
            .StreamMessageListenerContainerOptions
            .builder()
            .pollTimeout(Duration.ZERO)
            .targetType(clazz)
            .build();

        container = StreamMessageListenerContainer.create(Objects.requireNonNull(redis.getRedisTemplate().getConnectionFactory()), options);

        prepareQueueGroup(getQueueName(), getGroupName());

        Consumer consumer = Consumer.from(getGroupName(), getConsumerName());

        container.receiveAutoAck(consumer, StreamOffset.create(getQueueName(), ReadOffset.lastConsumed()), this);
        container.start();
    }

    private void prepareQueueGroup(String queue, String group) {
        StreamInfo.XInfoGroups groups = redis.getRedisTemplate().opsForStream().groups(queue);
        if (groups.stream().noneMatch(xInfoGroup -> group.equals(xInfoGroup.groupName()))) {
            redis.getRedisTemplate().opsForStream().createGroup(queue, ReadOffset.from("0-0"), group);
        }
    }

    @Override
    public void close() throws IOException {
        if (container != null) {
            container.stop();
        }
    }

    @Override
    public void destroy() throws Exception {
        close();
    }

}
