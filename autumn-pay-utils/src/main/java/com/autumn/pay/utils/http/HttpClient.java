package com.autumn.pay.utils.http;


import com.autumn.pay.utils.ExceptionUtils;
import com.sun.jndi.toolkit.url.Uri;
import org.apache.http.HttpVersion;
import org.springframework.web.context.request.WebRequest;

import java.util.stream.Stream;

/**
 * Http 客户端
 *
 * @author yangmbin
 * @date 2018/7/4 15:53
 * @since 1.0.0
 */
public class HttpClient {
    private static String default_ContentType = "application/x-www-form-urlencoded";

    /**
     静态实例化

     */
    static {
        ServicePointManager.ServerCertificateValidationCallback = new RemoteCertificateValidationCallback(RemoteCertificateValidation);
    }

    private static boolean RemoteCertificateValidation(Object sender, X509Certificate certificate, X509Chain chain, SslPolicyErrors errors) {
        return true; //总是接受     

        }

    /**
     * 实例化 HttpClient 类新实例
     */
    public HttpClient() {
        this._Encoding = getEncoding().UTF8;
        this.setProtocolVersion(HttpVersion.Version11);
        this.setContentType(default_ContentType);
    }

    /**
     * 获取或设置Cookie容器
     */
    private CookieContainer privateCookieContainer;

    public final CookieContainer getCookieContainer() {
        return privateCookieContainer;
    }

    public final void setCookieContainer(CookieContainer value) {
        privateCookieContainer = value;
    }

    //C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
    //[DebuggerBrowsable(DebuggerBrowsableState.Never)]
    private Encoding _Encoding;

    /**
     * 获取或设置编码
     */
    public final Encoding getEncoding() {
        return this._Encoding;
    }

    public final void setEncoding(Encoding value) {
        this._Encoding = ExceptionUtils.CheckNotNull(value, nameof(value));
    }

    //C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
    //[DebuggerBrowsable(DebuggerBrowsableState.Never)]
    private java.util.List<X509Certificate> _Certificates;

    /**
     * 获取或设置证书
     */
    public final java.util.List<X509Certificate> getCertificates() {
        if (this._Certificates == null) {
            this._Certificates = new java.util.ArrayList<X509Certificate>();
        }
        return this._Certificates;
    }

    /**
     * 获取或设置请求的身份验证信息。
     */
    private ICredentials privateCredentials;

    public final ICredentials getCredentials() {
        return privateCredentials;
    }

    public final void setCredentials(ICredentials value) {
        privateCredentials = value;
    }

    /**
     * Http 版本
     */
    private Version privateProtocolVersion;

    public final Version getProtocolVersion() {
        return privateProtocolVersion;
    }

    public final void setProtocolVersion(Version value) {
        privateProtocolVersion = value;
    }

    /**
     * 获取或设置 Accept HTTP 标头的值。
     */
    private String privateAccept;

    public final String getAccept() {
        return privateAccept;
    }

    public final void setAccept(String value) {
        privateAccept = value;
    }

    /**
     * 获取或设置一个值，该值指示请求是否应跟随重定向响应。
     */
    private boolean privateAllowAutoRedirect;

    public final boolean getAllowAutoRedirect() {
        return privateAllowAutoRedirect;
    }

    public final void setAllowAutoRedirect(boolean value) {
        privateAllowAutoRedirect = value;
    }

    /**
     * 获取或设置 Content-type HTTP 标头的值。
     */
    private String privateContentType;

    public final String getContentType() {
        return privateContentType;
    }

    public final void setContentType(String value) {
        privateContentType = value;
    }

    /**
     * 获取或设置写入或读取流时的超时（以毫秒为单位）;默认值为 300,000 毫秒（5 分钟）。
     */
    private int privateReadWriteTimeout;

    public final int getReadWriteTimeout() {
        return privateReadWriteTimeout;
    }

    public final void setReadWriteTimeout(int value) {
        privateReadWriteTimeout = value;
    }
    =300000;

    /**
     * 获取或设置 System.Net.HttpWebRequest.GetResponse 和 System.Net.HttpWebRequest.GetRequestStream
     * 方法的超时值（以毫秒为单位）。默认值是 100,000 毫秒（100 秒）
     */
    private int privateTimeout;

