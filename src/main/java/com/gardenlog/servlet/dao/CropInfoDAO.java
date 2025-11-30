package com.gardenlog.servlet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.gardenlog.servlet.util.JdbcConnectUtil;

public class CropInfoDAO {

    private static final String INSERT =
        "INSERT INTO crop_info (crop_title, info_json) VALUES (?, ?) " +
        "ON DUPLICATE KEY UPDATE info_json = VALUES(info_json)";

    public void saveJson(String cropCode, String json) {

        try (Connection conn = JdbcConnectUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT)) {

            pstmt.setString(1, cropCode);
            pstmt.setString(2, json);

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
