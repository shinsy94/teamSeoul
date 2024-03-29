package com.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Calendar;

import javax.servlet.http.HttpServletResponse;

public class FileManager {
	/**
	 * 파일 다운로드 메소드
	 * @param saveFilename 서버에저장된파일명
	 * @param originalFilename 클라이언트가업로드한파일명
	 * @param pathname 서버에저장된경로
	 * @param resp HttpServletResponse 객체
	 * @return 다운로드성공여부
	 */
	public static boolean doFiledownload(String saveFilename, String originalFilename, String pathname, HttpServletResponse resp) {
		boolean flag=false;
		
		if(pathname==null || saveFilename==null || saveFilename.length()==0 ||
				originalFilename==null || originalFilename.length()==0) {
			return flag;
		}
		
		try {
			originalFilename=new String(
					originalFilename.getBytes("euc-kr"), "8859_1");
			pathname=pathname+File.separator+saveFilename;
			File f=new File(pathname);
			if(! f.exists()) {
				return flag;
			}
			
			// 클라이언트에게 전송할 문서타입이 스트림이라고 설정
			resp.setContentType("application/octet-stream");
			
			// 파일명은 헤더에
			resp.setHeader("Content-disposition",
					"attachment;filename="+originalFilename);
			
			// 클라이언트에게 파일의 내용을 전송
			byte[] b=new byte[1024];
			BufferedInputStream bis=
					new BufferedInputStream(
							new FileInputStream(f));
			
			// 클라이언트에게 전송할 출력 스트림
			OutputStream os=resp.getOutputStream();
			
			int n;
			while((n=bis.read(b, 0, b.length))!=-1) {
				os.write(b, 0, n);
			}
			os.flush();
			os.close();
			bis.close();
			
			flag=true;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return flag;
	}
	
	/**
	 * 파일 이름 변경(년월일시분초나노초)
	 * @param pathname 파일이저장된 경로
	 * @param filename 변경할 파일명
	 * @return 새로운파일명
	 */
	public static String doFilerename(String pathname, String filename) {
		String newname="";
		
    	String fileExt = filename.substring(
    			       filename.lastIndexOf(".")); // 파일 중간에 .이 있을 수 도 있기때문에 뒤에서 부터 찾아야 한다.
    	// 파일 이름을 현재시간으로 변경할 거임
    	String s = String.format(
    			"%1$tY%1$tm%1$td%1$tH%1$tM%1$tS",  
				 Calendar.getInstance());
    	
    	
    	s += System.nanoTime(); // 파일 시간이 겹치는걸 방지하기 위해 파일 이름 뒤에 나노타임을 더한다.
    	s += fileExt; // 파일명 뒤에 확장자 붙이기.
    	
    	try {
	    	File f1=new File(pathname+File.separator+filename); // 원래 이름
	    	File f2=new File(pathname+File.separator+s); // 새로운 이름
	    	f1.renameTo(f2); // 이름 변경. 동일 이름이 있다면 안바뀌고 터짐.
	    	
	    	newname = s;
    	}catch(Exception e) {
    	}
		
		return newname;
	}
	
	public static String doFilerenameSomeNail(String pathname, String filename) {
		String newname="";
		
    	String fileExt = filename.substring(
    			       filename.lastIndexOf(".")); // 파일 중간에 .이 있을 수 도 있기때문에 뒤에서 부터 찾아야 한다.
    	// 파일 이름을 현재시간으로 변경할 거임
    	String s = String.format(
    			"%1$tY%1$tm%1$td%1$tH%1$tM%1$tS",  
				 Calendar.getInstance());
    	
    	
    	s += System.nanoTime(); // 파일 시간이 겹치는걸 방지하기 위해 파일 이름 뒤에 나노타임을 더한다.
    	s += fileExt; // 파일명 뒤에 확장자 붙이기.
    	
    	try {
	    	File f1=new File(pathname+File.separator+filename); // 원래 이름
	    	File f2=new File(pathname+File.separator+"some"+s); // 새로운 이름
	    	f1.renameTo(f2); // 이름 변경. 동일 이름이 있다면 안바뀌고 터짐.
	    	
	    	newname ="some"+s;
    	}catch(Exception e) {
    	}
		
		return newname;
	}
	
	public static String doFilerenameBody(String pathname, String filename) {
		String newname="";
		
    	String fileExt = filename.substring(
    			       filename.lastIndexOf(".")); // 파일 중간에 .이 있을 수 도 있기때문에 뒤에서 부터 찾아야 한다.
    	// 파일 이름을 현재시간으로 변경할 거임
    	String s = String.format(
    			"%1$tY%1$tm%1$td%1$tH%1$tM%1$tS",  
				 Calendar.getInstance());
    	
    	
    	s +=System.nanoTime(); // 파일 시간이 겹치는걸 방지하기 위해 파일 이름 뒤에 나노타임을 더한다.
    	s += fileExt; // 파일명 뒤에 확장자 붙이기.
    	
    	try {
	    	File f1=new File(pathname+File.separator+filename); // 원래 이름
	    	File f2=new File(pathname+File.separator+"body"+s); // 새로운 이름
	    	f1.renameTo(f2); // 이름 변경. 동일 이름이 있다면 안바뀌고 터짐.
	    	
	    	newname = "body"+s;
    	}catch(Exception e) {
    	}
		
		return newname;
	}
	
	/**
	 * 파일 삭제
	 * @param pathname 파일이 저장된 경로
	 * @param filename 삭제할 파일명
	 * @return 파일 삭제 성공 여부
	 */
	public static boolean doFiledelete(String pathname, String filename) {
		String path=pathname+File.separator+filename;
		
		try {
			File f=new File(path); // 파일 처리 할때 path는 리얼 주소여야 한다.
			
			if(! f.exists()) // 파일이 없으면
				return false;
			
			f.delete();
		} catch (Exception e) {
		}
		
		return true;
	}
	
}
