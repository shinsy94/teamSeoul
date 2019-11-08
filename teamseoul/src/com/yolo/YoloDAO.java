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
		sql="INSERT INTO yolo(num, title, content, userId, imageFileName, attention) VALUES(yolo_seq.NEXTVAL, ?, ?, ?, ?,?)";
		pstmt=conn.prepareStatement(sql);
		
		pstmt.setString(1, dto.getTitle());
		pstmt.setString(2, dto.getContent());
		pstmt.setString(3, dto.getUserId());
		pstmt.setString(4, dto.getImageFileName());
		pstmt.setInt(5, dto.getAttention());
		
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

// 전체 개수
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


// 검색에서의 전체 개수
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


// 욜로족 리스트 불러오기
public List<YoloDTO> listYolo(int offset, int rows) {
	List<YoloDTO> list = new ArrayList<YoloDTO>();
	PreparedStatement pstmt = null;
	ResultSet rs=null;
	StringBuilder sb=new StringBuilder();
	
	try {
		sb.append("SELECT num, title, y.userId, created, imageFileName, hitCount  ");
		sb.append("  FROM yolo y  ");
		sb.append("  JOIN member m ON y.userId = m.userId  ");
		sb.append("  ORDER BY num DESC   ");
		sb.append("  OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
		
		pstmt=conn.prepareStatement(sb.toString());
		pstmt.setInt(1, offset);
		pstmt.setInt(2, rows);
		
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


// 욜로족 검색 리스트 불러오기
public List<YoloDTO> listYolo(int offset, int rows, String condition, String keyword) {
	List<YoloDTO> list = new ArrayList<YoloDTO>();
	PreparedStatement pstmt = null;
	ResultSet rs=null;
	StringBuilder sb=new StringBuilder();
	
	try {
		sb.append("SELECT num, title, y.userId, created, imageFileName, hitCount  ");
		sb.append("  FROM yolo y  ");
		sb.append("  JOIN member m ON y.userId = m.userId  ");
		
		if(condition.equalsIgnoreCase("created")) {
			keyword=keyword.replaceAll("-", "");
			sb.append("    WHERE TO_CHAR(created, 'YYYYMMDD') = ? ");
		} else {
			sb.append("    WHERE INSTR("+condition+", ?) >= 1 ");
		}
		sb.append("  ORDER BY num DESC   ");
		sb.append("  OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
		
		pstmt = conn.prepareStatement(sb.toString());
		
		pstmt.setString(1, keyword);
		pstmt.setInt(2, offset);
		pstmt.setInt(3, rows);
		
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


// 욜로족 공지글
public List<YoloDTO> listAttention() {
	List<YoloDTO> list = new ArrayList<YoloDTO>();
	PreparedStatement pstmt = null;
	ResultSet rs=null;
	StringBuilder sb=new StringBuilder();
	
	try {
		sb.append("SELECT num, title, y.userId, created, imageFileName, hitCount  ");
		sb.append("  FROM yolo y  ");
		sb.append("  JOIN member m ON y.userId = m.userId  ");
		sb.append("	 WHERE attention=1 ");
		sb.append("  ORDER BY num DESC   ");
		
		pstmt = conn.prepareStatement(sb.toString());
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


// 욜로족 작성된 글 내용 불러오기
public YoloDTO readYolo(int num){
	YoloDTO dto=null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuilder sb = new StringBuilder();
	
	try {
		sb.append("SELECT num, title, y.userId, created, imageFileName, hitCount, content, attention  ");
		sb.append("  FROM yolo y  ");
		sb.append("  JOIN member m ON y.userId = m.userId  ");
		sb.append("  WHERE num = ? ");
		
		pstmt = conn.prepareStatement(sb.toString());
		pstmt.setInt(1, num);
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			dto = new YoloDTO();
			
			dto.setNum(rs.getInt("num"));
			dto.setTitle(rs.getString("title"));
			dto.setUserId(rs.getString("userId"));
			dto.setCreated(rs.getString("created"));
			dto.setImageFileName(rs.getString("imageFileName"));
			dto.setHitCount(rs.getInt("hitCount"));
			dto.setContent(rs.getString("content"));
			dto.setAttention(rs.getInt("attention"));
			
		 }
    } catch (Exception e) {
       e.printStackTrace();
    } finally {
       if(pstmt != null) {
          try {
             pstmt.close();
          } catch (Exception e2) {
          }
       }
       if(rs != null) {
          try {
             rs.close();
          } catch (Exception e2) {
          }
       }
    }
    
    
    return dto;
}


// 욜로족 이전글
public YoloDTO preReadYolo(int num, String condition, String keyword) {
	YoloDTO dto=null;
	
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	StringBuffer sb = new StringBuffer();
	
	try {
		if(keyword.length() != 0) {
			sb.append("SELECT num, title FROM yolo y JOIN member m ON y.userId = m.userId  ");
			if(condition.equalsIgnoreCase("created")) {
				keyword=keyword.replaceAll("-", "");
				sb.append(" WHERE (TO_CHAR(created, 'YYYYMMDD') = ?)  ");
            } else {
            	sb.append(" WHERE (INSTR(" + condition + ", ?) >= 1)  ");
            }
			sb.append("         AND (num > ? )  ");
            sb.append(" ORDER BY num ASC  ");
            sb.append(" FETCH  FIRST  1  ROWS  ONLY");
            
            pstmt=conn.prepareStatement(sb.toString());
            pstmt.setString(1, keyword);
            pstmt.setInt(2, num);
		} else {
			sb.append("SELECT num, title FROM yolo y JOIN member m ON y.userId = m.userId  ");
			sb.append(" WHERE num > ?  ");
			sb.append(" ORDER BY num ASC  ");
			sb.append(" FETCH  FIRST  1  ROWS  ONLY");
			
			pstmt=conn.prepareStatement(sb.toString());
            pstmt.setInt(1, num);
		}
		
		rs=pstmt.executeQuery();
		
		if(rs.next()) {
			dto=new YoloDTO();
			dto.setNum(rs.getInt("num"));
			dto.setTitle(rs.getString("title"));
		}
         
	  } catch (Exception e) {
          e.printStackTrace();
      } finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
				
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
  
      return dto;
  }


// 욜로족 다음글
public YoloDTO nextReadYolo(int num, String condition, String keyword) {
	YoloDTO dto=null;
	
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	StringBuffer sb = new StringBuffer();
	
	try {
		if(keyword.length() != 0) {
			sb.append("SELECT num, title FROM yolo y JOIN member m ON y.userId = m.userId  ");
			if(condition.equalsIgnoreCase("created")) {
				keyword=keyword.replaceAll("-", "");
				sb.append(" WHERE (TO_CHAR(created, 'YYYYMMDD') = ?)  ");
            } else {
            	sb.append(" WHERE (INSTR(" + condition + ", ?) >= 1)  ");
            }
			sb.append("         AND (num < ? )  ");
            sb.append(" ORDER BY num DESC  ");
            sb.append(" FETCH  FIRST  1  ROWS  ONLY");
            
            pstmt=conn.prepareStatement(sb.toString());
            pstmt.setString(1, keyword);
            pstmt.setInt(2, num);
		} else {
			sb.append("SELECT num, title FROM yolo y JOIN member m ON y.userId = m.userId  ");
			sb.append(" WHERE num < ?  ");
			sb.append(" ORDER BY num DESC  ");
			sb.append(" FETCH  FIRST  1  ROWS  ONLY");
			
			pstmt=conn.prepareStatement(sb.toString());
            pstmt.setInt(1, num);
		}
		
		rs=pstmt.executeQuery();
		
		if(rs.next()) {
			dto=new YoloDTO();
			dto.setNum(rs.getInt("num"));
			dto.setTitle(rs.getString("title"));
		}
         
	  } catch (Exception e) {
          e.printStackTrace();
      } finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
				
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
  
      return dto;
  }


// 욜로족 조회수 업데이트
public void updateHitCount(int num) {
	PreparedStatement pstmt = null;
	String sql;
	
	try {
		sql = "UPDATE yolo SET hitCount=hitCount+1 WHERE num=?";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, num);
		pstmt.executeUpdate();
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
			}
		}
	}
}


// 욜로족 게시글 수정
public void updateYolo(YoloDTO dto) {
	PreparedStatement pstmt = null;
	String sql;
	
	try {
		sql="UPDATE yolo SET title=?, content=?, imageFileName=?, attention=? ";
		sql+= " WHERE num=? AND userId=?";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, dto.getTitle());
		pstmt.setString(2, dto.getContent());
		pstmt.setString(3, dto.getImageFileName());
		pstmt.setInt(4, dto.getAttention());
		pstmt.setInt(5, dto.getNum());
		pstmt.setString(6, dto.getUserId());
		pstmt.executeUpdate();
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
			}
		}
	}
}


// 욜로족 게시글 삭제
public void deleteYolo(int num, String userId) {
	PreparedStatement pstmt = null;
	String sql;
	
	try {
		if(userId.equals("admin")) {
			sql="DELETE FROM yolo WHERE num = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
		} else {
			sql="DELETE FROM yolo WHERE num = ? AND userId = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, userId);
		}
		
		pstmt.executeUpdate();
		
		
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
			}
		}
	}
}



}