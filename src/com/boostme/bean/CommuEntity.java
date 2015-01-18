package com.boostme.bean;

import java.io.Serializable;

public class CommuEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String postName;
	private String postTime;
	
	private String postContent;
	private Integer favourNum;
	private Integer replyNum;
	//头像的icon url
	private String headIcon;
	
	
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public String getPostTime() {
		return postTime;
	}
	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	public String getFavourNum() {
		return favourNum + "";
	}
	public void setFavourNum(Integer favourNum) {
		this.favourNum = favourNum;
	}
	public String getReplyNum() {
		return replyNum + "";
	}
	public void setReplyNum(Integer replyNum) {
		this.replyNum = replyNum;
	}
	public String getHeadIcon() {
		return headIcon;
	}
	public void setHeadIcon(String headIcon) {
		this.headIcon = headIcon;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString()
	{
		return "CommuEntity [postName=" + postName + ", postTime=" + postTime
				+ ", postContent=" + postContent + ", favourNum=" + favourNum
				+ ", replyNum=" + replyNum + ", headIcon=" + headIcon + "]";
	}
}
