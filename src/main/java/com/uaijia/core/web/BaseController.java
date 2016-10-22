package com.uaijia.core.web;

import com.uaijia.config.ResultConstant;
import com.uaijia.core.web.vo.AjaxResult;

public class BaseController {

	public AjaxResult returnSuccess(){
		return new AjaxResult(ResultConstant.CODE_SUCCESS);
	}
	
	public AjaxResult returnSuccess(Object obj){
		return new AjaxResult(obj);
	}

	public AjaxResult returnFailue(){
		return new AjaxResult(ResultConstant.CODE_FAILURE);
	}

	public AjaxResult returnFailue(String message){
		return new AjaxResult(ResultConstant.CODE_FAILURE,message);
	}
	
	public AjaxResult returnFailue(String code, String message){
		return new AjaxResult(code,message);
	}
}
