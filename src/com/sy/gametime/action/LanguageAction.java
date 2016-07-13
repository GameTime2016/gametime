package com.sy.gametime.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.sy.gametime.dao.LanguageDao;
import com.sy.gametime.dao.imp.LanguageDaoImp;
import com.sy.gametime.pojo.Language;

public class LanguageAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	private LanguageDao languageDao;
	
	public void getLanguage() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String webkey = request.getParameter("WebKey");
		
		Map<String, Object> resultMap= new HashMap<String, Object>();
		System.out.println(webkey);
		if("websiteKey123".equals(webkey)){
			resultMap.put("resultCod", 100);
			resultMap.put("resultDesc", "数据加载成功");
			languageDao = new LanguageDaoImp();
			List<Language> language = languageDao.getLanguage();
			resultMap.put("Language", language);
		} else {
			resultMap.put("resultCod", 401);
			resultMap.put("resultDesc", "WebKey错误");
		}
		
		
		try {
			JSONObject json = JSONObject.fromObject(resultMap);
			HttpServletResponse response = ServletActionContext.getResponse();
//	        HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);  
	        response.setCharacterEncoding("UTF-8"); 
			response.getWriter().print(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
