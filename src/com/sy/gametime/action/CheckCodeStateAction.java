package com.sy.gametime.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.ActionSupport;
import com.sy.gametime.util.CommonUtil;
import com.sy.gametime.util.DicCons;

public class CheckCodeStateAction extends ActionSupport{

	private static final long serialVersionUID = 1L;

	/**
	 * 获取验证码状态Action
	 */
	public void getCheckCodeState() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String webkey = request.getParameter(DicCons.WEB_KEY);
		
		Map<String, Object> resultMap= new HashMap<String, Object>();
		
		if(CommonUtil.checkWebKey(webkey)){
			Map<String, Object> session = ServletActionContext.getContext().getSession();
			//session.
			if (!session.containsKey(DicCons.CHECK_ERROR_NO))
			{
				session.put(DicCons.CHECK_ERROR_NO, 0);
			}
			int num = (Integer) session.get(DicCons.CHECK_ERROR_NO);
			if(num < 3){
				resultMap.put(DicCons.RESULT_CODE, 100);
				resultMap.put(DicCons.RESULT_DESC, "未超过错误次数");
			} else {
				resultMap.put(DicCons.RESULT_CODE, 50);
				resultMap.put(DicCons.RESULT_DESC, "超出3次");
				String path = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + request.getContextPath();
				Random ran = new Random();
				resultMap.put("CodePicUrl", path + "/GI_GetCheckCode.aspx?" + ran.nextInt(999999));
			}

		} else {
			resultMap.put(DicCons.RESULT_CODE, 501);
			resultMap.put(DicCons.RESULT_DESC, "WebKey错误");
		}
		
		try {
			Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
			HttpServletResponse response = ServletActionContext.getResponse();
	        response.setCharacterEncoding(DicCons.CHARACTER_ENCODE_DEFAULT); 
			response.getWriter().print(gson.toJson(resultMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
