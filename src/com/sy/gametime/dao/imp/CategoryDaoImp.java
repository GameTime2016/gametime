package com.sy.gametime.dao.imp;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.sy.gametime.dao.CategoryDao;
import com.sy.gametime.pojo.Category;
import com.sy.gametime.util.MybatisUtil;

public class CategoryDaoImp implements CategoryDao{

	@Override
	public List<Category> getCategory() {
		List<Category> categorys = null;
		SqlSession session = MybatisUtil.currentSession();
		try {
			CategoryDao categoryDao = session.getMapper(CategoryDao.class);
			categorys = categoryDao.getCategory();
        } finally {
        	MybatisUtil.closeSession();
        }
		return categorys;
	}
	
}
