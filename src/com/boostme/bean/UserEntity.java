package com.boostme.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class UserEntity implements Serializable
{
	private static final long serialVersionUID = -978852841093905618L;

	@SerializedName("uid")
	private int uid;
	
	@SerializedName("username")
	private String username;
	
	@SerializedName("password")
	private String password;
	
	@SerializedName("avatar")
	private String avatar;
	
	@SerializedName("email")
	private String email;

	public int getUid()
	{
		return uid;
	}

	public void setUid(int uid)
	{
		this.uid = uid;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getAvatar()
	{
		return avatar;
	}

	public void setAvatar(String avatar)
	{
		this.avatar = avatar;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	@Override
	public String toString()
	{
		return "UserEntity [uid=" + uid + ", username=" + username
				+ ", password=" + password + ", avatar=" + avatar + ", email="
				+ email + "]";
	}
}
