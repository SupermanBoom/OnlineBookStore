package com.shine.bookshop.bean;

import java.util.Date;
import java.util.Map;

public class Message {
	private int messageId;
	private String userName;
	private String content;
	private String replyContent;
	private String status;
	private Date createTime;
	private Date replyTime;

	public Message() {
	}

	public Message(Map<String, Object> map) {
		if (map.get("messageId") instanceof Number) {
			this.messageId = ((Number) map.get("messageId")).intValue();
		}
		this.userName = (String) map.get("userName");
		this.content = (String) map.get("content");
		this.replyContent = (String) map.get("replyContent");
		this.status = (String) map.get("status");
		this.createTime = (Date) map.get("createTime");
		this.replyTime = (Date) map.get("replyTime");
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}
}
