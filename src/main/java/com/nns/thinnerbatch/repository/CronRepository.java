package com.nns.thinnerbatch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nns.thinnerbatch.entity.CronEntity;

public interface CronRepository extends JpaRepository<CronEntity, Long> {

	Optional<CronEntity> findByCronIdx(Long cronIdx);
}
