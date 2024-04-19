package com.nns.thinnerbatch.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.nns.thinnerbatch.common.mapper.GenericMapper;
import com.nns.thinnerbatch.dto.LogDto;
import com.nns.thinnerbatch.entity.LogEntity;

@Mapper(
	componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface LogMapper extends GenericMapper<LogDto, LogEntity> {

	LogDto.Info toInfoDto(LogEntity logEntity);
	LogEntity fromDtoToEntity(LogDto.Info logDto);

	List<LogDto.Info> toInfoListDto(List<LogEntity> logList);

}
