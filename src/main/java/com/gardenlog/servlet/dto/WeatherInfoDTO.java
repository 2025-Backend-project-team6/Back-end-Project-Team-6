package com.gardenlog.servlet.dto;

public class WeatherInfoDTO {
	
	// 예보 날짜 (yyyyMMdd)
    private String fcstDate;
    // 예보 시각 (HHMM)
    private String fcstTime;
    // 예보 항목 코드 (예: T1H - 기온, SKY - 하늘 상태)
    private String category;
    // 예보 값
    private String fcstValue;
    //  grid x,y 좌표
    private int nx;
    private int ny;
    
    public WeatherInfoDTO() {}
    
    public WeatherInfoDTO(String fcstDate, String fcstTime, String category, String fcstValue, int nx, int ny) {
        this.fcstDate = fcstDate;
        this.fcstTime = fcstTime;
        this.category = category;
        this.fcstValue = fcstValue;
        this.nx = nx;
        this.ny = ny;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getFcstValue() {
		return fcstValue;
	}

	public void setFcstValue(String fcstValue) {
		this.fcstValue = fcstValue;
	}

	public int getNx() {
		return nx;
	}

	public void setNx(int nx) {
		this.nx = nx;
	}

	public int getNy() {
		return ny;
	}

	public void setNy(int ny) {
		this.ny = ny;
	}
}
