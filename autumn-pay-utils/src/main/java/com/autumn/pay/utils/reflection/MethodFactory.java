package com.autumn.pay.utils.reflection;

import com.autumn.util.ArgumentNullException;
import com.sun.org.apache.xpath.internal.compiler.OpCodes;

/**
 成员工厂
 
*/
public final class MethodFactory
{
	/** 
	 创建动态成员
	 
	 @param name 名称
	 @param returnType 返回类型
	 @param parameterTypes 参数集合
	 @param owner 成员类型
	 @return 
	*/
	public static DynamicMethod CreateDynamicMethod(String name, Class returnType, Class[] parameterTypes, Class owner)
	{
		DynamicMethod dynamicMethod = !owner.IsInterface ? new DynamicMethod(name, returnType, parameterTypes, owner, true) : new DynamicMethod(name, returnType, parameterTypes, owner.Module, true);
		return dynamicMethod;
	}

	/** 
	 创建属性Set方法
	 
	 <typeparam name="T">对象类型</typeparam>
	 @param propertyInfo 属性
	 @return 
	*/
	public static <T> Action<T, Object> CreatePropertySet(PropertyInfo propertyInfo)
	{
		if (propertyInfo == null)
		{
			throw new ArgumentNullException("propertyInfo");
		}
		DynamicMethod dynamicMethod = CreateDynamicMethod("Set" + propertyInfo.getName(), void.class, new Object[] { T.class, Object.class }, propertyInfo.ReflectedType);
		ILGenerator generator = dynamicMethod.GetILGenerator();
		java.lang.reflect.Method setMethod = propertyInfo.GetSetMethod(true);
		if (!setMethod.IsStatic)
		{
			generator.PushInstance(propertyInfo.ReflectedType);
		}
		generator.Emit(OpCodes.Ldarg_1);
		generator.UnboxIfNeeded(propertyInfo.PropertyType);
		generator.CallMethod(setMethod);
		generator.Emit(OpCodes.Ret);
		return (Action<T, Object>)dynamicMethod.CreateDelegate(Action<T, Object>.class);
	}

	/** 
	 创建属性Get方法
	 
	 <typeparam name="T">对象类型</typeparam>
	 @param propertyInfo 属性
	 @return 
	*/
	public static <T> Func<T, Object> CreatePropertyGet(PropertyInfo propertyInfo)
	{
		if (propertyInfo == null)
		{
			throw new ArgumentNullException("propertyInfo");
		}
		DynamicMethod dynamicMethod = CreateDynamicMethod("Get" + propertyInfo.getName(), T.class, new Object[] { Object.class }, propertyInfo.ReflectedType);
		ILGenerator generator = dynamicMethod.GetILGenerator();
		java.lang.reflect.Method getMethod = propertyInfo.GetGetMethod(true);
		if (!getMethod.IsStatic)
		{
			generator.PushInstance(propertyInfo.ReflectedType);
		}
		generator.CallMethod(getMethod);
		generator.BoxIfNeeded(propertyInfo.PropertyType);
		generator.Emit(OpCodes.Ret);
		return (Func<T, Object>)dynamicMethod.CreateDelegate(Func<T, Object>.class);
	}

	/** 
	 创建字段Set方法
	 
	 <typeparam name="T">对象类型</typeparam>
	 @param fieldInfo 字段
	 @return 
	*/
	public static <T> Action<T, Object> CreateFieldSet(java.lang.reflect.Field fieldInfo)
	{
		DynamicMethod dynamicMethod = CreateDynamicMethod("Set" + fieldInfo.getName(), null, new Class[] { T.class, Object.class }, fieldInfo.ReflectedType);
		ILGenerator generator = dynamicMethod.GetILGenerator();
		if (!fieldInfo.IsStatic)
		{
			generator.PushInstance(fieldInfo.ReflectedType);
		}
		generator.Emit(OpCodes.Ldarg_1);
		generator.UnboxIfNeeded(fieldInfo.FieldType);
		generator.Emit(OpCodes.Stfld, fieldInfo);
		generator.Emit(OpCodes.Ret);
		return (Action<T, Object>)dynamicMethod.CreateDelegate(Action<T, Object>.class);
	}

