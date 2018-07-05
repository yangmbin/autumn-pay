package com.autumn.pay.utils.reflection.meta;

import GN.Pay.Utils.*;

/** 
 元属性
 
*/
public class MetaProperty extends MetaMember
{
	/** 
	 实例化 MetaProperty 类新实例
	 
	 @param propertyInfo 属性
	*/
	public MetaProperty(PropertyInfo propertyInfo)
	{
		super(propertyInfo);
		this.setPropertyInfo(ExceptionUtils.CheckNotNull(propertyInfo, nameof(propertyInfo)));
		if (this.getPropertyInfo().CanRead)
		{
			this._GetMemberValue = MethodFactory.<Object>CreatePropertyGet(propertyInfo);
		}
		else
		{
			this._GetMemberValue = null;
		}
		if (this.getPropertyInfo().CanWrite)
		{
			this._SetMemberValue = MethodFactory.<Object>CreatePropertySet(propertyInfo);
		}
		else
		{
			this._SetMemberValue = null;
		}
	}

	/** 
	 获取属性信息
	 
	*/
	private PropertyInfo privatePropertyInfo;
	public final PropertyInfo getPropertyInfo()
	{
		return privatePropertyInfo;
	}
	private void setPropertyInfo(PropertyInfo value)
	{
		privatePropertyInfo = value;
	}

	/** 
	 获取成员类型
	 
	*/
	@Override
	public final Class getMemberType()
	{
		return this.getPropertyInfo().PropertyType;
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
	public final Func<Object, Object> getGetMemberValue()
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
		return this.getPropertyInfo();
	}

	/** 
	 获取是否是属性
	 
	*/
	@Override
	public final boolean getIsProperty()
	{
		return true;
	}

	/** 
	 释放
	 
	*/
	@Override
	protected void dispose(boolean isDispose)
	{
		super.dispose(isDispose);
		this._SetMemberValue = null;
		this.setPropertyInfo(null);
		this._SetMemberValue = null;

	}
}