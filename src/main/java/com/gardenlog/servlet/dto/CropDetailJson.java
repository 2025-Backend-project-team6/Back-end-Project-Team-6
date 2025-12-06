package com.gardenlog.servlet.dto;

public class CropDetailJson {
	// 1. 재배 적 특성
		private String scientificName;
		private String classification;
		private String suitableSoil;
		private String physiologicalFeatures;
		private String mainTechniques;
		
		// 2. 생육 온도 정보
		private String suitableTempGermination;
		private String suitableTempGrowth;
		private String suitableTempFlower;
		
		// 3. 씨뿌림 및 수확 일정
		private String plantingSchedule;
		private String sowingInfo;
		private String harvestInfo;
		
		// 4. 관리 작업 (비료 포함)
		private String managementInfo;
		private String fertilizerInfo;
		
		// 5. 기상재해 및 생리장해 대책
		private String disasterCountermeasures;

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
