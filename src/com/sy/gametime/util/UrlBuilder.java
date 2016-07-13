package com.sy.gametime.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class UrlBuilder {
	
	private static UrlBuilder instance = null;
	private StringBuffer path;
	
	public static UrlBuilder getInstance(){
		if (instance == null) {
			instance = new UrlBuilder();
		}
		return instance;
	}
	
	
	/**
	 * 创建请求路径
	 * @param request HttpServletRequest请求
	 * @param userCode 用户编码
	 * @param api 访问接口名称
	 * @return
	 */
	public String urlBuideWithAPI(HttpServletRequest request, String userCode, String api) {
		path = new StringBuffer(request.getScheme());
		path.append("://");
		path.append(request.getServerName());
		path.append(":");
		path.append(request.getServerPort());
		path.append(DicCons.TRADE_PATH);
		path.append(api);
		path.append(".trade?");
		path.append(DicCons.TRADE_KEY);
		
		path.append(DicCons.AND);
	    path.append(DicCons.USER_CODE);
	    path.append(DicCons.EQUAL);
	    path.append(userCode);
		
		paramsBuild(request);
		
		return path.toString();
	}
	
	/**
	 * 创建请求路径
	 * @param request
	 * @param userCode
	 * @return
	 */
	public String urlBuide(HttpServletRequest request, String userCode)
	{
		path = new StringBuffer(request.getScheme());
		path.append("://");
		path.append(request.getServerName());
		path.append(":");
		path.append(request.getServerPort());
		path.append(DicCons.TRADE_PATH);
		path.append(request.getServletPath().replaceAll("aspx", "trade?"));

		path.append(DicCons.TRADE_KEY);
		
		path.append(DicCons.AND);
	    path.append(DicCons.USER_CODE);
	    path.append(DicCons.EQUAL);
	    path.append(userCode);
		
		paramsBuild(request);
		
		return path.toString();
	}

	/**
	 * 创建请求路径
	 * @param request
	 * @param userCode
	 * @return
	 */
	public String urlBuide2(HttpServletRequest request, String userCode)
	{
		path = new StringBuffer(request.getRequestURL().toString().replaceAll(request.getContextPath(), DicCons.TRADE_PATH).replaceAll("aspx", "trade?"));		
		
		path.append(DicCons.TRADE_KEY);
		
		path.append(DicCons.AND);
	    path.append(DicCons.USER_CODE);
	    path.append(DicCons.EQUAL);
	    path.append(userCode);
		
		paramsBuild(request);
		
		return path.toString();
	}
	
	/**
	 * 压入请求参数
	 * @param request
	 */
	private void paramsBuild(HttpServletRequest request) {
		Enumeration<String> em = request.getParameterNames();
		
		while (em.hasMoreElements()) {
			
			String key = em.nextElement();
			String value = request.getParameter(key);
			
			path.append(DicCons.AND);
			path.append(key);
			path.append(DicCons.EQUAL);
			path.append(value);
		}
	}
}
