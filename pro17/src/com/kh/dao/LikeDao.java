package com.kh.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.kh.vo.PointVo;

public class LikeDao {
	private Connection conn;

	public LikeDao(Connection conn) {
		this.conn = conn;
	}

	private void close(PreparedStatement pstmt, ResultSet rs) {
		if (rs != null)
			try {
				rs.close();
			} catch (Exception e) {
			}
		if (pstmt != null)
			try {
				pstmt.close();
			} catch (Exception e) {
			}
	}

	public boolean insertLike(int b_no, String id) {
		PreparedStatement pstmt = null;
		try {
			String sql = "insert into t_like (l_no ,b_no ,id) "
					+ "	values (seq_like.nextval, ?, ?) ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, b_no);
			pstmt.setString(2, id);
			int count =pstmt.executeUpdate();
			if(count >0) {
				return true;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt, null);
		}
		
		return false;
	}

	public boolean deleteLike(int b_no, String id) {
		PreparedStatement pstmt = null;
		try {
			String sql = "delete from t_like where b_no = ? and id = ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, b_no);
			pstmt.setString(2, id);
			int count =pstmt.executeUpdate();
			if(count >0) {
				return true;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt, null);
		}
		
		return false;
	}

	public boolean isLike(int b_no, String id) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "select count(*) cnt from t_like " 
					   + " where b_no = ? and id = ?";
			pstmt	= conn.prepareStatement(sql);
			pstmt.setInt(1, b_no);
			pstmt.setString(2, id);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				int count=rs.getInt("cnt");
				if(count >0) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt, rs);
		}
		

		return false;
	}
	
}
