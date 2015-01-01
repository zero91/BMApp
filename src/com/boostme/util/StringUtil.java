package com.boostme.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class StringUtil
{
	public static boolean isEmpty(String str)
	{
		return ((str == null) || (str.length() == 0));
	}

	public static boolean isBlank(String str)
	{
		int length;
		if ((str == null) || ((length = str.length()) == 0)) {
			return true;
		}

		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static byte[] trimIllegalUtf8(byte[] bytes)
	{
		int i = 0;
		while (i < bytes.length && (bytes[i] < 0 || bytes[i] == 32)) {
			i++;
		}
		byte[] finalbyte = Arrays.copyOfRange(bytes, i, bytes.length);
		return finalbyte;
	}

	/**
	 * 把bytes字节数组转化为utf-8字符串，在转化过程中去除非法的bom头字节
	 * @param bytes
	 * @return
	 */
	public static String toUtf8(byte[] bytes)
	{
		if (bytes == null || bytes.length < 1) {
			return "";
		}

		try {
			String result = new String(StringUtil.trimIllegalUtf8(bytes), "utf-8");
			return result;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean isIntegerOrEmpty(String str)
	{
		if (str == null || str.length() == 0) {
			return true;
		}
		try {
			Integer.valueOf(str);
		} catch (Exception exp) {
			return false;
		}
		return true;
	}
}