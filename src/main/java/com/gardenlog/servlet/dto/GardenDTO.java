package com.gardenlog.servlet.dto;

import java.sql.Date;

public class GardenDTO {
	private Integer gardenid;
	private String userid;
	private String gardenname;
	private String location;
	private Integer area;
	private Integer crop_count;
	private Date start_date;
	private Date expected_harvest_date;
		
	public Integer getGardenid() {
		return gardenid;
	}
	public void setGardenid(Integer gardenid) {
		this.gardenid = gardenid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getGardenname() {
		return gardenname;
	}
	public void setGardenname(String gardenname) {
		this.gardenname = gardenname;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
	public Integer getCrop_count() {
		return crop_count;
	}
	public void setCrop_count(Integer crop_count) {
		this.crop_count = crop_count;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getExpected_harvest_date() {
		return expected_harvest_date;
	}
	public void setExpected_harvest_date(Date expected_harvest_date) {
		this.expected_harvest_date = expected_harvest_date;
	}
	
}