    public final int getTimeout() {
        return privateTimeout;
    }

    public final void setTimeout(int value) {
        privateTimeout = value;
    }
	=100000;

    /**
     * 获取或设置 User-agent HTTP 标头的值。
     */
    private String privateUserAgent;

    public final String getUserAgent() {
        return privateUserAgent;
    }

    public final void setUserAgent(String value) {
        privateUserAgent = value;
    }

    /**
     * 请求字符值
     *
     * @param requestDictionary
     * @param isUrlEncode       是否编码
     * @return
     */
    private String RequestString(java.util.Map<String, String> requestDictionary, boolean isUrlEncode) {
        StringBuilder builder = new StringBuilder();
        int count = 0;
        if (requestDictionary != null && requestDictionary.size() > 0) {
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
            for (var keyValue : requestDictionary) {
                if (!String.IsNullOrWhiteSpace(keyValue.getValue()) && !String.IsNullOrWhiteSpace(keyValue.getKey())) {
                    if (count > 0) {
                        builder.append("&");
                    }
                    builder.append(keyValue.getKey());
                    builder.append("=");
                    // builder.Append(isUrlEncode ? UrlEncoderUtils.UrlEncode(keyValue.Value, this.Encoding) : keyValue.Value);
                    builder.append(isUrlEncode ? HttpUtility.UrlEncode(keyValue.getValue(), this.getEncoding()) : keyValue.getValue());
                    count++;
                }
            }
        }
        return builder.toString();
    }

    /**
     * 设置请求
     *
     * @param request
     */
    private void SetRequest(HttpWebRequest request) {
        String contentType = this.getContentType();
        if (String.IsNullOrWhiteSpace(contentType)) {
            contentType = default_ContentType;
        }

        //TODO:GZIP支持修改
        request.Headers[HttpRequestHeader.AcceptEncoding] = "gzip, deflate";
        request.AutomaticDecompression = DecompressionMethods.Deflate | DecompressionMethods.GZip;
        request.CookieContainer = new System.Net.CookieContainer();

        request.ContentType = contentType + ";charset=" + this.getEncoding().WebName;
        request.Accept = this.getAccept();
        request.AllowAutoRedirect = this.getAllowAutoRedirect();
        request.Credentials = this.getCredentials();
        request.ReadWriteTimeout = this.getReadWriteTimeout();
        request.Timeout = this.getTimeout();
        request.UserAgent = this.getUserAgent();
        java.util.List<X509Certificate> certificates = this._Certificates;
        if (certificates != null && certificates.size() > 0) {
            request.ClientCertificates.AddRange(certificates.toArray(new X509Certificate[]{}));
        }
        CookieContainer cookieContainer = this.getCookieContainer();
        if (cookieContainer != null) {
            request.CookieContainer = cookieContainer;
        }
    }

    /**
     * Http Get 字符异步响应请求
     *
     * @param requestUrl requestUrl地址
     * @return
     */
    public final async Task

    <String> GetStringAsync(Uri requestUrl) {
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
        return await Task.<String>Run(() = >
                {
        return GetString(requestUrl);
		}
	   );
    }

    /**
     * Http Get 字符响应请求
     *
     * @param requestUrl requestUrl地址
     * @return
     */
    public final String GetString(Uri requestUrl) {
        HttpWebResponse response = Get(requestUrl);
        return response.ResponseStringResult(this.getEncoding());
    }

    /**
     * Http Get 异步字符响应请求
     *
     * @param requestUrl requestUrl地址
     * @param requestDictionary 请求字典
     * @return
     */
    public final async Task

    <String> GetStringAsync(String requestUrl, java.util.Map<String, String> requestDictionary) {
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
        return await Task.<String>Run(() = >
                {
        return GetString(requestUrl, requestDictionary);
		}
	   );
    }

    /**
     * Http Get 字符响应请求
     *
     * @param requestUrl        requestUrl地址
     * @param requestDictionary 请求字典
     * @return
     */
    public final String GetString(String requestUrl, java.util.Map<String, String> requestDictionary) {
        HttpWebResponse response = Get(requestUrl, requestDictionary);
        return response.ResponseStringResult(this.getEncoding());
    }

