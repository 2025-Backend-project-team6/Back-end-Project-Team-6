package com.gardenlog.servlet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gardenlog.servlet.dto.UserDTO;
import com.gardenlog.servlet.util.JdbcConnectUtil;

public class UserDAO {
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private final String USER_JOIN = "insert into users(userid, password, username, email) values(?, ?, ?, ?)";
	
	private final String USER_LOGIN = "select * from users where userid = ? and password = ?";

	
	/**
	 * 회원가입 (기존과 동일)
	 */
	public int userJoin(UserDTO udto) {
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
	
	
	/**
	 * 로그인 (매개변수 및 쿼리 수정)
	 */
	public UserDTO login(String userid, String password) { 
		UserDTO user = null;
		
		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(USER_LOGIN);
			pstmt.setString(1, userid);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				user = new UserDTO();
				user.setUserid(rs.getString("userid"));
				user.setPassword(rs.getString("password"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setLevel(rs.getString("level"));
				user.setProfile_path(rs.getString("profile_path"));
				user.setCreate_at(rs.getString("create_at"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt, rs);
		}
		
		return user;
	}
}