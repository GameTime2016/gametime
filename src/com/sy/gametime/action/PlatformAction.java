package com.sy.gametime.action;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import net.sf.json.JSONObject;
//import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
//import com.google.gson.JsonObject;
import com.opensymphony.xwork2.ActionSupport;
import com.sy.gametime.dao.PlatformDao;
import com.sy.gametime.dao.imp.PlatformDaoImp;
import com.sy.gametime.pojo.Platform;

public class PlatformAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PlatformDao platformDao;
	
	public void getPlatform() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String webkey = request.getParameter("WebKey");
		Map<String, Object> resultMap= new HashMap<String, Object>();
		
		if("websiteKey123".equals(webkey)){
			
			resultMap.put("resultCod", 100);
			resultMap.put("resultDesc", "数据加载成功");
			platformDao = new PlatformDaoImp();
			List<Platform> platform = platformDao.getPlatform();
			resultMap.put("Platform",  platform);
			
		} else {
			resultMap.put("resultCod", 401);
			resultMap.put("resultDesc", "WebKey错误");
		}
		try {
			Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
			//JSONObject json = JSONObject.fromObject(resultMap);
			//JSONArray json = JSONArray.fromObject(resultMap);
			HttpServletResponse response = ServletActionContext.getResponse();
//	        HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);  
	        response.setCharacterEncoding("UTF-8"); 
			response.getWriter().print(gson.toJson(resultMap));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
