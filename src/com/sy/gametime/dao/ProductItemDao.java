package com.sy.gametime.dao;

import java.util.List;

import com.sy.gametime.pojo.ProductItem;

public interface ProductItemDao {
	public List<ProductItem> getProductItemByCode(String productItemCode);
}
