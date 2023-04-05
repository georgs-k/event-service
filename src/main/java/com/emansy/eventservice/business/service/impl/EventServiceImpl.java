package com.emansy.eventservice.business.service.impl;

import com.emansy.eventservice.business.handler.ResourceNotFoundException;
import com.emansy.eventservice.business.mapper.EventMapper;
import com.emansy.eventservice.business.repository.EventRepository;
import com.emansy.eventservice.business.repository.model.EventEntity;
import com.emansy.eventservice.business.service.EventService;
import com.emansy.eventservice.model.EventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private final EventMapper eventMapper;

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
        Optional<EventEntity> eventEntity = Optional.ofNullable(eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event Id: " + id + " does not exist in Db")));
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

    @Override
    public Set<EventDto> getEventsByIdsAndDate(Set<Long> eventIds, String fromDate, String thruDate) {
        LocalDate startDate = null;
        List<EventEntity> events = null;
        if (Objects.nonNull(fromDate)) {
            startDate = LocalDate.parse(fromDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        if (Objects.nonNull(thruDate)) {
            LocalDate endDate = LocalDate.parse(thruDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            events = eventRepository.findByIdInAndDateBetween(eventIds, startDate, endDate);
        } else {
            events = eventRepository.findByIdInAndDateGreaterThanEqual(eventIds, startDate);
        }

        Set<EventDto> eventDtos = events.stream().map(eventEntity -> new EventDto(eventEntity.getId(), eventEntity.getTitle(), eventEntity.getDetails(), eventEntity.getDate(), eventEntity.getStartTime(), eventEntity.getEndTime())).collect(Collectors.toSet());

        return eventDtos;
    }
}
