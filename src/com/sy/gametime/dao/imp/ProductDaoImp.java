
package com.sy.gametime.dao.imp;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.sy.gametime.dao.ProductDao;
import com.sy.gametime.pojo.Product;
import com.sy.gametime.util.MybatisUtil;

public class ProductDaoImp implements ProductDao{

	@Override
	public List<Product> getProducts(Map<String, Object> paraMap) {
		List<Product> products = null;
		SqlSession session = MybatisUtil.currentSession();
		try {
			ProductDao productDao= session.getMapper(ProductDao.class);
			products = productDao.getProducts(paraMap);
        } finally {
        	MybatisUtil.closeSession();
        }
		return products;
	}

	@Override
	public Product getProduct(String productCode) {
		// TODO Auto-generated method stub
		Product product = null;
		SqlSession session = MybatisUtil.currentSession();
		try {
			ProductDao productDao= session.getMapper(ProductDao.class);
			product = productDao.getProduct(productCode);
        } finally {
        	MybatisUtil.closeSession();
        }
		return product;
	}
	
}
