package com.autumn.pay.utils;

/** 
 日期时间帮助
 
*/
public final class DateTimeUtils
{
	/** 
	 开始时间
	 
	*/
	public static final java.util.Date Begin_DateTime = new java.util.Date(1970, 1, 1, 0, 0, 0, 0);

	/** 
	 获取当前Utc时间时间戳的毫秒数
	 
	*/
	public static long getCurrentUtcTimeStampMilliseconds()
	{
		TimeSpan ts = java.util.Date.UtcNow - Begin_DateTime;
		return (long)ts.TotalMilliseconds;
	}

	/** 
	 获取当前Utc时间时间戳的秒数
	 
	*/
	public static long getCurrentUtcTimeStampSeconds()
	{
		TimeSpan ts = java.util.Date.UtcNow - Begin_DateTime;
		return (long)ts.TotalSeconds;
	}

	/** 
	 获取当前时间时间戳的毫秒数
	 
	*/
	public static long getCurrentTimeStampMilliseconds()
	{
		TimeSpan ts = new java.util.Date() - Begin_DateTime;
		return (long)ts.TotalMilliseconds;
	}

	/** 
	 获取当前时间时间戳的秒数
	 
	*/
	public static long getCurrentTimeStampSeconds()
	{
		TimeSpan ts = new java.util.Date() - Begin_DateTime;
		return (long)ts.TotalSeconds;
	}

	public static String GetTimestamp()
	{
		TimeSpan span = (TimeSpan)(java.util.Date.UtcNow - new java.util.Date(0x7b2, 1, 1, 0, 0, 0, 0));
		return Long.parseLong(span.TotalSeconds).toString();
	}

	public static java.util.Date GetDateTimeFormat(String time, String format)
	{
		return java.util.Date.ParseExact(time, format, System.Globalization.CultureInfo.CurrentCulture);
	}
}