package com.epweike;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.FieldStatsInfo;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.RangeFacet;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.FacetParams;
import org.apache.solr.common.params.FacetParams.FacetRangeInclude;
import org.apache.solr.common.params.StatsParams;
import org.junit.Test;

import com.epweike.util.DateUtils;

public class SolrJTest {
	
//	@Test
//	public void query() throws SolrServerException, IOException {
//		
//		String urlString = "http://solr.api.epweike.net/talent";
//		SolrClient solr = new HttpSolrClient(urlString);
//		
//		
//		SolrQuery parameters = new SolrQuery("*:*").setFacet(true).addFacetField("province","city");
//		
//		QueryResponse response = solr.query(parameters);
//		
//		//SolrDocumentList list = response.getResults();
//		
//		//地区分布统计
//		List<FacetField> facetFields = response.getFacetFields(); 
//		List<Map<String, Object>> countList = new ArrayList<Map<String, Object>>();
//		for (FacetField ff : facetFields) {
//		     System.out.println("--------------------");
//		     System.out.println("name=" + ff.getName() + "\tcount=" + ff.getValueCount());
//		     System.out.println("--------------------");
//		     switch (ff.getName()) {
//	         	case "province"://按省份分布统计
//			    for (Count count : ff.getValues()) {
//			    	System.out.println("name=" + count.getName() + "\tcount=" + count.getCount());
//			    	Map<String, Object> map = new HashMap<String, Object>();
//			    	map.put("label", count.getName());
//			    	map.put("data", count.getCount());
//			    	countList.add(map);
//		        }
//			    break;
//		     }
//	    }
//		//List to json
//		JSONArray ja = JSONArray.fromObject(countList);
//		System.out.println(ja.toString());
//		solr.close();
//		
//	}
	
	
//	@Test
//	public void query() throws SolrServerException, IOException {
//		
//		String urlString = "http://solr.api.epweike.net/talent";
//		SolrClient solr = new HttpSolrClient(urlString);
//		
//		
//		SolrQuery parameters = new SolrQuery("*:*").setFacet(true).addFacetField("province","city");
//		
//		QueryResponse response = solr.query(parameters);
//		
//		List<FacetField> facetFields = response.getFacetFields(); 
//		
//		//地区分布统计
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		
//		for (FacetField ff : facetFields) {
//		     System.out.println("--------------------");
//		     System.out.println("name=" + ff.getName() + "\tcount=" + ff.getValueCount());
//		     System.out.println("--------------------");
//		     switch (ff.getName()) {
//	         	case "province"://按省份分布统计
//	         	int i = 0;
//			    for (Count count : ff.getValues()) {
//			    	System.out.println("name=" + count.getName() + "\tcount=" + count.getCount());
//			    	Map<String, Object> map = new HashMap<String, Object>();
//			    	map.put("name", count.getName());
//			    	map.put("count", count.getCount());
//			    	list.add(map);
//			    	i++;
//		        }
//		     }
//	    }
//		//List to json
//		System.out.println("countList="+list.toString());
////		JSONArray ja = JSONArray.fromObject(countList);
////		System.out.println(ja.toString());
//		solr.close();
//		
//	}
	
	
//	@Test
//	public void query() throws SolrServerException, IOException {
//		
//		String urlString = "http://solr.api.epweike.net/login";
//		SolrClient solr = new HttpSolrClient(urlString);
//		
//		Date d1 = new Date();
//		d1.setTime(1398873600);
//		Date d2 = new Date();
//		d2.setTime(1399651200);
//		SolrQuery parameters = new SolrQuery("*:*")
//		.setFacet(true)
//		.addDateRangeFacet("on_time", d1, d2, "%2BDAY");
//		
//		QueryResponse response = solr.query(parameters);
//		
//		List<FacetField> facetFields = response.getFacetFields(); 
//		
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		
//		for (FacetField ff : facetFields) {
//		     System.out.println("--------------------");
//		     System.out.println("name=" + ff.getName() + "\tcount=" + ff.getValueCount());
//		     System.out.println("--------------------");
//		     switch (ff.getName()) {
//	         	case "on_time":
//			    for (Count count : ff.getValues()) {
//			    	System.out.println("name=" + count.getName() + "\tcount=" + count.getCount());
//			    	Map<String, Object> map = new HashMap<String, Object>();
//			    	map.put("name", count.getName());
//			    	map.put("count", count.getCount());
//			    	list.add(map);
//		        }
//		     }
//	    }
//		//List to json
//		System.out.println("countList="+list.toString());
////		JSONArray ja = JSONArray.fromObject(countList);
////		System.out.println(ja.toString());
//		solr.close();
//		
//	}

//	@Test
//	public void query() throws Exception {
//		
//		String urlString = "http://solr.api.epweike.net/task";
//		SolrServer solr = new HttpSolrServer(urlString);
//		Date start = DateUtils.parseDate("2015-05-29");
//		Date end = DateUtils.parseDate("2015-06-30");
//		SolrQuery parameters = new SolrQuery("*:*");
//		parameters//android、ios、wap
//		.setFacet(true).addDateRangeFacet("pub_time", start, end, "+1DAY").addFacetPivotField("source");;
////		parameters.addFilterQuery("model_id:4").addFilterQuery(
////				"task_type:3");
//		QueryResponse response = solr.query(parameters);
//		
//		List<RangeFacet> listFacet = response.getFacetRanges(); 
//		int total = 0;
//        for(RangeFacet rf : listFacet){  
//            List<RangeFacet.Count> listCounts = rf.getCounts();  
//            for(RangeFacet.Count count : listCounts){  
//                System.out.println("RangeFacet:"+count.getValue()+":"+count.getCount()+":"+count.getValue());  
//                total = total + count.getCount();
//            }  
//        }  
//        System.out.println("Total:"+total);  
//	}
	
	@Test
	public void stats() throws Exception {
		
		String urlString = "http://solr2.api.epweike.net/finance";
		SolrServer solr = new HttpSolrServer(urlString);

		SolrQuery parameters = new SolrQuery("*:*");
		parameters.addFilterQuery("fina_time_date:{2015-06-28T00:00:00Z TO 2015-06-29T00:00:00Z}").addFilterQuery("fina_action:task_bid");
		parameters.setGetFieldStatistics(true);
		parameters.setParam(StatsParams.STATS_FIELD, "fina_cash"); 
		parameters.setParam(StatsParams.STATS_FACET, "username"); 

		QueryResponse response = solr.query(parameters);
		
		Map<String, Object> tmp1 = new HashMap<String, Object>();
		Map<String, FieldStatsInfo> stats = response.getFieldStatsInfo();
		FieldStatsInfo statsInfo = stats.get("fina_cash");
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		
		//total
		tmp1.put("name", "汇总");
		tmp1.put("min", statsInfo.getMin());
		tmp1.put("sum", statsInfo.getSum());
		tmp1.put("count", statsInfo.getCount());
		tmp1.put("missing", statsInfo.getMissing());
		tmp1.put("mean", statsInfo.getMean());
		tmp1.put("stddev", statsInfo.getStddev());
		resultList.add(tmp1);
		System.out.println("tmp:"+tmp1.toString());
		
		//facets
		Map<String, List<FieldStatsInfo>> map = stats.get("fina_cash").getFacets();
		List<FieldStatsInfo> list = map.get("username");
		if(list != null && list.size() >0){
			for(int i = 0; i < list.size(); i++){
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
		System.out.println("resultList:"+resultList.toString());
		
		
		
	}

}
