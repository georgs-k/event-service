package com.emansy.eventservice.business.service;


import com.emansy.eventservice.business.repository.model.EventEntity;
import com.emansy.eventservice.model.EventDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventService {

    public EventDto create(EventEntity eventEntity);

    public List<EventEntity> getAllEventBetween(LocalDate startDate, LocalDate endDate);

    public Optional<EventDto> getByID(Long Id);

    public Boolean existsInDb(Long id);

    public void deleteById(Long id);


}
