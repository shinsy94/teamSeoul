package com.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.util.DBConn;

public class AdminDAO {
	private Connection conn=DBConn.getConnection();
	
	public void insertView(AdminDTO dto) {
		StringBuilder sb= new StringBuilder();
		PreparedStatement pstmt=null;
		try {
			sb.append("insert into views(num,title,content,userId,areaCode ");
			sb.append(") values(views_seq.NEXTVAL,?,?,?,?) ");
			pstmt=conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getUserId());
			pstmt.setInt(4, dto.getAreaCode());
			pstmt.executeUpdate();
			
			pstmt.close();
			sb.setLength(0);
			
			sb.append("insert into viewsFile(num,imagefilename) values(views_seq.CURRVAL,?)");
			for(int i=0;i<dto.getImageFileName().size();i++) {
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setString(1, dto.getImageFileName().get(i));
				pstmt.executeUpdate();
				pstmt.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			 }
		  }
		
	   }
	
	public void updateView() {
		String sql;
		PreparedStatement pstmt=null;
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			 }
		  }
		
	   }
	
	public void deleteView() {
		String sql;
		PreparedStatement pstmt=null;
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			 }
		  }
		
	   }
	
	public void insertFestival(AdminDTO dto) {
		StringBuilder sb= new StringBuilder();
		PreparedStatement pstmt=null;
		try {
			sb.append("insert into festival(num,title,content,userId,seasonCode ");
			sb.append(") values(festival_seq.NEXTVAL,?,?,?,?) ");
			pstmt=conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getUserId());
			pstmt.setInt(4, dto.getSeasonCode());
			pstmt.executeUpdate();
			
			pstmt.close();
			sb.setLength(0);
			
			sb.append("insert into festivalFile(num,imagefilename) values(festival_seq.CURRVAL,?)");
			for(int i=0;i<dto.getImageFileName().size();i++) {
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setString(1, dto.getImageFileName().get(i));
				pstmt.executeUpdate();
				pstmt.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			 }
		  }
		
	   }
	
	public void insertEvent(AdminDTO dto) {
		StringBuilder sb= new StringBuilder();
		PreparedStatement pstmt=null;
		try {
			sb.append("insert into event(num,title,content,userId,eventLink ");
			sb.append(") values(event_seq.NEXTVAL,?,?,?,?) ");
			pstmt=conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getUserId());
			pstmt.setString(4, dto.getEventLink());
			pstmt.executeUpdate();
			
			pstmt.close();
			sb.setLength(0);
			
			sb.append("insert into eventFile(num,imagefilename) values(event_seq.CURRVAL,?)");
			for(int i=0;i<dto.getImageFileName().size();i++) {
				pstmt=conn.prepareStatement(sb.toString());
				pstmt.setString(1, dto.getImageFileName().get(i));
				pstmt.executeUpdate();
				pstmt.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			 }
		  }
		
	   }
	public void insertNotice(AdminDTO dto) {
		StringBuilder sb= new StringBuilder();
		PreparedStatement pstmt=null;
		try {
			sb.append("insert into notice(num,title,content,userId,originalfilename ");
			sb.append(",savefilename,filesize) values(event_seq.NEXTVAL,?,?,?,?,?,?) ");
			pstmt=conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getUserId());
			pstmt.setString(4, dto.getOriginalFileName());
			pstmt.setString(5, dto.getSaveFileName());
			pstmt.setLong(6, dto.getFilesize());
			pstmt.executeUpdate();
	
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			 }
		  }
		
	   }	

	public Map<String,String> ListAreaCode(String bigareaCode) {
		Map<String,String> map =new HashMap<String,String>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		try {
			sql="select areaCode,local from area where bigarea=? and areaCode!=?";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, bigareaCode);
			pstmt.setString(2, bigareaCode);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				map.put(rs.getString("areaCode"), rs.getString("local"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}
}
