package com.nitish.kafka.consumer;

import com.nitish.kafka.common.AbstractMessageKafka;
import com.nitish.kafka.common.CustomDeserializerMqtt;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${kafka.bootstrap_address:http://localhost:8081}")
    private String bootstrapAddress;

    @Value(value = "${kafka.consumer.auto_offset_reset:latest}")
    private String autoOffsetReset;

    @Value(value = "${kafka.consumer.max_partition_fetch_bytes:6144}")
    private String maxPartitionFetchBytes;

    @Value(value = "${kafka.consumer.fetch_min_bytes:10240}")
    private String fetchMinBytes;

    @Value(value = "${kafka.consumer.fetch_max_wait_ms:5000}")
    private String fetchMaxWaitMs;

    @Bean
    public ConsumerFactory<String, AbstractMessageKafka> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, maxPartitionFetchBytes);
        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, fetchMinBytes);
        props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, fetchMaxWaitMs);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomDeserializerMqtt.class);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new CustomDeserializerMqtt());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AbstractMessageKafka> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AbstractMessageKafka> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        factory.setConcurrency(2);
        return factory;
    }

}
