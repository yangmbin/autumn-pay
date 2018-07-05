package com.autumn.pay.utils;

/** 
 数据转换帮助
 
*/
public final class DataConvertUtils
{
	/** 
	 获取不依赖区域信息
	 
	*/
	public static final System.Globalization.CultureInfo InvariantCulture = System.Globalization.CultureInfo.InvariantCulture;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !DEBUG
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	
//#endif
	private static java.util.HashMap<Class, Func<Class, Object, Object>> _TargetConverter;

	/** 
	 静态实例
	 
	*/
	static
	{
		DataConvertUtils._TargetConverter = new java.util.HashMap<Class, Func<Class, Object, Object>>();
		DataConvertUtils._TargetConverter.put(String.class, DataConvertUtils.ToStringConvert);
		DataConvertUtils._TargetConverter.put(Byte.class, DataConvertUtils.ToByteConvert);
		DataConvertUtils._TargetConverter.put(Byte.class, DataConvertUtils.ToByteConvert);
		DataConvertUtils._TargetConverter.put(Byte.class, DataConvertUtils.ToSByteConvert);
		DataConvertUtils._TargetConverter.put(Byte.class, DataConvertUtils.ToSByteConvert);
		DataConvertUtils._TargetConverter.put(Short.class, DataConvertUtils.ToInt16Convert);
		DataConvertUtils._TargetConverter.put(Short.class, DataConvertUtils.ToInt16Convert);
		DataConvertUtils._TargetConverter.put(Short.class, DataConvertUtils.ToUInt16Convert);
		DataConvertUtils._TargetConverter.put(Short.class, DataConvertUtils.ToUInt16Convert);
		DataConvertUtils._TargetConverter.put(Integer.class, DataConvertUtils.ToInt32Convert);
		DataConvertUtils._TargetConverter.put(Integer.class, DataConvertUtils.ToInt32Convert);
		DataConvertUtils._TargetConverter.put(Integer.class, DataConvertUtils.ToUInt32Convert);
		DataConvertUtils._TargetConverter.put(Integer.class, DataConvertUtils.ToUInt32Convert);
		DataConvertUtils._TargetConverter.put(Long.class, DataConvertUtils.ToInt64Convert);
		DataConvertUtils._TargetConverter.put(Long.class, DataConvertUtils.ToInt64Convert);
		DataConvertUtils._TargetConverter.put(Long.class, DataConvertUtils.ToUInt64Convert);
		DataConvertUtils._TargetConverter.put(Long.class, DataConvertUtils.ToUInt64Convert);
		DataConvertUtils._TargetConverter.put(Float.class, DataConvertUtils.ToSingleConvert);
		DataConvertUtils._TargetConverter.put(Float.class, DataConvertUtils.ToSingleConvert);
		DataConvertUtils._TargetConverter.put(Double.class, DataConvertUtils.ToDoubleConvert);
		DataConvertUtils._TargetConverter.put(Double.class, DataConvertUtils.ToDoubleConvert);
		DataConvertUtils._TargetConverter.put(java.math.BigDecimal.class, DataConvertUtils.ToDecimalConvert);
		DataConvertUtils._TargetConverter.put(java.math.BigDecimal.class, DataConvertUtils.ToDecimalConvert);
		DataConvertUtils._TargetConverter.put(Boolean.class, DataConvertUtils.ToBooleanConvert);
		DataConvertUtils._TargetConverter.put(Boolean.class, DataConvertUtils.ToBooleanConvert);
		DataConvertUtils._TargetConverter.put(Character.class, DataConvertUtils.ToCharConvert);
		DataConvertUtils._TargetConverter.put(Character.class, DataConvertUtils.ToCharConvert);
		DataConvertUtils._TargetConverter.put(Guid.class, DataConvertUtils.ToGuidConvert);
		DataConvertUtils._TargetConverter.put(Guid.class, DataConvertUtils.ToGuidConvert);
		DataConvertUtils._TargetConverter.put(java.util.Date.class, DataConvertUtils.ToDateTimeConvert);
		DataConvertUtils._TargetConverter.put(java.util.Date.class, DataConvertUtils.ToDateTimeConvert);
		DataConvertUtils._TargetConverter.put(TimeSpan.class, DataConvertUtils.ToTimeSpanConvert);
		DataConvertUtils._TargetConverter.put(TimeSpan.class, DataConvertUtils.ToTimeSpanConvert);
		DataConvertUtils._TargetConverter.put(DateTimeOffset.class, DataConvertUtils.ToDateTimeOffsetConvert);
		DataConvertUtils._TargetConverter.put(DateTimeOffset.class, DataConvertUtils.ToDateTimeOffsetConvert);
	}



//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region convert

