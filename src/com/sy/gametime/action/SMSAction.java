package com.sy.gametime.action;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.patchca.word.RandomWordFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.ActionSupport;
import com.sy.gametime.dao.UserDao;
import com.sy.gametime.dao.imp.UserDaoImp;
import com.sy.gametime.util.CommonUtil;
import com.sy.gametime.util.DicCons;
import com.sy.gametime.util.SMSCons;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class SMSAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
	
	public void sms() throws IOException, ApiException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession(true);
		String webkey = request.getParameter(DicCons.WEB_KEY);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(CommonUtil.checkWebKey(webkey)) {
			//获取手机号码
			String phone = request.getParameter("Phone");
			//判断手机号是否注册
			userDao = new UserDaoImp();
			int count = userDao.getPhone(phone);
			if(count > 0) {
				resultMap.put(DicCons.RESULT_CODE, 30);
				resultMap.put(DicCons.RESULT_DESC, "手机号已注册");
			} else {
				Map<String, Object> sessionMap = ServletActionContext.getContext().getSession();
				if(sessionMap.containsKey(SMSCons.SMS_NO)) {
					//判断发送次数
					int smsNo = (Integer)sessionMap.get(SMSCons.SMS_NO);
					if(smsNo >= 0 && smsNo <= 3 ) {
						Date dt = (Date) sessionMap.get(SMSCons.SMS_TIME);
						Date cd = new Date();
						long i = cd.getTime() - dt.getTime();
						//判断两次发送时间间隔
						if (i < 6000) {
							resultMap.put(DicCons.RESULT_CODE, 10);
							resultMap.put(DicCons.RESULT_DESC, "未到发送时间");
						} else {
							session.setAttribute(SMSCons.SMS_NO, smsNo + 1);
							session.setAttribute(SMSCons.SMS_TIME, new Date());
							//发送短信验证码
							alisms(session, phone, resultMap);
						}
					} else {
						resultMap.put(DicCons.RESULT_CODE, 20);
						resultMap.put(DicCons.RESULT_DESC, "超出发送次数");
					}
				} else {
					session.setAttribute(SMSCons.SMS_NO, 1);
					session.setAttribute(SMSCons.SMS_TIME, new Date());
					//发送短信验证码
					alisms(session, phone, resultMap);
				}
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
	 * 阿里大鱼短信验证码
	 * @param session
	 * @param phone
	 * @param resultMap
	 * @throws ApiException
	 */
	private void alisms(HttpSession session, String phone, Map<String, Object> resultMap) throws ApiException {
		//生成6位随机验证码
		RandomWordFactory rwf = new RandomWordFactory();
		rwf.setCharacters(DicCons.ALPHANUM);
		String checkcode = rwf.getNextWord();
		//将验证码放入session
		session.setAttribute(SMSCons.SMS_CODE, checkcode);
		//将手机号放入session
		session.setAttribute("Phone", phone);
		//生成大鱼请求
		TaobaoClient client = new DefaultTaobaoClient(SMSCons.SMS_URL, SMSCons.SMS_APPKEY, SMSCons.SMS_SECRET);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		//配置大鱼请求参数
		req.setSmsType(SMSCons.SMS_TYPE);
		req.setSmsFreeSignName(SMSCons.SMS_FREE_SIGN_NAME_REGISTER_CHECK);
		req.setSmsParamString("{\"code\":\""+ checkcode +"\",\"product\":\"gametime\"}");
		req.setRecNum(phone);
		req.setSmsTemplateCode(SMSCons.SMS_TEMPLATE_CODE_REGISTER_CHECK);
		//发送请求
		AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//			System.out.println(rsp.getBody());
		if(rsp.isSuccess()) {
			resultMap.put(DicCons.RESULT_CODE, 100);
			resultMap.put(DicCons.RESULT_DESC, "发送成功");
		} else {
			resultMap.put(DicCons.RESULT_CODE, 0);
			resultMap.put(DicCons.RESULT_DESC, "发送失败");
		}
	}
}
