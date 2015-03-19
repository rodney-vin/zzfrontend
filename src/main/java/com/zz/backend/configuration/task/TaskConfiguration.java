package com.zz.backend.configuration.task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class TaskConfiguration {
	
	@Bean
	public TaskExecutor getExecutor(){
		ThreadPoolTaskExecutor a = new ThreadPoolTaskExecutor();
		
		return a;
	}
	
	@Bean
	public TaskScheduler getScheduler(){
		ConcurrentTaskScheduler a = new ConcurrentTaskScheduler();
		return a;
	}
}
