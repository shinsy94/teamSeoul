package com.festival;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.util.DBConn;

public class FestivalDAO {
	private Connection conn = DBConn.getConnection();
	
	public int dataCount(int seasonCode) {
		int dataCount = 0;
		String sql;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			sql = "SELECT NVL(count(*),0) FROM festival WHERE seasoncode = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, seasonCode);
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
			sql = "SELECT NVL(count(*),0) FROM festival";
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
	public Map<String, String> seasonList() {
		Map<String, String> map = new HashMap<String, String>();
		String sql;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			sql = "select seasoncode, season from sea ORDER BY seasoncode";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				map.put(rs.getString("seasoncode"), rs.getString("season"));
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
		return map;
	}
	
	public List<FestivalDTO>  somenailList(int offset, int rows){
		List<FestivalDTO> list = new ArrayList<FestivalDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT f.num, title, content, seasoncode, IMAGEFILENAME  ");
			sb.append("	FROM festival f ");
			sb.append(" JOIN festivalFile ff ON f.num = ff.num  ");
			sb.append(" WHERE INSTR(IMAGEFILENAME,'some') > 0");
			
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				FestivalDTO dto = new FestivalDTO();
				dto.setNum(rs.getInt("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setSeasonCode(rs.getInt("seasoncode"));
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
	
	public List<FestivalDTO>  somenailList(int offset, int rows,int seasonCode){
		List<FestivalDTO> list = new ArrayList<FestivalDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT f.num, title, content, seasoncode, IMAGEFILENAME  ");
			sb.append("	FROM festival f ");
			sb.append(" JOIN festivalFile ff ON f.num = ff.num ");
			sb.append(" WHERE seasoncode=? AND INSTR(IMAGEFILENAME,'some') > 0");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, seasonCode);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				FestivalDTO dto = new FestivalDTO();
				dto.setNum(rs.getInt("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setSeasonCode(rs.getInt("seasonCode"));
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
	
	public List<FestivalDTO>  readFestival(int num){
		List<FestivalDTO> list = new ArrayList<FestivalDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT f.num, title, content, s.seasoncode, ff.IMAGEFILENAME, userId  ");
			sb.append("	FROM sea s ");
			sb.append(" JOIN festival f ON s.seasoncode = f.seasoncode ");
			sb.append(" JOIN festivalFile ff ON f.num = ff.num");
			sb.append(" WHERE f.num=?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			
			
			while(rs.next()) {
				FestivalDTO dto = new FestivalDTO();
				dto.setNum(rs.getInt("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setSeasonCode(rs.getInt("seasonCode"));
				dto.setImageFileName(rs.getString("IMAGEFILENAME"));
				dto.setUserId(rs.getString("userId"));
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
			sql="INSERT INTO festivalCOMMENT(COMMENTNUM, content, userId, num) VALUES(festivalCOMMENT_SEQ.NEXTVAL, ?, ?, ?)";
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
			sql = "SELECT NVL(count(*),0) FROM festival v JOIN festivalCOMMENT vc ON v.num = vc.num WHERE v.num=?  ";
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
			sql = "SELECT vc.COMMENTNUM, vc.userId, vc.content, vc.created FROM festival v JOIN festivalcomment vc ON v.num = vc.num WHERE v.num=? ORDER BY COMMENTNUM OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
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
