package com.gardenlog.servlet.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.time.LocalDateTime;


import com.gardenlog.servlet.dto.UserDTO;
import com.gardenlog.servlet.util.JdbcConnectUtil;

public class UserDAO {
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
    
	private final String USER_CHECKID = "SELECT userid FROM users WHERE userid = ?;";
	private final String USER_JOIN = "insert into users(userid, password, username, email) values(?, ?, ?, ?);";
	private final String USER_LOGIN = "select * from users where userid = ? and password = ?;";
	private final String USER_GET = "SELECT u.*, g.location FROM users u " + "LEFT JOIN garden g ON u.userid = g.userid " + "WHERE u.userid = ? LIMIT 1";
	private final String USER_UPDATE = "UPDATE users SET password = ?, username = ?, email = ? WHERE userid = ?;";
	private final String GARDEN_UPDATE_LOCATION = "UPDATE garden SET location = ? WHERE userid = ?";
	private final String USER_UPDATE_LEVEL = "UPDATE users SET level = ? WHERE userid = ?";

	// 관리자 페이지 조회, 수정, 삭제
	final String USER_SELECT_ONE = "SELECT userid, username, email, level, role, user_status, created_at FROM users WHERE userid = ?";
	final String SELECT_USER_LIST = "select * from users;";
	final String USER_DELETE = "DELETE FROM users WHERE userid = ?";
	final String ADMIN_USER_UPDATE = "UPDATE users SET level = ?, role = ?, user_status = ? WHERE userid = ?";
	final String USER_UPDATE_STATUS = "UPDATE users SET user_status = ? WHERE userid = ?";
	
    /* 로그인 */
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
	            user.setLevel(rs.getInt("level"));
	            user.setProfile_path(rs.getString("profile_path"));
	            user.setRole(rs.getString("role"));
	            user.setUser_status(rs.getString("user_status"));
	            user.setCreated_at(rs.getObject("created_at", LocalDateTime.class));
	          }
	      } catch (SQLException e) {
		         e.printStackTrace();
	      } finally {
	         JdbcConnectUtil.close(conn, pstmt, rs);
	      }

	      return user;
	}  
    
	/* 아이디로 회원 정보 1명 조회하기 (마이페이지용) */
	public UserDTO getUser(String userid) {
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
                user.setLevel(rs.getInt("level"));
                user.setLocation(rs.getString("location")); 
                // user.setRole(rs.getString("role")); // 필요하면 추가
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectUtil.close(conn, pstmt, rs);
        }
        return user;
    }

	/* 회원가입 */
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
	
	/* 회원가입 - 아이디중복확인 */
	public boolean checkId(UserDTO udto) {
		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(USER_CHECKID);
			pstmt.setString(1, udto.getUserid());
			
			rs = pstmt.executeQuery();
			
			return rs.next();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt, rs);
		}
		
		return false;
	}

    /* 회원 정보 수정하기 */
	public int updateUser(UserDTO user) {
        int result = 0;
        try {
            conn = JdbcConnectUtil.getConnection();
            
            // users 테이블 수정
            pstmt = conn.prepareStatement(USER_UPDATE);
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getUserid());
            result = pstmt.executeUpdate();
            
            pstmt.close(); 

            // garden 테이블 주소 수정
            if(user.getLocation() != null) {
                pstmt = conn.prepareStatement(GARDEN_UPDATE_LOCATION);
                pstmt.setString(1, user.getLocation());
                pstmt.setString(2, user.getUserid());
                pstmt.executeUpdate();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectUtil.close(conn, pstmt);
        }
        return result;
    }
	
	/* 레벨 업(등급 수정) */
    public void updateLevel(String userid, int newLevel) {
        try {
            conn = JdbcConnectUtil.getConnection();
            pstmt = conn.prepareStatement(USER_UPDATE_LEVEL);
            pstmt.setInt(1, newLevel);
            pstmt.setString(2, userid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectUtil.close(conn, pstmt);
        }
    }
    
    /* 관리자: 회원 1명 조회 */
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
	
	/* 관리자: 회원 리스트 조회 */
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
				udto.setLevel(rs.getInt("level"));
				udto.setProfile_path(rs.getString("profile_path"));
				udto.setRole(rs.getString("role")); 
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
	
	/* 관리자: 회원 삭제 */
	public int deleteUserAdmin(String userId){
		int result = 0;
		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(USER_DELETE);
			pstmt.setString(1, userId);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt);
		}
		return result;
	}
	
	/* 관리자: 회원 정보 수정 (level, role, status) */
	public int updateUserAdmin(UserDTO udto){
		int result = 0;
		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(ADMIN_USER_UPDATE);
			pstmt.setInt(1, udto.getLevel());
			pstmt.setString(2, udto.getRole());
			pstmt.setString(3, udto.getUser_status());
			pstmt.setString(4, udto.getUserid());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt);
		}
		return result;
	}

	/* 관리자 / 기타: 상태만 변경 */
	public int updateUserStatus(String userId, String status){
	    int result = 0;
	    try {
	        conn = JdbcConnectUtil.getConnection();
	        pstmt = conn.prepareStatement(USER_UPDATE_STATUS);
	        pstmt.setString(1, status);
	        pstmt.setString(2, userId);
	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JdbcConnectUtil.close(conn, pstmt);
	    }
	    return result;
	}

}