package com.nns.thinnerbatch.common.config;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskHolder;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

public class CustomScheduledTaskRegistrar implements ScheduledTaskHolder, InitializingBean, DisposableBean {
	private final ScheduledTaskRegistrar registrar = new ScheduledTaskRegistrar();
	protected final Map<String, ScheduledTask> container = new ConcurrentHashMap<>();

	public void setTaskScheduler(TaskScheduler taskScheduler) {
		this.registrar.setTaskScheduler(taskScheduler);
	}

	public void setScheduler(Object scheduler) {
		this.registrar.setScheduler(scheduler);
	}

	public void updateCronTask(String name, String cron) {
		ScheduledTask findTask = container.get(name);

		if (!Objects.isNull(findTask)) {
			findTask.cancel();
			ScheduledTask updatedTask = registrar.scheduleCronTask(
				new CronTask(findTask.getTask().getRunnable(), cron));
			container.put(name, updatedTask);
		}

	}

	public ScheduledTask scheduleCronTask(String name, CronTask cronTask) {
		ScheduledTask scheduledTask = registrar.scheduleCronTask(cronTask);
		container.put(name, scheduledTask);
		return scheduledTask;
	}

	@Override
	public void destroy() throws Exception {
		registrar.destroy();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		registrar.afterPropertiesSet();
	}

	@Override
	public Set<ScheduledTask> getScheduledTasks() {
		return registrar.getScheduledTasks();
	}
}
