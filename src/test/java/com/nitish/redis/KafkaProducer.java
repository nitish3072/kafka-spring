package com.nitish.redis;

import com.nitish.kafka.common.AbstractMessageKafka;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class KafkaProducer {

	@Value(value = "${kafka.topic}")
	private String topic;

	@Autowired
	private KafkaTemplate<String, AbstractMessageKafka> kafkaNotificationTemplate;

	public void sendMessage(String key, AbstractMessageKafka msg) {
		log.error("Kafka Producer Notification send message : key {} msg {}",key,msg);
		CompletableFuture<SendResult<String, AbstractMessageKafka>> future = kafkaNotificationTemplate.send(topic, key, msg);
		future.whenComplete((result, throwable) -> {
			if (throwable != null) {
				log.info("Unable to send message=["
						+ msg + "] due to : " + throwable.getMessage());
			} else {
				log.info("Sent message=[" + msg +
						"] with offset=[" + result.getRecordMetadata().offset() + "]");
			}
		});
	}

}