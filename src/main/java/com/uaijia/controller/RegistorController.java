package com.uaijia.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uaijia.core.util.WebUtil;
import com.uaijia.core.web.BaseController;
import com.uaijia.core.web.vo.AjaxResult;
import com.uaijia.entity.User;
import com.uaijia.entity.enums.LoginStatusEnum;
import com.uaijia.entity.enums.RoleEnum;
import com.uaijia.entity.enums.SexEnum;
import com.uaijia.user.service.UserService;

@Controller
@RequestMapping("/register")
public class RegistorController extends BaseController{
	
	@Resource
	private UserService userService;

	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public AjaxResult registor(ModelMap model,@Valid @ModelAttribute("user")User user,BindingResult result,
			HttpServletRequest request,HttpServletResponse response){
		if(result.hasErrors()) {
			return returnFailue(result.getAllErrors().get(0).getDefaultMessage());
		}
		
		boolean hasExist = userService.hasExistPhone(user.getMobilephone());
		if(hasExist){
			return returnFailue("该手机号已被注册");
		}
		
		//用户注册
		user.setUsername(user.getMobilephone());
		user.setSex(SexEnum.MALE.getValue());
		user.setRole(RoleEnum.NORMAL.getValue());
		user.setState(LoginStatusEnum.COMMON.getValue());
		user.setPassword(WebUtil.encrypt(user.getPassword(), user.getUsername(), false));
		userService.registerUser(user);
		
		return returnSuccess();
		
	}
	
	@RequestMapping(value="/existNotUsername",method=RequestMethod.GET)
	@ResponseBody
	public boolean existUsername(String username){
		return !userService.hasExistUsername(username);
	}
}
