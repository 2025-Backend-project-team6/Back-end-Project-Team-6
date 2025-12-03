package com.gardenlog.servlet.dto;

public class CropDataDTO {
	private String crop_code;
	private String crop_title;
	private String info_json;
	
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
	
}
