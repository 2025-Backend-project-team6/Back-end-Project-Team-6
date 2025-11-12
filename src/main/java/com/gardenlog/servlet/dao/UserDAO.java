package com.gardenlog.servlet.dao;

import java.sql.*;
import java.util.*;
import com.gardenlog.servlet.dto.UserDTO;
import com.gardenlog.servlet.util.JdbcConnectUtil;


public class UserDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	final String USER_JOIN = "insert into users(userid, password, username, email) values(?, ?, ?, ?);";
	
	
	public int userJoin(UserDTO udto){
		int result = 0;

		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(USER_JOIN);
			
			pstmt.setString(1, udto.getUserid());
			pstmt.setString(2, udto.getPassword());
			pstmt.setString(3, udto.getUsername());
			pstmt.setString(4, udto.getEmail());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt);
		}
		
		return result;	
	}
	
}
