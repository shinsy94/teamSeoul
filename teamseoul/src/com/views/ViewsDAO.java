package com.views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.util.DBConn;

public class ViewsDAO {
	private Connection conn = DBConn.getConnection();

	public Map<String, String> ListAreaCode(String bigareaCode) {
		Map<String, String> map = new HashMap<String, String>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "select areaCode,local from area where bigarea=? and areaCode!=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, bigareaCode);
			pstmt.setString(2, bigareaCode);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				map.put(rs.getString("areaCode"), rs.getString("local"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public int dataCount(int areaCode) {
		int dataCount = 0;
		String sql;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			sql = "SELECT NVL(count(*),0) FROM views WHERE areacode = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, areaCode);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dataCount = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return dataCount;
	}
	
	public int dataCount() {
		int dataCount = 0;
		String sql;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			sql = "SELECT NVL(count(*),0) FROM views";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dataCount = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return dataCount;
	}
	public List<ViewsDTO> areaList() {
		List<ViewsDTO> list = new ArrayList<ViewsDTO>();
		String sql;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			sql = "SELECT areacode, local FROM area WHERE bigarea = 0";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ViewsDTO dto = new ViewsDTO();
				dto.setAreaCode(rs.getInt("areacode"));
				dto.setLocal(rs.getString("local"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return list;
	}
	
	public List<ViewsDTO>  somenailList(int offset, int rows){
		List<ViewsDTO> list = new ArrayList<ViewsDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT v.num, title, content, areacode, IMAGEFILENAME ");
			sb.append("	FROM views v ");
			sb.append(" JOIN viewsFile vf ON v.num = vf.num ");
			sb.append(" WHERE INSTR(IMAGEFILENAME,'some') > 0");
			
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ViewsDTO dto = new ViewsDTO();
				dto.setNum(rs.getInt("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setAreaCode(rs.getInt("areacode"));
				dto.setSomenailImg(rs.getString("IMAGEFILENAME"));
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return list;
	}
	
	public List<ViewsDTO>  somenailList(int offset, int rows,int areaCode){
		List<ViewsDTO> list = new ArrayList<ViewsDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT v.num, title, content, areacode, IMAGEFILENAME ");
			sb.append("	FROM views v ");
			sb.append(" JOIN viewsFile vf ON v.num = vf.num ");
			sb.append(" WHERE areacode = ? and INSTR(IMAGEFILENAME,'some') > 0");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, areaCode);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ViewsDTO dto = new ViewsDTO();
				dto.setNum(rs.getInt("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setAreaCode(rs.getInt("areacode"));
				dto.setSomenailImg(rs.getString("IMAGEFILENAME"));
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return list;
	}
	
	public List<ViewsDTO>  readViews(int num){
		List<ViewsDTO> list = new ArrayList<ViewsDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT bigarea, v.num, title, content, v.areacode, IMAGEFILENAME, userId ");
			sb.append("	FROM area a JOIN views v ON a.areacode = v.areacode");
			sb.append(" JOIN viewsFile vf ON v.num = vf.num ");
			sb.append(" WHERE vf.num = ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			
			
			while(rs.next()) {
				ViewsDTO dto = new ViewsDTO();
				dto.setNum(rs.getInt("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setAreaCode(rs.getInt("areacode"));
				dto.setImageFileName(rs.getString("IMAGEFILENAME"));
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return list;
	}

	
	public int insertReply(ReplyDTO dto) {
		int result =0;
		
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql="INSERT INTO VIEWSCOMMENT(COMMENTNUM, content, userId, num) VALUES(VIEWCOMMENT_SEQ.NEXTVAL, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getContent());
			pstmt.setString(2, dto.getUserId());
			pstmt.setInt(3, dto.getNum());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}
	
	public int dataCountReply(int num) {
		int result = 0;
		String sql;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			sql = "SELECT NVL(count(*),0) FROM views v JOIN VIEWSCOMMENT vc ON v.num = vc.num WHERE v.num=?  ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}
	
	public List<ReplyDTO> listReply(int num,  int offset, int rows) {
		List<ReplyDTO> list = new ArrayList<ReplyDTO>();
		String sql;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			sql = "SELECT vc.COMMENTNUM, vc.userId, vc.content, vc.created FROM views v JOIN VIEWSCOMMENT vc ON v.num = vc.num WHERE v.num=? ORDER BY COMMENTNUM OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, rows);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReplyDTO dto = new ReplyDTO();
				dto.setCommentNum(rs.getInt("COMMENTNUM"));
				dto.setUserId(rs.getString("userId"));
				dto.setContent(rs.getString("content"));
				dto.setCreated(rs.getString("created"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return list;
	}
	
	public int insertFavorite(FavoriteDTO dto) {
		int result = 0;
		String sql;
		PreparedStatement pstmt = null;
		
		try {
			sql = "INSERT INTO favorite(NUM, CATEGORY, USERID) VALUES(?, ?, ?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getNum());
			pstmt.setString(2, dto.getCategory());
			pstmt.setString(3, dto.getUserId());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		
		return result;
	}
	
	
}
