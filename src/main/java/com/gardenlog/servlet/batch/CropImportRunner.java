package com.gardenlog.servlet.batch;

import java.util.List;

import com.gardenlog.servlet.api.WorkScheduleGrpListApi;
import com.gardenlog.servlet.dao.CropDAO;
import com.gardenlog.servlet.dto.CropDTO;

public class CropImportRunner {

	public static void main(String[] args) {
		try {
			WorkScheduleGrpListApi api = new WorkScheduleGrpListApi();
			CropDAO cdao = new CropDAO();
			
			List<CropDTO> cropList = api.fetchCrops();
			System.out.println("API로 받은 품목 수: " + cropList.size());
			
			cdao.saveAll(cropList);
			System.out.println("DB 저장 완료");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
