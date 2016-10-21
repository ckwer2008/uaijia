package com.uaijia.user.service;

import com.uaijia.entity.User;

public interface UserService {
	
	/**
	 * 用户注册
	 */
	void registerUser(User user);

	/**
	 * 添加用户
	 */
	void addUser(User user);
	
	void editUser();
	
	User findUserById(int id);
	
	/**
	 * 根据用户名查找用户
	 */
	User findUserByUsername(String username);
	
	/**
	 * 是否存在用户名
	 */
	boolean hasExistUsername(String username);
	
	/**
	 * 是否存在手机号
	 */
	boolean hasExistPhone(String mobilephone);
}
