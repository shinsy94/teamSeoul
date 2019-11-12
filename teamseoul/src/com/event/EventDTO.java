package com.event;

import java.util.List;

public class EventDTO {
	private int num; //�۹�ȣ
	private String title;//����
	private String content; //����
	private String userId; //�ۼ���
	private List<String> imageFileName; //�̹��������̸�
	private String imageName;
	private String eventLink; //�̺�Ʈ ��ũ �ּ�
	private String created; //�ۼ�����
	
	
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