	/** 
	 字节数组转换为字符
	 
	 @param bytes 字节
	 @param removeDashes 移除中间分隔符
	 @return 
	*/
	public static String BytesToHex(byte[] bytes, boolean removeDashes)
	{
		ExceptionUtils.CheckNotNull(bytes, nameof(bytes));
		String hex = BitConverter.ToString(bytes);
		if (removeDashes)
		{
			hex = hex.replace("-", "");
		}
		return hex;
	}

	/** 
	 字符转换为字节数组
	 
	 @param hex 字符
	 @return 
	*/
	public static byte[] HexToBytes(String hex)
	{
		if (String.IsNullOrWhiteSpace(hex))
		{
			return null;
		}
		String fixedHex = hex.replace("-", "");
		byte[] bytes = new byte[fixedHex.length() / 2];
		int shift = 4;
		int offset = 0;
		for (char c : fixedHex)
		{
			int b = (c - '0') % 32;
			if (b > 9)
			{
				b -= 7;
			}
			bytes[offset] |= (byte)(b << shift);
			shift ^= 4;
			if (shift != 0)
			{
				offset++;
			}
		}
		return bytes;
	}

	/** 
	 获取目标转换器(可自定义添加)
	 
	*/
	public static java.util.HashMap<Class, Func<Class, Object, Object>> getTargetConverter()
	{
		return DataConvertUtils._TargetConverter;
	}

	/** 
	 目标值转换
	 
	 @param targetType 目标类型
	 @param value 值
	 @return 返回转换后的值
	*/
	public static Object TargetValueConvert(Class targetType, Object value)
	{
		if (targetType == null)
		{
			return value;
		}
		if (value == null || value == DBNull.getValue())
		{
			if (targetType.IsValueType)
			{
				return Activator.CreateInstance(targetType);
			}
			return null;
		}
		Class type = value.getClass();
		if (targetType.equals(type))
		{
			return value;
		}
		Class genericType = null;
		RefObject<Class> tempRef_genericType = new RefObject<Class>(genericType);
		boolean tempVar = targetType.IsNullableType(tempRef_genericType);
			genericType = tempRef_genericType.argvalue;
		if (tempVar)
		{
			targetType = genericType;
			if (genericType.equals(type))
			{
				return value;
			}
		}
		Func<Class, Object, Object> fun = null;
		if ((fun = DataConvertUtils.getTargetConverter().get(targetType)) != null)
		{
			return fun(targetType, value);
		}
		else
		{
			if (value != null && targetType.IsEnumOrNullableType())
			{
				return DataConvertUtils.ToEnumConvert(targetType, value);
			}
			return value;
		}
	}

	/** 
	 目标值转换
	 
	 @param value 值
	 @return 返回转换后的值
	*/
//C# TO JAVA CONVERTER TODO TASK: The C# 'struct' constraint has no equivalent in Java:
	public static <T extends struct> T TargetValueConvert(Object value)
	{
		if (value instanceof T)
		{
			return (T)value;
		}
		if (value == null)
		{
			return null;
		}
		Class targetType = T.class;
		return (T)DataConvertUtils.TargetValueConvert(targetType, value);
	}

	/** 
	 是否允许目标转换
	 
	 @param targetType 目标类型
	 @return 
	*/
	public static boolean IsAllowTargetConvert(Class targetType)
	{
		if (targetType == null)
		{
			return false;
		}
		if (getTargetConverter().containsKey(targetType))
		{
			return true;
		}
		else
		{
			return targetType.IsEnumOrNullableType();
		}
	}

