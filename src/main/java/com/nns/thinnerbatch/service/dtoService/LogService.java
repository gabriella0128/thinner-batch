package com.nns.thinnerbatch.service.dtoService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nns.thinnerbatch.dto.LogDto;
import com.nns.thinnerbatch.entity.LogEntity;
import com.nns.thinnerbatch.mapper.LogMapper;
import com.nns.thinnerbatch.repository.LogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogService {

	private final LogRepository logRepository;
	private final LogMapper logMapper;

	@Transactional
	public LogDto.Info save(LogDto.Info logInfo){
		LogEntity log = logMapper.fromDtoToEntity(logInfo);
		logRepository.save(log);
		return logMapper.toInfoDto(log);
	}

	@Transactional(readOnly = true)
	public List<LogDto.Info> findByCreateDt(LocalDateTime fromDt, LocalDateTime toDt) {
		return logMapper.toInfoListDto(logRepository.findAllByCreateDtBetween(fromDt, toDt));

	}

}
