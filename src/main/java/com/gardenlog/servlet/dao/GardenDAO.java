package com.gardenlog.servlet.dao;

import java.sql.*;

import com.gardenlog.servlet.dto.GardenDTO;
import com.gardenlog.servlet.util.JdbcConnectUtil;
import com.mysql.cj.jdbc.JdbcConnection;

public class GardenDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	final String GARDEN_GARDENCOUNT = "select count(gardenid) from garden where userid=?;";
	final String GARDEN_ADDGARDEN = "insert into garden(userid, gardenname, location, area, start_date) values(?, ?, ?, ?, ?);";
	
	public int gardenCount(String userid) {
		int count = 0;
		
		try {			
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(GARDEN_GARDENCOUNT);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1); //rs.getInt("count(gardenid)");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt, rs);
		}
		return count;
	}
	
	
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
}
