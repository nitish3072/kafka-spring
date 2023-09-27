package com.nitish.kafka.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VoidMessageKafka implements AbstractMessageKafka {

    final String type = "0.0";

    @Override
    @JsonIgnore
    public String getVersion() {
        return "0.0";
    }

}
