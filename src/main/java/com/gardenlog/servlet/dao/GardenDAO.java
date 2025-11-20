package com.gardenlog.servlet.dao;

import java.sql.*;

import com.gardenlog.servlet.dto.GardenDTO;
import com.gardenlog.servlet.util.JdbcConnectUtil;
import com.mysql.cj.jdbc.JdbcConnection;

public class GardenDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	final String GARDEN_CROPCOUNT = "select crop_count form garden where userid=?;";
	
	public int checkCropCount(String userid){
		int count = 0;
		
		try {			
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(GARDEN_CROPCOUNT);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt, rs);
		}
		
		return count;
	}
}