	/** 
	 值类型转换
	 
	 <typeparam name="T">类型</typeparam>
	 @param fun 函数
	 @param targetType 需要转换的目标类型
	 @param value 值
	 @return 返回转换后的值
	*/
//C# TO JAVA CONVERTER TODO TASK: The C# 'struct' constraint has no equivalent in Java:
	public static <T extends struct> T ToValueTypeConvert(Func<Object, T> fun, Class targetType, Object value)
	{
		if (value == null || value == DBNull.getValue())
		{
			if (targetType == T.class)
			{
				return null;
			}
			return null;
		}
		Class type = value.getClass();
		if (type == targetType) //类型相同时
		{
			return (T)value;
		}
		if (type.IsValueType)
		{
			return fun(value);
		}
		else
		{
			if (type == String.class)
			{
				return fun(value.toString().trim());
			}
			else
			{
				return fun(value);
			}
		}
	}

	/** 
	 值类型转换
	 
	 <typeparam name="T">类型</typeparam>
	 @param fun 函数
	 @param targetType 需要转换的目标类型
	 @param value 值
	 @return 返回转换后的值
	*/
//C# TO JAVA CONVERTER TODO TASK: The C# 'struct' constraint has no equivalent in Java:
	public static <T extends struct> T ToValueTypeConvert(Func<String, T> fun, Class targetType, Object value)
	{
		if (value == null || value == DBNull.getValue())
		{
			if (targetType == T.class)
			{
				return null;
			}
			return null;
		}
		Class type = value.getClass();
		if (type == targetType) //类型相同时
		{
			return (T)value;
		}
		return fun(value.toString().trim());
	}

	/** 
	 String转换
	 
	 @param targetType 需要转换的目标类型
	 @param value 值
	 @return 返回转换后的值
	*/
	private static Object ToStringConvert(Class targetType, Object value)
	{
		if (value == null || value == DBNull.getValue())
		{
			return null;
		}
		return value.toString();
	}

	/** 
	 Byte转换
	 
	 @param targetType 需要转换的目标类型
	 @param value 值
	 @return 返回转换后的值
	*/
	private static Object ToByteConvert(Class targetType, Object value)
	{
		return ToValueTypeConvert<Byte>(Byte.parseByte, targetType, value);
	}

	/** 
	 SByte转换
	 
	 @param targetType 需要转换的目标类型
	 @param value 值
	 @return 返回转换后的值
	*/
	private static Object ToSByteConvert(Class targetType, Object value)
	{
		return ToValueTypeConvert<Byte>(Byte.parseByte, targetType, value);
	}

	/** 
	 short转换
	 
	 @param targetType 需要转换的目标类型
	 @param value 值
	 @return 返回转换后的值
	*/
	private static Object ToInt16Convert(Class targetType, Object value)
	{
		return ToValueTypeConvert<Short>(Short.parseShort, targetType, value);
	}

	/** 
	 UInt16转换
	 
	 @param targetType 需要转换的目标类型
	 @param value 值
	 @return 返回转换后的值
	*/
	private static Object ToUInt16Convert(Class targetType, Object value)
	{
		return ToValueTypeConvert<Short>(Short.parseShort, targetType, value);
	}

	/** 
	 Int32转换
	 
	 @param targetType 需要转换的目标类型
	 @param value 值
	 @return 返回转换后的值
	*/
	private static Object ToInt32Convert(Class targetType, Object value)
	{
		return ToValueTypeConvert<Integer>(Integer.parseInt, targetType, value);
	}

	/** 
	 UInt32转换
	 
	 @param targetType 需要转换的目标类型
	 @param value 值
	 @return 返回转换后的值
	*/
	private static Object ToUInt32Convert(Class targetType, Object value)
	{
		return ToValueTypeConvert<Integer>(Integer.parseInt, targetType, value);
	}

	/** 
	 Int64转换
	 
	 @param targetType 需要转换的目标类型
	 @param value 值
	 @return 返回转换后的值
	*/
	private static Object ToInt64Convert(Class targetType, Object value)
	{
		return ToValueTypeConvert<Long>(Long.parseLong, targetType, value);
	}

	/** 
	 UInt64转换
	 
	 @param targetType 需要转换的目标类型
	 @param value 值
	 @return 返回转换后的值
	*/
	private static Object ToUInt64Convert(Class targetType, Object value)
	{
		return ToValueTypeConvert<Long>(Long.parseLong, targetType, value);
	}

	/** 
	 Single转换
	 
	 @param targetType 需要转换的目标类型
	 @param value 值
	 @return 返回转换后的值
	*/
	private static Object ToSingleConvert(Class targetType, Object value)
	{
		return ToValueTypeConvert<Float>(Float.parseFloat, targetType, value);
	}

