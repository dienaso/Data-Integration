package com.epweike.controller.solr;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.epweike.controller.BaseController;
import com.epweike.model.PageModel;
import com.epweike.util.DateUtils;
import com.epweike.util.SolrUtils;

/**
 * @author wuxp
 */
@Controller
@RequestMapping("/messqueue")
public class MessqueueController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(MessqueueController.class);
	
	/**
	 * @Description:获取一品消息列表
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月10日 下午3:28:27
	 */
	@RequestMapping(value = "get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getLoginDetail(HttpServletRequest request)
			throws Exception {

		// 获取查询关键参数
		String aoData = request.getParameter("aoData");
		logger.info(aoData);
		// 解析查询关键参数
		PageModel<Map<String, Object>> pageModel = parsePageParamFromJson(aoData);

		// 开始时间
		String startTime = getParamFromAodata(aoData, "start");
		Date start = DateUtils.parseDate(startTime);
		// 结束时间
		String endTime = getParamFromAodata(aoData, "end");
		Date end = DateUtils.parseDateTime(endTime + " 23:59:59");
		// 消息类型
		String messagetype = getParamFromAodata(aoData, "messagetype");
		// 标题
		String title = getParamFromAodata(aoData, "title");
		// 邮箱/手机号
		String targetno = getParamFromAodata(aoData, "targetno");
		// 过滤条件
		SolrQuery params = new SolrQuery("*:*");

		if (!messagetype.equals("全部"))
			params.addFilterQuery("messagetype:" + messagetype);
		if (!title.equals(""))
			params.addFilterQuery("title:" + title);
		if (!targetno.equals(""))
			params.addFilterQuery("targetno:" + targetno);

		params.addFilterQuery("intime:[" + start.getTime() / 1000 + " TO "
				+ end.getTime() / 1000 + "]");
		params.setStart(pageModel.getiDisplayStart());
		params.setRows(pageModel.getiDisplayLength());
		params.setSort("intime", SolrQuery.ORDER.desc);

		QueryResponse response = SolrUtils.getSolrServer("messqueue").query(params);

		// 获取登陆明细列表
		SolrDocumentList list = response.getResults();

		// 搜索结果数
		pageModel.setiTotalDisplayRecords(list.getNumFound());
		pageModel.setiTotalRecords(list.getNumFound());
		pageModel.setAaData(list);
		JSONObject json = JSONObject.fromObject(pageModel);
		logger.info("获取消息列表！！！" + json);

		return json.toString();
	}

	/**
	 * @Description:一品消息队列
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月10日 下午3:28:27
	 */
	@RequestMapping(value = { "list" })
	public ModelAndView loginDetail() throws SolrServerException, IOException {
		// 返回视图
		ModelAndView mv = new ModelAndView("solr/messqueue/list");
		logger.info("进入消息列表！！！");
		return mv;
	}
}
