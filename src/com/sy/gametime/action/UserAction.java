package com.sy.gametime.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.ActionSupport;
import com.sy.gametime.dao.UserDao;
import com.sy.gametime.dao.imp.UserDaoImp;
import com.sy.gametime.pojo.User;
import com.sy.gametime.util.CommonUtil;
import com.sy.gametime.util.DicCons;
import com.sy.gametime.util.SMSCons;

public class UserAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private UserDao userDao;
	private User user;
	
	/**
	 * 更改用基本信息
	 * @throws IOException
	 */
	public void updateUserInfo() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		String webkey = request.getParameter(DicCons.WEB_KEY);
		
		Map<String, Object> resultMap= new HashMap<String, Object>();
		
		if(CommonUtil.checkWebKey(webkey)){
			String account = request.getParameter("Account");
			userDao = new UserDaoImp();
			user = userDao.getUserByAccount(account);
			if (user == null) {
				resultMap.put(DicCons.RESULT_CODE, 0);
				resultMap.put(DicCons.RESULT_DESC, "更新失败");
			} else {
				user.setUserNickName(request.getParameter("NickNam"));
				user.setUserSex(request.getParameter("Sex"));
				user.setUserBirthday(request.getParameter("Birthday"));
				user.setUserName(request.getParameter("UserName"));
				user.setUserIDCard(request.getParameter("IDCard"));
				user.setUserCreditRating(request.getParameter("Credit"));
				user.setUserAddress(request.getParameter("Address"));
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dt = df.format(new Date());
				user.setUserUpdDate(dt);
				userDao.updateUserInfo(user);
				resultMap.put(DicCons.RESULT_CODE, 100);
				resultMap.put(DicCons.RESULT_DESC, "更新成功");
			}
		} else {
			resultMap.put(DicCons.RESULT_CODE, 401);
			resultMap.put(DicCons.RESULT_DESC, "WebKey错误");
		}
		
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding(DicCons.CHARACTER_ENCODE_DEFAULT); 
		response.getWriter().print(gson.toJson(resultMap));
	}
	
	
	/**
	 * 更改用户手机
	 * @throws IOException
	 */
	public void updateUserPhone() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String webkey = request.getParameter(DicCons.WEB_KEY);
		
		Map<String, Object> resultMap= new HashMap<String, Object>();
		
		if(CommonUtil.checkWebKey(webkey)){
			String account = request.getParameter("Account");
			String phone = request.getParameter("Phone");
			String smsCode = request.getParameter("SMSCode");
			//获取session中的手机号和短信验证码信息
			Map<String, Object> sessionMap = ServletActionContext.getContext().getSession();
			String smsC = (String) sessionMap.get(SMSCons.SMS_CODE);
			String smsPhone = (String) sessionMap.get(SMSCons.SMS_PHONE);
			if(smsPhone.equals(phone)) {
				if(smsC.equals(smsCode)) {
					userDao = new UserDaoImp();
					user = userDao.getUserByAccount(smsPhone);
					if (user == null) {
						user.setUserAcount(account);
						user.setUserPhone(smsPhone);
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String dt = df.format(new Date());
						user.setUserUpdDate(dt);
						userDao.updateUserPhone(user);
						resultMap.put(DicCons.RESULT_CODE, 100);
						resultMap.put(DicCons.RESULT_DESC, "更改成功");
					} else {
						resultMap.put(DicCons.RESULT_CODE, 3);
						resultMap.put(DicCons.RESULT_DESC, "手机已被绑定");
					}
				} else {
					resultMap.put(DicCons.RESULT_CODE, 2);
					resultMap.put(DicCons.RESULT_DESC, "短信验证码不正确");
				}
			} else {
				resultMap.put(DicCons.RESULT_CODE, 1);
				resultMap.put(DicCons.RESULT_DESC, "手机号与发送短信验证码手机号不一致");
			}
		} else {
			resultMap.put(DicCons.RESULT_CODE, 401);
			resultMap.put(DicCons.RESULT_DESC, "WebKey错误");
		}
		
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding(DicCons.CHARACTER_ENCODE_DEFAULT); 
		response.getWriter().print(gson.toJson(resultMap));
	}
	
	/**
	 * 更改用户密码
	 * @throws IOException
	 */
	public void updateUserPassword() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		String webkey = request.getParameter(DicCons.WEB_KEY);
		
		Map<String, Object> resultMap= new HashMap<String, Object>();
		
		if(CommonUtil.checkWebKey(webkey)){
			String account = request.getParameter("Account");
			String password = request.getParameter("NewPassword");
			String smsCode = request.getParameter("SMSCode");
			Map<String, Object> sessionMap = ServletActionContext.getContext().getSession();
			String smsC = (String) sessionMap.get(SMSCons.SMS_CODE);
			if(smsC.equals(smsCode)) {
				userDao = new UserDaoImp();
				user = userDao.getUserByAccount(account);
				if(user.getUserPassword().equals(password)) {
					resultMap.put(DicCons.RESULT_CODE, 50);
					resultMap.put(DicCons.RESULT_DESC, "新密码不能与原密码一致");
				} else {
					user.setUserPassword(password);
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String dt = df.format(new Date());
					user.setUserUpdDate(dt);
					userDao.updateUserPassword(user);
				}
			} else {
				resultMap.put(DicCons.RESULT_CODE, 1);
				resultMap.put(DicCons.RESULT_DESC, "短信验证码错误");
			}
		} else {
			resultMap.put(DicCons.RESULT_CODE, 401);
			resultMap.put(DicCons.RESULT_DESC, "WebKey错误");
		}
		
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding(DicCons.CHARACTER_ENCODE_DEFAULT); 
		response.getWriter().print(gson.toJson(resultMap));
	}
}
