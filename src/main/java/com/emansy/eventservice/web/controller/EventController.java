package com.emansy.eventservice.web.controller;

import com.emansy.eventservice.business.handler.ResourceNotFoundException;
import com.emansy.eventservice.business.mapper.EventMapper;
import com.emansy.eventservice.business.repository.model.EventEntity;
import com.emansy.eventservice.business.service.EventService;
import com.emansy.eventservice.model.EventDto;
import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Api(tags = "Event Controller")
@Log4j2
@Validated
@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventMapper eventMapper;


    @ApiOperation(value = "Saves a new Event",
            notes = "Provide event data to save a new event object",
            response = EventDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The event is successfully saved"),
            @ApiResponse(code = 400, message = "Missed required parameters, parameters are not valid"),
            @ApiResponse(code = 401, message = "The request requires user authentication"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The server has not found anything matching the Request-URI"),
            @ApiResponse(code = 500, message = "Server error")})
    @PostMapping(value = "/save")
    public ResponseEntity<EventDto> saveEvent(@Valid @RequestBody EventDto eventDto, BindingResult bindingResult) {
        log.info("Controller layer -> Event object {} is ", eventDto);
        if (bindingResult.hasErrors()) {
            log.info("New Event is not created. Error {}", bindingResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.create(eventMapper.dtoToEntity(eventDto)));
    }

    @ApiOperation(value = "Find Events between the given Date Range ",
            notes = "Provide date range to get all the events",
            response = EventDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The event is successfully saved"),
            @ApiResponse(code = 400, message = "Missed required parameters, parameters are not valid"),
            @ApiResponse(code = 401, message = "The request requires user authentication"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The server has not found anything matching the Request-URI"),
            @ApiResponse(code = 500, message = "Server error")})
    @GetMapping(value = "/{startDate}/{endDate}")
    public ResponseEntity<List<EventEntity>> findAllEventBetween(@ApiParam(value = "start date from where you want to get events", required = true) @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Required date format: yyyy-MM-dd") @PathVariable String startDate, @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Required date format: yyyy-MM-dd") @PathVariable String endDate) {
        List<EventEntity> eventEntities = eventService.getAllEventBetween(LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        if (eventEntities.isEmpty() || eventEntities == null) {
            log.info("No Event Found between Date: {} and {}.", startDate, endDate);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        log.info("Events retrieved from DB {}", eventEntities);
        return ResponseEntity.ok(eventEntities);
    }

    @ApiOperation(value = "Delete Event by ID",
            notes = "Provide an id to delete a specific event",
            response = EventDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The event is successfully saved"),
            @ApiResponse(code = 400, message = "Missed required parameters, parameters are not valid"),
            @ApiResponse(code = 401, message = "The request requires user authentication"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The server has not found anything matching the Request-URI"),
            @ApiResponse(code = 500, message = "Server error")})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEventById(@ApiParam(value = "Id of an event", required = true) @Positive(message = "a positive integer number is required") @PathVariable Long id) {
        log.info("Event with id {} is passed to controller for deletion.", id);
        if (!eventService.existsInDb(id)) {
            log.warn("Event with id {} does not exist");
            throw new ResourceNotFoundException("Id Number: " + id + " does not exist. Cannot delete the event");
        }
        eventService.deleteById(id);
        return ResponseEntity.ok("Event Deleted with Id:" + id);
    }

    @ApiOperation(value = "Get Event by ID",
            notes = "Provide an id to get a specific event",
            response = EventDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The event is successfully saved"),
            @ApiResponse(code = 400, message = "Missed required parameters, parameters are not valid"),
            @ApiResponse(code = 401, message = "The request requires user authentication"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The server has not found anything matching the Request-URI"),
            @ApiResponse(code = 500, message = "Server error")})
    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventById(@ApiParam(value = "Id of an event", required = true) @Positive(message = "a positive integer number is required") @PathVariable Long id) {
        log.info("Event with id {} is passed to controller.", id);
        return ResponseEntity.ok(eventService.getByID(id).get());
    }

    @ApiOperation(value = "Update an Event",
            notes = "Provide an Event data for updating the existing event",
            response = EventDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The event is successfully saved"),
            @ApiResponse(code = 400, message = "Missed required parameters, parameters are not valid"),
            @ApiResponse(code = 401, message = "The request requires user authentication"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The server has not found anything matching the Request-URI"),
            @ApiResponse(code = 500, message = "Server error")})
    @PutMapping()
    public ResponseEntity<EventDto> updateEvent(@Valid @RequestBody EventEntity eventEntity, BindingResult bindingResult) {
        log.info("Controller layer: update the event whose Id is: {} and new details are {}", eventEntity.getId(), eventEntity);
        if (bindingResult.hasErrors()) {
            log.error("Event is not updated: error {}", bindingResult);
            return ResponseEntity.badRequest().build();
        }

        if (!(eventService.existsInDb(eventEntity.getId()))) {
            log.info("Id number {} does not exists.Failed to update the event details.", eventEntity.getId());
            throw new ResourceNotFoundException("Id Number: " + eventEntity.getId() + ". Does not exist. Event is NOT saved as new event");
            //  return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Update Method", "Id Number Does not exist. Event is NOT saved as new event").build() ;
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.create(eventEntity));

    }

}
