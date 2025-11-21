package com.gardenlog.servlet.dao;

import java.sql.*;
import java.util.*;
import com.gardenlog.servlet.dto.UserDTO;
import com.gardenlog.servlet.util.JdbcConnectUtil;


public class UserDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	// 회원가입
	final String USER_JOIN = "insert into users(userid, password, username, email) values(?, ?, ?, ?);";
	
	// 관리자 페이지 조회, 수정, 삭제
	final String SELECT_USER_LIST = "select * from users;";
	final String USER_DELETE = "DELETE FROM users WHERE userid = ?";
	final String USER_UPDATE = "UPDATE users SET level = ?, role = ?, user_status = ? WHERE userid = ?";
	
	
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
	
	public List<UserDTO> selectUserListAdmin(){
		
		List<UserDTO> memberList = new ArrayList<>();
		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(SELECT_USER_LIST);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				UserDTO udto = new UserDTO();
				udto.setUserid(rs.getString("userid"));
				udto.setPassword(rs.getString("password")); 
				udto.setUsername(rs.getString("username"));
				udto.setEmail(rs.getString("email"));
				udto.setRole(rs.getString("user_role")); 
				udto.setProfile_path(rs.getString("profile_path"));
				udto.setUser_status(rs.getString("user_status"));
				Timestamp ts = rs.getTimestamp("created_at");
				udto.setCreated_at(ts.toLocalDateTime());
				
				memberList.add(udto);
				
		} } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt, rs);
		}
	
		return memberList;
	}
	
	
public int deleteUserAdmmin(String userId){
		
		int result = 0;
		
		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(USER_DELETE);
			pstmt.setString(1, userId);
			result = pstmt.executeUpdate();
			
				
		 } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt);
		}
	
		return result;
	}
	
public int updateUserAdmin(UserDTO udto){
	
	int result = 0;
	UserDTO udto1 = new UserDTO();
	try {
		conn = JdbcConnectUtil.getConnection();
		pstmt = conn.prepareStatement(USER_UPDATE);
		pstmt.setInt(1, udto1.getLevel());
		pstmt.setString(2, udto1.getRole());
		pstmt.setString(3, udto1.getUser_status());
		
		pstmt.setString(4, udto.getUserid());
		
		result = pstmt.executeUpdate();
		
	 } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		JdbcConnectUtil.close(conn, pstmt);
	}

	return result;
}
	
	
}
