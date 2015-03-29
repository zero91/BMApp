package com.boostme.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class QuestionEntity implements Serializable
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
	
	@SerializedName("description")
	private String description;
	
	@SerializedName("time")
	private long time;
	
	@SerializedName("update_time")
	private long updateTime;
	
	@SerializedName("answers")
	private Integer replyNum;
	
	@SerializedName("attentions")
	private Integer attentions;
	
	@SerializedName("goods")
	private Integer favourNum;
	
	@SerializedName("views")
	private Integer views;
	
	@SerializedName("status")
	private Integer status;
	
	@SerializedName("avatar")
	private String avatar;
	
	@SerializedName("format_time")
	private String formatTime;
	
	@SerializedName("format_update_time")
	private String formatUptdateTime;
	
	@SerializedName("strip_description")
	private String stripDescription;
	
	@SerializedName("images")
	private String [] images;

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

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public long getTime()
	{
		return time;
	}

	public void setTime(long time)
	{
		this.time = time;
	}

	public long getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(long updateTime)
	{
		this.updateTime = updateTime;
	}

	public Integer getReplyNum()
	{
		return replyNum;
	}

	public void setReplyNum(Integer replyNum)
	{
		this.replyNum = replyNum;
	}

	public Integer getAttentions()
	{
		return attentions;
	}

	public void setAttentions(Integer attentions)
	{
		this.attentions = attentions;
	}

	public Integer getFavourNum()
	{
		return favourNum;
	}

	public void setFavourNum(Integer favourNum)
	{
		this.favourNum = favourNum;
	}

	public Integer getViews()
	{
		return views;
	}

	public void setViews(Integer views)
	{
		this.views = views;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public String getAvatar()
	{
		return avatar;
	}

	public void setAvatar(String avatar)
	{
		this.avatar = avatar;
	}

	public String getFormatTime()
	{
		return formatTime;
	}

	public void setFormatTime(String formatTime)
	{
		this.formatTime = formatTime;
	}

	public String getFormatUptdateTime()
	{
		return formatUptdateTime;
	}

	public void setFormatUptdateTime(String formatUptdateTime)
	{
		this.formatUptdateTime = formatUptdateTime;
	}

	public String getStripDescription()
	{
		return stripDescription;
	}

	public void setStripDescription(String stripDescription)
	{
		this.stripDescription = stripDescription;
	}

	public String [] getImages()
	{
		return images;
	}

	public void setImages(String [] images)
	{
		this.images = images;
	}

	@Override
	public String toString()
	{
		return "QuestionEntity [qid=" + qid + ", author=" + author
				+ ", authorid=" + authorid + ", title=" + title
				+ ", description=" + description + ", time=" + time
				+ ", updateTime=" + updateTime + ", replyNum=" + replyNum
				+ ", attentions=" + attentions + ", favourNum=" + favourNum
				+ ", views=" + views + ", status=" + status + ", avatar="
				+ avatar + ", formatTime=" + formatTime
				+ ", formatUptdateTime=" + formatUptdateTime
				+ ", stripDescription=" + stripDescription + ", images="
				+ images2String() + "]";
	}
	
	public String images2String()
	{
		String str = "";
		if (images == null) return str;
		for (String img: images) {
			if (str.length() > 0) str = str + "," + img;
			else str = img;
		}
		str = "【" + str +"】";
		return str;
	}
}
