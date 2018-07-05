package com.autumn.pay.utils;


import org.yaml.snakeyaml.reader.StreamReader;

import java.util.stream.Stream;

/**
 公共扩展
 
*/
public final class CommonExtension
{
	IsoDateTimeConverter tempVar = new IsoDateTimeConverter();
	tempVar.DateTimeFormat = "yyyy-MM-dd HH:mm:ss";
	private static IsoDateTimeConverter timeFormat = tempVar;

	JsonSerializerSettings tempVar2 = new JsonSerializerSettings();
	tempVar2.ContractResolver = new CamelCasePropertyNamesContractResolver();
	tempVar2.DateFormatHandling = DateFormatHandling.IsoDateFormat;
	tempVar2.DateFormatString = "yyyy-MM-dd HH:mm:ss";
	private static JsonSerializerSettings CamelCase_Setting = tempVar2;

	JsonSerializerSettings tempVar3 = new JsonSerializerSettings();
	tempVar3.ContractResolver = new DefaultContractResolver();
	tempVar3.DateFormatHandling = DateFormatHandling.IsoDateFormat;
	tempVar3.DateFormatString = "yyyy-MM-dd HH:mm:ss";
	private static JsonSerializerSettings Default_Setting = tempVar3;


	/** 
	 转换为Json对象
	 
	 @param value 值
	 @param isCamelCase 是否转换为骆锋命名
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: Extension methods are not available in Java:
//ORIGINAL LINE: public static string ToJson(this object value, bool isCamelCase)
	public static String ToJson(Object value, boolean isCamelCase)
	{
		if (value == null)
		{
			return null;
		}
		if (isCamelCase)
		{
			return JsonConvert.SerializeObject(value, CamelCase_Setting);
		}
		return JsonConvert.SerializeObject(value, Default_Setting);
	}

	/** 
	 Json转换为对象
	 
	 @param value
	 @param type 类型
	 @param isCamelCase
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: Extension methods are not available in Java:
//ORIGINAL LINE: public static object JsonToObject(this string value, Type type, bool isCamelCase)
	public static Object JsonToObject(String value, Class type, boolean isCamelCase)
	{
		if (String.IsNullOrWhiteSpace(value))
		{
			return null;
		}
		if (isCamelCase)
		{
			return JsonConvert.DeserializeObject(value, type, CamelCase_Setting);
		}
		return JsonConvert.DeserializeObject(value, type, Default_Setting);
	}

	/** 
	 Json转换为对象
	 
	 @param value
	 @param isCamelCase
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: Extension methods are not available in Java:
//ORIGINAL LINE: public static T JsonToObject<T>(this string value, bool isCamelCase)
	public static <T> T JsonToObject(String value, boolean isCamelCase)
	{
		if (String.IsNullOrWhiteSpace(value))
		{
			return null;
		}
		if (isCamelCase)
		{
			return JsonConvert.<T>DeserializeObject(value, CamelCase_Setting);
		}
		return JsonConvert.<T>DeserializeObject(value, Default_Setting);
	}

//C# TO JAVA CONVERTER TODO TASK: Extension methods are not available in Java:
//ORIGINAL LINE: public static string ReadStreamString(this Stream stream)
	public static String ReadStreamString(Stream stream)
	{
		if (stream == null)
		{
			return "";
		}
		//使用读取流读取响应结果
//C# TO JAVA CONVERTER NOTE: The following 'using' block is replaced by its Java equivalent:
//		using (StreamReader reader = new StreamReader(stream))
		StreamReader reader = new StreamReader(stream);
		try
		{
			return reader.ReadToEnd();
		}
		finally
		{
			reader.dispose();
		}
	}
}