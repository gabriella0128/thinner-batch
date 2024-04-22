package com.nns.thinnerbatch.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.nns.thinnerbatch.common.mapper.GenericMapper;
import com.nns.thinnerbatch.dto.CronDto;
import com.nns.thinnerbatch.entity.CronEntity;

@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CronMapper extends GenericMapper<CronDto, CronEntity> {

	CronEntity map(Long cronIdx);

	CronDto.Info toInfoDto(CronEntity cronEntity);

	CronEntity fromInfoToEntity(CronDto.Info cronDto);
}
