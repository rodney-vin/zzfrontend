package com.zz.backend.configuration.sms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class MockSmsSender implements SMSSender {
	private Log log = LogFactory.getLog(MockSmsSender.class);
	
	@Autowired
    private TaskExecutor executor;
	
	@Override
	public void send(SMSMessage msg) {
		executor.execute(new Runnable(){

			@Override
			public void run() {
				log.info("SMS Message send");
			}});
	}

}
