package com.epweike.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
	private static final Logger logger = LoggerFactory
			.getLogger(BaseController.class);

	/*
	 * SOLR地址
	 */
	public static final String SOLR_URL = "http://solr.api.epweike.com/";

	/**  
	* @Description:连接solr服务器
	*  
	* @author  吴小平
	* @version 创建时间：2015年6月11日 上午9:32:13
	*/  
	public static SolrServer getSolrServer(String core) {
		SolrServer solr = new HttpSolrServer(SOLR_URL + core);
		return solr;
	}

	public static HttpSession getSession() {
		HttpSession session = null;
		try {
			session = getRequest().getSession();
		} catch (Exception e) {
			logger.error("获取session异常！详细错误：" + e);
		}
		return session;
	}

	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
	}

	/**
	 * @Description:从json数据中解析关键分页参数
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年5月15日 下午2:08:44
	 */
	public <T> PageModel<T> parsePageParamFromJson(String aoData) {

		JSONArray ja = (JSONArray) JSONArray.fromObject(aoData);
		PageModel<T> pageModel = new PageModel<T>();

		for (int i = 0; i < ja.size(); i++) {
			JSONObject obj = (JSONObject) ja.get(i);
			if (obj.get("name").equals("sEcho"))
				pageModel.setsEcho(obj.get("value").toString());
			if (obj.get("name").equals("iDisplayStart"))
				pageModel.setiDisplayStart(Integer.parseInt(obj.get("value")
						.toString()));
			if (obj.get("name").equals("iDisplayLength"))
				pageModel.setiDisplayLength(Integer.parseInt(obj.get("value")
						.toString()));
			if (obj.get("name").equals("sSearch"))
				pageModel.setsSearch(obj.get("value").toString());
			if (obj.get("name").equals("sColumns"))
				pageModel.setsColumns(obj.get("value").toString());
		}
		return pageModel;
	}

	/**
	 * @Description:获取前台传递的自定义参数
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月10日 下午6:34:18
	 */
	public String getParamFromAodata(String aoData, String name) {

		JSONArray ja = (JSONArray) JSONArray.fromObject(aoData);
		String param = "";
		for (int i = 0; i < ja.size(); i++) {
			JSONObject obj = (JSONObject) ja.get(i);
			if (obj.get("name").equals(name))
				param = obj.get("value").toString();
		}
		return param;
	}

}
