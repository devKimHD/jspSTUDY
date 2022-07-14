package com.kh.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.kh.vo.BoardVo;
import com.kh.vo.CommentVo;
import com.kh.vo.PagingDto;


public class BoardDao {

	private Connection conn;

	public BoardDao(Connection conn) {
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

	public boolean insertArticle(BoardVo vo) {
		PreparedStatement pstmt = null;
		try {
			String sql = "insert into t_board" + "	(b_no, g_no, b_title, b_content, " + "	id)" + "	values("
					+ "	seq_board.nextval, seq_board.nextval" + "	, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getB_title());
			pstmt.setString(2, vo.getB_content());
			pstmt.setString(3, vo.getId());
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

	public List<BoardVo> getList(PagingDto pagingDto) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 최신글이 먼저 올라오도록 ->글그룹 별로 같으면 seq순
			String searchType=pagingDto.getSearchType();
			String keyword=pagingDto.getKeyword();
			String sql ="SELECT   * FROM"  
					+" (select rownum rnum,a.* from ("
						    +" select * from t_board ";
			sql +=getSearchString(searchType, keyword);
						    
			sql+= " order by g_no desc, re_seq asc "
					   +" ) a) "
					+" where rnum between ? and ?";
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,pagingDto.getStartRow());
			pstmt.setInt(2, pagingDto.getEndRow());
			rs = pstmt.executeQuery();
			
			List<BoardVo> list = new ArrayList<BoardVo>();
			while (rs.next()) {
				int b_no = rs.getInt("b_no");
				int g_no = rs.getInt("g_no");
				String b_title = rs.getString("b_title");
				String b_content = rs.getString("b_content");
				String b_file_name = rs.getString("b_file_name");
				Date b_date = rs.getDate("b_date");
				String id = rs.getString("id");
				int read_count = rs.getInt("read_count");
				int re_seq=rs.getInt("re_seq");
				int re_level=rs.getInt("re_level");
				BoardVo boardVo = new BoardVo(b_no, g_no, b_title, b_content, b_file_name, b_date, id, read_count, re_seq, re_level);
				list.add(boardVo);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt, rs);
		}
		return null;
	}

	public void increaseReadCount(int b_no) {
		PreparedStatement pstmt = null;
		try {
			String sql = "update t_board set" + "	read_count = read_count + 1" + "	where b_no = " + b_no;
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			close(pstmt, null);
		}
	}

