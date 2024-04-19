package com.nns.thinnerbatch.job;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.nns.thinnerbatch.dto.LogDto;
import com.nns.thinnerbatch.service.LogProcessService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class LogStoreJobConfig {

	public static final String JOB_NM = "LOG_STORE_JOB";
	public static final String STEP_ME = "LOG_STORE_STEP";

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final ChunkListener chunkListener;

	private final LogProcessService logProcessService;

	@Bean
	public ItemReader<LogDto.Info> logStoreReader(){
		return new AbstractPagingItemReader<LogDto.Info>() {
			@Override
			protected void doReadPage() {
				results.addAll(logProcessService.findLogListByCreateDt());
			}
		};
	}

	@Bean
	public ItemWriter<LogDto.Info> logStoreWriter(){
		return null;
	}


}
