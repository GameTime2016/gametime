package com.sy.gametime.action;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import org.apache.struts2.ServletActionContext;
import com.sy.gametime.pojo.Product;
import com.sy.gametime.dao.ProductDao;
import com.sy.gametime.dao.imp.ProductDaoImp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.ActionSupport;
import com.sy.gametime.dao.CollectorsDao;
import com.sy.gametime.dao.imp.CollectorsDaoImp;
import com.sy.gametime.pojo.Collectors;
import com.sy.gametime.util.DicCons;
import com.sy.gametime.util.GUID;
public class CollectorsAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	private CollectorsDao collectorsDao;
	private ProductDao  productDao;
	private Collectors   collectors;
	private Product     product;
	public void setAddCollectorsID(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String webkey = request.getParameter("WebKey");
		HttpSession session = request.getSession(true);
		Map<String, Object> resultMap= new HashMap<String, Object>();
		if("websiteKey123".equals(webkey)){
			Map<String, Object> sessionMap = ServletActionContext.getContext().getSession();	
	
			if(sessionMap.containsKey(DicCons.USER_CODE))
			{
				if(session.getMaxInactiveInterval()>20*3600)
				{
					resultMap.put("resultCod", 502);
					resultMap.put("resultDesc", "登录超时");
				}
				else
				{
					collectors=new Collectors();
					collectors.setCollectorsCode((String)GUID.getUUID());
					collectors.setCollectorsUserCode((String)sessionMap.get(DicCons.USER_CODE));
					collectors.setCollectorsPrdCode(request.getParameter("Code"));
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String dt = df.format(new Date());
					collectors.setCollectorsCrtDate(dt);
					collectors.setCollectorsNote("");
					productDao =new ProductDaoImp();
					product=productDao.getProduct(collectors.getCollectorsPrdCode());
					if(product==null)
					{
						resultMap.put("resultCod", 20);
						resultMap.put("resultDesc", "没有这个游戏");
					}
					else
					{
						int i=0;
						collectorsDao=new CollectorsDaoImp();
						List<Collectors> collectorstemp=collectorsDao.selectCollectors(collectors.getCollectorsPrdCode());
						for(i=0;i < collectorstemp.size();i++)
						{
							if(collectorstemp.get(i).getCollectorsUserCode()==collectors.getCollectorsUserCode())
							{
								resultMap.put("resultCod", 10);
								resultMap.put("resultDesc", "游戏已添加");
							}
	
						}
						if(i==collectorstemp.size())
						{
							collectorsDao.setAddCollectors(collectors);
							resultMap.put("resultCod", 100);
							resultMap.put("resultDesc", "添加成功");
						}
					}
					
				}
				
			}
			else{
				resultMap.put("resultCod", 530);
				resultMap.put("resultDesc", "未登录");
			}
			
		}
		else{
			resultMap.put("resultCod", 0);
			resultMap.put("resultDesc", "网站密钥错误");
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
	public void deleteCollectorsID(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String webkey = request.getParameter("WebKey");
		HttpSession session = request.getSession(true);
		Map<String, Object> resultMap= new HashMap<String, Object>();
		if("websiteKey123".equals(webkey)){
			Map<String, Object> sessionMap = ServletActionContext.getContext().getSession();	
			if(sessionMap.containsKey(DicCons.USER_CODE))
			{
				if(session.getMaxInactiveInterval()>20*3600)
				{
					resultMap.put("resultCod", 502);
					resultMap.put("resultDesc", "登录超时");
				}
				else
				{
					collectors=new Collectors();
					collectors.setCollectorsCode((String)GUID.getUUID());
					collectors.setCollectorsUserCode((String)sessionMap.get(DicCons.USER_CODE));
					collectors.setCollectorsPrdCode(request.getParameter("Code"));
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String dt = df.format(new Date());
					collectors.setCollectorsCrtDate(dt);
					collectors.setCollectorsNote("");
					collectorsDao=new CollectorsDaoImp();
					List<Collectors> collectorstemp=collectorsDao.selectCollectors(collectors.getCollectorsPrdCode());
					if(collectorstemp==null)
					{
						resultMap.put("resultCod", 10);
						resultMap.put("resultDesc", "未找到收藏");
						
					}
					else{
						collectorsDao.deleteCollectors(collectors);
						resultMap.put("resultCod", 100);
						resultMap.put("resultDesc", "删除成功");
					}	
						
				}
					
			}
			else{
				resultMap.put("resultCod", 530);
				resultMap.put("resultDesc", "未登录");
			}
		}
		else{
			resultMap.put("resultCod", 0);
			resultMap.put("resultDesc", "网站密钥错误");
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
