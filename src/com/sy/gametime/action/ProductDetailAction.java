package com.sy.gametime.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.ActionSupport;
import com.sy.gametime.dao.PictureDao;
import com.sy.gametime.dao.ProductDao;
import com.sy.gametime.dao.ProductItemDao;
import com.sy.gametime.dao.imp.PictureDaoImp;
import com.sy.gametime.dao.imp.ProductDaoImp;
import com.sy.gametime.dao.imp.ProductItemDaoImp;
import com.sy.gametime.pojo.Picture;
import com.sy.gametime.pojo.Product;
import com.sy.gametime.pojo.ProductItem;
import com.sy.gametime.util.CommonUtil;
import com.sy.gametime.util.DicCons;

public class ProductDetailAction extends ActionSupport{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProductDao productDao;
	private PictureDao pictureDao;
	private ProductItemDao productItemDao;
	private Product product;
	private List<Picture> postPicList;
	private List<Picture> cutPicList;
	private List<ProductItem> productItems;
	
	public void getProductDetail() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String webkey = request.getParameter(DicCons.WEB_KEY);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(CommonUtil.checkWebKey(webkey)) {
			//获取游戏基本信息
			String productCode = request.getParameter("Code");
			productDao = new ProductDaoImp();
			product = productDao.getProduct(productCode);
			
			if(product == null) {
				resultMap.put(DicCons.RESULT_CODE, 500);
				resultMap.put(DicCons.RESULT_DESC, "没有数据");
			} else {
				resultMap.put(DicCons.RESULT_CODE, 100);
				resultMap.put(DicCons.RESULT_DESC, "获取数据成功");
//				resultMap.put("Lst", product);
				//压入游戏基本信息
				putProduct(resultMap);
				
				pictureDao = new PictureDaoImp();
				//获取游戏海报路径
				postPicList = pictureDao.getPicture(productCode,"P");
				resultMap.put("Poster", postPicList);
				
				//获取游戏截图路径
				cutPicList = pictureDao.getPicture(productCode,"C");
				resultMap.put("Cut", cutPicList);
				
				//获取游戏商品列表
				productItemDao = new ProductItemDaoImp();
				productItems = productItemDao.getProductItemByCode(productCode);
				resultMap.put("List", productItems);
			}
			
		} else {
			resultMap.put(DicCons.RESULT_CODE, 401);
			resultMap.put(DicCons.RESULT_DESC, "WebKey错误");
		}
		
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().excludeFieldsWithoutExposeAnnotation().create();
		HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding(DicCons.CHARACTER_ENCODE_DEFAULT); 
		response.getWriter().print(gson.toJson(resultMap));
	}
	
	/**
	 * 压入游戏基本信息
	 * @param resultMap
	 */
	private void putProduct(Map<String, Object> resultMap) {
		resultMap.put("Name", product.getProductsName());
		resultMap.put("Desc", product.getProductsDesc());
		resultMap.put("Issue", product.getProductsIssue());
		resultMap.put("Company", product.getProductsCompany());
		resultMap.put("Category", product.getProductsCategoryName());
		resultMap.put("Players", product.getProductsPlayers());
		resultMap.put("Picture", product.getPicture());
	}

}
