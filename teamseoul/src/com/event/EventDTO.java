package com.event;

import java.util.List;

public class EventDTO {
	private int num; //글번호
	private String title;//제목
	private String content; //내용
	private String userId; //작성자
	private List<String> imageFileName; //이미지파일이름
	private String imageName;
	private String eventLink; //이벤트 링크 주소
	private String created; //작성일자
	
	
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

	public String getEventLink() {
		return eventLink;
	}
	public void setEventLink(String eventLink) {
		this.eventLink = eventLink;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public List<String> getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(List<String> imageFileName) {
		this.imageFileName = imageFileName;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

}
