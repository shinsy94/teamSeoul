package com.yolo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yolo.YoloDTO;
import com.util.DBConn;

public class YoloDAO {
private Connection conn=DBConn.getConnection();

// 욜로족 글작성
public int insertyolo(YoloDTO dto) {
	int result=0;
	PreparedStatement pstmt=null;
	String sql;
	
	try {
		sql="INSERT INTO yolo(num, title, content, userId, created, imageFileName, hitCount) VALUES(yolo_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";
		pstmt=conn.prepareStatement(sql);
		
		pstmt.setString(1, dto.getTitle());
		pstmt.setString(2, dto.getContent());
		pstmt.setString(3, dto.getUserId());
		pstmt.setString(4, dto.getCreated());
		pstmt.setString(5, dto.getImageFileName());
		pstmt.setInt(6, dto.getHitcount());
		
		pstmt.executeUpdate();
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	return result;
}

public int dataCount() {
	int result = 0;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	String sql;
	
	try {
		sql = "Select NVL(count(*), 0) FROM yolo";
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
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql;
	
	try {
		sql = "SELECT NVL(COUNT(*), 0) FROM yolo y JOIN member m ON y.userId = m.userId ";
		if(condition.equalsIgnoreCase("created")) {
			keyword=keyword.replaceAll("-", "");
			sql += " WHERE TO_CHAR(created, 'YYYYMMDD') = ?";
		} else {
			sql += " WHERE INSTR("+condition+", ?) >= 1";
		}
		
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, keyword);
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

public List<YoloDTO> listYolo(int offset, int rows) {
	List<YoloDTO> list = new ArrayList<YoloDTO>();
	PreparedStatement pstmt = null;
	ResultSet rs=null;
	StringBuilder sb=new StringBuilder();
	
	try {
		sb.append("SELECT num, title, content, userId, created, imageFileName, hitCount  ");
		sb.append(" FROM yolo y  ");
		sb.append(" JOIN member m ON y.userId = m.userId  ");
		sb.append(" ORDER BY num DESC   ");
		sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
		
		pstmt=conn.prepareStatement(sb.toString());
		pstmt.setInt(1, offset);
		pstmt.setInt(2, rows);
		
		rs = pstmt.executeQuery();
		while(rs.next()) {
			YoloDTO dto = new YoloDTO();
			dto.setNum(rs.getInt("num"));
			dto.setTitle(rs.getString("title"));
			dto.setContent(rs.getString("content"));
			dto.setUserId(rs.getString("userId"));
			dto.setCreated(rs.getString("created"));
			dto.setImageFileName(rs.getString("imageFileName"));
			dto.setHitcount(rs.getInt("hitCount"));
			
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

public List<YoloDTO> listYolo(int offset, int rows, String condition, String keyword) {
	
	
	return null;
}

public List<YoloDTO> listYolo(){
	
	
	return null;
}

public YoloDTO readYolo(int num){
	
	
	return null;
}

public YoloDTO preReadYolo(int num, String condition, String keyword) {
	
	
	return null;
}

public YoloDTO nextReadYolo(int num, String condition, String keyword) {
	
	
	return null;
}

public void updateHitCount(int num) {
	
}

public void updateYolo(YoloDTO dto) {
	
}

public void deleteYolo(int num, String userId) {
	
}


}
