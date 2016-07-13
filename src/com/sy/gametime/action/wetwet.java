package com.sy.gametime.action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;

public class wetwet extends ActionSupport{
    
	private static final long serialVersionUID = 1L;

	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
		
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String webkey = request.getParameter("WebKey");
		
		System.out.println(webkey);
		
		int a = 111;
		
		JSONArray jsonArray = JSONArray.fromObject(a);  
        HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);  
        response.setCharacterEncoding("UTF-8"); 
        response.getWriter().print(jsonArray);
		
		return null;
	}

	
	
}
