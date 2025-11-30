package com.gardenlog.servlet.dao;

import java.sql.*;
import java.util.*;

import com.gardenlog.servlet.dto.CropDTO;
import com.gardenlog.servlet.util.JdbcConnectUtil;

public class CropDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	private final String INSERT_CROPLIST = "insert into crops(crop_nm, crop_code, sort_order) values(?, ?, ?);";
	private final String SELECT_CROPLIST = "select crop_nm, crop_code, sort_order from crops;";
	
	public void saveAll(List<CropDTO> cropList) {
		try {
			conn = JdbcConnectUtil.getConnection();
			conn.setAutoCommit(false);;
		
			pstmt = conn.prepareStatement(INSERT_CROPLIST);
			for(CropDTO crop: cropList) {
				pstmt.setString(1, crop.getCrop_nm());
				pstmt.setString(2, crop.getCrop_code());
				pstmt.setInt(3, crop.getSort_order());
				pstmt.addBatch();
			}
			
			pstmt.executeBatch();
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {conn.rollback();} catch (SQLException e1) {e1.printStackTrace();}
			
		} finally {
			JdbcConnectUtil.close(conn, pstmt);
		}
	}

	
	public List<CropDTO> getAllCrops() {
		List<CropDTO> cropList = new ArrayList<CropDTO>();
		
		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(SELECT_CROPLIST);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CropDTO cdto = new CropDTO();
				cdto.setCrop_nm(rs.getString("crop_nm"));
				cdto.setCrop_code(rs.getString("crop_code"));
				cdto.setSort_order(rs.getInt("sort_order"));
			
				cropList.add(cdto);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt, rs);
		}
		
		return cropList;
	}
}
