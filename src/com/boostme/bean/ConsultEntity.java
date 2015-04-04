package com.boostme.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class ConsultEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@SerializedName("id")
	private String id;

	@SerializedName("uid")
	private String uid;

	@SerializedName("username")
	private String publisherName;

	@SerializedName("profile")
	private String description;

	private String serviceCategoty = "";

	@SerializedName("price")
	private String price;

	@SerializedName("service_num")
	private String serviceNum;

	@SerializedName("comment_num")
	private String commentNum;

	@SerializedName("view_num")
	private String viewNum;

	@SerializedName("format_time")
	private String formatTime;

	@SerializedName("avg_score")
	private String avgScore;

	// 头像图片的url
	@SerializedName("picture")
	private String headImageUrl;

	public String getHeadImageUrl() {
		return headImageUrl;
	}

	public void setHeadImageUrl(String headImage) {
		this.headImageUrl = headImage;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getServiceNum() {
		return serviceNum;
	}

	public void setServiceNum(String serviceNum) {
		this.serviceNum = serviceNum;
	}

	public String getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(String commentNum) {
		this.commentNum = commentNum;
	}

	public String getViewNum() {
		return viewNum;
	}

	public void setViewNum(String viewNum) {
		this.viewNum = viewNum;
	}

	public String getFormatTime() {
		return formatTime;
	}

	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}

	public String getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(String avgScore) {
		this.avgScore = avgScore;
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

	public String toString() {

		return "ConsultEntity [id=" + id + ", uid=" + uid + ", userName="
				+ publisherName + ", profile=" + description + ", price="
				+ price + ", picture=" + headImageUrl + ", avgScore="
				+ avgScore + ", serviceNum=" + serviceNum + ", commentNum="
				+ commentNum + ", viewNum=" + viewNum + ", formatTime="
				+ formatTime + "]";
	}

}
