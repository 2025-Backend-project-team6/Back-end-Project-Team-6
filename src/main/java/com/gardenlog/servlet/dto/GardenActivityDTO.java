package com.gardenlog.servlet.dto;

import java.sql.Date;

public class GardenActivityDTO {
	private int activityid;
	private String userid;
	private int gardenid;
	private int cropid;
	private String activity_type;
	private Date activity_date;
	private String memo;
	
	public int getActivityid() {
		return activityid;
	}
	public void setActivityid(int activityid) {
		this.activityid = activityid;
	}
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
	public int getCropid() {
		return cropid;
	}
	public void setCropid(int cropid) {
		this.cropid = cropid;
	}
	public String getActivity_type() {
		return activity_type;
	}
	public void setActivity_type(String activity_type) {
		this.activity_type = activity_type;
	}
	public Date getActivity_date() {
		return activity_date;
	}
	public void setActivity_date(Date activity_date) {
		this.activity_date = activity_date;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}	
	
}
