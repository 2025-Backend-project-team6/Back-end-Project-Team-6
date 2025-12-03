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
	
	public List<CropDataDTO> searchCropData(String keyword) {
		List<CropDataDTO> list = new ArrayList<>();

		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(ADDCROP_SEARCHCROP);
			
			pstmt.setString(1, keyword);
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
	
	private CropDataDTO resultSetToCrop(ResultSet rs) throws SQLException {
		CropDataDTO cddto = new CropDataDTO();
		
		cddto.setCrop_code(rs.getString("crop_code"));
		cddto.setCrop_title(rs.getString("crop_title"));
		cddto.setCrop_title(rs.getString("info_json"));
		
		return cddto;
	}

}
