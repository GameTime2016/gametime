package com.sy.gametime.dao.imp;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.sy.gametime.dao.ProductItemDao;
import com.sy.gametime.pojo.ProductItem;
import com.sy.gametime.util.MybatisUtil;

public class ProductItemDaoImp implements ProductItemDao {

	@Override
	public List<ProductItem> getProductItemByCode(String productItemCode) {
		// TODO Auto-generated method stub
		List<ProductItem> productItem = null;
		SqlSession session = MybatisUtil.currentSession();
        try {
        	ProductItemDao productItemDao = session.getMapper(ProductItemDao.class);
            productItem = productItemDao.getProductItemByCode(productItemCode);
        } finally {
        	MybatisUtil.closeSession();
        }
		return productItem;
	}

}
