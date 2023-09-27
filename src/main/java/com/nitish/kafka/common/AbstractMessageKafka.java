package com.nitish.kafka.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "version",
        defaultImpl = VoidMessageKafka.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MessageKafkaV1.class, name = "1.0")})
public interface AbstractMessageKafka {

    @JsonIgnore
    String getVersion();

    String toString();

}
