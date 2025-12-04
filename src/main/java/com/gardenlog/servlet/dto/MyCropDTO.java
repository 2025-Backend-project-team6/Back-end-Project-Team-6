package com.gardenlog.servlet.dto;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MyCropDTO {
	private String userid;
	private int gardenid;
	private String gardenname;
	private String category;
	private int cropid;
	private String nickname;
	private Date planted_date;
	private int water_count;
	private Date last_watered_at;
	private String status;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getGardenid() {
		return gardenid;
	}
	public void setGardenid(int gardenid) {
		this.gardenid = gardenid;
	}
	public String getGardenname() {
		return gardenname;
	}
	public void setGardenname(String gardenname) {
		this.gardenname = gardenname;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getCropid() {
		return cropid;
	}
	public void setCropid(int cropid) {
		this.cropid = cropid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Date getPlanted_date() {
		return planted_date;
	}
	public void setPlanted_date(Date planted_date) {
		this.planted_date = planted_date;
	}
	public int getWater_count() {
		return water_count;
	}
	public void setWater_count(int water_count) {
		this.water_count = water_count;
	}
	public Date getLast_watered_at() {
		return last_watered_at;
	}
	public void setLast_watered_at(Date last_watered_at) {
		this.last_watered_at = last_watered_at;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}	
	
}
