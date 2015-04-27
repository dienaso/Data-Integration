package com.epweike.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.epweike.model.Lexicon;
import com.epweike.model.PageModel;


/**  
* @Description:控制器通用方法类
*  
* @author  吴小平
* @version 创建时间：2014年12月10日 下午2:36:07
*/  
/**
 * @author Administrator
 *
 */
public class BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	//页码
	public static int pageNum = 1;
	/**
	 * @return the pageNum
	 */
	public static int getPageNum() {
		return pageNum;
	}

	/**
	 * @param pageNum the pageNum to set
	 */
	public static void setPageNum(int pageNum) {
		BaseController.pageNum = pageNum;
	}

	/**
	 * @return the pageSize
	 */
	public static int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public static void setPageSize(int pageSize) {
		BaseController.pageSize = pageSize;
	}

	//每页显示条数
	public static int pageSize = 30;
	
	public static HttpSession getSession() {
		HttpSession session = null;
		try {
		    session = getRequest().getSession();
		}catch (Exception e) {
			logger.error("获取session异常！详细错误："+e);
		}
		return session;
	}

	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();    
	}
		
	//从json数据中解析关键参数
	public <T> PageModel<T> parsePageParamFromJson(String aoData) {
		
		JSONArray ja = (JSONArray) JSONArray.fromObject(aoData);
		PageModel<T> pageModel = new PageModel<T>();
		
		for (int i = 0; i < ja.size(); i++) {
          JSONObject obj = (JSONObject) ja.get(i);
          if (obj.get("name").equals("sEcho"))
        	  pageModel.setsEcho(obj.get("value").toString());
          if (obj.get("name").equals("iDisplayStart"))
          	pageModel.setiDisplayStart(Integer.parseInt(obj.get("value").toString()));
          if (obj.get("name").equals("iDisplayLength"))
          	pageModel.setiDisplayLength(Integer.parseInt(obj.get("value").toString()));
          if (obj.get("name").equals("sSearch"))
        	  pageModel.setsSearch(obj.get("value").toString());
          if (obj.get("name").equals("sColumns"))
        	  pageModel.setsColumns(obj.get("value").toString());
        }
		return pageModel;
	}

}
