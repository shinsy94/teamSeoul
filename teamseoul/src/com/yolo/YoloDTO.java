package com.yolo;

public class YoloDTO {

private int num, hitcount, attention, listNum, notice;
private String title, content, userId, created, imageFileName;
private long filesize, gap;


public int getNotice() {
	return notice;
}
public void setNotice(int notice) {
	this.notice = notice;
}
public int getListNum() {
	return listNum;
}
public void setListNum(int listNum) {
	this.listNum = listNum;
}
public long getFilesize() {
	return filesize;
}
public void setFilesize(long filesize) {
	this.filesize = filesize;
}
public long getGap() {
	return gap;
}
public void setGap(long gap) {
	this.gap = gap;
}
public int getNum() {
	return num;
}
public void setNum(int num) {
	this.num = num;
}
public int getHitcount() {
	return hitcount;
}
public void setHitcount(int hitcount) {
	this.hitcount = hitcount;
}
public int getAttention() {
	return attention;
}
public void setAttention(int attention) {
	this.attention = attention;
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
public String getCreated() {
	return created;
}
public void setCreated(String created) {
	this.created = created;
}
public String getImageFileName() {
	return imageFileName;
}
public void setImageFileName(String imageFileName) {
	this.imageFileName = imageFileName;
}
	


}
