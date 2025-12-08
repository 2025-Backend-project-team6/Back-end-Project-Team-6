package com.gardenlog.servlet.dto;

public class CropDTO {
	private String crop_nm;
	private String crop_code;
	private int sort_order;
	
	public String getCrop_nm() {
		return crop_nm;
	}
	public void setCrop_nm(String crop_nm) {
		this.crop_nm = crop_nm;
	}
	public String getCrop_code() {
		return crop_code;
	}
	public void setCrop_code(String crop_code) {
		this.crop_code = crop_code;
	}
	public int getSort_order() {
		return sort_order;
	}
	public void setSort_order(int sort_order) {
		this.sort_order = sort_order;
	}
	
}
