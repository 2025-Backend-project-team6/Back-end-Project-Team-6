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
	
	// [추가] 회원 정보 수정 쿼리 (비밀번호, 이메일, 이름 수정)
    private final String USER_UPDATE = "UPDATE users SET password = ?, email = ?, username = ? WHERE userid = ?";
    
    // [추가] 회원 정보 조회 (ID로 조회 - 수정 후 세션 갱신용)
    private final String USER_GET = "SELECT * FROM users WHERE userid = ?";

	
	/**
	 * 회원가입
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
	 * 로그인
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
	
	/**
     * 회원 정보 수정
     */
    public int updateUser(UserDTO user) {
        int result = 0;
        try {
            conn = JdbcConnectUtil.getConnection();
            pstmt = conn.prepareStatement(USER_UPDATE);
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getUsername());
            pstmt.setString(4, user.getUserid());
            
            result = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectUtil.close(conn, pstmt);
        }
        return result;
    }
    
    /**
     * 아이디로 회원 정보 가져오기 (세션 갱신용)
     */
    public UserDTO getUserById(String userid) {
        UserDTO user = null;
        try {
            conn = JdbcConnectUtil.getConnection();
            pstmt = conn.prepareStatement(USER_GET);
            pstmt.setString(1, userid);
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                user = new UserDTO();
                user.setUserid(rs.getString("userid"));
                user.setPassword(rs.getString("password"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setLevel(rs.getString("level"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectUtil.close(conn, pstmt, rs);
        }
        return user;
    }
}