package com.boostme.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class QuestionEntity implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@SerializedName("qid")
	private int qid;
	
	@SerializedName("author")
	private String postName;
	
	@SerializedName("authorid")
	private int authorid;
	
	@SerializedName("title")
	private String title;
	
	@SerializedName("time")
	private long postTime;
	
	@SerializedName("format_time")
	private String formatPostTime;
	
	@SerializedName("strip_description")
	private String postContent;
	
	@SerializedName("goods")
	private Integer favourNum;
	
	@SerializedName("answers")
	private Integer replyNum;
	
	@SerializedName("avatar")
	private String headIcon;
	
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public String getFormatPostTime() {
		return formatPostTime;
	}
	public void setFormatPostTime(String formatPostTime) {
		this.formatPostTime = formatPostTime;
	}
	public long getPostTime() {
		return postTime;
	}
	public void setPostTime(long postTime) {
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
	public int getQid()
	{
		return qid;
	}
	public void setQid(int qid)
	{
		this.qid = qid;
	}
	public int getAuthorid()
	{
		return authorid;
	}
	public void setAuthorid(int authorid)
	{
		this.authorid = authorid;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	@Override
	public String toString()
	{
		return "QuestionEntity [qid=" + qid + ", postName=" + postName
				+ ", authorid=" + authorid + ", title=" + title + ", postTime="
				+ postTime + ", postContent=" + postContent + ", favourNum="
				+ favourNum + ", replyNum=" + replyNum + ", headIcon="
				+ headIcon + "]";
	}
}
