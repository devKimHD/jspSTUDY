package com.kh.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.kh.vo.PointVo;

public class PointDao {
	private Connection conn;

	public PointDao(Connection conn) {
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

	// vo가 여러개 생기고 있으니 vo라 짓지말고 확실하게 할것
	public boolean addPoint(PointVo pointVo) {
		PreparedStatement pstmt = null;
		System.out.println(pointVo);
		try {
			String sql = "insert into t_point" + "	(p_no, id , point, point_code, point_date)"
					+ "	values(seq_point.nextval, ?, (select point from t_point_code"
					+ "	where point_code=?),?,sysdate)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pointVo.getId());
			pstmt.setInt(2, pointVo.getPoint_code());
			pstmt.setInt(3, pointVo.getPoint_code());
			pstmt.executeUpdate();
			return true;
		} catch (Exception e) {

		} finally {
			close(pstmt, null);
		}
		return false;
	}

	public List<PointVo> getPointList(String id) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "select p.p_no, c.point_name, p.point, p.point_date " + "	from t_point p, t_point_code c"
					+ "	where p.point_code = c.point_code and id = ? " + "	order by point_date desc";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			List<PointVo> pointList = new ArrayList<>();
			while (rs.next()) {
				int p_no = rs.getInt("p_no");
				String point_name = rs.getString("point_name");
				int point = rs.getInt("point");
				Date point_date = rs.getDate("point_date");
				PointVo pointVo = new PointVo();
				pointVo.setP_no(p_no);
				pointVo.setPoint_name(point_name);
				pointVo.setPoint(point);
				pointVo.setPoint_date(point_date);
				pointList.add(pointVo);
			}
			return pointList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt, rs);
		}
		return null;
	}

	public int getPointByPointCode(int point_code) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql="select point from t_point_code "
					+ "	where point_code = ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, point_code);
			
			rs=pstmt.executeQuery();
			if(rs.next()) {
				int point = rs.getInt("point");
				return point; 
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt, rs);
		}
		return 0;
	}
}
