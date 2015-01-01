package com.boostme.util;

import android.util.Base64;

public class Base64Util
{
	public static String encode(String str)
	{
		return Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
	}

	public static String decode(String str)
	{
		return new String(Base64.decode(str.getBytes(), Base64.DEFAULT));
	}

	public static void main(String[] args)
	{
		String str = "Love厦大\n哈哈";
		String encode = Base64Util.encode(str);
		System.out.println(encode);
		System.out.println(Base64Util.decode(encode));
	}
}
