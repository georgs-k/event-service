package com.emansy.eventservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventConsumer.class);

    @KafkaListener(topics = "event_topic", groupId = "myGroup")
    public void consume(String message) {
        LOGGER.info(String.format("Message received -> %s", message));

    }
}
