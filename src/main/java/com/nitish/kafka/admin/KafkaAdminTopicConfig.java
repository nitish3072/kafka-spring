package com.nitish.kafka.admin;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaAdminTopicConfig {

    @Value(value = "${kafka.bootstrap_address:127.0.0.1:9093}")
    private String bootstrapAddress;

    @Value(value = "${kafka.topic}")
    private String topic;

    @Value(value = "${kafka.topic_partition:1}")
    private Integer topicPartition;

    @Value(value = "${kafka.topic_replication:1}")
    private Short topicReplication;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configs.put(AdminClientConfig.RETRIES_CONFIG, "3");
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name(topic)
                .partitions(topicPartition)
                .replicas(topicReplication)
                .config(TopicConfig.RETENTION_MS_CONFIG, "604800000")
                .config(TopicConfig.SEGMENT_BYTES_CONFIG, "26214400")
                .config(TopicConfig.RETENTION_BYTES_CONFIG, "1048576000")
                .config(TopicConfig.MIN_IN_SYNC_REPLICAS_CONFIG, "1")
                .build();
    }

}
