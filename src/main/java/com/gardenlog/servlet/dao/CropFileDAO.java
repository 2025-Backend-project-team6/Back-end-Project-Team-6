package com.gardenlog.servlet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.gardenlog.servlet.dto.CropFileDTO;
import com.gardenlog.servlet.util.JdbcConnectUtil;

public class CropFileDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	private final String INSERT_CROPFILE = 
		"insert into crop_files(crop_code, contents_no, file_url, file_name, file_se_code, original_file_name, title) values(?, ?, ?, ?, ?, ?, ?);";
	
	public void saveAllFile(List<CropFileDTO> cfList) {
		try {
			conn = JdbcConnectUtil.getConnection();
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(INSERT_CROPFILE);
			for(CropFileDTO cfdto: cfList) {
				pstmt.setString(1, cfdto.getCrop_code());
				pstmt.setString(2, cfdto.getContents_no());
				pstmt.setString(3, cfdto.getFile_url());
				pstmt.setString(4, cfdto.getFile_name());
				pstmt.setString(5, cfdto.getFile_se_code());
				pstmt.setString(6, cfdto.getOriginal_file_name());
				pstmt.setString(7, cfdto.getTitle());
				
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
