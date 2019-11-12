package com.festival;

/**
 * @author sist
 *
 */
public class FestivalDTO {
	private int num;
	private String title;
	private String content;
	private String userId;
	private String imageFileName;
	private String somenailImg;
	private String created;
	private int listNum;
	private int seasonCode;
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	
	
	public String getSomenailImg() {
		return somenailImg;
	}
	public void setSomenailImg(String somenailImg) {
		this.somenailImg = somenailImg;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public int getListNum() {
		return listNum;
	}
	public void setListNum(int listNum) {
		this.listNum = listNum;
	}
	public int getSeasonCode() {
		return seasonCode;
	}
	public void setSeasonCode(int seasonCode) {
		this.seasonCode = seasonCode;
	}
	
}
