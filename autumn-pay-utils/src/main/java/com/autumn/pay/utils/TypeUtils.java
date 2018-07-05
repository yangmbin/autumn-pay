package com.autumn.pay.utils;

import GN.Pay.Utils.*;

/** 
 类型帮助
 
*/
public final class TypeUtils
{
	/** 
	 获取类型默认构造的分配的类型集合
	 
	 @param assembly 程序集 
	 @param isReferencedAssemblie 是否引用程序集      
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: Extension methods are not available in Java:
//ORIGINAL LINE: public static List<Type> GetDefaultConstructorAssignableTypes<T>(this Assembly assembly, Type attributeType, bool isReferencedAssemblie)
	public static <T> java.util.ArrayList<Class> GetDefaultConstructorAssignableTypes(Assembly assembly, Class attributeType, boolean isReferencedAssemblie)
	{
		if (assembly == null)
		{
			throw new ArgumentNullException(nameof(assembly));
		}
		Func<Class, Boolean> predicate;
		if (attributeType != null)
		{
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
			predicate = t => !t.IsAbstract && !t.IsInterface && t.GetConstructor(Class.EmptyTypes) != null && t.IsDefined(attributeType, false);
		}
		else
		{
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
			predicate = t => !t.IsAbstract && !t.IsInterface && t.GetConstructor(Class.EmptyTypes) != null;
		}
		return GetAssignableTypes<T>(assembly, predicate, isReferencedAssemblie);
	}

	/** 
	 获取分配类型类型集合
	 
	 <typeparam name="T">类型</typeparam>
	 <typeparam name="TAttribute">特性类型</typeparam>
	 @param assembly 程序集
	 @param predicate 条件
	 @param isReferencedAssemblie 是否包含引用
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: Extension methods are not available in Java:
//ORIGINAL LINE: public static Dictionary<Type, TAttribute> GetAssignableTypeDictionary<T, TAttribute>(this Assembly assembly, Func<Type, bool> predicate, bool isReferencedAssemblie) where TAttribute : Attribute
	public static <T, TAttribute extends Attribute> java.util.HashMap<Class, TAttribute> GetAssignableTypeDictionary(Assembly assembly, Func<Class, Boolean> predicate, boolean isReferencedAssemblie)
	{
		Func<Class, Boolean> predicate2;
		if (predicate != null)
		{
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
			predicate2 = t => predicate(t) && t.IsDefined(TAttribute.class, false);
		}
		else
		{
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
			predicate2 = t => t.IsDefined(TAttribute.class, false);
		}
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
		var typeList = GetAssignableTypes<T>(assembly, predicate2, isReferencedAssemblie);
		java.util.HashMap<Class, TAttribute> dic = new java.util.HashMap<Class, TAttribute>();
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
		for (var type : typeList)
		{
			Object tempVar = Attribute.GetCustomAttribute(type, TAttribute.class);
			dic.put(type, (TAttribute)((tempVar instanceof TAttribute) ? tempVar : null));
		}
		return dic;
	}

	/** 
	 是否是异步方法
	 
	 @param method A method to check
	*/
//C# TO JAVA CONVERTER TODO TASK: Extension methods are not available in Java:
//ORIGINAL LINE: public static bool IsAsyncMethod(this MethodInfo method)
	public static boolean IsAsyncMethod(java.lang.reflect.Method method)
	{
		return (method.ReturnType == Task.class || (method.ReturnType.IsGenericType && method.ReturnType.GetGenericTypeDefinition() == Task<>.class));
	}

