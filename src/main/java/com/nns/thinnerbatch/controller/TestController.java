package com.nns.thinnerbatch.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nns.thinnerbatch.job.LogStoreJobConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/test")
public class TestController {

	private final JobLauncher jobLauncher;
	private final LogStoreJobConfig logStoreJobConfig;


	@GetMapping("/log-store")
	public ResponseEntity<String> executeJobTrigger() throws
		Exception {
		JobParameters jobParameters = new JobParametersBuilder()
			.addString("dateTime", LocalDateTime.now().toString())
			.addString("generated", "BATCH")
			.toJobParameters();

		JobExecution jobExecution = jobLauncher.run(logStoreJobConfig.logStoreJob(), jobParameters);

		Optional<StepExecution> stepExecution = jobExecution.getStepExecutions().stream().findFirst();
		String summary = stepExecution.isEmpty() ? null : stepExecution.get().getSummary();

		return ResponseEntity.ok().body(summary);

	}




}
