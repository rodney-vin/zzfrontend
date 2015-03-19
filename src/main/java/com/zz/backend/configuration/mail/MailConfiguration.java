package com.zz.backend.configuration.mail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;

import com.zz.backend.Constants;

@Configuration
@EnableConfigurationProperties(MailProperties.class)
@ConditionalOnProperty(name = "my.mail.vendor", havingValue = Constants.MAIL_IMPL_SENDCLOUD)
public class MailConfiguration {
	private Log log = LogFactory.getLog(MailConfiguration.class);
	@Bean
	@ConditionalOnProperty(name = "my.mail.sendcloud.protocol", havingValue = Constants.MAIL_SENDCLOUD_PROTOCOL_WEBAPI)
	public MailSender getMailSenderUsingWebApi() {
		log.info("SendCloud Mail sender created");
		SendCloudMailSenderWebApiImpl sender = new SendCloudMailSenderWebApiImpl();
		return sender;
	}
	
	@Bean
	@ConditionalOnProperty(name = "my.mail.sendcloud.protocol", havingValue = Constants.MAIL_SENDCLOUD_PROTOCOL_SMTP)
	public MailSender getMailSenderUsingSMTP() {
		log.info("SendCloud Mail sender created");
		SendCloudMailSenderSMTPImpl sender = new SendCloudMailSenderSMTPImpl();
		return sender;
	}
}
