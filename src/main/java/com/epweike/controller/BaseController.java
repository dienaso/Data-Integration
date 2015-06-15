package com.epweike.controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

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
	 * 从配置文件中读取SOLR地址
	 */
	@Value("#{configProperties['solr.url']}")
	private String solr_url;

	/**
	 * @Description:连接solr服务器
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月11日 上午9:32:13
	 */
	public SolrServer getSolrServer(String core) {
		logger.info("读取配置文件属性：solr.url=" + solr_url);
		if (solr_url == null) {
			logger.error("solr.url=" + solr_url
					+ "，将使用使用默认值:'http://solr.api.epweike.net/'！！！");
			solr_url = "http://solr.api.epweike.net/";
		}
		SolrServer solr = new HttpSolrServer(solr_url + core);
		logger.info("SOLR URL IS:" + solr_url + core);
		return solr;
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
