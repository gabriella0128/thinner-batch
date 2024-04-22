package com.nns.thinnerbatch.service.dtoService;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nns.thinnerbatch.dto.CronDto;
import com.nns.thinnerbatch.entity.CronEntity;
import com.nns.thinnerbatch.mapper.CronMapper;
import com.nns.thinnerbatch.repository.CronRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CronService {

	private final CronRepository cronRepository;
	private final CronMapper cronMapper;

	@Transactional(readOnly = true)
	public Optional<CronDto.Info> findOptionalCronByCronIdx(Long cronIdx) {
		return cronRepository.findByCronIdx(cronIdx).map(cronMapper::toInfoDto);
	}

	@Transactional
	public CronDto.Info save(CronDto.Info cronDto) {
		CronEntity cronEntity = cronMapper.fromInfoToEntity(cronDto);
		cronRepository.save(cronEntity);
		return cronMapper.toInfoDto(cronEntity);
	}
}
