package com.uaijia.entity.enums;

public enum SexEnum {

	MALE("男",0),
	FEMALE("女",1);
	
	private SexEnum(String name,int value) {
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
