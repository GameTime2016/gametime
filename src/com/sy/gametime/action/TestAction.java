package com.sy.gametime.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.ActionSupport;
import com.sy.gametime.util.CommonUtil;
import com.sy.gametime.util.DicCons;
import com.sy.gametime.util.HttpClientUtil;
import com.sy.gametime.util.UrlBuilder;	

public class TestAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	public void test() throws IOException {
		
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding(DicCons.CHARACTER_ENCODE_DEFAULT); 
        response.setContentType(DicCons.CONTENT_TYPE_DEFAULT);
        
		HttpServletRequest request = ServletActionContext.getRequest();
//		HttpSession session = request.getSession(true);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> sessionMap = ServletActionContext.getContext().getSession();
		String webkey = request.getParameter(DicCons.WEB_KEY);
		if(CommonUtil.checkWebKey(webkey)) {
			//if(sessionMap.containsKey(DicCons.USER_CODE)) {
				
				String userCode = (String) sessionMap.get(DicCons.USER_CODE);
				
//				path.append(DicCons.AND);
//				path.append(DicCons.USER_CODE);
//				path.append(DicCons.EQUAL);
//				sb.append(userCode);
				String s = request.getRequestURL().toString();
				String s2 = request.getServletPath();
				String s3 = request.getRequestURI();
				
				String path = UrlBuilder.getInstance().urlBuideWithAPI(request, userCode, "GI_GnrtBooking");
				String path1 = UrlBuilder.getInstance().urlBuide(request, userCode);
				String path2 = UrlBuilder.getInstance().urlBuide2(request, userCode);
				String result = HttpClientUtil.getInstance().sendHttpPost(path);
				
		        response.getWriter().print(result);
				
			/*} else {
				resultMap.put(DicCons.RESULT_CODE, 402);
				resultMap.put(DicCons.RESULT_DESC, "登录超时");
				response.getWriter().print(gson.toJson(resultMap));
			}*/
		} else {
			resultMap.put(DicCons.RESULT_CODE, 401);
			resultMap.put(DicCons.RESULT_DESC, "WebKey错误");
			response.getWriter().print(gson.toJson(resultMap));
		}
	}
}
