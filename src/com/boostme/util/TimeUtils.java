package com.boostme.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtils
{
	public static String YYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
	public static String MMDD_HHMM = "MM月dd日 HH:mm";
	public static String YYMMDD_HHMM = "yyyy年MM月dd日 HH:mm";
	
	public static String twoDateDistance(Date startDate, Date endDate)
	{
		if (startDate == null || endDate == null) {
			return null;
		}

		long fromTime = startDate.getTime();
		long toTime = endDate.getTime();
		return twoDateDistance(fromTime, toTime, "yyyy-MM-dd HH:mm:ss");
	}

	public static String twoDateDistance(long fromTime, long toTime, String timeFormat)
	{
		String suffix = fromTime > toTime ? "后" : "前";
		long timeLong = Math.abs(toTime - fromTime);
		if (timeLong < 60 * 1000)
			return timeLong / 1000 + "秒" + suffix;
		else if (timeLong < 60 * 60 * 1000) {
			timeLong = timeLong / 1000 / 60;
			return timeLong + "分钟" + suffix;
		} else if (timeLong < 60 * 60 * 24 * 1000) {
			timeLong = timeLong / 60 / 60 / 1000;
			return timeLong + "小时" + suffix;
		} else if (timeLong < 60 * 60 * 24 * 1000 * 7) {
			timeLong = timeLong / 1000 / 60 / 60 / 24;
			return timeLong + "天" + suffix;
		} else if (timeLong < 60 * 60 * 24 * 1000 * 7 * 4) {
			timeLong = timeLong / 1000 / 60 / 60 / 24 / 7;
			return timeLong + "周" + suffix;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat(timeFormat, Locale.getDefault());
			sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
			return sdf.format(new Date(fromTime));
		}
	}

	public static String getDateDistanceToNow(long fromTime)
	{
		return getDateDistanceToNow(fromTime, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String getDateDistanceToNow(long fromTime, String timeFormat)
	{
		return twoDateDistance(fromTime, System.currentTimeMillis(), timeFormat);
	}

	// 只计算以前的
	public static String twoDateDistanceBefore(long fromTime, long toTime, String timeFormat)
	{
		if (fromTime > toTime) {
			return "刚刚";
		}
		String suffix = "前";
		long timeLong = Math.abs(toTime - fromTime);
		if (timeLong < 60 * 1000) {
			return "刚刚";
			// return timeLong / 1000 + "秒" + suffix;
		} else if (timeLong < 60 * 60 * 1000) {
			timeLong = timeLong / 1000 / 60;
			return timeLong + "分钟" + suffix;
		} else if (timeLong < 60 * 60 * 24 * 1000) {
			timeLong = timeLong / 60 / 60 / 1000;
			return timeLong + "小时" + suffix;
		} else if (timeLong < 60 * 60 * 24 * 1000 * 7) {
			timeLong = timeLong / 1000 / 60 / 60 / 24;
			return timeLong + "天" + suffix;
		} else if (timeLong < 60 * 60 * 24 * 1000 * 7 * 4) {
			timeLong = timeLong / 1000 / 60 / 60 / 24 / 7;
			return timeLong + "周" + suffix;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat(timeFormat, Locale.getDefault());
			sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
			return sdf.format(new Date(fromTime));
		}
	}

//	public static String getDateDistanceToNowBefore(long fromTime)
//	{
//		return getDateDistanceToNowBefore(fromTime, "yyyy-MM-dd HH:mm:ss");
//	}
	
	public static String getDateDistanceToNowBefore(long fromTime, String timeFormat)
	{
		return twoDateDistanceBefore(fromTime, System.currentTimeMillis(), timeFormat);
	}

	public static String formatFullTime(long time)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm",
				Locale.getDefault());
		return sdf.format(new Date(time));
	}

	public static String formatShortTime(long time)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm",
				Locale.getDefault());
		return sdf.format(new Date(time));
	}

	public static Calendar getCalendar(int year, int month, int day, int hour, int minute, int second, int millisecond)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, millisecond);
		return calendar;
	}

	/**
	 * 如果当前时间比较大，则返回-1；否则返回1
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @return
	 */
	public static int compareToCurrentTime(int year, int month, int day, int hour, int minute)
	{
		Calendar objCalendar = getCalendar(year, month, day, hour, minute, 0, 0);
		return compareToCurrentTime(objCalendar);
	}

	/**
	 * 如果当前时间比较大，则返回-1；否则返回1
	 * 
	 * @param objCalendar
	 * @return
	 */
	public static int compareToCurrentTime(Calendar objCalendar)
	{
		Calendar curCalendar = Calendar.getInstance();
		curCalendar.set(Calendar.SECOND, 0);
		curCalendar.set(Calendar.MILLISECOND, 0);

		return compareToTime(curCalendar, objCalendar);
	}

	/**
	 * 如果calendar1时间比calendar2大，则返回-1；否则返回1
	 * 
	 * @param calendar1
	 * @param calendar2
	 * @return
	 */
	public static int compareToTime(Calendar calendar1, Calendar calendar2)
	{
		if (calendar1.getTimeInMillis() > calendar2.getTimeInMillis()) {
			return -1;
		} else {
			return 1;
		}
	}
	
	public static void main(String [] args)
	{
		System.out.println(TimeUtils.getDateDistanceToNowBefore(1423385484 * 1000L, MMDD_HHMM));
	}
}
