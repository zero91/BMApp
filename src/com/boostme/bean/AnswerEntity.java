package com.boostme.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class AnswerEntity implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@SerializedName("qid")
	private int qid;
	
	@SerializedName("author")
	private String author;
	
	@SerializedName("authorid")
	private int authorid;
	
	@SerializedName("title")
	private String title;
	
	@SerializedName("content")
	private String content;
	
	@SerializedName("time")
	private long time;
	
	@SerializedName("format_time")
	private String formatTime;
	
	@SerializedName("comments")
	private Integer comments;
	
	@SerializedName("supports")
	private Integer supports;
	
	@SerializedName("avatar")
	private String avatar;

	public int getQid()
	{
		return qid;
	}

	public void setQid(int qid)
	{
		this.qid = qid;
	}

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(String author)
	{
		this.author = author;
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

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public long getTime()
	{
		return time;
	}

	public void setTime(long time)
	{
		this.time = time;
	}

	public String getFormatTime()
	{
		return formatTime;
	}

	public void setFormatTime(String formatTime)
	{
		this.formatTime = formatTime;
	}

	public Integer getComments()
	{
		return comments;
	}

	public void setComments(Integer comments)
	{
		this.comments = comments;
	}

	public Integer getSupports()
	{
		return supports;
	}

	public void setSupports(Integer supports)
	{
		this.supports = supports;
	}

	public String getAvatar()
	{
		return avatar;
	}

	public void setAvatar(String avatar)
	{
		this.avatar = avatar;
	}

	@Override
	public String toString()
	{
		return "AnswerEntity [qid=" + qid + ", author=" + author
				+ ", authorid=" + authorid + ", title=" + title + ", content="
				+ content + ", time=" + time + ", formatTime=" + formatTime
				+ ", comments=" + comments + ", supports=" + supports
				+ ", avatar=" + avatar + "]";
	}
}
