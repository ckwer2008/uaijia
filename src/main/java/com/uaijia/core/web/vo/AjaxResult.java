package com.uaijia.core.web.vo;

import java.io.Serializable;

import com.uaijia.config.ResultConstant;

public class AjaxResult implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String code;
	private String message="";
	private Object obj;
	
	public AjaxResult() {
	}

	public AjaxResult(String code){
		this.code = code;
	}
	
	public AjaxResult(String code,String msg){
		this.code = code;
		this.message = msg;
	}
	
	public AjaxResult(Object obj){
		this.code = ResultConstant.CODE_SUCCESS;
		this.obj = obj;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
}
