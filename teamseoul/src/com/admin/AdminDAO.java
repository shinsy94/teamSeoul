package com.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.event.EventDTO;
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
	
	public void updateViews(AdminDTO dto) {
		String sql;
		PreparedStatement pstmt=null;
		try {
			sql="update views set title=?,content=?,userId=?,areaCode=? where num=?";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getUserId());
			pstmt.setInt(4, dto.getAreaCode());
			pstmt.setInt(5, dto.getNum());
			pstmt.executeUpdate();
			pstmt.close();
			
			if(dto.getImageFileName()==null) {
				return;
			}
			sql="insert into viewsFile(num,imagefilename) values(?,?)";
			for(int i=0;i<dto.getImageFileName().size();i++) {
				pstmt=conn.prepareStatement(sql);
				
				pstmt.setInt(1, dto.getNum());
				pstmt.setString(2, dto.getImageFileName().get(i));
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
	
	public void updateFestival(AdminDTO dto) {

		String sql;
		PreparedStatement pstmt=null;
		try {
			sql="update Festival set title=?,content=?,userId=?,seasonCode=? where num=?";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getUserId());
			pstmt.setInt(4, dto.getSeasonCode());
			pstmt.setInt(5, dto.getNum());
			pstmt.executeUpdate();
			pstmt.close();
			
			if(dto.getImageFileName()==null) {
				return;
			}
			sql="insert into FestivalFile(num,imagefilename) values(?,?)";
			for(int i=0;i<dto.getImageFileName().size();i++) {
				pstmt=conn.prepareStatement(sql);
				
				pstmt.setInt(1, dto.getNum());
				pstmt.setString(2, dto.getImageFileName().get(i));
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
	
	public void updateEvent(AdminDTO dto) {

		String sql;
		PreparedStatement pstmt=null;
		try {
			sql="update Event set title=?,content=?,userId=?,EventLink=? where num=?";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getUserId());
			pstmt.setString(4, dto.getEventLink());
			pstmt.setInt(5, dto.getNum());
			pstmt.executeUpdate();
			pstmt.close();
			
			if(dto.getImageFileName()==null) {
				return;
			}
			sql="insert into EventFile(num,imagefilename) values(?,?)";
			for(int i=0;i<dto.getImageFileName().size();i++) {
				pstmt=conn.prepareStatement(sql);
				
				pstmt.setInt(1, dto.getNum());
				pstmt.setString(2, dto.getImageFileName().get(i));
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
	
	public void deleteViews() {
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
	public List<EventDTO> readEvent(int num) {
		EventDTO dto=null;
		List<EventDTO> list=new ArrayList<EventDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT e.num, title, content, e.userId, f.imageFileName, eventLink, created FROM event e JOIN eventfile f ON e.num = f.num JOIN member m ON e.userId=m.userId WHERE e.num=? ");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, num);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				dto=new EventDTO();
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setUserId(rs.getString("userId"));
				dto.setImageName(rs.getString("imageFileName"));
				dto.setEventLink(rs.getString("eventLink"));
				dto.setCreated(rs.getString("created"));
				list.add(dto);
				
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
		
		return list;
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
	
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		return map;
	}
	
	public Map<String,String> ListseasonCode(String seasonCode) {
		Map<String,String> map =new HashMap<String,String>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		try {
			sql="select seasonCode,season from sea where seasonCode=? ";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, seasonCode);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				map.put(rs.getString("seasonCode"), rs.getString("season"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}
	
	public void deleteVEF(int num,String table,String imageFileName) {
		PreparedStatement pstmt=null;
		String sql;
		try {
			sql="delete from "+table+"file where num=? and imagefilename=?";
			pstmt=conn.prepareStatement(sql);
			
			
			pstmt.setInt(1, num);
			pstmt.setString(2, imageFileName);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}
	
	
}
