package com.uaijia.entity.enums;

public enum RoleEnum{

	NORMAL("普通用户",1);
	
	private RoleEnum(String name,int value){
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