	/** 
	 创建字段Get方法
	 
	 <typeparam name="T">对象类型</typeparam>
	 @param fieldInfo 字段
	 @return 
	*/
	public static <T> Func<T, Object> CreateFieldGet(java.lang.reflect.Field fieldInfo)
	{
		DynamicMethod dynamicMethod = CreateDynamicMethod("Get" + fieldInfo.getName(), T.class, new Object[] { Object.class }, fieldInfo.ReflectedType);
		ILGenerator generator = dynamicMethod.GetILGenerator();
		if (!fieldInfo.IsStatic)
		{
			generator.PushInstance(fieldInfo.ReflectedType);
		}
		generator.Emit(OpCodes.Ldfld, fieldInfo);
		generator.BoxIfNeeded(fieldInfo.FieldType);
		generator.Emit(OpCodes.Ret);
		return (Func<T, Object>)dynamicMethod.CreateDelegate(Func<T, Object>.class);
	}

	/** 
	 整数压入堆栈方法
	 
	 @param il 指令
	 @param value 值
	*/
//C# TO JAVA CONVERTER TODO TASK: Extension methods are not available in Java:
//ORIGINAL LINE: internal static void EmitInt32(this ILGenerator il, int value)
	public static void EmitInt32(ILGenerator il, int value)
	{
		switch (value)
		{
			case -1:
				il.Emit(OpCodes.Ldc_I4_M1);
				break;
			case 0:
				il.Emit(OpCodes.Ldc_I4_0);
				break;
			case 1:
				il.Emit(OpCodes.Ldc_I4_1);
				break;
			case 2:
				il.Emit(OpCodes.Ldc_I4_2);
				break;
			case 3:
				il.Emit(OpCodes.Ldc_I4_3);
				break;
			case 4:
				il.Emit(OpCodes.Ldc_I4_4);
				break;
			case 5:
				il.Emit(OpCodes.Ldc_I4_5);
				break;
			case 6:
				il.Emit(OpCodes.Ldc_I4_6);
				break;
			case 7:
				il.Emit(OpCodes.Ldc_I4_7);
				break;
			case 8:
				il.Emit(OpCodes.Ldc_I4_8);
				break;
			default:
				if (value >= -128 && value <= 127)
				{
					il.Emit(OpCodes.Ldc_I4_S, (byte)value);
				}
				else
				{
					il.Emit(OpCodes.Ldc_I4, value);
				}
				break;
		}
	}

	/** 
	 压入整数堆栈方法
	 
	 @param index 索引
	 @return 
	*/
	public static OpCode GetLdc_I4(int index)
	{
		if (index == 0)
		{
			return OpCodes.Ldc_I4_0;
		}
		if (index == 1)
		{
			return OpCodes.Ldc_I4_1;
		}
		if (index == 2)
		{
			return OpCodes.Ldc_I4_2;
		}
		if (index == 3)
		{
			return OpCodes.Ldc_I4_3;
		}
		if (index == 4)
		{
			return OpCodes.Ldc_I4_4;
		}
		if (index == 5)
		{
			return OpCodes.Ldc_I4_5;
		}
		if (index == 6)
		{
			return OpCodes.Ldc_I4_6;
		}
		if (index == 7)
		{
			return OpCodes.Ldc_I4_7;
		}
		if (index == 8)
		{
			return OpCodes.Ldc_I4_8;
		}
		if (index > 8)
		{
			return OpCodes.Ldc_I4_S;
		}

		throw new IllegalArgumentException(String.format("未知的  Ldc_I 值 %1$s 。", index));
	}

	/** 
	 根据传入的数据类型，获取数组元素的op
	 
	 @param reflectType 类型
	 @return 
	*/
//C# TO JAVA CONVERTER TODO TASK: Extension methods are not available in Java:
//ORIGINAL LINE: public static OpCode GetLdelem(this Type reflectType)
	public static OpCode GetLdelem(Class reflectType)
	{
		if (!reflectType.IsValueType)
		{
			return OpCodes.Ldelem_Ref;
		}

		if (Byte.class.equals(reflectType))
		{
			return OpCodes.Ldelem_I1;
		}

		if (Short.class.equals(reflectType))
		{
			return OpCodes.Ldelem_I2;
		}

		if (Integer.class.equals(reflectType))
		{
			return OpCodes.Ldelem_I4;
		}

		if (Long.class.equals(reflectType))
		{
			return OpCodes.Ldelem_I8;
		}

		if (Float.class.equals(reflectType))
		{
			return OpCodes.Ldelem_R4;
		}

		if (Double.class.equals(reflectType))
		{
			return OpCodes.Ldelem_R8;
		}

		return OpCodes.Ldelem_Ref;
	}

