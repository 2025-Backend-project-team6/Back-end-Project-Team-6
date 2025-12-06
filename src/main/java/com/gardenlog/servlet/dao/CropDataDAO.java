package com.gardenlog.servlet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gardenlog.servlet.dto.CropDataDTO;
import com.gardenlog.servlet.util.JdbcConnectUtil;

public class CropDataDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	private final String ADDCROP_SEARCHCROP= "select * from crop_info where crop_title like ?;";
	private final String ADDCROP_GETCROPID = "select cropid from crop_info where crop_title=?;";
	
	// 작물 검색
	private final String GET_ALL_CROPS = "SELECT cropid, crop_title, info_json, "
            + "category_name, difficulty_level, period_text, water_cycle, sunlight_hours "
            + "FROM crop_info ORDER BY crop_title ASC;";
	
	public List<CropDataDTO> searchCropData(String keyword) {
		List<CropDataDTO> list = new ArrayList<>();

		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(ADDCROP_SEARCHCROP);
			
			pstmt.setString(1, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CropDataDTO cddto = resultSetToCrop(rs);
				list.add(cddto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt, rs);
		}
		 
		return list;	
	}
	
	public int getCropid(String selectedCrop) {
		int cropid = 0;

		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(ADDCROP_GETCROPID);
			
			pstmt.setString(1, selectedCrop);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cropid = rs.getInt("cropid");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt, rs);
		}
		
		return cropid;
	}
	
	private CropDataDTO resultSetToCrop(ResultSet rs) throws SQLException {
		CropDataDTO cddto = new CropDataDTO();
		
		cddto.setCropid(rs.getInt("cropid"));
		cddto.setCrop_code(rs.getString("crop_code"));
		cddto.setCrop_title(rs.getString("crop_title"));
		cddto.setInfo_json(rs.getString("info_json"));
		
		cddto.setCategory_name(rs.getString("category_name"));
	    cddto.setDifficulty_level(rs.getString("difficulty_level"));
	    cddto.setPeriod_text(rs.getString("period_text"));
	    cddto.setWater_cycle(rs.getString("water_cycle"));
	    cddto.setSunlight_hours(rs.getString("sunlight_hours"));
		return cddto;
	}
	
	// 작물 검색 용도의 모든 작물 검색 DAO
	public List<CropDataDTO> getAllCropData() {
	    
	    List<CropDataDTO> list = new ArrayList<>();

	    try {
	        // 2. JDBC 연결 및 PreparedStatement 준비
	        conn = JdbcConnectUtil.getConnection();
	        pstmt = conn.prepareStatement(GET_ALL_CROPS); 
	        
	        // 3. 쿼리 실행 (파라미터 설정은 필요 없음)
	        rs = pstmt.executeQuery();
	        
	        // 4. ResultSet 순회 및 DTO 변환
	        while(rs.next()) {
	            CropDataDTO cddto = resultSetToCrop(rs); 
	            list.add(cddto);
	        }
	        
	    } catch (SQLException e) {
	        // SQL 관련 오류 발생 시 예외 출력
	        e.printStackTrace();
	    } finally {
	        // 5. JDBC 자원 해제 (가장 중요!)
	        JdbcConnectUtil.close(conn, pstmt, rs);
	    }
	    
	    return list;
	}
	
	// json 파싱 용도의 아이디 얻는 DAO
	public CropDataDTO getCropDataById(int cropId) {
	    CropDataDTO cddto = null;
	    final String GET_CROP_BY_ID = "SELECT * FROM crop_info WHERE cropid = ?;";

	    try {
	        conn = JdbcConnectUtil.getConnection();
	        pstmt = conn.prepareStatement(GET_CROP_BY_ID);
	        
	        pstmt.setInt(1, cropId); // ID 설정
	        rs = pstmt.executeQuery();
	        
	        if(rs.next()) {
	            cddto = resultSetToCrop(rs); // 이미 정의한 변환 메소드 사용
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JdbcConnectUtil.close(conn, pstmt, rs);
	    }
	    return cddto;
	}

}
