package com.gardenlog.servlet.dto;

import java.sql.Date;

public class JournalDTO {
    private int logId;
    private String userid;
    private int myCropId;        // 작물 ID (DB 저장용)
    private String cropNickname; // 작물 애칭 (화면 출력용)
    private String title;
    private String content;
    private String weather;
    private Date logDate;
    private String logImg;
    private String logType; // 일지 분류 (물주기, 비료, 관찰, 수확)
    
	public int getLogId() {
		return logId;
	}
	public void setLogId(int logId) {
		this.logId = logId;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getMyCropId() {
		return myCropId;
	}
	public void setMyCropId(int myCropId) {
		this.myCropId = myCropId;
	}
	public String getCropNickname() {
		return cropNickname;
	}
	public void setCropNickname(String cropNickname) {
		this.cropNickname = cropNickname;
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
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public Date getLogDate() {
		return logDate;
	}
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
	public String getLogImg() {
		return logImg;
	}
	public void setLogImg(String logImg) {
		this.logImg = logImg;
	}
	public String getLogType() {
		return logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
}