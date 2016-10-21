package com.uaijia.entity.enums;

public enum LoginStatusEnum {

	COMMON("正常",0),
	LOCK("锁定",1);
	
	private LoginStatusEnum(String name,int value) {
		this.name = name;
		this.value = value;
	}
	
	private String name;
	private int value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
