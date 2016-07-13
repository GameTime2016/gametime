package com.sy.gametime.dao.imp;

//import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.sy.gametime.dao.UserAddressDao;
import com.sy.gametime.pojo.UserAddress;
import com.sy.gametime.util.MybatisUtil;

public class UserAddressDaoImp implements UserAddressDao {
	@Override
	public void setUseraddress(UserAddress userAddress)
	{
		SqlSession session = MybatisUtil.currentSession();
		try {
			UserAddressDao userAddressDao=session.getMapper(UserAddressDao.class);
			userAddressDao.setUseraddress(userAddress);
			session.commit();
		}finally {
        	MybatisUtil.closeSession();
        }
	}
	@Override
	public void deleteUseraddress(String id)
	{
		SqlSession session = MybatisUtil.currentSession();
		try {
			UserAddressDao userAddressDao=session.getMapper(UserAddressDao.class);
			userAddressDao.deleteUseraddress(id);
			session.commit();
		}finally {
        	MybatisUtil.closeSession();
        }
	}
	@Override
	public void updateUseraddress(UserAddress userAddress)
	{
		SqlSession session = MybatisUtil.currentSession();
		try {
			UserAddressDao userAddressDao=session.getMapper(UserAddressDao.class);
			userAddressDao.updateUseraddress(userAddress);
			session.commit();
		}finally {
        	MybatisUtil.closeSession();
        }
	}
	@Override
	public UserAddress selectUseraddress(String code)
	{
		UserAddress userAddresstemp=null;
		SqlSession session = MybatisUtil.currentSession();
		try {
			UserAddressDao userAddressDao=session.getMapper(UserAddressDao.class);
			userAddresstemp=userAddressDao.selectUseraddress(code);
		}finally {
        	MybatisUtil.closeSession();
        }
		return userAddresstemp;
	}
	
}
