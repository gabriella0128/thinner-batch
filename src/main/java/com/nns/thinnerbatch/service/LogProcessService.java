package com.nns.thinnerbatch.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nns.thinnerbatch.dto.LogDto;
import com.nns.thinnerbatch.service.dtoService.LogService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class LogProcessService {

	private final LogService logService;

	public List<LogDto.Info> findLogListByCreateDt(){
		LocalDateTime now = LocalDateTime.now();

		return logService.findByCreateDt(
			LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0)
				.minus(Duration.ofHours(24))
				,LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0)
			);

	}
}
