package com.autumn.pay.utils.http;

import com.autumn.pay.utils.ExceptionUtils;
import com.autumn.util.StringUtils;
import com.sun.jndi.toolkit.url.Uri;
import org.yaml.snakeyaml.reader.StreamReader;

import java.util.stream.Stream;

/**
 Http 帮助
 
*/
public final class HttpUtils
{
	/** 
	 http 异步字符响应结果
	 
	 @param response 响应
	 @param encoding 编码
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: Extension methods are not available in Java:
//ORIGINAL LINE: public static async Task<string> ResponseStringResultAsync(this HttpWebResponse response, Encoding encoding = null)
//C# TO JAVA CONVERTER TODO TASK: C# optional parameters are not converted to Java:
	public static async Task<String> ResponseStringResultAsync(HttpWebResponse response, Encoding encoding)
	{
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
		return await Task.<String>Run(() =>
		{
			return ResponseStringResult(response, encoding);
		}
	   );
	}

	/** 
	 http 字符响应结果
	 
	 @param response 响应
	 @param encoding 编码
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: Extension methods are not available in Java:
//ORIGINAL LINE: public static string ResponseStringResult(this HttpWebResponse response, Encoding encoding = null)
//C# TO JAVA CONVERTER TODO TASK: C# optional parameters are not converted to Java:
	public static String ResponseStringResult(HttpWebResponse response, Encoding encoding)
	{
		try
		{
			ExceptionUtils.CheckNotNull(response, nameof(response));
			if (String.IsNullOrWhiteSpace(response.ContentEncoding))
			{
				if (encoding == null)
				{
					encoding = Encoding.UTF8;
				}
			}
			else
			{
				encoding = Encoding.GetEncoding(response.ContentEncoding);
			}
//C# TO JAVA CONVERTER NOTE: The following 'using' block is replaced by its Java equivalent:
//			using (Stream myResponseStream = response.GetResponseStream())
			Stream myResponseStream = response.GetResponseStream();
			try
			{
				StreamReader myStreamReader = new StreamReader(myResponseStream, encoding);
				try
				{
					return myStreamReader.ReadToEnd();
				}
				finally
				{
					if (myStreamReader != null)
					{
						myStreamReader.dispose();
					}
				}
			}
			finally
			{
				myResponseStream.dispose();
			}
		}
		finally
		{
			response.Close();
		}
	}

	/** 
	 Http Post 异步请求
	 
	 @param url url地址
	 @param requestDictionary 请求字典  
	 @param encoding 编码     
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: C# optional parameters are not converted to Java:
//ORIGINAL LINE: public static async Task<HttpWebResponse> PostAsync(string url, IDictionary<string, string> requestDictionary, Encoding encoding = null)
	public static async Task<HttpWebResponse> PostAsync(String url, java.util.Map<String, String> requestDictionary, Encoding encoding)
	{
		ExceptionUtils.CheckNotNullOrNotWhiteSpace(url, nameof(url));
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
		return await Task.<HttpWebResponse>Run(() =>
		{
			return Post(url, requestDictionary, encoding);
		}
	   );
	}

	/** 
	 Http Post 异步请求
	 
	 @param url url地址
	 @param requestDictionary 请求字典   
	 @param encoding 编码   
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: C# optional parameters are not converted to Java:
//ORIGINAL LINE: public static async Task<string> PostStringAsync(string url, IDictionary<string, string> requestDictionary, Encoding encoding = null)
	public static async Task<String> PostStringAsync(String url, java.util.Map<String, String> requestDictionary, Encoding encoding)
	{
		ExceptionUtils.CheckNotNullOrNotWhiteSpace(url, nameof(url));
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
		return await Task.<String>Run(() =>
		{
			return PostString(url, requestDictionary, encoding);
		}
	   );
	}

	/** 
	 Http Post 请求
	 
	 @param url url地址
	 @param requestDictionary 请求字典  
	 @param encoding 编码   
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: C# optional parameters are not converted to Java:
//ORIGINAL LINE: public static string PostString(string url, IDictionary<string, string> requestDictionary, Encoding encoding = null)
	public static String PostString(String url, java.util.Map<String, String> requestDictionary, Encoding encoding)
	{
		ExceptionUtils.CheckNotNullOrNotWhiteSpace(url, nameof(url));
		HttpClient client = new HttpClient();
		if (encoding != null)
		{
			client.setEncoding(encoding);
		}
		HttpWebResponse response = client.Post(url, requestDictionary);
		return response.ResponseStringResult(client.getEncoding());
	}

	/** 
	 Http Post 请求
	 
	 @param url url地址
	 @param requestDictionary 请求字典     
	 @param encoding 编码   
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: C# optional parameters are not converted to Java:
//ORIGINAL LINE: public static HttpWebResponse Post(string url, IDictionary<string, string> requestDictionary, Encoding encoding = null)
	public static HttpWebResponse Post(String url, java.util.Map<String, String> requestDictionary, Encoding encoding)
	{
		ExceptionUtils.CheckNotNullOrNotWhiteSpace(url, nameof(url));
		HttpClient client = new HttpClient();
		if (encoding != null)
		{
			client.setEncoding(encoding);
		}
		return client.Post(url, requestDictionary);
	}

	/** 
	 
	 
	 @param url
	 @param body
	 @param encoding
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: C# optional parameters are not converted to Java:
//ORIGINAL LINE: public static string PostString(string url, string body, Encoding encoding = null)
	public static String PostString(String url, String body, Encoding encoding)
	{
		ExceptionUtils.CheckNotNullOrNotWhiteSpace(url, nameof(url));
		HttpClient client = new HttpClient();
		if (encoding != null)
		{
			client.setEncoding(encoding);
		}
		return client.PostString(new Uri(url), body);
	}

	/** 
	 
	 
	 @param url
	 @param body
	 @param encoding
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: C# optional parameters are not converted to Java:
//ORIGINAL LINE: public static HttpWebResponse Post(string url, string body, Encoding encoding = null)
	public static HttpWebResponse Post(String url, String body, Encoding encoding)
	{
		ExceptionUtils.CheckNotNullOrNotWhiteSpace(url, nameof(url));
		HttpClient client = new HttpClient();
		if (encoding != null)
		{
			client.setEncoding(encoding);
		}
		return client.Post(new Uri(url), body);
	}

	/** 
	 
	 
	 @param url
	 @param requestBytes
	 @param encoding
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: C# optional parameters are not converted to Java:
//ORIGINAL LINE: public static string PostString(string url, byte[] requestBytes, Encoding encoding = null)
	public static String PostString(String url, byte[] requestBytes, Encoding encoding)
	{
		ExceptionUtils.CheckNotNullOrNotWhiteSpace(url, nameof(url));
		HttpClient client = new HttpClient();
		if (encoding != null)
		{
			client.setEncoding(encoding);
		}
		return client.PostString(new Uri(url), requestBytes);
	}

	/** 
	 Http Post 请求
	 
	 @param url url地址
	 @param requestDictionary 请求字典     
	 @param encoding 编码   
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: C# optional parameters are not converted to Java:
//ORIGINAL LINE: public static HttpWebResponse Post(string url, byte[] requestBytes, Encoding encoding = null)
	public static HttpWebResponse Post(String url, byte[] requestBytes, Encoding encoding)
	{
		ExceptionUtils.CheckNotNullOrNotWhiteSpace(url, nameof(url));
		HttpClient client = new HttpClient();
		if (encoding != null)
		{
			client.setEncoding(encoding);
		}
		return client.Post(new Uri(url), requestBytes);
	}

	/** 
	 Http Get 异步请求
	 
	 @param url url地址
	 @param requestDictionary 请求字典      
	 @param encoding 编码   
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: C# optional parameters are not converted to Java:
//ORIGINAL LINE: public static async Task<string> GetStringAsync(string url, IDictionary<string, string> requestDictionary, Encoding encoding = null)
	public static async Task<String> GetStringAsync(String url, java.util.Map<String, String> requestDictionary, Encoding encoding)
	{
		ExceptionUtils.CheckNotNullOrNotWhiteSpace(url, nameof(url));
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
		return await Task.<String>Run(() =>
		{
			return GetString(url, requestDictionary, encoding);
		}
	   );
	}

	/** 
	 Http Get 异步请求
	 
	 @param url url地址
	 @param requestDictionary 请求字典      
	 @param encoding 编码   
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: C# optional parameters are not converted to Java:
//ORIGINAL LINE: public static async Task<HttpWebResponse> GetAsync(string url, IDictionary<string, string> requestDictionary, Encoding encoding = null)
	public static async Task<HttpWebResponse> GetAsync(String url, java.util.Map<String, String> requestDictionary, Encoding encoding)
	{
		ExceptionUtils.CheckNotNullOrNotWhiteSpace(url, nameof(url));
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
		return await Task.<HttpWebResponse>Run(() =>
		{
			return Get(url, requestDictionary, encoding);
		}
	   );
	}

	/** 
	 Http Get 请求
	 
	 @param url url地址
	 @param requestDictionary 请求字典     
	 @param encoding 编码   
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: C# optional parameters are not converted to Java:
//ORIGINAL LINE: public static string getString(string url, IDictionary<string, string> requestDictionary, Encoding encoding = null)
	public static String GetString(String url, java.util.Map<String, String> requestDictionary, Encoding encoding)
	{
		HttpClient client = new HttpClient();
		if (encoding != null)
		{
			client.setEncoding(encoding);
		}
		HttpWebResponse response = client.Get(url, requestDictionary);
		return response.ResponseStringResult(client.getEncoding());
	}

	/** 
	 Http Get 请求
	 
	 @param url url地址
	 @param requestDictionary 请求字典   
	 @param encoding 编码   
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: C# optional parameters are not converted to Java:
//ORIGINAL LINE: public static HttpWebResponse Get(string url, IDictionary<string, string> requestDictionary, Encoding encoding = null)
	public static HttpWebResponse Get(String url, java.util.Map<String, String> requestDictionary, Encoding encoding)
	{
		ExceptionUtils.CheckNotNullOrNotWhiteSpace(url, nameof(url));
		HttpClient client = new HttpClient();
		if (encoding != null)
		{
			client.setEncoding(encoding);
		}
		return client.Get(url, requestDictionary);
		StringUtils
	}
}