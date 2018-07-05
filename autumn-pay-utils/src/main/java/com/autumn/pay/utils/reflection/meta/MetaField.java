package com.autumn.pay.utils.reflection.meta;

import GN.Pay.Utils.*;

/** 
 元字段
 
*/
public class MetaField extends MetaMember
{
	/** 
	 实例化 MetaField 类新实例
	 
	 @param fieldInfo 字段
	*/
	public MetaField(java.lang.reflect.Field fieldInfo)
	{
		super(fieldInfo);
		this.setFieldInfo(ExceptionUtils.CheckNotNull(fieldInfo, nameof(fieldInfo)));
		this._GetMemberValue = MethodFactory.<Object>CreateFieldGet(fieldInfo);
		if (fieldInfo.IsInitOnly)
		{
			this._SetMemberValue = null;
		}
		else
		{
			this._SetMemberValue = MethodFactory.<Object>CreateFieldSet(fieldInfo);
		}
	}

	/** 
	 获取字段
	 
	*/
	private java.lang.reflect.Field privateFieldInfo;
	public final java.lang.reflect.Field getFieldInfo()
	{
		return privateFieldInfo;
	}
	private void setFieldInfo(java.lang.reflect.Field value)
	{
		privateFieldInfo = value;
	}

	/** 
	 获取成员类型
	 
	*/
	@Override
	public final Class getMemberType()
	{
		return this.getFieldInfo().FieldType;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !DEBUG
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	
//#endif
	private Func<Object, Object> _GetMemberValue;

	/** 
	 获取成员值
	 
	*/
	@Override
	public Func<Object, Object> getGetMemberValue()
	{
		return this._GetMemberValue;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !DEBUG
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	
//#endif
	private Action<Object, Object> _SetMemberValue;

	/** 
	 设置成员值
	 
	*/
	@Override
	public final Action<Object, Object> getSetMemberValue()
	{
		return this._SetMemberValue;
	}

	/** 
	 获取成员
	 
	*/
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
//#if !DEBUG
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[DebuggerBrowsable(DebuggerBrowsableState.Never)]
	
//#endif
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[Browsable(false), EditorBrowsable(EditorBrowsableState.Never)]
	public final MemberInfo getMemberInfo()
	{
		return this.getFieldInfo();
	}

	/** 
	 获取是否是属性
	 
	*/
	@Override
	public final boolean getIsProperty()
	{
		return false;
	}

	/** 
	 释放
	 
	*/
	@Override
	protected void dispose(boolean isDispose)
	{
		super.dispose(isDispose);
		this._SetMemberValue = null;
		this.setFieldInfo(null);
		this._SetMemberValue = null;
	}
}