    /**
     * Http Get 异步请求
     *
     * @param requestUrl requestUrl地址
     * @param requestDictionary 请求字典
     * @return
     */
    public final async Task

    <HttpWebResponse> GetAsync(String requestUrl, java.util.Map<String, String> requestDictionary) {
        ExceptionUtils.CheckNotNullOrNotWhiteSpace(requestUrl, nameof(requestUrl));
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
        return await Task.<HttpWebResponse>Run(() = >
                {
        return Get(requestUrl, requestDictionary);
		}
	   );
    }

    /**
     * Http Get 请求
     *
     * @param requestUrl        requestUrl地址
     * @param requestDictionary 请求字典
     * @return
     */
    public final HttpWebResponse Get(String requestUrl, java.util.Map<String, String> requestDictionary) {
        ExceptionUtils.CheckNotNullOrNotWhiteSpace(requestUrl, nameof(requestUrl));
        String requestData = this.RequestString(requestDictionary, true);
        requestUrl = requestUrl.trim();
        Uri url = new Uri(requestUrl + (requestData.length() == 0 || requestUrl.substring(requestUrl.length() - 1).equals("?") ? "" : "?") + requestData);
        return Get(url);
    }

    /**
     * Http Get 异步请求
     *
     * @param requestUrl requestUrl地址
     * @return
     */
    public final async Task

    <HttpWebResponse> GetAsync(Uri requestUrl) {
        ExceptionUtils.CheckNotNull(requestUrl, nameof(requestUrl));
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
        return await Task.<HttpWebResponse>Run(() = >
                {
        return Get(requestUrl);
		}
	   );
    }

    /**
     * Http Post 字符响应异步请求
     *
     * @param requestUrl requestUrl地址
     * @param requestDictionary 请求字典
     * @return
     */
    public final async Task

    <String> PostStringAsync(String requestUrl, java.util.Map<String, String> requestDictionary) {
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
        return await Task.<String>Run(() = >
                {
        return PostString(requestUrl, requestDictionary);
		}
	   );
    }

    /**
     * Http Post 字符响应异步请求
     *
     * @param requestUrl requestUrl地址
     * @param requestDictionary 请求字典
     * @return
     */
    public final async Task

    <String> PostStringAsync(Uri requestUrl, java.util.Map<String, String> requestDictionary) {
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
        return await Task.<String>Run(() = >
                {
        return PostString(requestUrl, requestDictionary);
		}
	   );
    }

    /**
     * Http Post 字符响应请求
     *
     * @param requestUrl        requestUrl地址
     * @param requestDictionary 请求字典
     * @return
     */
    public final String PostString(String requestUrl, java.util.Map<String, String> requestDictionary) {
        HttpWebResponse response = Post(requestUrl, requestDictionary);
        return response.ResponseStringResult(this.getEncoding());
    }

    /**
     * Http Post 字符响应请求
     *
     * @param requestUrl        requestUrl地址
     * @param requestDictionary 请求字典
     * @return
     */
    public final String PostString(Uri requestUrl, java.util.Map<String, String> requestDictionary) {
        HttpWebResponse response = Post(requestUrl, requestDictionary);
        return response.ResponseStringResult(this.getEncoding());
    }

    /**
     * Http Post 异步请求
     *
     * @param requestUrl requestUrl地址
     * @param requestDictionary 请求字典
     * @return
     */
    public final async Task

    <HttpWebResponse> PostAsync(String requestUrl, java.util.Map<String, String> requestDictionary) {
        ExceptionUtils.CheckNotNullOrNotWhiteSpace(requestUrl, nameof(requestUrl));
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
        return await Task.<HttpWebResponse>Run(() = >
                {
        return Post(requestUrl, requestDictionary);
		}
	   );
    }

    /**
     * Http Post 请求
     *
     * @param requestUrl        requestUrl地址
     * @param requestDictionary 请求字典
     * @return
     */
    public final HttpWebResponse Post(String requestUrl, java.util.Map<String, String> requestDictionary) {
        ExceptionUtils.CheckNotNullOrNotWhiteSpace(requestUrl, nameof(requestUrl));
        return Post(new Uri(requestUrl), requestDictionary);
    }

