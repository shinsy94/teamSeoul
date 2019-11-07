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

	public void insertView(ViewsDTO dto) {
		StringBuilder sb = new StringBuilder();
		PreparedStatement pstmt = null;
		try {
			sb.append("insert into view(num,title,content,userId,areaCode, ");
			sb.append(",created) values(.NEXTVAL,?,?,?,?,?) ");
			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getUserId());
			pstmt.setInt(4, dto.getAreaCode());
			pstmt.setString(5, dto.getCreated());

			pstmt.executeUpdate();

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

	}

	public void updateView() {
		String sql;
		PreparedStatement pstmt = null;
		try {

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}

	}

	public void deleteView() {
		String sql;
		PreparedStatement pstmt = null;
		try {

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}

	}

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
			// TODO: handle exception
		}
		return map;
	}

	public List<String> listLocal(int areaCode) {
		List<String> list = new ArrayList<String>();
		String sql;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			sql = "SELECT local FROM area WHERE bigarea = ? AND areacode != ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, areaCode);
			pstmt.setInt(2, areaCode);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				list.add(rs.getString(1));
			}
				
			
		} catch (Exception e) {
			// TODO: handle exception
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
			// TODO: handle exception
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
}
