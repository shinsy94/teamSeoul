package com.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.util.DBConn;

public class MemberDAO {
	private Connection conn=DBConn.getConnection();
	
	public void insertMember(MemberDTO dto) throws Exception {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO member(userId, userPwd, userName, userTel, userEmail, userBirth, nickname) VALUES(?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getUserPwd());
			pstmt.setString(3, dto.getUserName());
			pstmt.setString(4, dto.getUserTel());
			pstmt.setString(5, dto.getUserEmail());
			pstmt.setString(6, dto.getUserBirth());
			pstmt.setString(7, dto.getNickname());
			
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
			sql = "UPDATE member set userPwd=?, userTel=?, userEmail=?, userBitrh=?, userNickname=? ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserPwd());
			pstmt.setString(2, dto.getUserTel());
			pstmt.setString(3, dto.getUserEmail());
			pstmt.setString(4, dto.getUserBirth());
			pstmt.setString(5, dto.getNickname());

			
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
			sql = "SELECT userId, userPwd, userName, userTel, userEmail, userBirth, nickname, yoloCount, tierCode FROM member WHERE userId = ?";
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
				dto.setNickname(rs.getString("nickname"));
				dto.setYoloCount(rs.getInt("yoloCount"));
				dto.setTierCode(rs.getInt("tierCode"));			
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
	
	
}
