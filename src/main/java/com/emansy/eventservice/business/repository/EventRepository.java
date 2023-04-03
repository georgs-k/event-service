package com.emansy.eventservice.business.repository;

import com.emansy.eventservice.business.repository.model.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
    List<EventEntity> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
