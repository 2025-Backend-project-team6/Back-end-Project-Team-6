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

    public List<JournalDTO> selectJournalList(String userid) {
        List<JournalDTO> list = new ArrayList<>();
        
        String sql = "SELECT f.*, c.nickname " 
                   + "FROM farming_logs f " 
                   + "LEFT JOIN my_crop c ON f.my_crop_id = c.id " 
                   + "WHERE f.userid = ? " 
                   + "ORDER BY f.log_date DESC";
        
        try {
            conn = JdbcConnectUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                JournalDTO dto = new JournalDTO();
                dto.setLogId(rs.getInt("log_id"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setWeather(rs.getString("weather"));
                dto.setLogDate(rs.getDate("log_date"));
                dto.setLogImg(rs.getString("log_img"));
                
                dto.setMyCropId(rs.getInt("my_crop_id"));
                dto.setCropNickname(rs.getString("nickname"));
                
                list.add(dto);
            }
        } catch (SQLException e) { e.printStackTrace(); } 
        finally { JdbcConnectUtil.close(conn, pstmt, rs); }
        return list;
    }

    // 일지 작성 (저장)
    public int insertJournal(JournalDTO dto) {
        int result = 0;
        String sql = "INSERT INTO farming_logs (userid, my_crop_id, title, content, weather, log_date, log_img) " 
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
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
            
            result = pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); } 
        finally { JdbcConnectUtil.close(conn, pstmt); }
        return result;
    }
    
    // (마이페이지용) 전체 누적 일지 개수 세기
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
            while(rs.next()) {
                list.add(rs.getInt(1));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        finally { JdbcConnectUtil.close(conn, pstmt, rs); }
        return list;
    }

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

    public int getKeywordCount(String userid, int year, int month, String keyword) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM farming_logs "
                   + "WHERE userid = ? AND YEAR(log_date) = ? AND MONTH(log_date) = ? "
                   + "AND (title LIKE ? OR content LIKE ?)";
        try {
            conn = JdbcConnectUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            pstmt.setInt(2, year);
            pstmt.setInt(3, month);
            pstmt.setString(4, "%" + keyword + "%");
            pstmt.setString(5, "%" + keyword + "%");
            rs = pstmt.executeQuery();
            if(rs.next()) count = rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        finally { JdbcConnectUtil.close(conn, pstmt, rs); }
        return count;
    }

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
}