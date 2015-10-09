package com.epweike.controller.solr;

import com.epweike.controller.BaseController;
import com.epweike.model.PageModel;
import com.epweike.model.RetModel;
import com.epweike.model.solr.Talent;
import com.epweike.util.QueryUtils;
import com.epweike.util.SolrUtils;

import net.sf.json.JSONObject;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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

}
