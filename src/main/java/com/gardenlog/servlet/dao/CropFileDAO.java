package com.gardenlog.servlet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gardenlog.servlet.dto.CropFileDTO;
import com.gardenlog.servlet.util.JdbcConnectUtil;

public class CropFileDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	private final String INSERT_CROPFILE = 
		"insert into crop_files(crop_code, contents_no, file_url, file_name, file_se_code, original_file_name, title) values(?, ?, ?, ?, ?, ?, ?);";
	private static final String SELECT_ALL =
	        "SELECT crop_code, contents_no, file_url, file_name, file_se_code, original_file_name, title FROM crop_files;";
	
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
	
	
    public List<CropFileDTO> getAll() {

        List<CropFileDTO> list = new ArrayList<>();

        try (Connection conn = JdbcConnectUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {

                CropFileDTO dto = new CropFileDTO();

                // DTO의 스네이크 케이스 setter에 맞춰 작성
                dto.setCrop_code(rs.getString("crop_code"));
                dto.setContents_no(rs.getString("contents_no"));
                dto.setFile_url(rs.getString("file_url"));
                dto.setFile_name(rs.getString("file_name"));
                dto.setFile_se_code(rs.getString("file_se_code"));
                dto.setOriginal_file_name(rs.getString("original_file_name"));
                dto.setTitle(rs.getString("title"));

                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
	
}
