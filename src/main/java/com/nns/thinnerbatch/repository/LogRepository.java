package com.nns.thinnerbatch.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nns.thinnerbatch.entity.LogEntity;

public interface LogRepository extends JpaRepository<LogEntity,Long> {

	List<LogEntity> findAllByCreateDtBetween(LocalDateTime fromDt, LocalDateTime toDt);
}
