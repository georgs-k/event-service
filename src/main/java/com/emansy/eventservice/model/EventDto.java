package com.emansy.eventservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EventDto {
    @Positive(message = "Positive integer is required")
    private Long id;

    @NotEmpty(message = "required")
    private String title;

    @NotEmpty(message = "required")
    private String details;

    @FutureOrPresent(message = "Only Present or future date is allowed")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime endTime;

}
