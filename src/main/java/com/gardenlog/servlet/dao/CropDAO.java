package com.gardenlog.servlet.dao;

import java.sql.*;
import java.util.*;

import com.gardenlog.servlet.dto.CropDTO;
import com.gardenlog.servlet.util.JdbcConnectUtil;

public class CropDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	private final String INSERT_CROPLIST = "insert into crops(crop_nm, crop_code, sort_order) values(?, ?, ?);";
	
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
}
