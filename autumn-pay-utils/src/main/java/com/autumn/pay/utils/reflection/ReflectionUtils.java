package com.autumn.pay.utils.reflection;

import GN.Pay.Utils.Reflection.Meta.*;

/** 
 反射帮助
 
*/
public final class ReflectionUtils
{
	private static ConcurrentDictionary<Class, ISet<MetaMember>> Cache_MetaMembers = new ConcurrentDictionary<Class, ISet<MetaMember>>();

	private static ConcurrentDictionary<Class, ISet<MetaProperty>> Cache_MetaPropertys = new ConcurrentDictionary<Class, ISet<MetaProperty>>();

	/** 
	 获取成员元集合
	 
	 @param type 类型
	 @return 
	*/
	public static ISet<MetaMember> GetMetaMembers(Class type)
	{
		ExceptionUtils.CheckNotNull(type, nameof(type));
		ISet<MetaMember> metaMemberys = new java.util.HashSet<MetaMember>();
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
		var ps = type.GetProperties(BindingFlags.Public | BindingFlags.Instance);
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
		for (var p : ps)
		{
			metaMemberys.Add(new MetaProperty(p));
		}
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
		var fs = type.getFields(BindingFlags.Public | BindingFlags.Instance);
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
		for (var f : fs)
		{
			metaMemberys.Add(new MetaField(f));
		}
		return metaMemberys;
	}

	/** 
	 获取字段元集合
	 
	 @param type 类型
	 @return 
	*/
	public static ISet<MetaField> GetMetaFields(Class type)
	{
		ExceptionUtils.CheckNotNull(type, nameof(type));
		ISet<MetaField> metaFields = new java.util.HashSet<MetaField>();
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
		var fs = type.getFields(BindingFlags.Public | BindingFlags.Instance);
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
		for (var f : fs)
		{
			metaFields.Add(new MetaField(f));
		}
		return metaFields;
	}

	/** 
	 获取属性元集合
	 
	 @param type 类型
	 @return 
	*/
	public static ISet<MetaProperty> GetMetaPropertys(Class type)
	{
		ExceptionUtils.CheckNotNull(type, nameof(type));
		ISet<MetaProperty> metaPropertys = new java.util.HashSet<MetaProperty>();
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
		var ps = type.GetProperties(BindingFlags.Public | BindingFlags.Instance);
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
		for (var p : ps)
		{
			metaPropertys.Add(new MetaProperty(p));
		}
		return metaPropertys;
	}

	/** 
	 获取缓存的成员元集合
	 
	 @param type 类型
	 @return 
	*/
	public static ISet<MetaMember> GetCacheMetaMembers(Class type)
	{
		ExceptionUtils.CheckNotNull(type, nameof(type));
		Lazy<ISet<MetaMember>> lazy = null;
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
		ISet<MetaMember> metaMembers = Cache_MetaMembers.GetOrAdd(type, (t) =>
		{
			if (lazy == null)
			{
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
				lazy = new Lazy<ISet<MetaMember>>(() =>
				{
					return GetMetaMembers(t);
				}
			   );
			}
			return lazy.getValue();
		}
	   );
		return metaMembers;
	}

	/** 
	 获取缓存的属性元集合
	 
	 @param type 类型
	 @return 
	*/
	public static ISet<MetaProperty> GetCacheMetaPropertys(Class type)
	{
		ExceptionUtils.CheckNotNull(type, nameof(type));
		Lazy<ISet<MetaProperty>> lazy = null;
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
		ISet<MetaProperty> metaPropertys = Cache_MetaPropertys.GetOrAdd(type, (t) =>
		{
			if (lazy == null)
			{
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
				lazy = new Lazy<ISet<MetaProperty>>(() =>
				{
					return GetMetaPropertys(t);
				}
			   );
			}
			return lazy.getValue();
		}
	   );
		return metaPropertys;
	}
}