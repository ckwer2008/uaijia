package com.uaijia.user.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.uaijia.entity.User;
import com.uaijia.user.dao.UserMapper;
import com.uaijia.user.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Resource
	private UserMapper userMapper;
	
	@Override
	public void registerUser(User user) {
		addUser(user);
	}

	@Override
	public void addUser(User user) {
		userMapper.insert(user);
	}

	@Override
	public void editUser() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User findUserById(int id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public User findUserByUsername(String username) {
		return userMapper.selectByUsername(username);
	}

	@Override
	public boolean hasExistUsername(String username) {
		int count = userMapper.selectCountByUsername(username);
		return count >0 ;
	}

	@Override
	public boolean hasExistPhone(String mobilephone) {
		int count = userMapper.selectCountByPhone(mobilephone);
		return count >0 ;
	}

}
