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
	final String USER_SELECT_ONE = "SELECT userid, username, email, level, user_status, created_at FROM users WHERE userid = ?";
	final String SELECT_USER_LIST = "select * from users;";
	final String USER_DELETE = "DELETE FROM users WHERE userid = ?";
	final String USER_UPDATE = "UPDATE users SET level = ?, role = ?, user_status = ? WHERE userid = ?";
	final String USER_UPDATE_STATUS = "UPDATE users SET user_status = ? WHERE userid = ?";
	
	
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
	
	public UserDTO getUserByIdAdmin(String userId){
	    UserDTO user = null;
	    try {
	        conn = JdbcConnectUtil.getConnection();
	        pstmt = conn.prepareStatement(USER_SELECT_ONE);
	        pstmt.setString(1, userId);
	        rs = pstmt.executeQuery();
	        
	        // 1개만 조회하므로 if(rs.next()) 사용
	        if(rs.next()){ 
	            user = new UserDTO();
	            user.setUserid(rs.getString("userid"));
	            user.setUsername(rs.getString("username"));
	            user.setEmail(rs.getString("email"));
	            user.setLevel(rs.getInt("level"));
	            user.setUser_status(rs.getString("user_status"));
	            
	            Timestamp ts = rs.getTimestamp("created_at");
	            user.setCreated_at(ts.toLocalDateTime());
	            
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JdbcConnectUtil.close(conn, pstmt, rs); 
	    }
	    return user;
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
	
	
public int deleteUserAdmin(String userId){
		
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
	try {
		conn = JdbcConnectUtil.getConnection();
		pstmt = conn.prepareStatement(USER_UPDATE);
		pstmt.setInt(1, udto.getLevel());
		pstmt.setString(2, udto.getRole());
		pstmt.setString(3, udto.getUser_status());
		
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

public int updateUserStatus(String userId, String status){
    int result = 0;
    
    try {
        conn = JdbcConnectUtil.getConnection();
        pstmt = conn.prepareStatement(USER_UPDATE_STATUS);
        
        pstmt.setString(1, status); // 바꿀 상태 값
        pstmt.setString(2, userId); // 대상 ID
        
        result = pstmt.executeUpdate();
        
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        JdbcConnectUtil.close(conn, pstmt);
    }
    
    return result;
}
	
	
}
