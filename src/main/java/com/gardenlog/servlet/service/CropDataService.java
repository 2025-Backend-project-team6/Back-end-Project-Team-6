package com.gardenlog.servlet.service;

import java.util.ArrayList;

import java.util.List;

import com.gardenlog.servlet.dao.CropDataDAO;

import com.gardenlog.servlet.dto.CropDataDTO;

import com.gardenlog.servlet.dto.CropDetailDTO; // ⭐️ 상세 페이지 최종 DTO

import com.gardenlog.servlet.dto.CropDetailJson; // ⭐️ JSON 구조 매핑용 DTO (⭐️ import 수정)

import com.gardenlog.servlet.dto.CropViewDTO;

import com.google.gson.Gson; // ⭐️ Gson 라이브러리 import

public class CropDataService {

	private CropDataDAO cropDataDAO = new CropDataDAO();

	private final Gson gson = new Gson(); // Gson 객체 생성

	// CropDataService.java 내부

	// ⭐️ 목록 페이지를 위한 메소드를 추가합니다.

	public List<CropViewDTO> getAllCropDataForView() {

		// 1. DAO를 호출하여 DB 원본 데이터 (CropDataDTO 리스트)를 가져옵니다.

		List<CropDataDTO> dbList = cropDataDAO.getAllCropData();
		
		// ⭐️ [DEBUG A] DB 조회 성공 여부 확인 ⭐️

		if (dbList == null || dbList.isEmpty()) {

			System.out.println("[DEBUG] ERROR: DB에서 작물 데이터가 조회되지 않았습니다.");
			return new ArrayList<>(); // 데이터가 없으면 빈 리스트 반환

		}

		System.out.println("[DEBUG] A. DB에서 총 " + dbList.size() + "개의 작물 데이터 조회 성공!");

		// 2. View에 전달할 최종 리스트를 생성합니다.
		List<CropViewDTO> viewList = new ArrayList<>();

		// 3. 반복문을 돌며 JSON 파싱 및 DTO 변환을 수행합니다.
		for (CropDataDTO dbCrop : dbList) {

			CropViewDTO viewCrop = new CropViewDTO();
			viewCrop.setCropid(dbCrop.getCropid());
			viewCrop.setCrop_title(dbCrop.getCrop_title());
			viewCrop.setCategory_name(dbCrop.getCategory_name());
			viewCrop.setDifficulty_level(dbCrop.getDifficulty_level());
			viewCrop.setPeriod_text(dbCrop.getPeriod_text());
			viewCrop.setWater_cycle(dbCrop.getWater_cycle());
			viewCrop.setSunlight_hours(dbCrop.getSunlight_hours());
			viewList.add(viewCrop);

			// ⭐️ [DEBUG D] 최종 리스트 크기 확인 ⭐️

			System.out.println(
					"[DEBUG] 카테고리: " + viewCrop.getCategory_name() + ", 난이도: " + viewCrop.getDifficulty_level());
		}

		System.out.println("[DEBUG] D. Service 최종 반환 데이터 개수: " + viewList.size());
		return viewList;

	}

	public CropDetailDTO getCropDetail(int cropId) {

		// 1. DAO 인스턴스를 사용하여 DB 데이터 조회
		CropDataDTO dbCrop = this.cropDataDAO.getCropDataById(cropId);

		if (dbCrop == null) {
			return null; // 데이터가 없는 경우 null 반환
		}

		// 2. 상세 DTO 객체 생성
		CropDetailDTO detailDTO = new CropDetailDTO();

		// 3. 공통 정보 설정
		detailDTO.setCropid(dbCrop.getCropid());
		detailDTO.setCrop_title(dbCrop.getCrop_title());
		detailDTO.setCrop_code(dbCrop.getCrop_code());
		detailDTO.setPeriod(dbCrop.getPeriod_text());

		try {

			// 4. JSON 파싱: Gson을 사용하여 JSON 문자열을 JSON 구조 매핑용 DTO로 변환
			CropDetailJson jsonDetails = gson.fromJson(dbCrop.getInfo_json(), CropDetailJson.class);

			// 5. JSON에서 상세 정보를 추출하여 detailDTO에 설정

			// 1. 재배적 특성
			detailDTO.setScientificName(jsonDetails.getScientificName());
			detailDTO.setClassification(jsonDetails.getClassification());
			detailDTO.setSuitableSoil(jsonDetails.getSuitableSoil());
			detailDTO.setPhysiologicalFeatures(jsonDetails.getPhysiologicalFeatures());
			detailDTO.setMainTechniques(jsonDetails.getMainTechniques());

			// 2. 생육 온도 정보
			detailDTO.setSuitableTempGermination(jsonDetails.getSuitableTempGermination());
			detailDTO.setSuitableTempGrowth(jsonDetails.getSuitableTempGrowth());
			detailDTO.setSuitableTempFlower(jsonDetails.getSuitableTempFlower());

			// 3. 씨뿌림 및 수확 일정
			detailDTO.setPlantingSchedule(jsonDetails.getPlantingSchedule());
			detailDTO.setSowingInfo(jsonDetails.getSowingInfo());
			detailDTO.setHarvestInfo(jsonDetails.getHarvestInfo());

			// 4. 관리 작업 (비료 포함)
			detailDTO.setManagementInfo(jsonDetails.getManagementInfo());
			detailDTO.setFertilizerInfo(jsonDetails.getFertilizerInfo());

			// 5. 기상재해 및 생리장해 대책
			detailDTO.setDisasterCountermeasures(jsonDetails.getDisasterCountermeasures());

		} catch (Exception e) {
			System.err.println("JSON 파싱 오류: " + e.getMessage());
			// 개발 단계에서는 에러를 확인하기 위해 스택 트레이스를 출력
			e.printStackTrace();
		}
		// ⭐️ try-catch 블록 바깥에서 DTO 반환
		return detailDTO;

	}

}