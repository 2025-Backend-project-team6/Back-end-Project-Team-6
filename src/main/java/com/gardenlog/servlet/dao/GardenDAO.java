package com.gardenlog.servlet.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.gardenlog.servlet.dto.GardenDTO;
import com.gardenlog.servlet.util.JdbcConnectUtil;

public class GardenDAO {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private final String GARDEN_ADDGARDEN = "insert into garden(userid, gardenname, location, area, start_date) values(?, ?, ?, ?, ?);";
	private final String GARDEN_SEARCHGARDEN = "select * from garden where userid=? and gardenname like ?;";
	private final String GARDEN_GETALLGARDEN = "select * from garden where userid=?;";
	private final String GARDEN_DETAILGARDEN = "select * from garden where gardenid=?;";
	private final String ADDCROP_GETGARDENID = "select gardenid from garden where userid=? and gardenname=?;";
	
	public int addGarden(GardenDTO gdto) {
		int result =0;
		
		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(GARDEN_ADDGARDEN);
			
			pstmt.setString(1, gdto.getUserid());
			pstmt.setString(2, gdto.getGardenname());
			pstmt.setString(3, gdto.getLocation());
			pstmt.setInt(4, gdto.getArea());
			pstmt.setDate(5, gdto.getStart_date());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt);
		}
		return result;
	}
	
	
	public List<GardenDTO> searchGarden(String userid, String keyword){
		List<GardenDTO> list = new ArrayList<>();
		
		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(GARDEN_SEARCHGARDEN);
			
			pstmt.setString(1, userid);
			pstmt.setString(2, "%" + keyword + "%");
			rs = pstmt.executeQuery();

			while(rs.next()) {
				GardenDTO gdto = resultSetToGarden(rs);
				list.add(gdto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt, rs);
		}
		return list;	
	}
	
	
	public List<GardenDTO> getAllGarden(String userid){
		List<GardenDTO> list = new ArrayList<>();
		
		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(GARDEN_GETALLGARDEN);
			
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				GardenDTO gdto = resultSetToGarden(rs);
				list.add(gdto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt, rs);
		}
		return list;
	}
	
	
	public GardenDTO getDetailGarden(int gardenid) {
		GardenDTO gdto = null;
		
		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(GARDEN_DETAILGARDEN);
			
			pstmt.setInt(1, gardenid);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				gdto = resultSetToGarden(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt, rs);
		}
		return gdto;
	}
	
	public int getGardenid(String userid, String selectedGarden) {
		int gardenid = 0;
		
		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(ADDCROP_GETGARDENID);
			
			pstmt.setString(1, userid);
			pstmt.setString(2, selectedGarden);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				gardenid = rs.getInt("gardenid");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt, rs);
		}
		
		return gardenid;
	}
	
	private GardenDTO resultSetToGarden(ResultSet rs) throws SQLException {
		GardenDTO gdto = new GardenDTO();
		
		gdto.setGardenid(rs.getInt("gardenid"));
		gdto.setUserid(rs.getString("userid"));
		gdto.setGardenname(rs.getString("gardenname"));
		gdto.setLocation(rs.getString("location"));
		gdto.setArea(rs.getInt("area"));
		gdto.setCrop_count(rs.getInt("crop_count"));
		gdto.setStart_date(rs.getDate("start_date"));
		gdto.setExpected_harvest_date(rs.getDate("expected_harvest_date"));
					
		return gdto;
	}
	
}