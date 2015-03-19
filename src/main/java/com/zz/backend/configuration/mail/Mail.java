package com.zz.backend.configuration.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class Mail {
	@Autowired
	private MailSender sender;
	
	@Autowired
    private TaskExecutor executor;
	
	@Value("${spring.mail.username}")
	private String mailFrom;
	
	/**
	 * send simple mail. no complex format.
	 * @param to
	 * @param subject
	 * @param msg
	 */
	public void send(final String to, final String subject,final String msg){
		executor.execute(new Runnable(){

			@Override
			public void run() {
				SimpleMailMessage mail = new SimpleMailMessage();
				mail.setFrom(mailFrom);
				mail.setTo(to);
				mail.setSubject(subject);
				mail.setText(msg);
				sender.send(mail);				
			}
			
		});
	}
}
