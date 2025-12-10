package com.gardenlog.servlet.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.gardenlog.servlet.dto.JournalDTO;
import com.gardenlog.servlet.util.JdbcConnectUtil;

public class JournalDAO {
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    public List<JournalDTO> selectJournalList(String userid, String category) {
        List<JournalDTO> list = new ArrayList<>();
        
        String sql = "SELECT f.*, c.nickname " 
                   + "FROM farming_logs f " 
                   + "LEFT JOIN my_crop c ON f.my_crop_id = c.id " 
                   + "WHERE f.userid = ? ";
        
        if(category != null && !category.equals("전체") && !category.equals("")) {
            sql += "AND f.log_type = ? ";
        }
        
        sql += "ORDER BY f.log_date DESC";
        
        try {
            conn = JdbcConnectUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            
            if(category != null && !category.equals("전체") && !category.equals("")) {
                pstmt.setString(2, category);
            }
            
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                JournalDTO dto = new JournalDTO();
                dto.setLogId(rs.getInt("log_id"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setWeather(rs.getString("weather"));
                dto.setLogDate(rs.getDate("log_date"));
                dto.setLogImg(rs.getString("log_img"));
                dto.setLogType(rs.getString("log_type"));
                
                dto.setMyCropId(rs.getInt("my_crop_id"));
                dto.setCropNickname(rs.getString("nickname"));
                
                list.add(dto);
            }
        } catch (SQLException e) { e.printStackTrace(); } 
        finally { JdbcConnectUtil.close(conn, pstmt, rs); }
        return list;
    }

    public int insertJournal(JournalDTO dto) {
        int result = 0;
        String sql = "INSERT INTO farming_logs (userid, my_crop_id, title, content, weather, log_date, log_img, log_type) " 
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            conn = JdbcConnectUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dto.getUserid());
            
            if(dto.getMyCropId() == 0) pstmt.setNull(2, java.sql.Types.INTEGER);
            else pstmt.setInt(2, dto.getMyCropId());
            
            pstmt.setString(3, dto.getTitle());
            pstmt.setString(4, dto.getContent());
            pstmt.setString(5, dto.getWeather());
            pstmt.setDate(6, dto.getLogDate());
            pstmt.setString(7, dto.getLogImg());
            pstmt.setString(8, dto.getLogType());
            
            result = pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); } 
        finally { JdbcConnectUtil.close(conn, pstmt); }
        return result;
    }
    
    // (글쓰기용) 내 작물 목록 가져오기
    public List<JournalDTO> getMyCropList(String userid) {
        List<JournalDTO> list = new ArrayList<>();
        
        // garden 테이블과 JOIN해서 gardenname도 가져옴
        String sql = "SELECT m.id, m.nickname, g.gardenname "
                   + "FROM my_crop m "
                   + "JOIN garden g ON m.gardenid = g.gardenid "
                   + "WHERE m.userid = ?";
                   
        try {
            conn = JdbcConnectUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                JournalDTO dto = new JournalDTO();
                dto.setMyCropId(rs.getInt("id"));
                dto.setCropNickname(rs.getString("nickname"));
                dto.setGardenName(rs.getString("gardenname")); // 텃밭 이름 담기
                list.add(dto);
            }
        } catch (SQLException e) { e.printStackTrace(); } 
        finally { JdbcConnectUtil.close(conn, pstmt, rs); }
        return list;
    }
    

    
    // 달력용 날짜 가져오기
    public List<Integer> getLogDates(String userid, int year, int month) {
        List<Integer> list = new ArrayList<>();
        String sql = "SELECT DISTINCT DAY(log_date) FROM farming_logs "
                   + "WHERE userid = ? AND YEAR(log_date) = ? AND MONTH(log_date) = ?";
        try {
            conn = JdbcConnectUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            pstmt.setInt(2, year);
            pstmt.setInt(3, month);
            rs = pstmt.executeQuery();
            while(rs.next()) { list.add(rs.getInt(1)); }
        } catch (SQLException e) { e.printStackTrace(); }
        finally { JdbcConnectUtil.close(conn, pstmt, rs); }
        return list;
    }

    // 이번 달 총 일지 개수
    public int getMonthlyLogCount(String userid, int year, int month) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM farming_logs "
                   + "WHERE userid = ? AND YEAR(log_date) = ? AND MONTH(log_date) = ?";
        try {
            conn = JdbcConnectUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            pstmt.setInt(2, year);
            pstmt.setInt(3, month);
            rs = pstmt.executeQuery();
            if(rs.next()) count = rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        finally { JdbcConnectUtil.close(conn, pstmt, rs); }
        return count;
    }

    // 특정 타입(물주기, 수확 등) 개수 세기
    public int getKeywordCount(String userid, int year, int month, String type) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM farming_logs "
                   + "WHERE userid = ? AND YEAR(log_date) = ? AND MONTH(log_date) = ? "
                   + "AND log_type = ?";
        try {
            conn = JdbcConnectUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            pstmt.setInt(2, year);
            pstmt.setInt(3, month);
            pstmt.setString(4, type);
            rs = pstmt.executeQuery();
            if(rs.next()) count = rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        finally { JdbcConnectUtil.close(conn, pstmt, rs); }
        return count;
    }

    // 가장 많이 기록한 작물 이름
    public String getTopCropName(String userid, int year, int month) {
        String cropName = "-";
        String sql = "SELECT c.nickname FROM farming_logs f "
                   + "JOIN my_crop c ON f.my_crop_id = c.id "
                   + "WHERE f.userid = ? AND YEAR(f.log_date) = ? AND MONTH(f.log_date) = ? "
                   + "GROUP BY f.my_crop_id "
                   + "ORDER BY COUNT(*) DESC LIMIT 1";
        try {
            conn = JdbcConnectUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            pstmt.setInt(2, year);
            pstmt.setInt(3, month);
            rs = pstmt.executeQuery();
            if(rs.next()) cropName = rs.getString(1);
        } catch (SQLException e) { e.printStackTrace(); }
        finally { JdbcConnectUtil.close(conn, pstmt, rs); }
        return cropName;
    }
    
    // (마이페이지용) 전체 누적 일지
    public int getJournalCount(String userid) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM farming_logs WHERE userid = ?";
        try {
            conn = JdbcConnectUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            rs = pstmt.executeQuery();
            if(rs.next()) count = rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); } 
        finally { JdbcConnectUtil.close(conn, pstmt, rs); }
        return count;
    }
}