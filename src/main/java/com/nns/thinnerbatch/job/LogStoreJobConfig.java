package com.nns.thinnerbatch.job;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.nns.thinnerbatch.common.excel.SimpleExcelFile;
import com.nns.thinnerbatch.dto.LogDto;
import com.nns.thinnerbatch.entity.LogEntity;
import com.nns.thinnerbatch.listener.ExcelStepListener;
import com.nns.thinnerbatch.mapper.LogMapper;
import com.nns.thinnerbatch.service.LogProcessService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class LogStoreJobConfig {

	public static final String JOB_NM = "LOG_STORE_JOB";
	public static final String STEP_NM = "LOG_STORE_STEP";

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final LogProcessService logProcessService;

	@Bean
	public Job logStoreJob(){
		return new JobBuilder(JOB_NM,jobRepository)
			.start(logStoreStep())
			.build();
	}

	@Bean
	@JobScope
	public Step logStoreStep(){
		return new StepBuilder(STEP_NM, jobRepository)
			.<LogDto.Info, LogDto.Info>chunk(100, transactionManager)
			.reader(logStoreReader())
			.writer(logStoreWriter())
			.build();
	}

	@Bean
	public ItemReader<LogDto.Info> logStoreReader(){
		return new AbstractPagingItemReader<LogDto.Info>() {
			@Override
			protected void doReadPage() {
				if (results == null) {
					results = new ArrayList<>();
				} else {
					results.clear();
				}

				List<LogDto.Info> logListByCreateDt = logProcessService.findLogListByCreateDt();

				results.addAll(logListByCreateDt);
				this.setPageSize(100);

			}
		};
	}






	@Bean
	public ItemWriter<LogDto.Info> logStoreWriter(){

		return items -> {
			List<LogDto.Info> logDtos = new ArrayList<>();
			items.forEach(logDtos::add);
			String fileName = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "_log.xlsx";
			SimpleExcelFile<LogDto.Info> excelFile = new SimpleExcelFile<>(logDtos, LogDto.Info.class, fileName);
			excelFile.write();

		};
	}


}
