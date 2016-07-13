package com.sy.gametime.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.ActionSupport;
import com.sy.gametime.dao.UserDao;
import com.sy.gametime.dao.imp.UserDaoImp;
import com.sy.gametime.pojo.User;
import com.sy.gametime.util.CommonUtil;
import com.sy.gametime.util.DicCons;
import com.sy.gametime.util.GUID;
import com.sy.gametime.util.SMSCons;

public class AuthAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
	private User user;
	
	/**
	 * 用户注册
	 * @throws IOException
	 */
	public void register() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession(true);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String webkey = request.getParameter(DicCons.WEB_KEY);
		if(CommonUtil.checkWebKey(webkey)) {
			String account = request.getParameter("Account");
			String phone = request.getParameter("Phone");
			String smsCode = request.getParameter("SMSCode");
			Map<String, Object> sessionMap = ServletActionContext.getContext().getSession();
			String smsC = (String) sessionMap.get(SMSCons.SMS_CODE);
			String smsPhone = (String) sessionMap.get(SMSCons.SMS_PHONE);
			//判断注册手机号码和发送验证码手机号码是否一致
			if(smsPhone.equals(phone)) {
				//判断短信验证码是否正确
				if(smsC.equalsIgnoreCase(smsCode)) {
					userDao = new UserDaoImp();
					user = new User();
					Map<String, Object> ap = userDao.exitPhoneAndAccount(phone, account);
					//判断账户是否存在
					if((Integer)ap.get("Account") > 0) {
						resultMap.put(DicCons.RESULT_CODE, 1);
						resultMap.put(DicCons.RESULT_DESC, "帐号重复不能注册");
					//判断手机号是否存在
					}else if ((Integer)ap.get("Phone") > 0 && (Integer)ap.get("Account") == 0) {
						resultMap.put(DicCons.RESULT_CODE, 2);
						resultMap.put(DicCons.RESULT_DESC, "手机号重复不能注册");
					}else {
						String password = request.getParameter("Password");
						String userCode = GUID.getUUID();
						session.setAttribute(DicCons.USER_CODE, userCode);
						
						user.setUserCode(userCode);
						user.setUserAcount(account);
						user.setUserPhone(phone);
						user.setUserPassword(password);
						user.setUserCreditRating("0");
						user.setUserLockState("0");
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String dt = df.format(new Date());
						user.setUserCrtDate(dt);
						user.setUserUpdDate(dt);
						userDao.addUser(user);
						
						resultMap.put(DicCons.RESULT_CODE, 100);
						resultMap.put(DicCons.RESULT_DESC, "注册成功");
					}
				} else {
					resultMap.put(DicCons.RESULT_CODE, 4);
					resultMap.put(DicCons.RESULT_DESC, "短信验证码不正确");
				}
			} else {
				resultMap.put(DicCons.RESULT_CODE, 5);
				resultMap.put(DicCons.RESULT_DESC, "注册手机号与验证码手机号不一致");
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
	 * 登录
	 * @throws IOException 
	 */
	public void login() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession(true);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String webkey = request.getParameter(DicCons.WEB_KEY);
		if(CommonUtil.checkWebKey(webkey)) {
			String account = request.getParameter("Account");
			String password = request.getParameter("Password");
			String checkcode = request.getParameter("ChkCode");
			Map<String, Object> sessionMap = ServletActionContext.getContext().getSession();
			userDao = new UserDaoImp();
			user = userDao.getUserByAccount(account);
			//密码验证
			if(checkPassWord(password)) {
				//判断账户名密码是否错误3次
				if(sessionMap.containsKey(DicCons.CHECK_ERROR_NO)) {
					int checkErrorNo = (Integer)sessionMap.get(DicCons.CHECK_ERROR_NO);
					if(checkErrorNo > 3) {
						//验证码验证
						if(checkCheckCode(sessionMap, checkcode)) {
							//返回登陆成功结果
							putSucceedResult(resultMap);
							session.setAttribute(DicCons.USER_CODE, user.getUserCode());
						} else {
							//返回验证码错误结果
							resultMap.put(DicCons.RESULT_CODE, 40);
							resultMap.put(DicCons.RESULT_DESC, "验证码错误");
						}
					} else if (checkErrorNo == 3) {
						//返回登陆3次账户密码错误
						resultMap.put(DicCons.RESULT_CODE, 50);
						resultMap.put(DicCons.RESULT_DESC, "登陆3次账号或密码错误");
					} else {
						//返回登陆成功结果
						putSucceedResult(resultMap);
						session.setAttribute(DicCons.USER_CODE, user.getUserCode());
					}
				} else {
					//返回登陆成功结果
					putSucceedResult(resultMap);
					session.setAttribute(DicCons.USER_CODE, user.getUserCode());
				}
			} else {
				if(sessionMap.containsKey(DicCons.CHECK_ERROR_NO)) {
					//登陆错误次数＋1
					int errorNo = (Integer)sessionMap.get(DicCons.CHECK_ERROR_NO);
					if(errorNo >= 3) {
						resultMap.put(DicCons.RESULT_CODE, 50);
						resultMap.put(DicCons.RESULT_DESC, "登陆3次账号或密码错误");
					} else {
						resultMap.put(DicCons.RESULT_CODE, 0);
						resultMap.put(DicCons.RESULT_DESC, "登陆失败，账号或密码错误");
					}
					session.setAttribute(DicCons.CHECK_ERROR_NO,  errorNo + 1);
				} else {
					//若没有登陆次数则在session中加入登陆次数属性，属性值为1
					session.setAttribute(DicCons.CHECK_ERROR_NO, 1);
					resultMap.put(DicCons.RESULT_CODE, 0);
					resultMap.put(DicCons.RESULT_DESC, "登陆失败，账号或密码错误");
				}
			}
		} else {
			resultMap.put(DicCons.RESULT_CODE, 401);
			resultMap.put(DicCons.RESULT_DESC, "WebKey错误");
		}
//		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().enableComplexMapKeySerialization().create();
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding(DicCons.CHARACTER_ENCODE_DEFAULT); 
		response.getWriter().print(gson.toJson(resultMap));
	}
	
	/**
	 * 获取客户session(用户信息)
	 * @throws IOException 
	 */
	public void getLoginInfo() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession(true);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String webkey = request.getParameter(DicCons.WEB_KEY);
		if(CommonUtil.checkWebKey(webkey)) {
			Map<String, Object> sessionMap = ServletActionContext.getContext().getSession();
			if(sessionMap.containsKey(DicCons.USER_CODE)) {
				userDao = new UserDaoImp();
				user = userDao.getUserByCode((String)sessionMap.get(DicCons.USER_CODE));
				if(user == null) {
					resultMap.put(DicCons.RESULT_CODE, 0);
					resultMap.put(DicCons.RESULT_DESC, "数据获取失败");
				} else {
					resultMap.put(DicCons.RESULT_CODE, 100);
					resultMap.put(DicCons.RESULT_DESC, "数据获取成功");
					putUserInfo(resultMap);
					resultMap.put("SessionId", session.getId());
				}
			} else {
				resultMap.put(DicCons.RESULT_CODE, 0);
				resultMap.put(DicCons.RESULT_DESC, "数据获取失败");
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
	 * 获取用户权限(是否登陆)
	 * @throws IOException
	 */
	public void getUserRight() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String webkey = request.getParameter(DicCons.WEB_KEY);
		if(CommonUtil.checkWebKey(webkey)) {
			Map<String, Object> sessionMap = ServletActionContext.getContext().getSession();
			if(sessionMap.containsKey(DicCons.USER_CODE)) {
				resultMap.put(DicCons.RESULT_CODE, 100);
				resultMap.put(DicCons.RESULT_DESC, "member");
			} else {
				resultMap.put(DicCons.RESULT_CODE, 50);
				resultMap.put(DicCons.RESULT_DESC, "guest");
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
	 * 验证码验证
	 * @param session
	 * @return
	 */
	private boolean checkCheckCode(Map<String, Object> sessionMap, String checkcode) {
		if(sessionMap.containsKey(DicCons.CHECK_CODE)&&checkcode!=null&&checkcode!="") {
			String cc = (String) sessionMap.get(DicCons.CHECK_CODE);
			return  cc.equalsIgnoreCase(checkcode);
		} else {
			return false;
		}
			
	}
	
	/**
	 * 密码验证
	 * @return
	 */
	private boolean checkPassWord(String password) {
		if(user == null)
			return false;
		else
			return password.equals(user.getUserPassword());
	}
	
	/**
	 * 压入登陆成功结果
	 * @param resultMap
	 */
	private void putSucceedResult(Map<String, Object> resultMap) {
		resultMap.put(DicCons.RESULT_CODE, 100);
		resultMap.put(DicCons.RESULT_DESC, "登陆成功");
		putUserInfo(resultMap);
	}
	
	/**
	 * 压入用户信息
	 * @param resultMap
	 */
	private void putUserInfo(Map<String, Object> resultMap) {
		resultMap.put("Account", user.getUserAcount());
		resultMap.put("NickNam", user.getUserNickName());
		resultMap.put("Phone", user.getUserPhone());
		resultMap.put("Email", user.getUserEmail());
		resultMap.put("Sex", user.getUserSex());
		resultMap.put("Birthday", user.getUserBirthday());
		resultMap.put("UserName", user.getUserName());
		resultMap.put("IDCard", user.getUserIDCard());
		resultMap.put("Credit", user.getUserCreditRating());
		resultMap.put("Address", user.getUserAddress());
	}
	
	/**
	 * 退出
	 * @throws IOException 
	 */
	public void logout() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession(true);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String webkey = request.getParameter(DicCons.WEB_KEY);
		if(CommonUtil.checkWebKey(webkey)) {
			Map<String, Object> sessionMap = ServletActionContext.getContext().getSession();
			if(sessionMap.containsKey(DicCons.USER_CODE)) {
				session.invalidate();
				resultMap.put(DicCons.RESULT_CODE, 100);
				resultMap.put(DicCons.RESULT_DESC, "退出成功");
			} else {
				resultMap.put(DicCons.RESULT_CODE, 0);
				resultMap.put(DicCons.RESULT_DESC, "退出失败");
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
