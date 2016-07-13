package com.sy.gametime.dao;

import java.util.List;
import java.util.Map;

import com.sy.gametime.pojo.Product;

public interface ProductDao {
	public List<Product> getProducts(Map<String, Object> paraMap);
	
	public Product getProduct(String productCode);
}
