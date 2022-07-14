package com.kh.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.kh.vo.MemberVo;



public class MemberDao {
	
	
	private Connection conn;
	public MemberDao(Connection conn) {
		this.conn=conn;
	}
	private void close(PreparedStatement pstmt, ResultSet rs) {
		if (rs != null) try { rs.close(); } catch (Exception e) { }
		if (pstmt != null) try { pstmt.close(); } catch (Exception e) { }
	}
	
//	private Connection getConnection() {
//		try {
//			// javax.naming
//			Context ctx = new InitialContext();
//			// javax.sql.DataSource
//			DataSource ds = (DataSource)ctx.lookup(
//					"java:/comp/env/jdbc/oracle");
//			Connection conn = ds.getConnection();
////			System.out.println("conn:" + conn);
//			return conn;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	private void closeAll(ResultSet rs, PreparedStatement pstmt, Connection conn) {
//		if (rs != null) try { rs.close(); } catch (Exception e) { }
//		if (pstmt != null) try { pstmt.close(); } catch (Exception e) { }
//		if (conn != null) try { conn.close(); } catch (Exception e) { }
//	}
	
	public List<MemberVo> listMembers() {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			String sql = "select * from t_member"; 
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			List<MemberVo> list = new ArrayList<>();
			while (rs.next()) {
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String sname = rs.getString("name");
				String email = rs.getString("email");
				Date joindate = rs.getDate("joindate");
				
				MemberVo vo = new MemberVo(
						id, pwd, sname, email, joindate);
				list.add(vo);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt, rs);
		}
		return null;
	} // listMembers()
	
	public boolean addMember(MemberVo vo) {

		PreparedStatement pstmt = null;
		try {

			String sql = "insert into t_member(id, pwd, name, email)"
					+ "   values(?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			int i = 0;
			pstmt.setString(++i, vo.getId());
			pstmt.setString(++i, vo.getPwd());
			pstmt.setString(++i, vo.getName());
			pstmt.setString(++i, vo.getEmail());
			int count = pstmt.executeUpdate();
			if (count > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt, null);
		}
		return false;
	}
	
	public void delMember(String[] ids) {
		PreparedStatement pstmt = null;
		
		try {
			String sql = "delete from t_member"
					+ "   where id in (";
			for (int i = 0; i < ids.length; i++) {
				sql += "?";
				if (i < ids.length - 1) {
					sql += ",";
				}
			}
			sql += ")";
			System.out.println("sql:" + sql);
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < ids.length; i++) {
				pstmt.setString(i + 1, ids[i]);
			}
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt, null);
		}
	}
	
	public void delMember(String id) {
		
		PreparedStatement pstmt = null;
		
		try {
			String sql = "delete from t_member"
					+ "   where id = ?";
		
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt, null);
		}
	} // delMember()
	
	public boolean isExisted(MemberVo vo) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "select count(*) cnt from t_member"
					+ "	  where id = ? and pwd = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPwd());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int cnt = rs.getInt("cnt");
				if (cnt > 0) {
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
	
	public MemberVo selectMemberById(String id) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select * from t_member"
					+ "   where id = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String email = rs.getString("email");
				Date joindate = rs.getDate("joindate");
				
				MemberVo vo = new MemberVo(id, pwd, name, email, joindate);
				return vo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt, rs);
		}
		return null;
	}
	
	public boolean updateMember(MemberVo vo) {

		PreparedStatement pstmt = null;
		
		try {
			String sql = "update t_member set"
					+ "			pwd = ?,"
					+ "			name = ?,"
					+ "			email = ?"
					+ "	  where id = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getPwd());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getEmail());
			pstmt.setString(4, vo.getId());
			int count = pstmt.executeUpdate();
			if (count > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt, null);
		}
		return false;
	}
	
	public boolean deleteMember(String id) {

		PreparedStatement pstmt = null;
		
		try {
			String sql = "delete from t_member"
					+ "   where id = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			int count = pstmt.executeUpdate();
			if (count > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt, null);
		}
		return false;
	}
	
	public boolean isDupId(String id) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select count(*) cnt from t_member"
					+ "   where id = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int count = rs.getInt("cnt");
				if (count > 0) {
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
	
	public boolean updatePoint(String id, int point) {
		
		PreparedStatement pstmt = null;
		
		try {
			String sql = "update t_member set"
					+ "			point = point + ?"
					+ "   where id = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, point);
			pstmt.setString(2, id);
			int count = pstmt.executeUpdate();
			if (count > 0) {
				return true;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt, null);
		}
		return false;
	}
	public MemberVo selectMemberByIdAndPwd(String id, String pwd) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			String sql="select * from t_member where id = ? and pwd =?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				String name=rs.getString("name");
				String email=rs.getString("email");
				int point=rs.getInt("point");
				MemberVo memberVo=new MemberVo(id, pwd, name, email, null, point);
				return memberVo;
			}
		} catch (Exception e) {
		}finally {
			close(pstmt, rs);
		}
		return null;
	}
}
