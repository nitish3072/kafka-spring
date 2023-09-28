package com.nitish.redis;

import com.nitish.kafka.common.MessageKafkaV1;
import com.nitish.kafka.consumer.KafkaConsumerConfig;
import com.nitish.kafka.producer.KafkaProducerConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { KafkaConsumerConfig.class, KafkaProducerConfig.class, KafkaConsumer.class, KafkaProducer.class })
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TestRedisConfiguration.class})
@TestPropertySource(locations = {"classpath:application.properties"})
@EmbeddedKafka(partitions = 1, topics = {"${kafka.topic}"}, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class KafkaProducerConsumerIntegrationTest {

    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    KafkaConsumer kafkaConsumer;

    @Test
    public void produceAndConsume() throws InterruptedException {
        String data = "This is a message";
        MessageKafkaV1 messageProto = MessageKafkaV1
                .builder()
                .message(data)
                .customerId(UUID.randomUUID().toString())
                .incomingMessageDeviceType("RANDOM")
                .ts(System.currentTimeMillis())
                .build();
        // Produce message
        kafkaProducer.sendMessage("key", messageProto);
        boolean messageConsumed = kafkaConsumer.getLatch().await(10, TimeUnit.SECONDS);
        assertTrue(messageConsumed);
        assertEquals(kafkaConsumer.getPayload(), data);
    }

}
