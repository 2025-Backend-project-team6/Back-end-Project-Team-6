package com.gardenlog.servlet.dto;

// 시간대별 정보를 담는 DTO
public class HourlyWeatherDTO {
	
	private String hourKey; // 예: "20251208_1500"
    private String temperature; // 기온 (String으로 변경)
    private String skyStatus;   // 하늘 상태
    private String humidity;    // 습도 (String으로 변경)
    private String precipitationType; // 강수형태
    private String precipitationAmount; // 강수량
    
    private String fcstDate;
    private String fcstTime;
    
    // 기본 생성자
    public HourlyWeatherDTO() {
        this.temperature = "-";
        this.skyStatus = "1"; 
        this.humidity = "-";
        this.precipitationType = "0"; 
        this.precipitationAmount = "-"; 
    }
    
	public String getHourKey() {
		return hourKey;
	}
	public void setHourKey(String hourKey) {
		this.hourKey = hourKey;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getSkyStatus() {
		return skyStatus;
	}
	public void setSkyStatus(String skyStatus) {
		this.skyStatus = skyStatus;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public String getPrecipitationType() {
		return precipitationType;
	}
	public void setPrecipitationType(String precipitationType) {
		this.precipitationType = precipitationType;
	}
	public String getPrecipitationAmount() {
		return precipitationAmount;
	}
	public void setPrecipitationAmount(String precipitationAmount) {
		this.precipitationAmount = precipitationAmount;
	}
	public String getFcstDate() {
		return fcstDate;
	}
	public void setFcstDate(String fcstDate) {
		this.fcstDate = fcstDate;
	}
	public String getFcstTime() {
		return fcstTime;
	}
	public void setFcstTime(String fcstTime) {
		this.fcstTime = fcstTime;
	}
    
}
