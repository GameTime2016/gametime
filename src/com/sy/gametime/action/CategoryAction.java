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
import com.sy.gametime.dao.CategoryDao;
import com.sy.gametime.dao.imp.CategoryDaoImp;
import com.sy.gametime.pojo.Category;
import com.sy.gametime.util.CommonUtil;
import com.sy.gametime.util.DicCons;

public class CategoryAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CategoryDao categoryDao;
	
	public void getCategory() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String webkey = request.getParameter(DicCons.WEB_KEY);
		
		Map<String, Object> resultMap= new HashMap<String, Object>();
		
		if(CommonUtil.checkWebKey(webkey)){
			resultMap.put(DicCons.RESULT_CODE, 100);
			resultMap.put(DicCons.RESULT_DESC, "数据加载成功");
			categoryDao = new CategoryDaoImp();
			List<Category> categorys = categoryDao.getCategory();
			resultMap.put("Category", categorys);
		} else {
			resultMap.put(DicCons.RESULT_CODE, 401);
			resultMap.put(DicCons.RESULT_DESC, "WebKey错误");
		}
		
		try {
			Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
			HttpServletResponse response = ServletActionContext.getResponse();
	        response.setCharacterEncoding(DicCons.CHARACTER_ENCODE_DEFAULT); 
			response.getWriter().print(gson.toJson(resultMap));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
