package com.gardenlog.servlet.dto;

public class CropViewDTO {
	
	private int cropid;         // 작물 ID (자세히 보기 버튼 클릭 시 사용)
	private String crop_title;  // 작물명 (예: "토마토", "상추")
	
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
		public String getCrop_title() {
			return crop_title;
		}
		public void setCrop_title(String crop_title) {
			this.crop_title = crop_title;
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
