package com.gardenlog.servlet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.gardenlog.servlet.dto.MyCropDTO;
import com.gardenlog.servlet.util.JdbcConnectUtil;

public class MyCropDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	private final String CROP_SEARCHCROP = 
			"SELECT mc.*, c.crop_nm AS category, g.gardenname " + 
			"FROM my_crop mc " +
			"JOIN crop_info ci ON mc.cropid = ci.cropid " +
			"JOIN crops c ON ci.crop_code = c.crop_code " +
			"JOIN garden g ON mc.gardenid = g.gardenid " +
			"WHERE mc.userid=? and mc.nickname like ?;";
	private final String CROP_ALLCROP = 
			"SELECT mc.*, c.crop_nm AS category, g.gardenname " + 
			"FROM my_crop mc " +
			"JOIN crop_info ci ON mc.cropid = ci.cropid " +
			"JOIN crops c ON ci.crop_code = c.crop_code " +
			"JOIN garden g ON mc.gardenid = g.gardenid " +
			"WHERE mc.userid=?;";
	
	
	public List<MyCropDTO> searchMyCrop(String userid, String keyword){
		List<MyCropDTO> list = new ArrayList<>();
		
		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(CROP_SEARCHCROP);
			
			pstmt.setString(1, userid);
			pstmt.setString(2, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MyCropDTO mcdto = resultSetTOCrop(rs);
				list.add(mcdto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt, rs);
		}
		
		return list;
	}
	
	
	public List<MyCropDTO> allMyCrop(String userid) {
		List<MyCropDTO> list = new ArrayList<>();
		
		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(CROP_ALLCROP);
			
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MyCropDTO mcdto = resultSetTOCrop(rs);
				list.add(mcdto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt, rs);
		}
		
		return list;
	}
	
	
	private MyCropDTO resultSetTOCrop(ResultSet rs) throws SQLException {
		MyCropDTO mcdto = new MyCropDTO();
		
		mcdto.setUserid(rs.getString("userid"));
		mcdto.setGardenid(rs.getInt("gardenid"));
		mcdto.setGardenname(rs.getString("gardenname"));
		mcdto.setCategory(rs.getString("category"));
		mcdto.setCropid(rs.getInt("cropid"));
		mcdto.setNickname(rs.getString("nickname"));
		mcdto.setPlanted_date(rs.getDate("planted_at").toLocalDate());
		mcdto.setWater_count(rs.getInt("water_count"));
		mcdto.setLast_watered_at(rs.getTimestamp("last_watered_at").toLocalDateTime());
		mcdto.setStatus(rs.getString("status"));
		
		return mcdto;
	}
	
}
