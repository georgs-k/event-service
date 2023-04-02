package com.emansy.eventservice.business.mapper;

import com.emansy.eventservice.business.repository.model.EventEntity;
import com.emansy.eventservice.model.EventDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {

    EventDto entityToDto(EventEntity eventEntity);

    EventEntity dtoToEntity(EventDto eventDto);
}
