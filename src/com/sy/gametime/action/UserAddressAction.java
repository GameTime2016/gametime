package com.sy.gametime.action;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.sy.gametime.util.DicCons;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.ActionSupport;
import com.sy.gametime.dao.UserAddressDao;
import com.sy.gametime.dao.imp.UserAddressDaoImp;
import com.sy.gametime.pojo.UserAddress;

public class UserAddressAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	private UserAddressDao userAddressDao; 
	private UserAddress userAddress;
//	public UserAddress getUserAddress() {
//		return userAddress;
//	}
//	public void setUserAddress(UserAddress userAddress) {
//		this.userAddress = userAddress;
//	}
	
	public void setUserAddressID()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		String webkey = request.getParameter("WebKey");
		Map<String, Object> resultMap= new HashMap<String, Object>();
		if("websiteKey123".equals(webkey)){
			
			Map<String, Object> sessionMap = ServletActionContext.getContext().getSession(); 
			if(sessionMap.containsKey(DicCons.USER_CODE))
			{   
				
				userAddress=new UserAddress();
				userAddress.setUserAddressID("4");			
				userAddress.setUserCode((String)sessionMap.get(DicCons.USER_CODE));
				userAddress.setAddressDefault("N");
				userAddress.setAddressDesc(request.getParameter("Address"));
				userAddress.setAddressName(request.getParameter("Contact"));
				userAddress.setAddressPhone(request.getParameter("Phone"));
				userAddress.setAddressZipCode("");
				userAddress.setUserAddressDistrictsID("0");
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dt = df.format(new Date());
				userAddress.setAddressCrtDate(dt);
				userAddressDao=new UserAddressDaoImp();
				userAddressDao.setUseraddress(userAddress);
				resultMap.put("resultCod", 100);
				resultMap.put("resultDesc", "数据加载成功");
			} 	
			else
			{
				resultMap.put("resultCod", 0);
				resultMap.put("resultDesc","新建错误");
			}
			
		} else {
			resultMap.put("resultCod", 401);
			resultMap.put("resultDesc", "WebKey错误");
		}
		try {
			Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
			HttpServletResponse response = ServletActionContext.getResponse();
	        response.setCharacterEncoding("UTF-8"); 
			response.getWriter().print(gson.toJson(resultMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void deleteUserAddressID()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		String webkey = request.getParameter("WebKey");
		Map<String, Object> resultMap= new HashMap<String, Object>();
		if("websiteKey123".equals(webkey)){
			
			Map<String, Object> sessionMap = ServletActionContext.getContext().getSession(); 
			if(sessionMap.containsKey(DicCons.USER_CODE))
			{   
				
				userAddress=new UserAddress();
				userAddress.setUserAddressID((String)request.getParameter("ID"));			
/*				userAddress.setUserCode((String)sessionMap.get(DicCons.USER_CODE));
				userAddress.setAddressDefault("N");
				userAddress.setAddressDesc("沈阳市");
				userAddress.setAddressName(request.getParameter("Contact"));
				userAddress.setAddressPhone(request.getParameter("Phone"));
*/			    	
				userAddressDao=new UserAddressDaoImp();
				userAddressDao.deleteUseraddress(userAddress.getUserAddressID());
				resultMap.put("resultCod", 100);
				resultMap.put("resultDesc", "数据加载成功");
			} 	
			else
			{
				resultMap.put("resultCod", 0);
				resultMap.put("resultDesc","删除错误");
			}
			
		} else {
			resultMap.put("resultCod", 401);
			resultMap.put("resultDesc", "WebKey错误");
		}
		try {
			Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
			HttpServletResponse response = ServletActionContext.getResponse();
	        response.setCharacterEncoding("UTF-8"); 
			response.getWriter().print(gson.toJson(resultMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void updateUserAddressID()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		String webkey = request.getParameter("WebKey");
		Map<String, Object> resultMap= new HashMap<String, Object>();
		if("websiteKey123".equals(webkey)){
			
			Map<String, Object> sessionMap = ServletActionContext.getContext().getSession(); 
			if(sessionMap.containsKey(DicCons.USER_CODE))
			{   	
				userAddress=new UserAddress();
				userAddress.setUserAddressID(request.getParameter("ID"));			
				userAddress.setUserCode((String)sessionMap.get(DicCons.USER_CODE));
				userAddress.setAddressDefault("N");
				userAddress.setAddressDesc(request.getParameter("Address"));
				userAddress.setAddressName(request.getParameter("Contact"));
				userAddress.setAddressPhone(request.getParameter("Phone"));
				userAddress.setAddressZipCode("");
				userAddress.setUserAddressDistrictsID("0");
				userAddress.setAddressCrtDate(new java.sql.Date(new java.util.Date().getTime()).toString());
				userAddressDao=new UserAddressDaoImp();
				userAddressDao.updateUseraddress(userAddress);
				resultMap.put("resultCod", 100);
				resultMap.put("resultDesc", "数据加载成功");
			} 	
			else
			{
				resultMap.put("resultCod", 0);
				resultMap.put("resultDesc","更新错误");
			}
			
		} else {
			resultMap.put("resultCod", 401);
			resultMap.put("resultDesc", "WebKey错误");
		}
		try {
			Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
			HttpServletResponse response = ServletActionContext.getResponse();
	        response.setCharacterEncoding("UTF-8"); 
			response.getWriter().print(gson.toJson(resultMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 
	public void selectUserAddressID()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		String webkey = request.getParameter("WebKey");
		Map<String, Object> resultMap= new HashMap<String, Object>();
		
		if("websiteKey123".equals(webkey)){
			
			Map<String, Object> sessionMap = ServletActionContext.getContext().getSession(); 
			if(sessionMap.containsKey(DicCons.USER_CODE))
			{  
				userAddress=new UserAddress();
			    userAddress.setUserCode((String)sessionMap.get(DicCons.USER_CODE));
			    userAddressDao=new UserAddressDaoImp();
			    UserAddress userAddresstemp = userAddressDao.selectUseraddress(userAddress.getUserCode());
				resultMap.put("ID", userAddresstemp.getUserAddressID());
				resultMap.put("Address", userAddresstemp.getAddressDesc());
				resultMap.put("Phone", userAddresstemp.getAddressPhone());
				resultMap.put("Contact", userAddresstemp.getAddressName());
			    resultMap.put("resultCod", 100);
			    resultMap.put("resultDesc", "数据加载成功");			   
			}
			
		} else {
			resultMap.put("resultCod", 401);
			resultMap.put("resultDesc", "WebKey错误");
		}
		try {
			Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
			HttpServletResponse response = ServletActionContext.getResponse();
	        response.setCharacterEncoding("UTF-8"); 
			response.getWriter().print(gson.toJson(resultMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
