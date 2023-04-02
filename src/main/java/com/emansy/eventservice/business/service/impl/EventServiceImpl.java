package com.emansy.eventservice.business.service.impl;

import com.emansy.eventservice.business.handler.ResourceNotFoundException;
import com.emansy.eventservice.business.mapper.EventMapper;
import com.emansy.eventservice.business.repository.EventRepository;
import com.emansy.eventservice.business.repository.model.EventEntity;
import com.emansy.eventservice.business.service.EventService;
import com.emansy.eventservice.model.EventDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventMapper eventMapper;

    @Override
    public EventDto create(EventEntity eventEntity) {
        log.info("Service Layer: Event details are :{}.", eventEntity);
        return eventMapper.entityToDto(eventRepository.save(eventEntity));
    }

    @Override
    public List<EventEntity> getAllEventBetween(LocalDate startDate, LocalDate endDate) {
        return eventRepository.findByDateBetween(startDate, endDate);
    }

    @Override
    public Optional<EventDto> getByID(Long id) {
        Optional<EventEntity> eventEntity = Optional.ofNullable(eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event Id: does not exist in Db")));
        Optional<EventDto> eventDto = eventEntity.flatMap(card -> Optional.ofNullable(eventMapper.entityToDto(eventEntity.get())));
        log.info("Service layer -> Event with id {} is {}", id, eventDto);
        return eventDto;
    }

    @Override
    public Boolean existsInDb(Long id) {
        return eventRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        eventRepository.deleteById(id);
        log.info("Event Deleted id : {}", id);
    }
}