	/** 
	 判断是否需要装箱
	 
	 @param generator 指令
	 @param type 类型
	*/
//C# TO JAVA CONVERTER TODO TASK: Extension methods are not available in Java:
//ORIGINAL LINE: public static void BoxIfNeeded(this ILGenerator generator, Type type)
	public static void BoxIfNeeded(ILGenerator generator, Class type)
	{
		if (type.IsValueType)
		{
			generator.Emit(OpCodes.Box, type);
		}
		else
		{
			generator.Emit(OpCodes.Castclass, type);
		}
	}

	/** 
	 判断是否需要拆箱
	 
	 @param generator 指令
	 @param type 类型
	*/
//C# TO JAVA CONVERTER TODO TASK: Extension methods are not available in Java:
//ORIGINAL LINE: public static void UnboxIfNeeded(this ILGenerator generator, Type type)
	public static void UnboxIfNeeded(ILGenerator generator, Class type)
	{
		if (type.IsValueType)
		{
			generator.Emit(OpCodes.Unbox_Any, type);
		}
		else
		{
			generator.Emit(OpCodes.Castclass, type);
		}
	}

	/** 
	 压入实例
	 
	 @param generator 指令
	 @param type 类型
	*/
//C# TO JAVA CONVERTER TODO TASK: Extension methods are not available in Java:
//ORIGINAL LINE: public static void PushInstance(this ILGenerator generator, Type type)
	public static void PushInstance(ILGenerator generator, Class type)
	{
		generator.Emit(OpCodes.Ldarg_0);
		if (type.IsValueType)
		{
			generator.Emit(OpCodes.Unbox, type);
		}
		else
		{
			generator.Emit(OpCodes.Castclass, type);
		}
	}

	/** 
	 调用成员
	 
	 @param generator 指令
	 @param methodInfo 方法元素
	*/
//C# TO JAVA CONVERTER TODO TASK: Extension methods are not available in Java:
//ORIGINAL LINE: public static void CallMethod(this ILGenerator generator, MethodInfo methodInfo)
	public static void CallMethod(ILGenerator generator, java.lang.reflect.Method methodInfo)
	{
		if (methodInfo.IsFinal || !methodInfo.IsVirtual)
		{
			generator.Emit(OpCodes.Call, methodInfo);
		}
		else
		{
			generator.Emit(OpCodes.Callvirt, methodInfo);
		}
	}

	/** 
	 将指定索引变量推到当前堆栈上
	 
	 @param generator 指令
	 @param slot 索引(0表示 this)
	*/
//C# TO JAVA CONVERTER TODO TASK: Extension methods are not available in Java:
//ORIGINAL LINE: public static void Ldarg(this ILGenerator generator, int slot)
	public static void Ldarg(ILGenerator generator, int slot)
	{
		switch (slot)
		{
			case 0:
				generator.Emit(OpCodes.Ldarg_0);
				return;
			case 1:
				generator.Emit(OpCodes.Ldarg_1);
				return;
			case 2:
				generator.Emit(OpCodes.Ldarg_2);
				return;
			case 3:
				generator.Emit(OpCodes.Ldarg_3);
				return;
		}
		if (slot <= 0xff)
		{
			generator.Emit(OpCodes.Ldarg_S, slot);
		}
		else
		{
			generator.Emit(OpCodes.Ldarg, slot);
		}
	}

	/** 
	 从当前方法中返回
	 
	 @param generator 指令
	*/
//C# TO JAVA CONVERTER TODO TASK: Extension methods are not available in Java:
//ORIGINAL LINE: public static void Ret(this ILGenerator generator)
	public static void Ret(ILGenerator generator)
	{
		generator.Emit(OpCodes.Ret);
	}

	/** 
	 用新值替换在对象引用或指针的字段中存储的值。
	 
	 @param generator 指令
	 @param field 字段
	*/
//C# TO JAVA CONVERTER TODO TASK: Extension methods are not available in Java:
//ORIGINAL LINE: public static void Stfld(this ILGenerator generator, FieldBuilder field)
	public static void Stfld(ILGenerator generator, FieldBuilder field)
	{
		generator.Emit(OpCodes.Stfld, field);
	}

	/** 
	 查找对象中其引用当前位于计算堆栈的字段的值。
	 
	 @param generator 指令
	 @param field 字段
	*/
//C# TO JAVA CONVERTER TODO TASK: Extension methods are not available in Java:
//ORIGINAL LINE: public static void Ldfld(this ILGenerator generator, FieldBuilder field)
	public static void Ldfld(ILGenerator generator, FieldBuilder field)
	{
		generator.Emit(OpCodes.Ldfld, field);
	}
}