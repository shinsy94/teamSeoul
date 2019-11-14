package com.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;
import com.yolo.YoloDTO;

public class MemberDAO {
	private Connection conn=DBConn.getConnection();
	
	public void insertMember(MemberDTO dto) throws Exception {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO member(userId, userPwd, userName, userTel, userEmail, userBirth) VALUES(?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getUserPwd());
			pstmt.setString(3, dto.getUserName());
			pstmt.setString(4, dto.getUserTel());
			pstmt.setString(5, dto.getUserEmail());
			pstmt.setString(6, dto.getUserBirth());
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
	}
	
	public int dataCount() {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql = "SELECT COUNT(*) FROM member";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				result=rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}
	
	public int dataCount(String condition, String keyword) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			if(condition.equalsIgnoreCase("created")) {
				sql = "SELECT COUNT(*) FROM member WHERE TO_CHAR(created, 'YYYY-MM-DD') = ? OR TO_CHAR(created, 'YYYYMMDD') = ?";	
			} else if(condition.equalsIgnoreCase("name")) {
				sql = "SELECT COUNT(*) FROM member WHERE INSTR(name, ?) = 1";
			} else {
				sql = "SELECT COUNT(*) FROM member WHERE INSTR("+condition+", ?) >= 1";
			}
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			if(condition.equalsIgnoreCase("created")) {
				pstmt.setString(2, keyword);
			}
			rs=pstmt.executeQuery();
			if(rs.next()) {
				result=rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return result;
	}
	
	public void updateMember(MemberDTO dto) throws Exception {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE member set userPwd=?, userTel=?, userEmail=?, userBirth=? where userId=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserPwd());
			pstmt.setString(2, dto.getUserTel());
			pstmt.setString(3, dto.getUserEmail());
			pstmt.setString(4, dto.getUserBirth());
			pstmt.setString(5, dto.getUserId());
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
	}
	
	public MemberDTO readMember(String userId) {
		MemberDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT userId, userPwd, userName, userTel, userEmail, userBirth FROM member WHERE userId = ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				dto = new MemberDTO();
				dto.setUserId(rs.getString("userId"));
				dto.setUserPwd(rs.getString("userPwd"));
				dto.setUserName(rs.getString("userName"));
				dto.setUserTel(rs.getString("userTel"));
				dto.setUserEmail(rs.getString("userEmail"));
				dto.setUserBirth(rs.getString("userBirth"));	
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return dto;
	}
	
	
	public void deleteMember(String userId) throws Exception {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql="DELETE FROM member WHERE userId = ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
	}
	
	public List<YoloDTO> listYolo(String userId) {
		List<YoloDTO> list = new ArrayList<YoloDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		StringBuilder sb=new StringBuilder();
		
		try {
			sb.append("SELECT num, title, userId, TO_CHAR(created, 'YYYY-MM-DD') created, imageFileName, hitCount  ");
			sb.append("  FROM yolo WHERE userId = ?  ");
			sb.append("  ORDER BY num DESC   ");
			sb.append("  OFFSET 0 ROWS FETCH FIRST 5 ROWS ONLY ");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				YoloDTO dto = new YoloDTO();
				dto.setNum(rs.getInt("num"));
				dto.setTitle(rs.getString("title"));
				dto.setUserId(rs.getString("userId"));
				dto.setCreated(rs.getString("created"));
				dto.setImageFileName(rs.getString("imageFileName"));
				dto.setHitCount(rs.getInt("hitCount"));
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return list;
	}
	
	public List<FavoriteDTO> favoriteList(String userId){
		List<FavoriteDTO> list = new ArrayList<FavoriteDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("select f.userId,f.num,title,category from views v	");
			sb.append("	join favorite f on v.num=f.num	");
			sb.append("	where f.userId=?	");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				FavoriteDTO dto = new FavoriteDTO();
				dto.setCategory(rs.getString("category"));
				dto.setUserId(rs.getString("userId"));
				dto.setNum(rs.getInt("num"));
				dto.setTitle(rs.getString("title"));
				list.add(dto);
			}
			sb.delete(0, sb.length());
			pstmt.close();
			pstmt = null;
			
			rs.close();
			rs = null;
			
			sb.append(" select f.userId,f.num,title,category from festival p	");
			sb.append("	join favorite f on p.num=f.num 	");
			sb.append("	where f.userId=?	");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				FavoriteDTO dto = new FavoriteDTO();
				dto.setCategory(rs.getString("category"));
				dto.setUserId(rs.getString("userId"));
				dto.setNum(rs.getInt("num"));
				dto.setTitle(rs.getString("title"));
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return list;
	}
	
}
