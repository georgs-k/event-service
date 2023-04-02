package com.emansy.eventservice.business.mapper;

import com.emansy.eventservice.business.repository.model.EventEntity;
import com.emansy.eventservice.model.EventDto;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-29T13:36:06+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.18 (Oracle Corporation)"
)
@Component
public class EventMapperImpl implements EventMapper {

    @Override
    public EventDto entityToDto(EventEntity eventEntity) {
        if ( eventEntity == null ) {
            return null;
        }

        EventDto eventDto = new EventDto();

        eventDto.setId( eventEntity.getId() );
        eventDto.setTitle( eventEntity.getTitle() );
        eventDto.setDetails( eventEntity.getDetails() );
        eventDto.setDate( eventEntity.getDate() );
        eventDto.setStartTime( eventEntity.getStartTime() );
        eventDto.setEndTime( eventEntity.getEndTime() );

        return eventDto;
    }

    @Override
    public EventEntity dtoToEntity(EventDto eventDto) {
        if ( eventDto == null ) {
            return null;
        }

        EventEntity eventEntity = new EventEntity();

        eventEntity.setId( eventDto.getId() );
        eventEntity.setTitle( eventDto.getTitle() );
        eventEntity.setDetails( eventDto.getDetails() );
        eventEntity.setDate( eventDto.getDate() );
        eventEntity.setStartTime( eventDto.getStartTime() );
        eventEntity.setEndTime( eventDto.getEndTime() );

        return eventEntity;
    }
}
