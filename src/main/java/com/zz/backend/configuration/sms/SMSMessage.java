package com.zz.backend.configuration.sms;

public class SMSMessage {
	private String to;
	private String text;
	
	public SMSMessage(String to, String text) {
		this.to = to;
		this.text = text;
	}

	public String getTo() {
		return to;
	}

	public String getText() {
		return text;
	}
}
