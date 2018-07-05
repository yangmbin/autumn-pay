package com.autumn.pay.utils;

import GN.Pay.Utils.Security.*;

public final class PreParam
{
	public static String BuildRequestParam(java.util.HashMap<String, String> dicArray, String _input_charset)
	{
		StringBuilder builder = new StringBuilder();
		for (java.util.Map.Entry<String, String> pair : dicArray.entrySet())
		{
			builder.append(pair.getKey() + "=" + HttpUtility.UrlEncode(pair.getValue(), Encoding.GetEncoding(_input_charset)) + "&");
		}
		int length = builder.length();
		builder.deleteCharAt(length - 1);
		return builder.toString();
	}

	public static java.util.HashMap<String, String> FilterPara(java.util.HashMap<String, String> dicPara, String[] ignorKeys)
	{
		java.util.HashMap<String, String> dictionary = new java.util.HashMap<String, String>();
		java.util.TreeMap<String, String> sortedDictionary = new java.util.TreeMap<String, String>(dicPara);
		for (java.util.Map.Entry<String, String> pair : sortedDictionary)
		{
			if (!(ignorKeys.Contains(pair.getKey().toLowerCase()) || DotNetToJavaStringHelper.isNullOrEmpty(pair.getValue())))
			{
				dictionary.put(pair.getKey(), pair.getValue());
			}
		}
		return dictionary;
	}

	/** 
	 过滤为空的参数 进行排序
	 
	 @param dicPara
	 @return 
	*/
	public static java.util.HashMap<String, String> FilterPara(java.util.HashMap<String, String> dicPara)
	{
		java.util.HashMap<String, String> dictionary = new java.util.HashMap<String, String>();
		java.util.TreeMap<String, String> sortedDictionary = new java.util.TreeMap<String, String>(dicPara);
		for (java.util.Map.Entry<String, String> pair : sortedDictionary)
		{
			if (!DotNetToJavaStringHelper.isNullOrEmpty(pair.getValue()))
			{
				dictionary.put(pair.getKey(), pair.getValue());
			}
		}
		return dictionary;
	}
	/** 
	 key参数为空时进行MD5加密
	 
	 @param prestr
	 @param _input_charset
	 @param _sign_type
	 @param _key
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: C# optional parameters are not converted to Java:
//ORIGINAL LINE: public static string BuildRequestMysign(string prestr, string _input_charset, string _sign_type, string _key = "")
	public static String BuildRequestMysign(String prestr, String _input_charset, String _sign_type, String _key)
	{
//C# TO JAVA CONVERTER NOTE: The following 'switch' operated on a string member and was converted to Java 'if-else' logic:
//		switch (_sign_type.ToUpper())
//ORIGINAL LINE: case "MD5":
		if (_sign_type.toUpperCase().equals("MD5"))
		{
				return Md5Utils.Sign(prestr, Encoding.GetEncoding(_input_charset));
		}
//ORIGINAL LINE: case "RSA":
		else if (_sign_type.toUpperCase().equals("RSA"))
		{
				return RsaFromPkcs8Utils.Sign(prestr, _key, Encoding.GetEncoding(_input_charset));
		}
		return "";
	}

	/** 
	 拼接字符串
	 
	 @param dicArray
	 @return 
	*/
	public static String CreateLinkString(java.util.HashMap<String, String> dicArray)
	{
		StringBuilder builder = new StringBuilder();
		for (java.util.Map.Entry<String, String> pair : dicArray.entrySet())
		{
			builder.append(pair.getKey() + "=" + pair.getValue() + "&");
		}
		int length = builder.length();
		builder.deleteCharAt(length - 1);
		return builder.toString();
	}

	public static NameValueCollection GetQueryString(HttpRequestBase request)
	{
		if ((new String("POST")).equals(request.HttpMethod, StringComparison.InvariantCultureIgnoreCase))
		{
			return request.Form;
		}
		return request.QueryString;
	}

	/** 
	 获取查询字典
	 
	 @param request 请求
	 @return 
	*/
	public static java.util.Map<String, String> GetQueryDictionary(HttpRequestBase request)
	{
		NameValueCollection requestPara = GetQueryString(request);
		java.util.HashMap<String, String> inputDic = new java.util.HashMap<String, String>(StringComparer.InvariantCultureIgnoreCase);
		for (String str : requestPara.AllKeys)
		{
			if (str != null)
			{
				inputDic.put(str, requestPara[str]);
			}
		}
		return inputDic;
	}
}