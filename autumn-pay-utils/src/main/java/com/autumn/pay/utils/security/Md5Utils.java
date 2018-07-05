package com.autumn.pay.utils.security;

/** 
 Md5 帮助
 
*/
public final class Md5Utils
{
	/** 
	 签名
	 
	 @param content 内容
	 @param encoding 编码
	 @return 
	*/
	public static String Sign(String content, Encoding encoding)
	{
		ExceptionUtils.CheckNotNullOrNotWhiteSpace(content, nameof(content));
		ExceptionUtils.CheckNotNull(encoding, nameof(encoding));
		StringBuilder builder = new StringBuilder();
//C# TO JAVA CONVERTER NOTE: The following 'using' block is replaced by its Java equivalent:
//		using (var crypto = new MD5CryptoServiceProvider())
		MD5CryptoServiceProvider crypto = new MD5CryptoServiceProvider();
		try
		{
			byte[] buffer = crypto.ComputeHash(encoding.GetBytes(content));
			for (int i = 0; i < buffer.length; i++)
			{
				builder.append(String.format("%x", buffer[i]).PadLeft(2, '0'));
			}
			return builder.toString();
		}
		finally
		{
			crypto.dispose();
		}
	}

	/** 
	 签名验证,默认不区分大小写
	 
	 @param content 内容
	 @param sign 验名
	 @param encoding 编码
	 @return 
	*/
	public static boolean SginVerify(String content, String sign, Encoding encoding)
	{
		return (Sign(content, encoding).equals(sign));
	}

	/** 
	 签名验证,默认不区分大小写
	 
	 @param content 内容
	 @param sign 验名
	 @param encoding 编码
	 @return 
	*/
	public static boolean SginIgnoreCaseVerify(String content, String sign, Encoding encoding)
	{
		return (Sign(content, encoding).equals(sign, StringComparison.InvariantCultureIgnoreCase));
	}
}