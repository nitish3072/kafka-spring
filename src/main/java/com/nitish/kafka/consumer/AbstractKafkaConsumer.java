package com.nitish.kafka.consumer;

import com.nitish.kafka.common.VoidMessageKafka;
import com.nitish.kafka.common.AbstractMessageKafka;
import com.nitish.kafka.common.MessageKafkaV1;
import org.apache.logging.log4j.LogManager;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

public abstract class AbstractKafkaConsumer {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(AbstractKafkaConsumer.class);

    @KafkaListener(topics = "#{'${kafka.topic}'.split(',')}", groupId = "#{'${kafka.consumer.group_id}'}")
    public void listenGroupMessages(AbstractMessageKafka message, Acknowledgment acknowledgment) {
        try {
            if (message.getVersion().equals("1.0")) {
                logger.info("version is 1.0");
                MessageKafkaV1 messageKafka = (MessageKafkaV1) message;
                sendDataForTransport(messageKafka.getMessage(),
                        messageKafka.getIncomingMessageDeviceType(),
                        messageKafka.getCustomerId(),
                        messageKafka.getTs());
            } else {
                VoidMessageKafka voidMessageKafka = (VoidMessageKafka) message;
                dataNotRecognised(voidMessageKafka);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // TODO later check the errors properly to change offset
            acknowledgment.acknowledge();
        }
    }

    protected abstract void sendDataForTransport(String message, String incomingMessageDeviceType, String customerId, Long ts);

    protected abstract void dataNotRecognised(VoidMessageKafka messageKafka);
}
