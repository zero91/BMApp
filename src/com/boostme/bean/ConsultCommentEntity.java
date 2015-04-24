package com.boostme.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class ConsultCommentEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SerializedName("id")
	private String id;

	@SerializedName("authorid")
	private String autiorId;

	@SerializedName("author")
	private String author;

	@SerializedName("sid")
	private String serviceId;

	@SerializedName("score")
	private String score;

	@SerializedName("content")
	private String content;

	@SerializedName("format_time")
	private String formatTime;

	@SerializedName("time")
	private String time;

	@SerializedName("avatar")
	private String avatarUrl;

	@SerializedName("up")
	private String upNum;

	@SerializedName("down")
	private String downNum;

	public String getAutiorId() {
		return autiorId;
	}

	public void setAutiorId(String autiorId) {
		this.autiorId = autiorId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFormatTime() {
		return formatTime;
	}

	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getUpNum() {
		return upNum;
	}

	public void setUpNum(String upNum) {
		this.upNum = upNum;
	}

	public String getDownNum() {
		return downNum;
	}

	public void setDownNum(String downNum) {
		this.downNum = downNum;
	}

}
