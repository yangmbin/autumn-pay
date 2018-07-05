package com.autumn.pay.utils.reflection.meta;

import GN.Pay.Utils.*;

/** 
 元成员
 
*/
public abstract class MetaMember implements IDisposable
{
	/** 
	 实例化 MetaMember 类新实例
	 
	 @param memberInfo 成员
	*/
	protected MetaMember(MemberInfo memberInfo)
	{
		this.setMemberInfo(ExceptionUtils.CheckNotNull(memberInfo, nameof(memberInfo)));
		java.util.ArrayList<Attribute> atts = new java.util.ArrayList<Attribute>();
		java.util.ArrayList<ValidationAttribute> vatts = new java.util.ArrayList<ValidationAttribute>();
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
		var customs = this.getMemberInfo().getAnnotations();
		DisplayAttribute display = null;
		if (customs != null)
		{
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
			for (var item : customs)
			{
				atts.add((Attribute)item);
				if (item instanceof ValidationAttribute)
				{
					vatts.add((ValidationAttribute)item);
				}
				if (item instanceof getDisplayAttribute())
				{
					display = (DisplayAttribute)item;
				}
			}
		}
		if (display == null)
		{
			DisplayAttribute tempVar = new DisplayAttribute();
			tempVar.Name = memberInfo.getName();
			display = tempVar;
		}
		else
		{
			if (String.IsNullOrWhiteSpace(display.getName()))
			{
				display.setName(memberInfo.getName());
			}
		}
		this.setCustomAttributes(atts.AsReadOnly());
		this.setValidationAttributes(vatts.AsReadOnly());
		this.setDisplayAttribute(display);
	}

	/** 
	 获取友好特性
	 
	*/
	private DisplayAttribute privateDisplayAttribute;
	public final DisplayAttribute getDisplayAttribute()
	{
		return privateDisplayAttribute;
	}
	private void setDisplayAttribute(DisplayAttribute value)
	{
		privateDisplayAttribute = value;
	}

	/** 
	 获取成员
	 
	*/
	private MemberInfo privateMemberInfo;
	public final MemberInfo getMemberInfo()
	{
		return privateMemberInfo;
	}
	private void setMemberInfo(MemberInfo value)
	{
		privateMemberInfo = value;
	}

	/** 
	 获取成员类型
	 
	*/
	public abstract Class getMemberType();

	/** 
	 获取是否是属性
	 
	*/
	public abstract boolean getIsProperty();

	/** 
	 获取成员值
	 
	*/
	public abstract Func<Object, Object> getGetMemberValue();

	/** 
	 设置成员值
	 
	*/
	public abstract Action<Object, Object> getSetMemberValue();

	/** 
	 获取自定义特性集合
	 
	*/
	private ReadOnlyCollection<Attribute> privateCustomAttributes;
	public final ReadOnlyCollection<Attribute> getCustomAttributes()
	{
		return privateCustomAttributes;
	}
	private void setCustomAttributes(ReadOnlyCollection<Attribute> value)
	{
		privateCustomAttributes = value;
	}

	/** 
	 获取验证特性集合
	 
	*/
	private ReadOnlyCollection<ValidationAttribute> privateValidationAttributes;
	public final ReadOnlyCollection<ValidationAttribute> getValidationAttributes()
	{
		return privateValidationAttributes;
	}
	private void setValidationAttributes(ReadOnlyCollection<ValidationAttribute> value)
	{
		privateValidationAttributes = value;
	}

	/** 
	 响应成员名称
	 
	 @return 
	*/
	@Override
	public final String toString()
	{
		return this.getMemberInfo().getName();
	}

	/** 
	 释放
	 
	*/
	public final void dispose()
	{
		dispose(true);
	}

	protected void dispose(boolean isDispose)
	{
		this.setMemberInfo(null);
		this.setCustomAttributes(null);
		this.setValidationAttributes(null);
	}
}