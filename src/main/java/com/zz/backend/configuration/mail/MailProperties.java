package com.zz.backend.configuration.mail;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="my.mail.sendcloud")
public class MailProperties {
	private String apikey;

	private String apiuser;

	private String from;
	
	private String url;

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public String getApiuser() {
		return apiuser;
	}

	public void setApiuser(String apiuser) {
		this.apiuser = apiuser;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
