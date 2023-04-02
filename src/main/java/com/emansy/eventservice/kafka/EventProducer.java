//package com.emansy.eventservice.kafka;
//
//import com.emansy.eventservice.business.repository.model.EventEntity;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EventProducer {
///*    private static final Logger LOGGER= LoggerFactory.getLogger(EventProducer.class);
//    private KafkaTemplate<String, EventEntity> kafkaTemplate;
//
//    // No need to use @Autowire since the bean has one parameterize constructor, springframework will automatically inject
//    public EventProducer(KafkaTemplate<String, EventEntity> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//    // message that we write to kafka topic
//    public void sendMessage(EventEntity data){
//        Message<EventEntity> message= MessageBuilder
//                .withPayload(data)
//                .setHeader(KafkaHeaders.TOPIC,"event_topic")
//                .build();
//
//        LOGGER.info(String.format("Message Sent -> %s",data.toString()));
//        kafkaTemplate.send(message);
//
//    }*/
//
//
//}


