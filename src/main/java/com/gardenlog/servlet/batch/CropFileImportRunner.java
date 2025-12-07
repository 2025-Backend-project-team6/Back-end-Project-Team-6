package com.gardenlog.servlet.batch;

import java.util.ArrayList;
import java.util.List;

import com.gardenlog.servlet.api.WorkScheduleListApi;
import com.gardenlog.servlet.dao.CropDAO;
import com.gardenlog.servlet.dao.CropFileDAO;
import com.gardenlog.servlet.dto.CropDTO;
import com.gardenlog.servlet.dto.CropFileDTO;

public class CropFileImportRunner {

	public static void main(String[] args) {
		try {
			CropDAO cdao = new CropDAO();
			CropFileDAO cfdao = new CropFileDAO();
			WorkScheduleListApi api = new WorkScheduleListApi();
			
			List<CropDTO> cropList = cdao.getAllCrops();
			System.out.println("총 작물 수: " + cropList.size());
			
			List<CropFileDTO> saveList = new ArrayList<>();
			
			for(CropDTO cdto: cropList) {
				String cropCode = cdto.getCrop_code();
				System.out.println("API 호출 중: " + cropCode);
				
				List<CropFileDTO> fileList = api.fetchCropFile(cropCode);
				System.out.println("   → 받은 파일 수:" + fileList.size());
				
				saveList.addAll(fileList);
			}
			
			cfdao.saveAllFile(saveList);
			System.out.println("crop_files 테이블에 저장 완료");
				
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}

}
