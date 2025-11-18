package com.gardenlog.servlet.dto;

public class UserDTO {
	private String userid; // 아이디
	private String password; // 비밀번호
	private String username; // 이름
	private String email; // 이메일
	private int level; // 회원 등급 
	private String profile_path; // 프로필 사진 경로
	private String role; // 관리자 / 회원 구분
	private String create_at; // 회원 생성 일자
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getProfile_path() {
		return profile_path;
	}
	public void setProfile_path(String profile_path) {
		this.profile_path = profile_path;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getCreate_at() {
		return create_at;
	}
	public void setCreate_at(String create_at) {
		this.create_at = create_at;
	}
	
	
}
