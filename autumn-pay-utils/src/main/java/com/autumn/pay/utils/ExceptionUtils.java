package com.autumn.pay.utils;

import com.autumn.util.ArgumentNullException;

/**
 验证帮助
 
*/
public abstract class ExceptionUtils
{
	/** 
	 抛出验证性异常
	 
	 @param message 消息
	 @return 
	*/
	public static ValidationException ThrowValidationException(String message)
	{
		throw new ValidationException(message);
	}

	/** 
	 数据格式不正确异常
	 
	 @param message
	 @return 
	*/
	public static FormatException ThrowFormatException(String message)
	{
		throw new FormatException(message);
	}

	/** 
	 检查非空
	 
	 <typeparam name="T">类型</typeparam>
	 @param value 值
	 @param argName 参数名称
	*/
//C# TO JAVA CONVERTER TODO TASK: The C# 'class' constraint has no equivalent in Java:
	public static <T extends class> T CheckNotNull(T value, String argName)
	{
		if (value == null)
		{
			throw new ArgumentNullException(argName);
		}
		return value;
	}

	/** 
	 检查非空和非空白对象
	 
	 @param value 值
	 @param argName 参数名称
	 @return 
	*/
	public static String CheckNotNullOrNotWhiteSpace(String value, String argName)
	{
		if (String.IsNullOrWhiteSpace(value))
		{
			throw new ArgumentNullException(argName);
		}
		return value;
	}


}