package com.shine.bookshop.bean;

import java.util.Date;
import java.util.Map;

public class SiteInfo {
	private int id;
	private String storeName;
	private String introduction;
	private String contactInfo;
	private Date updateTime;

	public SiteInfo() {
	}

	public SiteInfo(Map<String, Object> map) {
		if (map.get("id") instanceof Number) {
			this.id = ((Number) map.get("id")).intValue();
		}
		this.storeName = (String) map.get("storeName");
		this.introduction = (String) map.get("introduction");
		this.contactInfo = (String) map.get("contactInfo");
		this.updateTime = (Date) map.get("updateTime");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
