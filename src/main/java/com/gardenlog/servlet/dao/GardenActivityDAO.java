package com.gardenlog.servlet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gardenlog.servlet.dto.GardenActivityDTO;
import com.gardenlog.servlet.util.JdbcConnectUtil;

public class GardenActivityDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	private final String GARDEN_ACTIVITY = "select * from garden_activity " 
										 + "where userid=? and gardenid=? "
										 + "and year(activity_date)=? and month(activity_date)=? "
										 + "order by activity_date asc;";
	private final String GARDEN_ADDACTIVTIY = "insert into garden_activity(userid, gardenid, cropid, activity_type, activity_date, memo) "
											+ "values(?, ?, ?, ?, ?, ?);";
	
	public List<GardenActivityDTO> getMonthlyActivity(String userid, int gardenid, int year, int month){
		List<GardenActivityDTO> list = new ArrayList<>();
		
		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(GARDEN_ACTIVITY);
			
			pstmt.setString(1, userid);
			pstmt.setInt(2, gardenid);
			pstmt.setInt(3, year);
			pstmt.setInt(4, month);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				GardenActivityDTO gadto = resultSetToActivity(rs);
				list.add(gadto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt, rs);
		}
		
		return list;
	}
	
	public int addActivity(GardenActivityDTO gadto) {
		int result = 0;
		
		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(GARDEN_ADDACTIVTIY);
			
	        pstmt.setString(1, gadto.getUserid());
	        pstmt.setInt(2, gadto.getGardenid());
	        pstmt.setInt(3, gadto.getCropid());
	        pstmt.setString(4, gadto.getActivity_type());
	        pstmt.setDate(5, new java.sql.Date(gadto.getActivity_date().getTime()));
	        pstmt.setString(6, gadto.getMemo());
	        result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt);
		}
		
		return result;
	}
	
	
	private GardenActivityDTO resultSetToActivity(ResultSet rs) throws SQLException {
        GardenActivityDTO gadto = new GardenActivityDTO();
        gadto.setActivityid(rs.getInt("activityid"));
        gadto.setUserid(rs.getString("userid"));
        gadto.setGardenid(rs.getInt("gardenid"));
        gadto.setCropid(rs.getInt("cropid"));
        gadto.setActivity_type(rs.getString("activity_type"));
        gadto.setActivity_date(rs.getDate("activity_date"));
        gadto.setMemo(rs.getString("memo"));
		
		return gadto;
	}
}
