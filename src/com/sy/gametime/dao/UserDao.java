package com.sy.gametime.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sy.gametime.pojo.User;

public interface UserDao {
	
	public User getUserByAccount(String account);
	
	public User getUserByCode(String code); 

	public Map<String, Object> exitPhoneAndAccount(@Param("Phone")String phone, @Param("Account")String account);
	
	public int getPhone(String phone);

	public int getAccount(String account);
	
	public void addUser(User user);
	
	public void updateUserInfo(User user);
	
	public void updateUserPhone(User user);
	
	public void updateUserPassword(User user);
}
