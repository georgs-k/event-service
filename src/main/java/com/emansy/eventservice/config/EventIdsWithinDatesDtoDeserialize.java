package com.emansy.eventservice.config;

import com.emansy.eventservice.model.EventIdsWithinDatesDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class EventIdsWithinDatesDtoDeserialize implements Deserializer<EventIdsWithinDatesDto> {

    @Override
    public EventIdsWithinDatesDto deserialize(String topic, byte[] data) {

        ObjectMapper mapper = new ObjectMapper();

        EventIdsWithinDatesDto event = null;
        try {
            event = mapper.readValue(data, EventIdsWithinDatesDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return event;
    }

}

