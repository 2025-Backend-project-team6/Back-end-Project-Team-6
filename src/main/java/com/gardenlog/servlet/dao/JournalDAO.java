// 이후에 일지 페이지 구현 시 수정 예정, 현재는 마이페이지 숫자 세기용

package com.gardenlog.servlet.dao;
import java.sql.*;
import com.gardenlog.servlet.util.JdbcConnectUtil;

public class JournalDAO {
    public int getJournalCount(String userid) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM farming_logs WHERE userid = ?";
        try (Connection conn = JdbcConnectUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userid);
            try (ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()) count = rs.getInt(1);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return count;
    }
}