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
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.RangeFacet;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.FacetParams;
import org.apache.solr.common.params.FacetParams.FacetRangeInclude;
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

	@Test
	public void query() throws Exception {
		
		String urlString = "http://solr.api.epweike.net/task";
		SolrServer solr = new HttpSolrServer(urlString);
		Date start = DateUtils.parseDate("2015-05-29");
		Date end = DateUtils.parseDate("2015-06-30");
		SolrQuery parameters = new SolrQuery("*:*");
		parameters//android、ios、wap
		.setFacet(true).addDateRangeFacet("pub_time", start, end, "+1DAY").addFacetPivotField("source");;
//		parameters.addFilterQuery("model_id:4").addFilterQuery(
//				"task_type:3");
		QueryResponse response = solr.query(parameters);
		
		List<RangeFacet> listFacet = response.getFacetRanges(); 
		int total = 0;
        for(RangeFacet rf : listFacet){  
            List<RangeFacet.Count> listCounts = rf.getCounts();  
            for(RangeFacet.Count count : listCounts){  
                System.out.println("RangeFacet:"+count.getValue()+":"+count.getCount()+":"+count.getValue());  
                total = total + count.getCount();
            }  
        }  
        System.out.println("Total:"+total);  
	}

}
