package com.gardenlog.servlet.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.gardenlog.servlet.util.JdbcConnectUtil;

public class VisitDAO {
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    public void addVisit(String userid) {
        String sql = "INSERT INTO visit_log (userid, visit_date) VALUES (?, curdate())";
        try {
            conn = JdbcConnectUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); } 
        finally { JdbcConnectUtil.close(conn, pstmt); }
    }

    public List<Integer> getVisitDays(String userid, int year, int month) {
        List<Integer> list = new ArrayList<>();
        String sql = "SELECT DISTINCT DAY(visit_date) FROM visit_log "
                   + "WHERE userid = ? AND YEAR(visit_date) = ? AND MONTH(visit_date) = ?";
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

    public int getTotalVisitCount(String userid) {
        int count = 0;
        String sql = "SELECT COUNT(DISTINCT visit_date) FROM visit_log WHERE userid = ?";
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