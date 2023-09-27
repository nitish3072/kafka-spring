package com.nitish.kafka.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageKafkaV1 implements AbstractMessageKafka {

    String message;
    String customerId;
    String incomingMessageDeviceType;
    Long ts;

    final String version = "1.0";

    @Override
    @JsonIgnore
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String toString() {
        return "MqttMessageKafkaV1{" +
                "message='" + message + '\'' +
                ", customerId='" + customerId + '\'' +
                ", incomingMessageDeviceType='" + incomingMessageDeviceType + '\'' +
                ", ts=" + ts +
                ", version='" + version + '\'' +
                '}';
    }

}
