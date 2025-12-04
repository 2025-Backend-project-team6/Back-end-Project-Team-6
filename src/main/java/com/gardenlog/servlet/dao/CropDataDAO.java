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
		
		return cddto;
	}

}