	/** 
	 Double转换
	 
	 @param targetType 需要转换的目标类型
	 @param value 值
	 @return 返回转换后的值
	*/
	private static Object ToDoubleConvert(Class targetType, Object value)
	{
		return ToValueTypeConvert<Double>(Double.parseDouble, targetType, value);
	}

	/** 
	 Decimal转换
	 
	 @param targetType 需要转换的目标类型
	 @param value 值
	 @return 返回转换后的值
	*/
	private static Object ToDecimalConvert(Class targetType, Object value)
	{
		return ToValueTypeConvert<java.math.BigDecimal>(Convert.ToDecimal, targetType, value);
	}

	/** 
	 Boolean转换
	 
	 @param targetType 需要转换的目标类型
	 @param value 值
	 @return 返回转换后的值
	*/
	private static Object ToBooleanConvert(Class targetType, Object value)
	{
		if (value != null && value instanceof String)
		{
			String str = value.toString().trim().ToUpperInvariant();
			if (str.equals("TRUE") || str.equals("1") || str.equals("YES") || str.equals("Y") || str.equals("是"))
			{
				return true;
			}
			else if (str.equals("FALSE") || str.equals("0") || str.equals("NO") || str.equals("N") || str.equals("否"))
			{
				return false;
			}
		}
		return ToValueTypeConvert<Boolean>(Boolean.parseBoolean, targetType, value);
	}

	/** 
	 Char转换
	 
	 @param targetType 需要转换的目标类型
	 @param value 值
	 @return 返回转换后的值
	*/
	private static Object ToCharConvert(Class targetType, Object value)
	{
		return ToValueTypeConvert<Character>(Convert.ToChar, targetType, value);
	}

	/** 
	 Guid转换
	 
	 @param targetType 需要转换的目标类型
	 @param value 值
	 @return 返回转换后的值
	*/
	private static Object ToGuidConvert(Class targetType, Object value)
	{
		return ToValueTypeConvert<Guid>(Guid.Parse, targetType, value);
	}

	/** 
	 日期时间转换
	 
	 @param targetType 需要转换的目标类型
	 @param value 值
	 @return 返回转换后的值
	*/
	private static Object ToDateTimeConvert(Class targetType, Object value)
	{
		if (value == null || value == DBNull.getValue())
		{
			if (targetType == java.util.Date.class)
			{
				return null;
			}
			return new java.util.Date(0);
		}
		Class type = value.getClass();
		if (type.equals(targetType))
		{
			return value;
		}
		if (type == String.class)
		{
			return Convert.ToDateTime(value.toString().trim());
		}
		else if (type == Double.class)
		{
			return java.util.Date.FromOADate(Double.parseDouble(value));
		}
		else
		{
			throw new InvalidCastException(String.format("无法将类型 %1$s 转换为类型%2$s。", type.FullName, targetType.FullName));
		}
	}

	/** 
	 时间间隔转换
	 
	 @param targetType 需要转换的目标类型
	 @param value 值
	 @return 返回转换后的值
	*/
	private static Object ToTimeSpanConvert(Class targetType, Object value)
	{
		return ToValueTypeConvert<TimeSpan>(TimeSpan.Parse, targetType, value);
	}

	/** 
	 世界时转换
	 
	 @param targetType 需要转换的目标类型
	 @param value 值
	 @return 返回转换后的值
	*/
	private static Object ToDateTimeOffsetConvert(Class targetType, Object value)
	{
		return ToValueTypeConvert<DateTimeOffset>(DateTimeOffset.Parse, targetType, value);
	}

	/** 
	 枚举转换
	 
	 @param targetType 需要转换的目标类型
	 @param value 值
	 @return 返回转换后的值
	*/
	private static Object ToEnumConvert(Class targetType, Object value)
	{
		if (value instanceof String)
		{
			return Enum.Parse(targetType, value.toString().trim(), true);
		}
		else
		{
			if (targetType.IsGenericType && targetType.IsValueType && .class.IsAssignableFrom(targetType.GetGenericTypeDefinition()))
			{
				targetType = targetType.GetGenericArguments()[0];
			}
			return Enum.ToObject(targetType, value);
		}
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}