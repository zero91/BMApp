package com.boostme.bean;

import java.io.Serializable;

public class ConsultEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	private String title;
	private String info;
	//头像图片的url
	private String headImage;

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

}
