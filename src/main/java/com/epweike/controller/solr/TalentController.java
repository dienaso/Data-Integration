package com.epweike.controller.solr;

import com.epweike.controller.BaseController;
import com.epweike.model.PageModel;
import com.epweike.model.RetModel;
import com.epweike.model.solr.Talent;
import com.epweike.util.QueryUtils;
import com.epweike.util.SolrUtils;
import com.epweike.util.StatUtils;

import net.sf.json.JSONObject;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.PivotField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.NamedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.Field;

/**
 * @author wuxp
 */
@Controller
@RequestMapping("/talent")
public class TalentController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(TalentController.class);

	public List<Talent> talentList;

	public Talent talent;

	@RequestMapping(value = { "list" })
	public String list(Model model) {

		return "solr/talent/list";
	}

	/**
	 * @Description:查询人才对象
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年9月29日 下午3:28:27
	 */
	public Talent getByUid(int uid) throws IOException {

		String sql = "SELECT a.uid,a.shop_id,a.seller_credit,CRC32(a.brand) AS sch_brand,a.brand,"
				+ "a.mobile,a.group_id,task_bid_num,IF(credit_score > 0, credit_score, 0) AS credit_score,"
				+ "a.shop_level,a.w_level,a.w_good_rate,a.integrity,a.integrity_ids,a.user_type,DATE_FORMAT(DATE_ADD(FROM_UNIXTIME(last_login_time), INTERVAL -8 HOUR),'%Y-%m-%dT%TZ') AS last_login_time,"
				+ "a.username,a.is_close,a.province,a.city,a.AREA,a.task_income_cash,a.task_income_credit,a.currency,a.accepted_num,a.auth_realname,"
				+ "a.auth_bank,a.auth_email,a.auth_mobile,a.chief_designer,a.isvip,CRC32(a.city) AS sch_city,CRC32(a.province) AS sch_province,"
				+ "a.vip_start_time,a.vip_end_time,CASE WHEN a.come IS NULL THEN 'WEB' WHEN a.come = '' THEN 'WEB' ELSE come END AS come,"
				+ "a.integrity_points,b.shop_name,b.shop_desc,b.views,c.skill_id,"
				+ "GROUP_CONCAT(CONCAT(d.g_id,'-',d.indus_pid,'-',d.indus_id)) AS skill_ids,GROUP_CONCAT(d.indus_id) AS indus_ids,GROUP_CONCAT(d.indus_name) AS indus_names"
				+ " FROM keke_witkey_space a LEFT JOIN keke_witkey_shop b ON a.uid=b.uid LEFT JOIN keke_witkey_member_skill c ON a.uid=c.uid LEFT JOIN keke_witkey_industry d ON c.skill_id=d.indus_id"
				+ " WHERE a.uid=?";
		QueryUtils<Talent> queryRunnerUtils = new QueryUtils<Talent>(
				Talent.class);

		Object params[] = { uid };
		// 搜索结果集
		try {
			talent = queryRunnerUtils.get(sql, params, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("获取人才对象！！！" + talent);

		return talent;
	}

	/**
	 * @Description:ajax获取人才列表
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年9月29日 下午3:28:27
	 * @throws SolrServerException
	 */
	@RequestMapping(value = "get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String paginationDataTables(HttpServletRequest request)
			throws IOException, SolrServerException {

		// 获取查询关键参数
		String aoData = request.getParameter("aoData");
		logger.info(aoData);
		// 解析查询关键参数
		PageModel<Map<String, Object>> pageModel = parsePageParamFromJson(aoData);

		// uid
		String uid = getParamFromAodata(aoData, "uid");
		// 用户名
		String username = getParamFromAodata(aoData, "username");
		// 商铺名
		String shop_name = getParamFromAodata(aoData, "shop_name");

		SolrQuery params = new SolrQuery("*:*");
		params.addSort(new SortClause("uid", SolrQuery.ORDER.asc));
		params.setStart(pageModel.getiDisplayStart());
		params.setRows(pageModel.getiDisplayLength());

		if (!uid.equals(""))
			params.addFilterQuery("uid:" + uid);

		if (!username.equals(""))
			params.addFilterQuery("username:" + username);

		if (!shop_name.equals(""))
			params.addFilterQuery("shop_name:" + shop_name);

		QueryResponse response = SolrUtils.query(params, "talent");
		// 获取人才列表
		SolrDocumentList list = response.getResults();
		// 总条数
		long total = response.getResults().getNumFound();

		// 搜索结果数
		pageModel.setiTotalDisplayRecords(total);
		pageModel.setiTotalRecords(total);
		pageModel.setAaData(list);
		JSONObject json = JSONObject.fromObject(pageModel);
		logger.info("获取人才列表！！！" + json);

		return json.toString();
	}

	@RequestMapping(value = "update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody RetModel update(HttpServletRequest request)
			throws IOException, SQLException, IllegalArgumentException,
			IllegalAccessException {
		// 返回结果对象
		RetModel retModel = new RetModel();
		// 获取主键
		int uid = Integer.parseInt(request.getParameter("uid"));
		// 获取人才对象
		Talent talent = null;
		try {
			talent = getByUid(uid);
		} catch (Exception e) {
			retModel.setFlag(false);
			retModel.setObj(e);
			retModel.setMsg("查询失败，暂时无法更新！");
			e.printStackTrace();
			return retModel;
		}

		if (talent == null) {
			retModel.setFlag(false);
			retModel.setMsg("未找到该记录，暂时无法更新！");
			return retModel;
		}

		Class<? extends Talent> clazz = talent.getClass();
		Field[] fields = clazz.getDeclaredFields();
		// 拼装索引对象
		SolrInputDocument doc = new SolrInputDocument();
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			f.setAccessible(true);
			System.out.println("属性名:" + f.getName() + " 属性值:" + f.get(talent));
			if ("indus_ids".equals(f.getName())
					|| "indus_names".equals(f.getName())
					|| "skill_ids".equals(f.getName())) {// 处理多值字段
				if (f.get(talent) != null) {
					String[] arr = f.get(talent).toString().split(",");
					for (int j = 0; j < arr.length; j++) {
						doc.addField(f.getName(), arr[j]);
					}
				}
			} else if (!"uid".equals(f.getName())
					&& !"serialVersionUID".equals(f.getName())) {
				Map<String, Object> oper = new HashMap<String, Object>();
				oper.put("set", f.get(talent));
				doc.addField(f.getName(), oper);
			} else if ("uid".equals(f.getName())) {
				doc.addField(f.getName(), f.get(talent));
			}
		}
		System.out.println(doc.toString());
		try {
			SolrUtils.update(doc, "talent");
			retModel.setMsg("更新成功！");
		} catch (Exception e) {
			retModel.setFlag(false);
			retModel.setObj(e);
			retModel.setMsg("更新失败！");
			e.printStackTrace();
		}

		return retModel;
	}

	@RequestMapping(value = "del", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody RetModel del(HttpServletRequest request)
			throws IOException {
		// 返回结果对象
		RetModel retModel = new RetModel();
		// 获取主键
		String uid = request.getParameter("uid");
		List<String> ids = new ArrayList<String>();
		ids.add(uid);
		try {
			SolrUtils.deleteById(ids, "talent");
			retModel.setMsg("删除成功！");
		} catch (Exception e) {
			retModel.setFlag(false);
			retModel.setObj(e);
			retModel.setMsg("删除失败！");
			e.printStackTrace();
		}

		return retModel;
	}

	/**
	 * @Description:一品用户省份分布统计
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月10日 下午3:28:27
	 */
	@RequestMapping(value = { "stat/province" })
	public ModelAndView provinceStat() throws SolrServerException, IOException {

		SolrQuery params = new SolrQuery("*:*").setFacet(true).addFacetField(
				"province");
		QueryResponse response = SolrUtils.getSolrServer("talent")
				.query(params);
		SolrDocumentList results = response.getResults();

		// 地区分布统计
		List<FacetField> facetFields = response.getFacetFields();

		// 返回视图
		ModelAndView mv = new ModelAndView("solr/talent/province");
		// 总数
		mv.addObject("total", results.getNumFound());
		// 饼状图数据
		// mv.addObject("pieData", ChartUtils.pieJson(facetFields));
		// 柱状图数据
		mv.addObject("barData", StatUtils.barJson(facetFields));
		logger.info("进入用户分布统计！！！");
		return mv;
	}

	/**
	 * @Description:一品用户注册统计
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月10日 下午3:28:27
	 */
	@RequestMapping(value = { "stat/register" })
	public ModelAndView register() throws SolrServerException, IOException {
		// 返回视图
		ModelAndView mv = new ModelAndView("solr/talent/register");
		logger.info("进入用户注册统计！！！");
		return mv;
	}

	/**
	 * @Description:获取注册用户
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月10日 下午3:28:27
	 */
	@RequestMapping(value = "register/get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getRegister(HttpServletRequest request)
			throws Exception {

		// 获取查询关键参数
		String aoData = request.getParameter("aoData");
		logger.info(aoData);
		// 解析查询关键参数
		PageModel<Map<String, Object>> pageModel = parsePageParamFromJson(aoData);

		// 注册时间
		String reg_start = getParamFromAodata(aoData, "reg_start");
		reg_start = (!"".equals(reg_start)) ? reg_start + "T00:00:00Z" : "*";
		String reg_end = getParamFromAodata(aoData, "reg_end");
		reg_end = (!"".equals(reg_end)) ? reg_end + "T23:59:59Z" : "*";

		SolrQuery params = new SolrQuery("*:*");
		params.addFilterQuery("reg_time_date:[" + reg_start + " TO " + reg_end
				+ "]");
		params.setFacet(true);
		params.addFacetPivotField("reg_date,user_role,come").setFacetLimit(
				Integer.MAX_VALUE);

		QueryResponse response = SolrUtils.getSolrServer("talent")
				.query(params);
		NamedList<List<PivotField>> namedList = response.getFacetPivot();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;

		int allTotal = 0;
		int allUncertain = 0;
		int allWitkey = 0;
		int allEmployer = 0;
		int allBoth = 0;
		int allWeb = 0;
		int allCpm = 0;
		int allApp = 0;
		int allWap = 0;
		int allMall = 0;
		int allBack = 0;
		int allYun = 0;

		if (namedList != null) {
			List<PivotField> pivotList = null;
			for (int i = 0, len = namedList.size(); i < len; i++) {
				pivotList = namedList.getVal(i);
				if (pivotList != null) {
					for (PivotField pivot : pivotList) {
						int total = 0;
						map = new HashMap<String, Object>();
						map.put("label", pivot.getValue());
						// 处理身份类型
						List<PivotField> fieldList = pivot.getPivot();
						if (fieldList != null) {
							for (PivotField field : fieldList) {
								int count = field.getCount();
								String value = field.getValue().toString();
								System.out.println("field=" + field.getField());
								String tmp = "";
								if ("0".equals(value)) {// 未确定
									tmp = "uncertain";
									allUncertain += count;
								} else if ("1".equals(value)) {// 威客
									tmp = "witkey";
									allWitkey += count;
								} else if ("2".equals(value)) {// 雇主
									tmp = "employer";
									allEmployer += count;
								} else if ("3".equals(value)) {
									tmp = "both";
									allBoth += count;
								}
								map.put(tmp, count);
								total += count;
								// 处理注册渠道
								List<PivotField> fieldList2 = field.getPivot();
								for (PivotField field2 : fieldList2) {
									int count2 = field2.getCount();
									String value2 = field2.getValue()
											.toString();
									int tmp2 = 0;
									if (map.get(value2) != null)
										tmp2 = Integer.parseInt(map.get(value2)
												.toString());
									map.put(value2, tmp2 + count2);

									switch (value2) {
									case "WEB":
										allWeb += count2;
										break;
									case "cpm":
										allCpm += count2;
										break;
									case "APP":
										allApp += count2;
										break;
									case "WAP":
										allWap += count2;
										break;
									case "mall":
										allMall += count2;
										break;
									case "background":
										allBack += count2;
										break;
									case "yun":
										allYun += count2;
										break;
									default:
										break;
									}
								}
							}
						}
						allTotal += total;
						System.out.println("map" + map.toString());
						map.put("TOTAL", total);
						// 不存在赋值0
						if (map.get("WEB") == null)
							map.put("WEB", 0);
						if (map.get("cpm") == null)
							map.put("cpm", 0);
						if (map.get("APP") == null)
							map.put("APP", 0);
						if (map.get("WAP") == null)
							map.put("WAP", 0);
						if (map.get("mall") == null)
							map.put("mall", 0);
						if (map.get("background") == null)
							map.put("background", 0);
						if (map.get("yun") == null)
							map.put("yun", 0);
						if (map.get("witkey") == null)
							map.put("witkey", 0);
						if (map.get("employer") == null)
							map.put("employer", 0);
						if (map.get("both") == null)
							map.put("both", 0);
						if (map.get("uncertain") == null)
							map.put("uncertain", 0);
						if (map.get("TOTAL") == null)
							map.put("TOTAL", 0);

						list.add(map);
					}
				}
			}
		}

		// 排序(按注册日期升序)
		Collections.sort(list, new Comparator<Map<String, Object>>() {
			public int compare(Map<String, Object> arg0,
					Map<String, Object> arg1) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				Date date1 = null;
				Date date2 = null;
				try {
					date1 = simpleDateFormat
							.parse(arg0.get("label").toString());
					date2 = simpleDateFormat
							.parse(arg1.get("label").toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Double timeStemp1 = (double) date1.getTime();
				Double timeStemp2 = (double) date2.getTime();
				return -(timeStemp1.compareTo(timeStemp2));
			}
		});

		// 汇总
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("label", "汇总");
		map2.put("TOTAL", allTotal);
		map2.put("witkey", allWitkey);
		map2.put("employer", allEmployer);
		map2.put("uncertain", allUncertain);
		map2.put("both", allBoth);
		map2.put("WEB", allWeb);
		map2.put("cpm", allCpm);
		map2.put("APP", allApp);
		map2.put("WAP", allWap);
		map2.put("mall", allMall);
		map2.put("background", allBack);
		map2.put("yun", allYun);
		list.add(0, map2);

		// 搜索结果数
		pageModel.setiTotalDisplayRecords(list.size());
		pageModel.setiTotalRecords(list.size());
		pageModel.setAaData(list);
		JSONObject json = JSONObject.fromObject(pageModel);
		logger.info("获取用户注册统计列表！！！" + json);

		return json.toString();
	}
	
}
