package com.autumn.pay.utils;

/** 
 Xml 帮助
 
*/
public final class XmlUtils
{



	/** 
	 加载 Xml 
	 
	 @param xmlString xml字符
	 @param rootElement 根元素
	 @return 
	*/
	public static java.util.Map<String, Object> LoadXmlString(String xmlString, String rootElement)
	{
		ExceptionUtils.CheckNotNullOrNotWhiteSpace(xmlString, nameof(xmlString));
		ExceptionUtils.CheckNotNullOrNotWhiteSpace(rootElement, nameof(rootElement));
		XDocument document = XDocument.Parse(xmlString);
		java.util.Map<String, Object> elementDic = new java.util.HashMap<String, Object>(StringComparer.InvariantCultureIgnoreCase);
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
		var element = document.Element(rootElement);
		if (element != null)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
			for (var e : element.Elements())
			{
				LoadXmlString(elementDic, e);
			}
		}
		return elementDic;
	}

	private static void LoadXmlString(java.util.Map<String, Object> elementDic, XElement xElement)
	{
		if (xElement.Elements().Count() > 0)
		{
			java.util.Map<String, Object> subDic = new java.util.HashMap<String, Object>(StringComparer.InvariantCultureIgnoreCase);
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
			for (var e : xElement.Elements())
			{
				LoadXmlString(subDic, e);
			}
			elementDic.put(xElement.getName().toString(), subDic);
		}
		else
		{
			elementDic.put(xElement.getName().toString(), xElement.getValue());
		}
	}
}