	public BoardVo getArticleByBno(int b_no) {
		PreparedStatement pstmt = null;

		ResultSet rs = null;
		try {
			String sql = "select * from t_board where b_no =" + b_no;

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				int g_no = rs.getInt("g_no");
				String b_title = rs.getString("b_title");
				String b_content = rs.getString("b_content");
				String b_file_name = rs.getString("b_file_name");
				Date b_date = rs.getDate("b_date");
				String id = rs.getString("id");
				int read_count = rs.getInt("read_count");
				int re_seq=rs.getInt("re_seq");
				int re_level=rs.getInt("re_level");
				int like_count=rs.getInt("like_count");
				BoardVo boardVo = new BoardVo(b_no, g_no, b_title, b_content, b_file_name, b_date, id, read_count, re_seq, re_level,like_count);
				return boardVo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt, rs);
		}
		return null;
	}

	public boolean updateArticle(BoardVo boardVo) {
		PreparedStatement pstmt = null;
		try {
			String sql = "update t_board set" + "	b_title = ?, " + "	b_content = ? " + " where b_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardVo.getB_title());
			pstmt.setString(2, boardVo.getB_content());
			pstmt.setInt(3, boardVo.getB_no());
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

	public boolean deleteArticle(int b_no) {
		PreparedStatement pstmt = null;
		try {
			String sql = "delete from t_board where b_no = " + b_no;
			pstmt = conn.prepareStatement(sql);
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
	public boolean insertReply(BoardVo boardVo) {
		PreparedStatement pstmt1=null;
		PreparedStatement pstmt2=null;		
		try {
			String sql1="update t_board set"
					+ "	re_seq = re_seq + 1"
					+ "	where g_no =? and re_seq>?";
			pstmt1=conn.prepareStatement(sql1);
			pstmt1.setInt(1, boardVo.getG_no());
			pstmt1.setInt(2, boardVo.getRe_seq());
			pstmt1.executeUpdate();
			String sql2="insert into t_board(b_no, g_no,b_title,b_content,"
					+ "	b_file_name, b_date, id, read_count, re_seq, re_level)"
					+ "	values(seq_board.nextval,? , ?, ?, null, sysdate, ?,0,?,?)";
			pstmt2=conn.prepareStatement(sql2);
			pstmt2.setInt(1, boardVo.getG_no());
			pstmt2.setString(2,boardVo.getB_title());
			pstmt2.setString(3, boardVo.getB_content());
			pstmt2.setString(4, boardVo.getId());
			pstmt2.setInt(5, boardVo.getRe_seq()+1);
			pstmt2.setInt(6, boardVo.getRe_level()+1);
			pstmt2.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			close(pstmt1, null);
			close(pstmt2, null);
		}
		return false;
	}
	public int getCount(String searchType, String keyword) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			String sql="select count(*) cnt from t_board";
			sql +=getSearchString(searchType, keyword);
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				int count=rs.getInt("cnt");
				return count;
			}
		}catch(Exception e) {e.printStackTrace();}finally {
			close(pstmt, rs);
		}
		return 0;
	}
	private String getSearchString(String searchType,String keyword) {
		String sql="";
		if( (searchType!=null && !"".equals(searchType)) 
				&& (keyword!=null && !"".equals(keyword))
				) {
			switch(searchType) {
			case "t": 
				sql += "	where b_title like '%"+keyword+"%'";
				break;
			case "c": 
				sql += "	where b_content like '%"+keyword+"%'";
				break;
			case "w": 
				sql += "	where id like '%"+keyword+"%'";
				break;
			case "tc": 
				sql += "	where b_title like '%"+keyword+"%'";
				sql += "	or b_content like '%"+keyword+"%'";
				break;
			case "tcw": 
				sql += "	where b_title like '%"+keyword+"%'";;
				sql += "	or b_content like '%"+keyword+"%'";;
				sql += "	or id like '%"+keyword+"%'";;
			
				break;
			}
		}//if
		return sql;
	}
	public boolean updateLikeCount(int b_no, int count) {
		PreparedStatement pstmt = null;
		try {
			String sql = "update t_board set	"
					+ "	like_count = like_count + (?)"
					+ "	where b_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, count);
			pstmt.setInt(2, b_no);
			int cnt=pstmt.executeUpdate();
			if(cnt >0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt, null);
		}
		return false;
	}
	public boolean commentWrite(CommentVo commentVo) {
		PreparedStatement pstmt=null;
		try 
		{
			String sql="insert into t_comment (c_id, c_content, id, b_no)	"
					+ "	values	(seq_comment.nextval, ?, ?, ?)";
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, commentVo.getC_content());
		pstmt.setString(2, commentVo.getId());
		pstmt.setInt(3, commentVo.getB_no());
		int count=pstmt.executeUpdate();
		if(count>0) {
			return true;
		}
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt, null);
		}
		return false;
	}
	
	public List<CommentVo> getCommentList(int b_no){
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			String sql="select * from t_comment where b_no=? order by c_date desc";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, b_no);
			rs=pstmt.executeQuery();
			List<CommentVo> list=new ArrayList<>();
			while(rs.next()) {
				int c_id=rs.getInt("c_id");
				String c_content=rs.getString("c_content");
				String id=rs.getString("id");
				Date c_date=rs.getDate("c_date");
				CommentVo commentVo=new CommentVo(c_id, c_content, id, b_no, c_date);
				list.add(commentVo);
			}
			return list;
		}catch(Exception e) {} finally {close(pstmt, rs);}
		return null;
	}
	public boolean updateComment(int c_id, String c_content) {
		PreparedStatement pstmt=null;
		try {
			String sql="update t_comment set	" + 
					"	c_content = ?	" + 
					"	where c_id = ?	";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, c_content);
			pstmt.setInt(2, c_id);
			int count=pstmt.executeUpdate();
			if(count>0) {
				return true;
			}
		}catch(Exception e) {} finally {
			close(pstmt, null);
		}
		return false;
	}
	
	public boolean deleteComment(int c_id) {
		PreparedStatement pstmt=null;
		try {
			String sql="delete from t_comment where c_id = ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, c_id);
			int count=pstmt.executeUpdate();
			if(count>0) {
				return true;
			}
		}catch(Exception e) {} finally {
			close(pstmt, null);
		}
		return false;
	}
}
