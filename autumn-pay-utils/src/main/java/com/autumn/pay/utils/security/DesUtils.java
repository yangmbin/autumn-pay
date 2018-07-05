package com.autumn.pay.utils.security;

/** 
 DES 帮助
 
*/
public final class DesUtils
{
	/** 
	 加密
	 
	 @param sourceString 源字符
	 @param key 密
	 @param iv 向量
	 @return 
	*/
	public static String Encrypt(String sourceString, String key, String iv)
	{
		byte[] btKey = Encoding.UTF8.GetBytes(key);
		byte[] btIV = Encoding.UTF8.GetBytes(iv);
//C# TO JAVA CONVERTER NOTE: The following 'using' block is replaced by its Java equivalent:
//		using (DESCryptoServiceProvider des = new DESCryptoServiceProvider())
		DESCryptoServiceProvider des = new DESCryptoServiceProvider();
		try
		{
			MemoryStream ms = new MemoryStream();
			CryptoStream cs = null;
			try
			{
				byte[] inData = Encoding.UTF8.GetBytes(sourceString);
				cs = new CryptoStream(ms, des.CreateEncryptor(btKey, btIV), CryptoStreamMode.Write);
				cs.Write(inData, 0, inData.length);
				cs.FlushFinalBlock();
				return Convert.ToBase64String(ms.toArray());
			}
			finally
			{
				ms.dispose();
				if (cs != null)
				{
					cs.dispose();
				}
			}
		}
		finally
		{
			des.dispose();
		}

	}

	/** 
	 解密
	 
	 @param encryptedString 加密字符
	 @param key 密
	 @param iv 向量
	 @return 
	*/
	public static String Decrypt(String encryptedString, String key, String iv)
	{
		byte[] btKey = Encoding.UTF8.GetBytes(key);
		byte[] btIV = Encoding.UTF8.GetBytes(iv);
//C# TO JAVA CONVERTER NOTE: The following 'using' block is replaced by its Java equivalent:
//		using (DESCryptoServiceProvider des = new DESCryptoServiceProvider())
		DESCryptoServiceProvider des = new DESCryptoServiceProvider();
		try
		{
			MemoryStream ms = new MemoryStream();
			CryptoStream cs = null;
			try
			{
				byte[] inData = Convert.FromBase64String(encryptedString);
				cs = new CryptoStream(ms, des.CreateDecryptor(btKey, btIV), CryptoStreamMode.Write);
				cs.Write(inData, 0, inData.length);
				cs.FlushFinalBlock();
				return Encoding.UTF8.GetString(ms.toArray());
			}
			finally
			{
				ms.dispose();
				if (cs != null)
				{
					cs.dispose();
				}
			}
		}
		finally
		{
			des.dispose();
		}
	}
}