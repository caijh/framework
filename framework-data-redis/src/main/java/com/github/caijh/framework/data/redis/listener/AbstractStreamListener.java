package com.github.caijh.framework.data.redis.listener;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.Objects;

import com.github.caijh.framework.core.util.LoggerUtils;
import com.github.caijh.framework.data.redis.support.Redis;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamInfo;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

/**
 * Redis Stream Object listener.
 *
 * @param <T> Redis Steam Object Type.
 */
public abstract class AbstractStreamListener<T> implements StreamListener<String, ObjectRecord<String, T>>, AutoCloseable, InitializingBean {

    private final Class<T> clazz;
    private final Logger logger = LoggerUtils.getLogger(getClass());
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

    private void registerConsumerListener() throws Exception {
        logger.debug("Create StreamListener {} on queue {} group {}", clazz.getName(), getQueueName(), getGroupName());
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

    private void prepareQueueGroup(String queue, String group)
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ObjectRecord<String, T> objectRecord = StreamRecords.newRecord().ofObject(clazz.getDeclaredConstructor().newInstance()).withStreamKey(queue);
        redis.getRedisTemplate().opsForStream().add(objectRecord);
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
    public void afterPropertiesSet() throws Exception {
        registerConsumerListener();
    }

}
