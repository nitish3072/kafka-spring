package com.nitish.redis;

import com.nitish.kafka.common.VoidMessageKafka;
import com.nitish.kafka.consumer.AbstractKafkaConsumer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.concurrent.CountDownLatch;


@Service
@Slf4j
@Data
public class KafkaConsumer extends AbstractKafkaConsumer {

    private String payload;
    private CountDownLatch latch = new CountDownLatch(1);

    @Override
    protected void sendDataForTransport(String message, String incomingMessageDeviceType, String customerId, Long ts) {
        payload = message;
        latch.countDown();
        return;
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    @Override
    protected void dataNotRecognised(VoidMessageKafka messageKafka) {
        log.warn("Access Kafka consumer data not recognised: {}", messageKafka);
    }

}
