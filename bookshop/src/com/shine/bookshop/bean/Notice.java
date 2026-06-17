package com.shine.bookshop.bean;

import java.util.Date;
import java.util.Map;

public class Notice {
	private int noticeId;
	private String title;
	private String content;
	private String status;
	private Date publishTime;

	public Notice() {
	}

	public Notice(Map<String, Object> map) {
		if (map.get("noticeId") instanceof Number) {
			this.noticeId = ((Number) map.get("noticeId")).intValue();
		}
		this.title = (String) map.get("title");
		this.content = (String) map.get("content");
		this.status = (String) map.get("status");
		this.publishTime = (Date) map.get("publishTime");
	}

	public int getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
}
