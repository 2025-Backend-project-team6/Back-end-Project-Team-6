package com.gardenlog.servlet.dao;

import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.gardenlog.servlet.dto.MyCropDTO;
import com.gardenlog.servlet.util.JdbcConnectUtil;

public class MyCropDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	private final String JOIN = 
			"SELECT mc.*, c.crop_nm AS category, g.gardenname, ci.crop_title " + 
			"FROM my_crop mc " +
			"JOIN crop_info ci ON mc.cropid = ci.cropid " +
			"JOIN crops c ON ci.crop_code = c.crop_code " +
			"JOIN garden g ON mc.gardenid = g.gardenid ";
	
	private final String CROP_SEARCHCROP = JOIN + "WHERE mc.userid=? and mc.nickname like ?;";
	private final String CROP_ALLCROP = JOIN + "WHERE mc.userid=?;";
	private final String CROP_CATEGORY = JOIN + "WHERE mc.userid=? and c.crop_nm=?;";
	private final String ADDCROP_INSERTCROP = "insert into my_crop(userid, gardenid, cropid, nickname, planted_date) values(?, ?, ?, ?, ?);";
	private final String DETAILGARDEN_GETGARDENCROP = JOIN + "WHERE mc.userid=? and mc.gardenid=?;";
	private final String DETAILGARDEN_PLUSWATER = "update my_crop set water_count=water_count+1, last_watered_at=CURDATE() where id=?;";
	private final String DETAILGARDEN_DELETECROP = "delete from my_crop where id=?;";
	private final String INDEX_TOTALWATER = "select sum(water_count) as total from my_crop where gardenid = ?;";
	private final String INDEX_TOTALCROPCOUNT = "SELECT COUNT(*) AS cnt FROM my_crop WHERE gardenid = ?;";
	
	public List<MyCropDTO> searchMyCrop(String userid, String keyword){
		List<MyCropDTO> list = new ArrayList<>();
		
		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(CROP_SEARCHCROP);
			
			pstmt.setString(1, userid);
			pstmt.setString(2, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MyCropDTO mcdto = resultSetTOCrop(rs);
				list.add(mcdto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt, rs);
		}
		
		return list;
	}
	
	
	public List<MyCropDTO> allMyCrop(String userid) {
		List<MyCropDTO> list = new ArrayList<>();
		
		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(CROP_ALLCROP);
			
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MyCropDTO mcdto = resultSetTOCrop(rs);
				list.add(mcdto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt, rs);
		}
		
		return list;
	}
	
	public List<MyCropDTO> findByCategory(String userid, String category){
		List<MyCropDTO> list = new ArrayList<>();		
		
		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(CROP_CATEGORY);
			
			pstmt.setString(1, userid);
			pstmt.setString(2, category);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MyCropDTO mcdto = resultSetTOCrop(rs);
				list.add(mcdto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt, rs);
		}
		
		return list;
	}
	
	public int addCrop(MyCropDTO mcdto) {
		int result = 0;
		
		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(ADDCROP_INSERTCROP);
			
			pstmt.setString(1, mcdto.getUserid());
			pstmt.setInt(2, mcdto.getGardenid());
			pstmt.setInt(3, mcdto.getCropid());
			pstmt.setString(4, mcdto.getNickname());
			pstmt.setDate(5, mcdto.getPlanted_date());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt);
		}
		
		return result;
	}
	
	public List<MyCropDTO> getGardenCrop(String userid, int gardenid){
		List<MyCropDTO> list = new ArrayList<MyCropDTO>();
		
		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(DETAILGARDEN_GETGARDENCROP);
			
			pstmt.setString(1, userid);
			pstmt.setInt(2, gardenid);;
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MyCropDTO mcdto = resultSetTOCrop(rs);
				list.add(mcdto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt, rs);
		}
		
		return list;
	}
	
	public int plusWater(int id) {
		int result = 0;
		
		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(DETAILGARDEN_PLUSWATER);
			
			pstmt.setInt(1, id);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt);
		}
		
		return result;
	}
	
	public int deleteCrop(int id) {
		int result = 0;
		
		try {
			conn = JdbcConnectUtil.getConnection();
			pstmt = conn.prepareStatement(DETAILGARDEN_DELETECROP);
			
			pstmt.setInt(1, id);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcConnectUtil.close(conn, pstmt);
		}
		
		return result;
	}
	
	public int getTotalWaterCount(int gardenid) {
	    int result = 0;
	    
	    try {
	        conn = JdbcConnectUtil.getConnection();
	        pstmt = conn.prepareStatement(INDEX_TOTALWATER);
	        
	        pstmt.setInt(1, gardenid);
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            result = rs.getInt("total");
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        JdbcConnectUtil.close(conn, pstmt, rs);
	    }
	    
	    return result;
	}
	
	public int getCropCount(int gardenid) {
	    int count = 0;
	    
	    try {
	        conn = JdbcConnectUtil.getConnection();
	        pstmt = conn.prepareStatement(INDEX_TOTALCROPCOUNT);
	        
	        pstmt.setInt(1, gardenid);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            count = rs.getInt("cnt");
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        JdbcConnectUtil.close(conn, pstmt, rs);
	    }

	    return count;
	}
	
	private MyCropDTO resultSetTOCrop(ResultSet rs) throws SQLException {
		MyCropDTO mcdto = new MyCropDTO();
		
		mcdto.setId(rs.getInt("id"));
		mcdto.setUserid(rs.getString("userid"));
		mcdto.setGardenid(rs.getInt("gardenid"));
		mcdto.setGardenname(rs.getString("gardenname"));
		mcdto.setCategory(rs.getString("category"));
		mcdto.setCropid(rs.getInt("cropid"));
		mcdto.setCrop_title(rs.getString("crop_title"));
		mcdto.setNickname(rs.getString("nickname"));
		mcdto.setPlanted_date(rs.getDate("planted_date"));
		mcdto.setWater_count(rs.getInt("water_count"));
		mcdto.setStatus(rs.getString("status"));
		mcdto.setLast_watered_at(rs.getDate("last_watered_at"));
		
		return mcdto;
	}
	
	
    public int getMyCropCount(String userid) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM my_crop WHERE userid = ?";
        
        try {
            conn = JdbcConnectUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcConnectUtil.close(conn, pstmt, rs);
        }
        return count;
    }
	
}
