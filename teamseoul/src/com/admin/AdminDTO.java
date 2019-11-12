package com.admin;

import java.util.List;

public class AdminDTO {
	private int num;
	private String title;
	private String content;
	private String userId;
	private int areaCode; 
	private List<String> imageFileName;
	private int seasonCode;
	private String created;
	private int listNum;
	private String eventLink;
	private String originalFileName;
	private String saveFileName;
	private long filesize;
	private String updated;
	private String local;
	private String seacon;
	
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public String getSeacon() {
		return seacon;
	}
	public void setSeacon(String seacon) {
		this.seacon = seacon;
	}
	public List<String> getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(List<String> imageFileName) {
		this.imageFileName = imageFileName;
	}
	public int getSeasonCode() {
		return seasonCode;
	}
	public void setSeasonCode(int seasonCode) {
		this.seasonCode = seasonCode;
	}
	public String getEventLink() {
		return eventLink;
	}
	public void setEventLink(String eventLink) {
		this.eventLink = eventLink;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public String getSaveFileName() {
		return saveFileName;
	}
	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}
	public long getFilesize() {
		return filesize;
	}
	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}	
	
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
	public int getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(int areaCode) {
		this.areaCode = areaCode;
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
}
