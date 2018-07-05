package com.autumn.pay.utils.security;

/** 
 Rsa 帮助
 
*/
public final class RsaUtils
{
	/** 
         RSA加密
         
         @param xmlPublicKey 公钥
         @param sourceString 源数据
	*/
        public static String Encrypt(String xmlPublicKey, String sourceString)
	{
//C# TO JAVA CONVERTER NOTE: The following 'using' block is replaced by its Java equivalent:
//		using (RSACryptoServiceProvider rsa = new RSACryptoServiceProvider())
		RSACryptoServiceProvider rsa = new RSACryptoServiceProvider();
		try
		{
			rsa.FromXmlString(xmlPublicKey);
			byte[] bytes = Encoding.UTF8.GetBytes(sourceString);
			return Encrypt(rsa, bytes);
		}
		finally
		{
			rsa.dispose();
		}
	}

	/** 
	 RSK 加密长度
	 
	*/
	public static final int RSA_ENCRYPT_SIZE = 86;

	/** 
	 加密
	 
	 @param rsa
	 @param data
	 @return 
	*/
	public static String Encrypt(RSACryptoServiceProvider rsa, byte[] data)
	{
		int size = rsa.KeySize / 8; //8位一个字节
//C# TO JAVA CONVERTER NOTE: The following 'using' block is replaced by its Java equivalent:
//		using (SHA1 sha = new SHA1CryptoServiceProvider())
		SHA1 sha = new SHA1CryptoServiceProvider();
		try
		{
			StringBuilder result = new StringBuilder();
			int length = data.length;
			int page = length / RSA_ENCRYPT_SIZE;
			if (data.length % RSA_ENCRYPT_SIZE != 0)
			{
				page++;
			}
			byte[] bytes = new byte[page * size];
			int count = length;
			int index = 0;
			for (int j = 0; j < page; j++)
			{
				int byteSize = count >= RSA_ENCRYPT_SIZE ? RSA_ENCRYPT_SIZE : count;
				byte[] buf = new byte[byteSize];
				for (int i = 0; i < byteSize; i++)
				{
					buf[i] = data[index];
					index++;
					count--;
				}
				byte[] source = rsa.Encrypt(buf, true);
				System.arraycopy(source, 0, bytes, j * source.length, source.length);
			}
			return Convert.ToBase64String(bytes);
		}
		finally
		{
			sha.dispose();
		}

	}

	/** 
         解密
         
         @param xmlPrivateKey 私钥
         @param encryptedString 待解密的数据
         @return 解密后的结果
	*/
        public static String Decrypt(String xmlPrivateKey, String encryptedString)
	{
//C# TO JAVA CONVERTER NOTE: The following 'using' block is replaced by its Java equivalent:
//		using (RSACryptoServiceProvider rsa = new RSACryptoServiceProvider())
		RSACryptoServiceProvider rsa = new RSACryptoServiceProvider();
		try
		{
			rsa.FromXmlString(xmlPrivateKey);
			byte[] rgb = Convert.FromBase64String(encryptedString);
			return Decrypt(rsa, rgb, Encoding.UTF8);
		}
		finally
		{
			rsa.dispose();
		}
	}

	/** 
	 解密
	 
	 @param rsa
	 @param data
	 @param encoding
	 @return 
	*/
	public static String Decrypt(RSACryptoServiceProvider rsa, byte[] data, Encoding encoding)
	{
		int size = rsa.KeySize / 8; //8位一个字节
//C# TO JAVA CONVERTER NOTE: The following 'using' block is replaced by its Java equivalent:
//		using (SHA1 sha = new SHA1CryptoServiceProvider())
		SHA1 sha = new SHA1CryptoServiceProvider();
		try
		{
			StringBuilder result = new StringBuilder();
			for (int j = 0; j < data.length / size; j++)
			{
				byte[] buf = new byte[size];
				for (int i = 0; i < size; i++)
				{
					buf[i] = data[i + size * j];
				}
				byte[] source = rsa.Decrypt(buf, true);
				result.append(encoding.GetString(source));
			}
			return result.toString();
		}
		finally
		{
			sha.dispose();
		}
	}

	/** 
	 创建证书
	 
	 @param rawData 二进制
	 @param password 密码
	 @return 
	*/
	public static X509Certificate2 CreateCertificate(byte[] rawData, String password)
	{
		ExceptionUtils.CheckNotNull(rawData, nameof(rawData));
		if (password == null)
		{
			password = "";
		}
		return new X509Certificate2(rawData, password, X509KeyStorageFlags.MachineKeySet | X509KeyStorageFlags.Exportable | X509KeyStorageFlags.PersistKeySet);
	}

	/** 
	 获取私钥提供
	 
	 @param rawData 二进制数据
	 @param password 密码
	 @return 
	*/
	public static RSACryptoServiceProvider PrivateKeyProvider(byte[] rawData, String password)
	{
		X509Certificate2 certificate = CreateCertificate(rawData, password);
		if (certificate.PrivateKey == null)
		{
			throw new IllegalArgumentException("私钥数据格式不正确。");
		}
		return (RSACryptoServiceProvider)certificate.PrivateKey;
	}

	/** 
	 获取证书的序列号
	 
	 @param rawData 二进制数据
	 @param password 密码
	 @return 
	*/
	public static String CertSerialNumber(byte[] rawData, String password)
	{
		X509Certificate2 certificate = CreateCertificate(rawData, password);
		return BigInteger.Parse(certificate.SerialNumber, NumberStyles.HexNumber).toString();
	}


	/** 
	 获取私钥提供
	 
	 @param rawData 二进制数据
	 @return 
	*/
	public static RSACryptoServiceProvider PublicKeyProvider(byte[] rawData)
	{
		ExceptionUtils.CheckNotNull(rawData, nameof(rawData));
		X509Certificate2 certificate = CreateCertificate(rawData, "");
		if (certificate.PublicKey == null || certificate.PublicKey.getKey() == null)
		{
			throw new IllegalArgumentException("公钥数据格式不正确。");
		}
		return (RSACryptoServiceProvider)certificate.PublicKey.getKey();
	}



}