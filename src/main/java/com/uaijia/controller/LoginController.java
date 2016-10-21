package com.uaijia.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uaijia.core.util.WebUtil;
import com.uaijia.core.web.BaseController;
import com.uaijia.core.web.vo.AjaxResult;
import com.uaijia.entity.User;
import com.uaijia.user.service.UserService;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController{
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Resource
	private UserService userService;
	
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public AjaxResult login(ModelMap model,String username,String password,HttpServletRequest request,HttpServletResponse response){
		UsernamePasswordToken token = new UsernamePasswordToken(username,new Md5Hash(password).toString() );
		try {
			SecurityUtils.getSubject().login(token);
		} catch (LockedAccountException e) {
			logger.info(e.getMessage(),e);
			return returnFailue("账户已停用");
		} catch (AuthenticationException e) {
			logger.info(e.getMessage(),e);
			return returnFailue("用户名密码错误");
		}
		User user = userService.findUserByUsername(username);
		WebUtil.login(user);
		
		return returnSuccess();
		
	}
	
	/**
	 * 退出登录。
	 */
	@RequestMapping(value = "/logout")
	public String logout(ModelMap model,HttpServletRequest request,HttpServletResponse response){
		WebUtil.logout();
		
		return "redirect:/index";
	}
	
//	@RequestMapping(value = "/getInfo")
//	@ResponseBody
//	public AjaxResult getUserInfo(String ryid){
//		RongyunUser rongyunUser = new RongyunUser();
//		if(StringUtils.isBlank(ryid)) {
//			return returnFailue(null);
//		}
//		
//		if(ryid.startsWith(Constant.RY_USER_ZUI)){
//			Integer uid = rongyuService.findUidByRyid(ryid);
//			if(uid==null) return returnFailue(null);
//			
//			User user = userService.findUserById(uid);
//			rongyunUser.setUsername(user.getUsername());
//		}else if(ryid.startsWith(Constant.RY_GUEST_ZUI)){
//			rongyunUser.setUsername("游客"+ryid.substring(6));
//		}else{
//			return returnFailue(null);
//		}
//		
//		
//		return returnSuccess(rongyunUser);
//	}
	
	
	
}
