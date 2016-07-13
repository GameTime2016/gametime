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
import com.sy.gametime.dao.ProductDao;
import com.sy.gametime.dao.imp.ProductDaoImp;
import com.sy.gametime.pojo.Product;
import com.sy.gametime.util.CommonUtil;
import com.sy.gametime.util.DicCons;

public class ProductAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProductDao productDao;
	private List<Product> products;
	
	public void getProductList() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		Map<String, Object> resultMap= new HashMap<String, Object>();
		
		String webkey = request.getParameter(DicCons.WEB_KEY);
		if(CommonUtil.checkWebKey(webkey)) {
			//获取游戏清单ProductList
			Map<String, Object> paraMap = new HashMap<String, Object>();
			//获取条件参数
//			String category = request.getParameter("Category");
//			String platform = request.getParameter("Platform");
//			String hot = request.getParameter("Hot");
//			String discount = request.getParameter("Discount");
//			String name = request.getParameter("Name");
//			int num = Integer.parseInt(request.getParameter("Number"));
//			int page = Integer.parseInt(request.getParameter("Page"));
			
			paraMap.put("Category", request.getParameter("Category"));
			paraMap.put("Platform", request.getParameter("Platform"));
			paraMap.put("Hot", request.getParameter("Hot"));
			paraMap.put("Discount", request.getParameter("Discount"));
			paraMap.put("Name", request.getParameter("Name"));
			String num = request.getParameter("Number");
			String page = request.getParameter("Page");
			if((num == null || num.equals("")) && (page == null || page.equals(""))) {
				paraMap.put("Start", 0);
				paraMap.put("Count", 10);
			}else if(num != null && !num.equals("") && (page == null || page.equals(""))){
				paraMap.put("Start", 0);
				paraMap.put("Count", Integer.parseInt(num));
			} else {
				int p = Integer.parseInt(page);
				int n = Integer.parseInt(num);
				paraMap.put("Start", (p - 1) * n);
				paraMap.put("Count", n);
			}
			
			productDao = new ProductDaoImp();
			products = productDao.getProducts(paraMap);
			
			if(products == null || products.isEmpty()) {
				resultMap.put(DicCons.RESULT_CODE, 500);
				resultMap.put(DicCons.RESULT_DESC, "没有数据");
			} else {
				resultMap.put(DicCons.RESULT_CODE, 100);
				resultMap.put(DicCons.RESULT_DESC, "获取数据成功");
				resultMap.put("Lst", products);
			}
			
		} else {
			resultMap.put(DicCons.RESULT_CODE, 401);
			resultMap.put(DicCons.RESULT_DESC, "WebKey错误");
		}
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().excludeFieldsWithoutExposeAnnotation().create();
        response.setCharacterEncoding(DicCons.CHARACTER_ENCODE_DEFAULT); 
		response.getWriter().print(gson.toJson(resultMap));
	}
	
}