	/** 
	 获取分配类型类型集合
	 
	 <typeparam name="T">类型</typeparam>
	 @param assembly 程序集
	 @param predicate 条件
	 @param isReferencedAssemblie 是否包含引用
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: Extension methods are not available in Java:
//ORIGINAL LINE: public static List<Type> GetAssignableTypes<T>(this Assembly assembly, Func<Type, bool> predicate, bool isReferencedAssemblie)
	public static <T> java.util.ArrayList<Class> GetAssignableTypes(Assembly assembly, Func<Class, Boolean> predicate, boolean isReferencedAssemblie)
	{
		if (assembly == null)
		{
			throw new ArgumentNullException(nameof(assembly));
		}
		Class convertType = T.class;
		Func<Class, Boolean> predicate2;
		if (predicate != null)
		{
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
			predicate2 = t => predicate(t) && convertType.IsAssignableFrom(t);
		}
		else
		{
//C# TO JAVA CONVERTER TODO TASK: Lambda expressions and anonymous methods are not converted by C# to Java Converter:
			predicate2 = t => convertType.IsAssignableFrom(t);
		}
		Class[] types = assembly.GetTypes(predicate2);
		java.util.ArrayList<Class> typeList = new java.util.ArrayList<Class>(types);
		if (isReferencedAssemblie)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
			var assemblies = assembly.GetReferencedAssemblies();
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
			for (var assemblie : assemblies)
			{
				assembly = Assembly.Load(assemblie);
				typeList.addAll(assembly.GetTypes(predicate2));
			}
		}
		return typeList;
	}

	private static Class[] AssemblyTypes(Assembly assembly)
	{
		try
		{
			Class[] types = assembly.GetTypes();
			return types;
		}
		catch (ReflectionTypeLoadException err)
		{
			if (err.LoaderExceptions.getLength() > 0)
			{
				throw err.LoaderExceptions[0];
			}
			throw err;
		}
	}

	/** 
	 获取类型集合
	 
	 @param assembly 程序集
	 @param predicate 条件
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: Extension methods are not available in Java:
//ORIGINAL LINE: public static Type[] GetTypes(this Assembly assembly, Func<Type, bool> predicate)
	public static Class[] GetTypes(Assembly assembly, Func<Class, Boolean> predicate)
	{
		if (assembly == null)
		{
			throw new ArgumentNullException(nameof(assembly));
		}
		Class[] types = AssemblyTypes(assembly);
		if (predicate != null)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no Java equivalent to LINQ queries:
			return types.Where(predicate).toArray();
		}
		return types;
	}

	/** 
	 是否属于Nullable分配类型
	 
	 @param type 类型
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: Extension methods are not available in Java:
//ORIGINAL LINE: public static bool IsNullableType(this Type type)
	public static boolean IsNullableType(Class type)
	{
		ExceptionUtils.CheckNotNull(type, nameof(type));
		return type.IsValueType && type.IsGenericType && .class.IsAssignableFrom(type.GetGenericTypeDefinition());
	}

	/** 
	 是否属于Nullable分配类型,并输出值类型
	 
	 @param type 类型
	 @param valueType 输出值类型
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: Extension methods are not available in Java:
//ORIGINAL LINE: public static bool IsNullableType(this Type type, out Type valueType)
	public static boolean IsNullableType(Class type, RefObject<Class> valueType)
	{
		if (IsNullableType(type))
		{
			valueType.argvalue = type.GetGenericArguments()[0];
			return true;
		}
		valueType.argvalue = null;
		return false;
	}

	/** 
	 数字类型集
	 
	*/
	public static final java.util.HashSet<Class> NumberTypeSet = new java.util.HashSet<Class>(java.util.Arrays.asList(new Class[] { Byte.class,Byte.class,Short.class, Short.class,Integer.class,Integer.class, Long.class,Long.class,Float.class, java.math.BigDecimal.class,Double.class }));

	/** 
	 基本类型集
	 
	*/
	public static final java.util.HashSet<Class> BaseTypeSet = new java.util.HashSet<Class>(NumberTypeSet) { String.class,Boolean.class,Character.class, Guid.class,java.util.Date.class,TimeSpan.class, DateTimeOffset.class };



	private static boolean IsSetTypeOrNullableDefinitionSetType(Class type, ISet<Class> comparerSet, RefObject<Class> valueType)
	{
		ExceptionUtils.CheckNotNull(type, nameof(type));
		if (comparerSet.Contains(type))
		{
			valueType.argvalue = type;
			return true;
		}
		if (IsNullableType(type, valueType))
		{
			return comparerSet.Contains(valueType.argvalue);
		}
		return false;
	}

	/** 
	 获取是否是数字类型
	 
	 @param type 类型
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: Extension methods are not available in Java:
//ORIGINAL LINE: public static bool IsNumberType(this Type type)
	public static boolean IsNumberType(Class type)
	{
		ExceptionUtils.CheckNotNull(type, nameof(type));
		return NumberTypeSet.contains(type);
	}

	/** 
	 获取是否是数字类型或Nullable定义的数字类型
	 
	 @param type 类型
	 @param valueType 值类型
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: Extension methods are not available in Java:
//ORIGINAL LINE: public static bool IsNumberTypeOrNullableDefinitionNumberType(this Type type, out Type valueType)
	public static boolean IsNumberTypeOrNullableDefinitionNumberType(Class type, RefObject<Class> valueType)
	{
		return IsSetTypeOrNullableDefinitionSetType(type, NumberTypeSet, valueType);
	}

	/** 
	 获取是否是基本类型
	 
	 @param type 类型
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: Extension methods are not available in Java:
//ORIGINAL LINE: public static bool IsBaseType(this Type type)
	public static boolean IsBaseType(Class type)
	{
		ExceptionUtils.CheckNotNull(type, nameof(type));
		return BaseTypeSet.contains(type);
	}

	/** 
	 获取是否是基本型或Nullable定义的基本类型
	 
	 @param type 类型
	 @param valueType 值类型
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: Extension methods are not available in Java:
//ORIGINAL LINE: public static bool IsBaseTypeOrNullableDefinitionBaseType(this Type type, out Type valueType)
	public static boolean IsBaseTypeOrNullableDefinitionBaseType(Class type, RefObject<Class> valueType)
	{
		return IsSetTypeOrNullableDefinitionSetType(type, BaseTypeSet, valueType);
	}

	/** 
	 获取是否是枚举类型或Nullable定义的枚举
	 
	 @param type 类型
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: Extension methods are not available in Java:
//ORIGINAL LINE: public static bool IsEnumOrNullableType(this Type type)
	public static boolean IsEnumOrNullableType(Class type)
	{
		ExceptionUtils.CheckNotNull(type, nameof(type));
		if (type.getSuperclass() != null && type.getSuperclass() == Enum.class)
		{
			return true;
		}
		else
		{
			if (type.IsValueType && type.IsGenericType)
			{
				return .class.IsAssignableFrom(type.GetGenericTypeDefinition()) && type.GetGenericArguments()[0].getSuperclass() == Enum.class;
			}
			return false;
		}
	}
}