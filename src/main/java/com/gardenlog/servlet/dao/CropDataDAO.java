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
	private final String GET_ALL_CROPS = "SELECT cropid, crop_title, info_json, category_name, difficulty_level, period_text, water_cycle, sunlight_hours FROM crop_info ORDER BY crop_title ASC";
	private final String CROPSBYCATEGORY = "select * from crop_info where crop_code=?;";
	private final String ALLCROP = "select * from crop_info";
	private final String CROPSBYLEVEL = "select * from crop_info where difficulty_level=?;";
	
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
	
	public List<CropDataDTO> getCropsByCategory(int crop_code){
		List<CropDataDTO> list = new ArrayList<>();
	
		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(CROPSBYCATEGORY);
			
			pstmt.setInt(1, crop_code);
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
	
	public List<CropDataDTO> getAllCrop(){
		List<CropDataDTO> list = new ArrayList<>();
		
		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(ALLCROP);
			
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
	
	public List<CropDataDTO> getCropByLevel(String levelSelect){
		List<CropDataDTO> list = new ArrayList<>();

		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(CROPSBYLEVEL);
			
			pstmt.setString(1, levelSelect);
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
	
		
	// 작물 검색 용도의 모든 작물 검색 DAO
	public List<CropDataDTO> getAllCropData() {
		System.out.println("DAO 실행 시작");
	    List<CropDataDTO> list = new ArrayList<>();

	    try {
	        conn = JdbcConnectUtil.getConnection();
	        pstmt = conn.prepareStatement(GET_ALL_CROPS); 

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
	
	// json 파싱 용도의 아이디 얻는 DAO
	public CropDataDTO getCropDataById(int cropId) {
	    CropDataDTO cddto = null;
	    final String GET_CROP_BY_ID = "SELECT cropid, crop_code, crop_title, info_json, category_name, difficulty_level, period_text, water_cycle, sunlight_hours "
                + "FROM crop_info WHERE cropid = ?;";

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
	
	private CropDataDTO resultSetToCrop(ResultSet rs) throws SQLException {
		CropDataDTO cddto = new CropDataDTO();
		
		cddto.setCropid(rs.getInt("cropid"));
		try {
	        cddto.setCrop_code(rs.getString("crop_code"));
	    } catch (SQLException e) {
	        // crop_code가 포함되지 않은 쿼리(SELECT *)로 호출될 때 발생하는 오류를 무시하고
	        // 빈 문자열로 설정하여 프로그램 충돌을 막습니다.
	        cddto.setCrop_code("");
	        // System.err.println("Warning: crop_code 컬럼을 ResultSet에서 찾지 못했습니다.");
	    }
		cddto.setCrop_title(rs.getString("crop_title"));
		cddto.setInfo_json(rs.getString("info_json"));
		
		cddto.setCategory_name(rs.getString("category_name"));
	    cddto.setDifficulty_level(rs.getString("difficulty_level"));
	    cddto.setPeriod_text(rs.getString("period_text"));
	    cddto.setWater_cycle(rs.getString("water_cycle"));
	    cddto.setSunlight_hours(rs.getString("sunlight_hours"));
		return cddto;
	}

}