    /**
     * Http Post 异步请求
     *
     * @param requestUrl requestUrl地址
     * @param requestDictionary 请求字典
     * @return
     */
    public final async Task

    <HttpWebResponse> PostAsync(Uri requestUrl, java.util.Map<String, String> requestDictionary) {
        ExceptionUtils.CheckNotNull(requestUrl, nameof(requestUrl));
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
        return await Task.<HttpWebResponse>Run(() = >
                {
        return Post(requestUrl, requestDictionary);
		}
	   );
    }

    /**
     * Http Get 请求
     *
     * @param requestUrl requestUrl
     * @return
     */
    public final HttpWebResponse Get(Uri requestUrl) {
        ExceptionUtils.CheckNotNull(requestUrl, nameof(requestUrl));
        HttpWebRequest request = (HttpWebRequest) WebRequest.Create(requestUrl);
        request.ProtocolVersion = this.getProtocolVersion();
        request.Method = "GET";
        SetRequest(request);
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
        var response = (HttpWebResponse) request.GetResponse();
        CookieContainer cookieContainer = this.getCookieContainer();
        if (cookieContainer != null) {
            response.Cookies = cookieContainer.GetCookies(response.ResponseUri);
        }
        return response;
    }

    /**
     * Http Post 请求
     *
     * @param requestUrl        requestUrl地址
     * @param requestDictionary 请求字典
     * @return
     */
    public final HttpWebResponse Post(Uri requestUrl, java.util.Map<String, String> requestDictionary) {
        ExceptionUtils.CheckNotNull(requestUrl, nameof(requestUrl));
        String requestData = RequestString(requestDictionary, false);
        byte[] bytes = this.getEncoding().GetBytes(requestData);
        return Post(requestUrl, bytes);
    }

    /**
     * Http Post 字符响应请求
     *
     * @param requestUrl requestUrl地址
     * @param body       请求体
     * @return
     */
    public final String PostString(Uri requestUrl, String body) {
        HttpWebResponse response = Post(requestUrl, body);
        return response.ResponseStringResult(this.getEncoding());
    }

    /**
     * Http Post 请求
     *
     * @param requestUrl requestUrl地址
     * @param body       请求体
     * @return
     */
    public final HttpWebResponse Post(Uri requestUrl, String body) {
        if (body == null) {
            body = "";
        }
        ExceptionUtils.CheckNotNull(requestUrl, nameof(requestUrl));
        byte[] bytes = this.getEncoding().GetBytes(body);
        return Post(requestUrl, bytes);
    }


    /**
     * Http Post 字符响应请求
     *
     * @param requestUrl   requestUrl地址
     * @param requestBytes 请求字节
     * @return
     */
    public final String PostString(Uri requestUrl, byte[] requestBytes) {
        HttpWebResponse response = Post(requestUrl, requestBytes);
        return response.ResponseStringResult(this.getEncoding());
    }

    /**
     * Http Post 请求
     *
     * @param requestUrl   requestUrl地址
     * @param requestBytes 请求字节
     * @return
     */
    public final HttpWebResponse Post(Uri requestUrl, byte[] requestBytes) {
        ExceptionUtils.CheckNotNull(requestUrl, nameof(requestUrl));
        HttpWebRequest request = (HttpWebRequest) WebRequest.Create(requestUrl);
        request.ProtocolVersion = this.getProtocolVersion();
        request.Method = "POST";
        SetRequest(request);
        byte[] bytes;
        if (requestBytes != null) {
            bytes = requestBytes;
        } else {
            bytes = new byte[1];
        }
        request.ContentLength = bytes.length;
//C# TO JAVA CONVERTER NOTE: The following 'using' block is replaced by its Java equivalent:
//		using (Stream requestStream = request.GetRequestStream())
        Stream requestStream = request.GetRequestStream();
        try {
            if (bytes.length > 0) {
                requestStream.Write(bytes, 0, bytes.length);
                requestStream.Flush();
            }
        } finally {
            requestStream.dispose();
        }
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
        var response = (HttpWebResponse) request.GetResponse();
        CookieContainer cookieContainer = this.getCookieContainer();
        if (cookieContainer != null) {
            response.Cookies = cookieContainer.GetCookies(response.ResponseUri);
        }
        return response;
    }
}