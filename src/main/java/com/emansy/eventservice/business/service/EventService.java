package com.emansy.eventservice.business.service;


import com.emansy.eventservice.business.repository.model.EventEntity;
import com.emansy.eventservice.model.EventDto;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EventService {

    public EventDto create(EventEntity eventEntity);

    public List<EventEntity> getAllEventBetween(LocalDate startDate, LocalDate endDate);

    public Optional<EventDto> getByID(Long Id);

    public Boolean existsInDb(Long id);

    public void deleteById(Long id);

    public Set<EventDto> getEventsByIdsAndDate(Set<Long> eventIds, String fromDate,String thruDate);


}
