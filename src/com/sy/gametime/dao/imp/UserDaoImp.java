package com.sy.gametime.dao.imp;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import com.sy.gametime.dao.UserDao;
import com.sy.gametime.pojo.User;
import com.sy.gametime.util.MybatisUtil;

public class UserDaoImp implements UserDao {

	@Override
	public User getUserByCode(String code) {
		// TODO Auto-generated method stub
		User user = null;
		SqlSession session = MybatisUtil.currentSession();
        try {
            UserDao userDao = session.getMapper(UserDao.class);
            user = userDao.getUserByCode(code);
        } finally {
        	MybatisUtil.closeSession();
        }
        return user;
	}

	@Override
	public User getUserByAccount(String account) {
		// TODO Auto-generated method stub
		User user = null;
		SqlSession session = MybatisUtil.currentSession();
        try {
            UserDao userDao = session.getMapper(UserDao.class);
            user = userDao.getUserByAccount(account);
        } finally {
        	MybatisUtil.closeSession();
        }
        return user;
	}

	@Override
	public Map<String, Object> exitPhoneAndAccount(@Param("Phone")String phone, @Param("Account")String account) {
		// TODO Auto-generated method stub
		Map<String, Object> pa = new HashMap<String, Object>();
		SqlSession session = MybatisUtil.currentSession();
        try {
            UserDao userDao = session.getMapper(UserDao.class);
            pa = userDao.exitPhoneAndAccount(phone, account);
        } finally {
        	MybatisUtil.closeSession();
        }
        return pa;
	}

	@Override
	public void addUser(User user) {
		// TODO Auto-generated method stub
		SqlSession session = MybatisUtil.currentSession();
        try {
            UserDao userDao = session.getMapper(UserDao.class);
            userDao.addUser(user);
            session.commit();
        } finally {
        	MybatisUtil.closeSession();
        }
	}

	@Override
	public int getPhone(String phone) {
		// TODO Auto-generated method stub
		int count = 0;
		SqlSession session = MybatisUtil.currentSession();
		try {
            UserDao userDao = session.getMapper(UserDao.class);
            count = userDao.getPhone(phone);
        } finally {
        	MybatisUtil.closeSession();
        }
		return count;
	}
	
	public int getAccount(String account) {
		// TODO Auto-generated method stub
		int count = 0;
		SqlSession session = MybatisUtil.currentSession();
		try {
            UserDao userDao = session.getMapper(UserDao.class);
            count = userDao.getAccount(account);
        } finally {
        	MybatisUtil.closeSession();
        }
		return count;
	}

	@Override
	public void updateUserInfo(User user) {
		// TODO Auto-generated method stub
		SqlSession session = MybatisUtil.currentSession();
        try {
            UserDao userDao = session.getMapper(UserDao.class);
            userDao.updateUserInfo(user);
            session.commit();
        } finally {
        	MybatisUtil.closeSession();
        }
	}

	@Override
	public void updateUserPhone(User user) {
		// TODO Auto-generated method stub
		SqlSession session = MybatisUtil.currentSession();
        try {
            UserDao userDao = session.getMapper(UserDao.class);
            userDao.updateUserPhone(user);
            session.commit();
        } finally {
        	MybatisUtil.closeSession();
        }
	}

	@Override
	public void updateUserPassword(User user) {
		// TODO Auto-generated method stub
		SqlSession session = MybatisUtil.currentSession();
        try {
            UserDao userDao = session.getMapper(UserDao.class);
            userDao.updateUserPassword(user);
            session.commit();
        } finally {
        	MybatisUtil.closeSession();
        }
	}
}
