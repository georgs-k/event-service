package com.emansy.eventservice.kafka;


import com.emansy.eventservice.model.EventDto;
import com.emansy.eventservice.model.EventIdsWithinDatesDto;
import com.emansy.eventservice.model.EventsDto;
import com.emansy.eventservice.web.controller.EventController;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Log4j2
@Service
public class EventConsumer {
    @Autowired
    private EventController eventController;

    @KafkaListener(topics = "events-request", groupId = "event-group")
    @SendTo
    public Message<EventsDto> consume(ConsumerRecord<String, EventIdsWithinDatesDto> consumerRecord) {
        EventIdsWithinDatesDto eventIdsWithinDatesDto = consumerRecord.value();
        log.info("Message received from Kafka-> {}", eventIdsWithinDatesDto);
        Set<Long> eventDtoSet = eventIdsWithinDatesDto.getIds();
        String fromDate = eventIdsWithinDatesDto.getFromDate();
        String thruDate = eventIdsWithinDatesDto.getThruDate();

        if (thruDate.isEmpty()) {
            thruDate = null;
        }
        Set<EventDto> eventDto = eventController.findAllEventByIdsBetween(eventDtoSet, fromDate, thruDate);
        EventsDto eventsDto = new EventsDto(eventDto);
        return MessageBuilder.withPayload(eventsDto).build();
    }
}
