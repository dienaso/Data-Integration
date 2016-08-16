package com.epweike;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FieldStatsInfo;
import org.apache.solr.client.solrj.response.PivotField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.RangeFacet;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.common.params.GroupParams;
import org.apache.solr.common.params.StatsParams;
import org.apache.solr.common.util.NamedList;
import org.junit.Test;

import com.epweike.util.DateUtils;
import com.epweike.util.StatUtils;

public class SolrJTest<E> {

	@Test
	public void stats() throws Exception {

		String urlString = "http://solr2.api.epweike.net/finance";
		SolrServer solr = new HttpSolrServer(urlString);

		SolrQuery parameters = new SolrQuery("*:*");
		parameters
				.addFilterQuery(
						"fina_time_date:{2015-06-28T00:00:00Z TO 2015-06-29T00:00:00Z}")
				.addFilterQuery("fina_action:task_bid");
		parameters.setGetFieldStatistics(true);
		parameters.setParam(StatsParams.STATS_FIELD, "fina_cash");
		parameters.setParam(StatsParams.STATS_FACET, "username");

		QueryResponse response = solr.query(parameters);

		Map<String, Object> tmp1 = new HashMap<String, Object>();
		Map<String, FieldStatsInfo> stats = response.getFieldStatsInfo();
		FieldStatsInfo statsInfo = stats.get("fina_cash");
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		// total
		tmp1.put("name", "汇总");
		tmp1.put("min", statsInfo.getMin());
		tmp1.put("sum", statsInfo.getSum());
		tmp1.put("count", statsInfo.getCount());
		tmp1.put("missing", statsInfo.getMissing());
		tmp1.put("mean", statsInfo.getMean());
		tmp1.put("stddev", statsInfo.getStddev());
		resultList.add(tmp1);
		System.out.println("tmp:" + tmp1.toString());

		// facets
		Map<String, List<FieldStatsInfo>> map = stats.get("fina_cash")
				.getFacets();
		List<FieldStatsInfo> list = map.get("username");
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> tmp2 = new HashMap<String, Object>();
				tmp2.put("name", list.get(i).getName());
				tmp2.put("min", list.get(i).getMin());
				tmp2.put("sum", list.get(i).getSum());
				tmp2.put("count", list.get(i).getCount());
				tmp2.put("missing", list.get(i).getMissing());
				tmp2.put("mean", list.get(i).getMean());
				tmp2.put("stddev", list.get(i).getStddev());
				resultList.add(tmp2);
			}
		}
		System.out.println("resultList:" + resultList.toString());

	}

	@Test
	public void regTypeStats() throws Exception {

		String urlString = "http://solr2.api.epweike.net/talent";
		SolrServer solr = new HttpSolrServer(urlString);

		// 注册统计
		Date end = new Date();// 结束时间
		Date start = DateUtils.addDays(end, -20);// 开始时间(20天前)
		// 统计类型(日、月、年)
		String statType = "+1DAY";

		SolrQuery parameters = new SolrQuery("*:*").setFacet(true)
				.addDateRangeFacet("reg_time_date", start, end, statType);

		QueryResponse response = solr.query(parameters);
		// 获取区间统计列表
		@SuppressWarnings("rawtypes")
		List<RangeFacet> listFacet = response.getFacetRanges();
		List<Map<String, Object>> list = StatUtils.getFacetRangeList(listFacet,
				0, 10);
		System.out.println(list.toString());
	}

	@Test
	public void getMultFacet() throws Exception {
		String urlString = "http://solr2.api.epweike.net/talent";
		SolrServer solr = new HttpSolrServer(urlString);
		Date end = new Date();// 结束时间
		Date start = DateUtils.addDays(end, -20);// 开始时间(20天前)
		SolrQuery parameters = new SolrQuery("*:*");
		parameters.addFilterQuery("reg_time:[" + start.getTime() + " TO "
				+ end.getTime() + "]");
		parameters.setFacet(true);
		parameters.addFacetField("reg_time_date", "come");

		QueryResponse response = solr.query(parameters);
		List<FacetField> facetFields = response.getFacetFields();

		for (FacetField ff : facetFields) {
			System.out.println("--------------------");
			System.out.println("name=" + ff.getName() + "\tcount="
					+ ff.getValueCount());
			System.out.println("--------------------");
			for (Count count : ff.getValues()) {
				System.out.println("name=" + count.getName() + "\tcount="
						+ count.getCount());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("name", count.getName());
				map.put("count", count.getCount());
			}
		}
	}
	
	@Test
	public void getRegFacetRange() throws Exception {
		String urlString = "http://solr2.api.epweike.net/talent";
		SolrServer solr = new HttpSolrServer(urlString);

		// 注册统计
		Date end = new Date();// 结束时间
		Date start = DateUtils.addDays(end, -20);// 开始时间(20天前)
		// 统计类型(日、月、年)
		String statType = "+1DAY";

		//WEB
		SolrQuery parameters1 = new SolrQuery("*:*").setFacet(true).addFilterQuery("come:WEB")
				.addDateRangeFacet("reg_time_date", start, end, statType);
		//CPM
		SolrQuery parameters2 = new SolrQuery("*:*").setFacet(true).addFilterQuery("come:cpm")
				.addDateRangeFacet("reg_time_date", start, end, statType);
		//APP
		SolrQuery parameters3 = new SolrQuery("*:*").setFacet(true).addFilterQuery("come:APP")
				.addDateRangeFacet("reg_time_date", start, end, statType);
		//WAP
		SolrQuery parameters4 = new SolrQuery("*:*").setFacet(true).addFilterQuery("come:WAP")
				.addDateRangeFacet("reg_time_date", start, end, statType);
		//MALL
		SolrQuery parameters5 = new SolrQuery("*:*").setFacet(true).addFilterQuery("come:mall")
				.addDateRangeFacet("reg_time_date", start, end, statType);
		//BACKGROUND
		SolrQuery parameters6 = new SolrQuery("*:*").setFacet(true).addFilterQuery("come:background")
				.addDateRangeFacet("reg_time_date", start, end, statType);
		//YUN
		SolrQuery parameters7 = new SolrQuery("*:*").setFacet(true).addFilterQuery("come:yun")
				.addDateRangeFacet("reg_time_date", start, end, statType);

		QueryResponse response = null;
		// 获取各个注册来源区间统计列表
		response = solr.query(parameters1);
		List<Map<String, Object>> list1 = StatUtils.getFacetRangeList(response.getFacetRanges(),
				0, 10);
		response = solr.query(parameters2);
		List<Map<String, Object>> list2 = StatUtils.getFacetRangeList(response.getFacetRanges(),
				0, 10);
		response = solr.query(parameters3);
		List<Map<String, Object>> list3 = StatUtils.getFacetRangeList(response.getFacetRanges(),
				0, 10);
		response = solr.query(parameters4);
		List<Map<String, Object>> list4 = StatUtils.getFacetRangeList(response.getFacetRanges(),
				0, 10);
		response = solr.query(parameters5);
		List<Map<String, Object>> list5 = StatUtils.getFacetRangeList(response.getFacetRanges(),
				0, 10);
		response = solr.query(parameters6);
		List<Map<String, Object>> list6 = StatUtils.getFacetRangeList(response.getFacetRanges(),
				0, 10);
		response = solr.query(parameters7);
		List<Map<String, Object>> list7 = StatUtils.getFacetRangeList(response.getFacetRanges(),
				0, 10);
		
		
		System.out.println("list1:======="+JSONArray.fromObject(list1));
		System.out.println("list2:======="+JSONArray.fromObject(list2));
		System.out.println("list3:======="+JSONArray.fromObject(list3));
		System.out.println("list4:======="+JSONArray.fromObject(list4));
		System.out.println("list5:======="+JSONArray.fromObject(list5));
		System.out.println("list6:======="+JSONArray.fromObject(list6));
		System.out.println("list7:======="+JSONArray.fromObject(list7));
	}

	@Test
	public void getPivot() throws Exception {
		String urlString = "http://solr2.api.epweike.com:8080/talent";
		SolrServer solr = new HttpSolrServer(urlString);
//		long end = System.currentTimeMillis() / 1000;// 结束时间
//		long start = end - 20 * 24 * 60 * 60;// 开始时间(20天前)
		Date end = new Date();// 结束时间
		Date start = DateUtils.addDays(end, -10);// 开始时间(20天前)
		SolrQuery parameters = new SolrQuery("*:*");
		//parameters.addFilterQuery("on_time:[" + start + " TO " + end + "]");
		parameters.addDateRangeFacet("reg_time_date", start, end, "+1DAY");
		parameters.setFacet(true);
		parameters.addFacetPivotField("user_role,come");
		//parameters.setParam(FacetParams.FACET_PIVOT_MINCOUNT, "0");

		QueryResponse response = solr.query(parameters);
		NamedList<List<PivotField>> namedList = response.getFacetPivot();
		System.out.println(namedList);// 底下为啥要这样判断，把这个值打印出来，你就明白了
		
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Map<String, Object> resultMap = null;
		Map<String, Object> map = null;
		Map<String, Object> map2 = null;
		
		if (namedList != null) {
			List<PivotField> pivotList = null;
			for (int i = 0; i < namedList.size(); i++) {
				pivotList = namedList.getVal(i);
				if (pivotList != null) {
					for (PivotField pivot : pivotList) {
						List<Map<String, Object>> tmpList = new ArrayList<Map<String, Object>>();
						map = new HashMap<String, Object>();
						map.put("label", pivot.getValue());
						//System.out.println("-----------------------------------------------------");
						//System.out.println(pivot.getValue());
						List<PivotField> fieldList = pivot.getPivot();
						if (fieldList != null) {
							for (PivotField field : fieldList) {
								map2 = new HashMap<String, Object>();
								map2.put("user_role", field.getValue().toString());
								map2.put("count", field.getCount());
								tmpList.add(map2);
							}
						}
						map.put("data", tmpList);
						
						resultMap = new HashMap<String, Object>();
						resultMap.put(pivot.getValue().toString(), map);
						resultList.add(resultMap);
					}
				}
			}
		}
		System.out.println("-----------------------------------------------------"+resultList);
		JSONArray json = JSONArray.fromObject(resultList);
		System.out.println("json="+json.toString());
	}

	@Test
	public void getDate() throws Exception {
//		String urlString = "http://solr2.api.epweike.com/talent";
//		SolrServer solr = new HttpSolrServer(urlString);
//		SolrQuery sParams = new SolrQuery("*:*").setFacet(true).addFacetField("w_level");
//		QueryResponse sResponse = solr.query(sParams);
//		List<FacetField> sFacetFields = sResponse.getFacetFields(); 
//		System.out.println("sFacetFields="+StatUtils.barJson(sFacetFields));
		System.out.println("贺卡。并且在贺卡中还是附上一段祝福语".length());
	}
	
	@Test
	public void getLoginFacetRange() throws Exception {
		String urlString = "http://solr.api.epweike.net/login";
		SolrServer solr = new HttpSolrServer(urlString);

		// 注册统计
		Date end = new Date();// 结束时间
		Date start = DateUtils.addDays(end, -20);// 开始时间(20天前)
		// 统计类型(日、月、年)
		String statType = "+1DAY";

		SolrQuery loginParams = new SolrQuery("*:*").setFacet(true)
				.addDateRangeFacet("on_time", start, end, statType);
		loginParams.setParam(GroupParams.GROUP_FACET, true);  
		loginParams.setParam(GroupParams.GROUP_FIELD, "uid");  

		QueryResponse response = null;
		// 获取各个注册来源区间统计列表
		response = solr.query(loginParams);
		List<Map<String, Object>> list = StatUtils.getFacetRangeList(response.getFacetRanges(),
				0, 10);
		
		System.out.println("list:======="+JSONArray.fromObject(list));
	}
}
