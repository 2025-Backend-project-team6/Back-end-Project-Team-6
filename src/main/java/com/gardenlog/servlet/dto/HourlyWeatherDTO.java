package com.gardenlog.servlet.dto;

// 시간대별 정보를 담는 DTO
public class HourlyWeatherDTO {
	
	private String hourKey; // 예: "20251208_1500"
    private double temperature; // 기온
    private String skyStatus;   // 하늘 상태 ( 맑음, 흐림 ... )
    private int humidity;       // 습도
    private String precipitationType; // 강수형태
    
    private String fcstDate;
    private String fcstTime;
    
    
	public String getHourKey() {
		return hourKey;
	}
	public void setHourKey(String hourKey) {
		this.hourKey = hourKey;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public String getSkyStatus() {
		return skyStatus;
	}
	public void setSkyStatus(String skyStatus) {
		this.skyStatus = skyStatus;
	}
	public int getHumidity() {
		return humidity;
	}
	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}
	public String getPrecipitationType() {
		return precipitationType;
	}
	public void setPrecipitationType(String precipitationType) {
		this.precipitationType = precipitationType;
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
