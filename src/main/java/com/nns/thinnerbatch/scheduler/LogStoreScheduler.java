package com.nns.thinnerbatch.scheduler;

import java.time.LocalDateTime;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Component;

import com.nns.thinnerbatch.common.config.CustomScheduledTaskRegistrar;
import com.nns.thinnerbatch.job.LogStoreJobConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RequiredArgsConstructor
public class LogStoreScheduler {
	private final LogStoreJobConfig logStoreJobConfig;
	private final JobLauncher jobLauncher;
	private final CustomScheduledTaskRegistrar registrar;

	@Bean
	public void logStoreScheduler(){
		JobParameters jobParameters = new JobParametersBuilder()
			.addString("dateTime", LocalDateTime.now().toString())
			.addString("generated", "Scheduler")
			.toJobParameters();

		registrar.scheduleCronTask("logStore",
			new CronTask(() -> {
				try {
					jobLauncher.run(logStoreJobConfig.logStoreJob(), jobParameters);

				} catch (JobExecutionAlreadyRunningException | JobRestartException | JobParametersInvalidException |
						 JobInstanceAlreadyCompleteException e) {
					throw new RuntimeException(e);
				}
			}, ""));

	}


}
