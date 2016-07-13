package com.sy.gametime.dao;


import com.sy.gametime.pojo.UserAddress;

public interface UserAddressDao {
	public void setUseraddress(UserAddress userAddress);
	public void deleteUseraddress(String id);
	public void updateUseraddress(UserAddress userAddress);
	public  UserAddress selectUseraddress(String code);
}
