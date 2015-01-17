package com.boostme.bean;

import java.io.Serializable;

public class ConsultEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	private String publisherName;
	private String description;
	private String serviceCategoty;
	private String price;
	
	//头像图片的url
	private String headImage;




	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getServiceCategoty() {
		return serviceCategoty;
	}

	public void setServiceCategoty(String serviceCategoty) {
		this.serviceCategoty = serviceCategoty;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	

}
