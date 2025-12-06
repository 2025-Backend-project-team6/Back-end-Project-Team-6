package com.gardenlog.servlet.dto;

public class CropDetailDTO {
	private int cropid;             // 작물 ID
	private String crop_title;      // 작물명
	private String crop_code;       // 작물 코드
	
    // ⭐️ info_json에서 파싱하여 담을 필드들 (첨부 파일의 주요 항목)
    
	// 1. 재배 적 특성
	private String scientificName;  // 학명 (예: Cucurbita maxima Duchesne)
	private String classification;  // 분류 (예: 박과)
	private String suitableSoil;    // 재배 적지 (예: 토양산도 pH 5.6~6.5)
	private String physiologicalFeatures; // 생리적 특성 요약 (문자열 리스트나 긴 문자열로 처리)
	private String mainTechniques;  // 주요 기술 요약 (문자열 리스트나 긴 문자열로 처리)
	
	// 2. 생육 온도 정보
	private String suitableTempGermination; // 발아적온 (예: 25~28℃)
	private String suitableTempGrowth;      // 생육적온 (예: 20~22℃)
	private String suitableTempFlower;      // 개화적온 (예: 10~12℃)
	
	// 3. 씨뿌림 및 수확 일정
	private String plantingSchedule; // 작형별 출하시기 표 (JSON 배열 또는 객체 리스트로 파싱하여 문자열로 저장)
	private String sowingInfo;       // 씨뿌림 상세 정보 (노지, 난지, 씨앗양 등)
	private String harvestInfo;      // 수확 작업 상세 정보 (수확적기, 큐어링 등)
	
	// 4. 관리 작업 (비료 포함)
	private String managementInfo;   // 심는 거리, 유인 및 정지 등 관리 작업
	private String fertilizerInfo;   // 비료주기 표 (JSON 배열 또는 문자열로 저장)
	
	// 5. 기상재해 및 생리장해 대책
	private String disasterCountermeasures; // 재해 대책 정보

	
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

	public String getCrop_code() {
		return crop_code;
	}

	public void setCrop_code(String crop_code) {
		this.crop_code = crop_code;
	}

	public String getScientificName() {
		return scientificName;
	}

	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getSuitableSoil() {
		return suitableSoil;
	}

	public void setSuitableSoil(String suitableSoil) {
		this.suitableSoil = suitableSoil;
	}

	public String getPhysiologicalFeatures() {
		return physiologicalFeatures;
	}

	public void setPhysiologicalFeatures(String physiologicalFeatures) {
		this.physiologicalFeatures = physiologicalFeatures;
	}

	public String getMainTechniques() {
		return mainTechniques;
	}

	public void setMainTechniques(String mainTechniques) {
		this.mainTechniques = mainTechniques;
	}

	public String getSuitableTempGermination() {
		return suitableTempGermination;
	}

	public void setSuitableTempGermination(String suitableTempGermination) {
		this.suitableTempGermination = suitableTempGermination;
	}

	public String getSuitableTempGrowth() {
		return suitableTempGrowth;
	}

	public void setSuitableTempGrowth(String suitableTempGrowth) {
		this.suitableTempGrowth = suitableTempGrowth;
	}

	public String getSuitableTempFlower() {
		return suitableTempFlower;
	}

	public void setSuitableTempFlower(String suitableTempFlower) {
		this.suitableTempFlower = suitableTempFlower;
	}

	public String getPlantingSchedule() {
		return plantingSchedule;
	}

	public void setPlantingSchedule(String plantingSchedule) {
		this.plantingSchedule = plantingSchedule;
	}

	public String getSowingInfo() {
		return sowingInfo;
	}

	public void setSowingInfo(String sowingInfo) {
		this.sowingInfo = sowingInfo;
	}

	public String getHarvestInfo() {
		return harvestInfo;
	}

	public void setHarvestInfo(String harvestInfo) {
		this.harvestInfo = harvestInfo;
	}

	public String getManagementInfo() {
		return managementInfo;
	}

	public void setManagementInfo(String managementInfo) {
		this.managementInfo = managementInfo;
	}

	public String getFertilizerInfo() {
		return fertilizerInfo;
	}

	public void setFertilizerInfo(String fertilizerInfo) {
		this.fertilizerInfo = fertilizerInfo;
	}

	public String getDisasterCountermeasures() {
		return disasterCountermeasures;
	}

	public void setDisasterCountermeasures(String disasterCountermeasures) {
		this.disasterCountermeasures = disasterCountermeasures;
	}


}
