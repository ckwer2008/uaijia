package com.uaijia.core.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.subject.Subject;

import com.uaijia.entity.User;

/**
 * Web工具类
 */
public class WebUtil {
	public static final String PRESENTUSER = "USER_SESSION_KEY";
	public static final String PRESENTUSERNAME = "USERNAME_SESSION_KEY";
	public static final String REFERER = "REFERER_SESSION_KEY";

	/**
	 * 获取当前登录账户
	 */
	public static User getCurrentUser() {
		Object o = SecurityUtils.getSubject().getSession().getAttribute(PRESENTUSER);
		User user = null;
		if (o instanceof User) {
			user = (User) o;
		}
		return user;
	}

	/**
	 * 登录成功后记录账户信息
	 */
	public static void login(User user) {
		SecurityUtils.getSubject().getSession().setAttribute(PRESENTUSER, user);
		SecurityUtils.getSubject().getSession().setAttribute(PRESENTUSERNAME, user.getUsername());
	}

	/**
	 * 退出登录时销毁账户资源
	 */
	public static void logout() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			subject.getSession().stop();
			subject.logout();
		}
	}
	
	/**
	 * 保存最近一次登录未授权访问的url
	 */
	public static void setReferer(HttpServletRequest request) {
		SecurityUtils.getSubject().getSession().setAttribute(REFERER,
				StringUtils.substringAfter(request.getRequestURI(), request.getContextPath()));
	}

	/**
	 * 获取最近一次登录未授权访问的url
	 */
	public static String getReferer() {
		Object o = SecurityUtils.getSubject().getSession().getAttribute(REFERER);
		return o instanceof String ? (String) o : null;
	}

	/**
	 * 哈希加密
	 * 
	 * @param plainText
	 *            明文
	 * @param salt
	 *            盐值
	 * @param isMobile
	 *            是否来自移动端
	 * @return 密文
	 */
	public static String encrypt(String plainText, String salt, boolean isMobile) {
		if(!isMobile) plainText = new Md5Hash(plainText).toString();
		
		return new Sha512Hash(plainText, salt, 1024).toBase64();
	}
	
	
	/**
	 * 设置session key value
	 */
	public static void setSessionVal(String key, Object value){
		if(StringUtils.isBlank(key)) return;
		
		SecurityUtils.getSubject().getSession().setAttribute(key, value);
	}
	
	/**
	 * 获取session string
	 */
	public static String getSessionStringVal(String key){
		if(StringUtils.isBlank(key)) return null;
		
		return (String)SecurityUtils.getSubject().getSession().getAttribute(key);
	}
	
	/**
	 * 获取session Integer
	 */
	public static Integer getSessionIntVal(String key){
		if(StringUtils.isBlank(key)) return null;
		
		String val = getSessionStringVal(key);
		if(val==null) return null;
		if(NumberUtils.isDigits(val)) return Integer.parseInt(val);
		
		throw new NumberFormatException("In Session Integer Type ==  KEY:"+key+",VALUE:"+val);
	}
	
	/**
	 * 获取session Float
	 */
	public static Float getSessionFloatVal(String key){
		if(StringUtils.isBlank(key)) return null;
		
		String val = getSessionStringVal(key);
		if(val==null) return null;
		if(NumberUtils.isNumber(val)) return Float.parseFloat(val);
		
		throw new NumberFormatException("In Session Float Type ==  KEY:"+key+",VALUE:"+val);
	}
	
	/**
	 * 获取session Long
	 */
	public static Long getSessionLongVal(String key){
		if(StringUtils.isBlank(key)) return null;
		
		String val = getSessionStringVal(key);
		if(val==null) return null;
		if(NumberUtils.isDigits(val)) return Long.parseLong(val);
		
		throw new NumberFormatException("In Session Long Type ==  KEY:"+key+",VALUE:"+val);
	}
	
	/**
	 * 获取session Boolean
	 */
	public static Boolean getSessionBooleanVal(String key){
		if(StringUtils.isBlank(key)) return null;
		
		String val = getSessionStringVal(key);
		if(val==null) return null;
		return BooleanUtils.toBoolean(val);
	}
	
	public static void main(String[] args) {
		System.err.println(encrypt("123456", "test", false).length());
	}
}
