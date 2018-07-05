package com.autumn.pay.utils;

/** 
 签名字典
 
*/
public class SignDictionary extends java.util.TreeMap<String, String> implements Serializable
{
	/** 
	 实例化 SignDictionary 类新实例
	 
	*/
	public SignDictionary()
	{
		super(StringComparer.InvariantCultureIgnoreCase);

	}

	/** 
	 实例化 SignDictionary 类新实例
	 
	 @param comparer
	*/
	public SignDictionary(java.util.Comparator<String> comparer)
	{
		super(comparer);

	}

	/** 
	 实例化 SignDictionary 类新实例
	 
	 @param dictionary 初始字典
	*/
	public SignDictionary(java.util.Map<String, String> dictionary)
	{
		super(dictionary, StringComparer.InvariantCultureIgnoreCase);

	}

	/** 
	 实例化 SignDictionary 类新实例
	 
	 @param dictionary
	 @param comparer
	*/
	public SignDictionary(java.util.Map<String, String> dictionary, java.util.Comparator<String> comparer)
	{
		super(dictionary, comparer);

	}

	/** 
	 获取Url连接字符
	 
	 @return 
	*/
	public final String UrlLinkString()
	{
		return this.UrlLinkString(null, null);
	}

	/** 
	 获取Url连接字符
	 
	 @param keyFunn
	 @param valueFunc
	 @return 
	*/
	public final String UrlLinkString(Func<String, String> keyFunn, Func<String, String> valueFunc)
	{
		StringBuilder builder = new StringBuilder();
		for (java.util.Map.Entry<String, String> pair : this)
		{
			if (!String.IsNullOrWhiteSpace(pair.getKey()) && !String.IsNullOrWhiteSpace(pair.getValue()))
			{
				String key = keyFunn != null ? keyFunn(pair.getKey()) : pair.getKey();
				String value = valueFunc != null ? valueFunc(pair.getValue()) : pair.getValue();
				builder.append(key + "=" + value + "&");
			}
		}
		int length = builder.length();
		if (length > 0)
		{
			builder.deleteCharAt(length - 1);
		}
		return builder.toString();
	}

	/** 
	 克隆
	 
	 @param keyFunn
	 @param valueFunc
	 @return 
	*/
	public final SignDictionary clone(Func<String, String> keyFunn, Func<String, String> valueFunc)
	{
		SignDictionary dic = new SignDictionary(this.Comparer);
		for (java.util.Map.Entry<String, String> pair : this)
		{
			if (!String.IsNullOrWhiteSpace(pair.getKey()) && !String.IsNullOrWhiteSpace(pair.getValue()))
			{
				String key = keyFunn != null ? keyFunn(pair.getKey()) : pair.getKey();
				String value = valueFunc != null ? valueFunc(pair.getValue()) : pair.getValue();
				dic[key] = value;
			}
		}
		return dic;
	}
}