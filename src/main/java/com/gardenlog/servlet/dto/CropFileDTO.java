package com.gardenlog.servlet.dto;

public class CropFileDTO {
	private String crop_code;
	private String contents_no;
	private String file_url;
	private String file_name;
	private String file_se_code;
	private String original_file_name;
	private String title;
	
	public String getCrop_code() {
		return crop_code;
	}
	public void setCrop_code(String crop_code) {
		this.crop_code = crop_code;
	}
	public String getContents_no() {
		return contents_no;
	}
	public void setContents_no(String contents_no) {
		this.contents_no = contents_no;
	}
	public String getFile_url() {
		return file_url;
	}
	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getFile_se_code() {
		return file_se_code;
	}
	public void setFile_se_code(String file_se_code) {
		this.file_se_code = file_se_code;
	}
	public String getOriginal_file_name() {
		return original_file_name;
	}
	public void setOriginal_file_name(String original_file_name) {
		this.original_file_name = original_file_name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
