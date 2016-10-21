package com.uaijia.core.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	/**
	 * 设置cookie
	 * @param response
	 * @param name  cookie名字
	 * @param value cookie值
	 * @param maxAge cookie生命周期  以秒为单位
	 */
	public static void addCookie(HttpServletResponse response,String name,String value,int maxAge){
	    Cookie cookie = new Cookie(name,value);
	    cookie.setPath("/");
	    if(maxAge>0)  cookie.setMaxAge(maxAge);
	    response.addCookie(cookie);
	}
	
	/**
	 * 设置cookie
	 * @param response
	 * @param name  cookie名字
	 * @param value cookie值
	 */
	public static void addCookie(HttpServletResponse response,String name,String value){
		addCookie(response, name, value,0);
	}
	
	public static boolean hasCookie(HttpServletRequest request, String key){
		Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组
		if(cookies==null) return false;
		for(Cookie cookie : cookies){
			if(key.equalsIgnoreCase(cookie.getName())) return true;
		}
		return false;
	}
	
	
	public static String getCookie(HttpServletRequest request, String key){
		Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组
		if(cookies==null) return null;
		for(Cookie cookie : cookies){
			if(key.equalsIgnoreCase(cookie.getName())) return cookie.getValue();
		}
		return null;
	}
	
}
