package com.newidor.learn.metaq.demo2;

public class EmailRequest {
	
	private String emailAddress;
	private String emailTopic;
	private String content;
	private String createTime;
	
	
	
	
	public EmailRequest(String emailAddress, String emailTopic, String content) {
		super();
		this.emailAddress = emailAddress;
		this.emailTopic = emailTopic;
		this.content = content;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getEmailTopic() {
		return emailTopic;
	}
	public void setEmailTopic(String emailTopic) {
		this.emailTopic = emailTopic;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
	
	
}
