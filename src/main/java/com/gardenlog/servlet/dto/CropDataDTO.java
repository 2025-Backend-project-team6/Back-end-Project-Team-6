package com.gardenlog.servlet.dto;

import java.util.List;
import java.util.Map;

public class CropDataDTO {
	private int cropid;
	private String crop_code;
	private String crop_title;
	private String info_json;
	private String category_name;
    private String difficulty_level;
    private String period_text;
    private String water_cycle;
    private String sunlight_hours;
    
	public int getCropid() {
		return cropid;
	}
	public void setCropid(int cropid) {
		this.cropid = cropid;
	}
	public String getCrop_code() {
		return crop_code;
	}
	public void setCrop_code(String crop_code) {
		this.crop_code = crop_code;
	}
	public String getCrop_title() {
		return crop_title;
	}
	public void setCrop_title(String crop_title) {
		this.crop_title = crop_title;
	}
	public String getInfo_json() {
		return info_json;
	}
	public void setInfo_json(String info_json) {
		this.info_json = info_json;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getDifficulty_level() {
		return difficulty_level;
	}
	public void setDifficulty_level(String difficulty_level) {
		this.difficulty_level = difficulty_level;
	}
	public String getPeriod_text() {
		return period_text;
	}
	public void setPeriod_text(String period_text) {
		this.period_text = period_text;
	}
	public String getWater_cycle() {
		return water_cycle;
	}
	public void setWater_cycle(String water_cycle) {
		this.water_cycle = water_cycle;
	}
	public String getSunlight_hours() {
		return sunlight_hours;
	}
	public void setSunlight_hours(String sunlight_hours) {
		this.sunlight_hours = sunlight_hours;
	}
	
}
