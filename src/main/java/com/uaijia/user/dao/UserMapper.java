package com.uaijia.user.dao;

import com.uaijia.core.db.BaseMapper;
import com.uaijia.entity.User;

public interface UserMapper extends BaseMapper<User>{
	
	User selectByUsername(String username);
	
	int selectCountByUsername(String username);
	
	int selectCountByPhone(String mobilephone);
}