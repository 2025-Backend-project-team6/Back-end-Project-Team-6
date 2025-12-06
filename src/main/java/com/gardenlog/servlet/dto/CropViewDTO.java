package com.gardenlog.servlet.dto;

public class CropViewDTO {
	
	private int cropid;         // 작물 ID (자세히 보기 버튼 클릭 시 사용)
	private String crop_title;  // 작물명 (예: "토마토", "상추")
	
	// info_json에서 파싱하여 담을 상세 정보 (카드에 표시되는 내용)
		private String category;    // 카테고리 (예: "열매")
		private String difficulty;  // 난이도 (예: "중급")
		
		// info_json에서 파싱하여 담을 관리 정보
		private String period;      // 재배 기간 (예: "80-100일")
		private String waterCycle;  // 물 주는 주기 (예: "2-3일에 1회")
		private String sunlight;    // 일조 시간 (예: "하루 6-8시간")
		
		
		
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
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public String getDifficulty() {
			return difficulty;
		}
		public void setDifficulty(String difficulty) {
			this.difficulty = difficulty;
		}
		public String getPeriod() {
			return period;
		}
		public void setPeriod(String period) {
			this.period = period;
		}
		public String getWaterCycle() {
			return waterCycle;
		}
		public void setWaterCycle(String waterCycle) {
			this.waterCycle = waterCycle;
		}
		public String getSunlight() {
			return sunlight;
		}
		public void setSunlight(String sunlight) {
			this.sunlight = sunlight;
		}
}
