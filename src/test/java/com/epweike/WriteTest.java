package com.epweike;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.junit.Before;
import org.junit.Test;

public class WriteTest {

	protected static SolrServer solr = null;

	@Before
	public void before() {
		solr = new HttpSolrServer("http://solr2.api.epweike.com:8080/talent");
	}

	@Test
	public void write() throws SolrServerException {
		String path = "F:\\talent.txt";
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(
					new File(path)));

			// 计时开始
			long t1 = System.currentTimeMillis();

			SolrQuery params = new SolrQuery("*:*");

			QueryResponse response = solr.query(params);
			
			long total = response.getResults().getNumFound();// 总数
			int size = 1000;// 单次遍历数据量
			int start = 0; // 起始

			// 遍历所有数据
			long lenth = (total / size) + 1;
			for (int i = 0; i <= lenth; i++) {
				
				System.out.println(i+"-------------------------"+lenth);
				
				params.setStart(start + i * size);
				params.setRows(size);
				response = solr.query(params);
				
				List<SolrDocument> solrDocs = null;
				solrDocs = response.getResults();
				
				for (int j = 0; j < size; j++) {
					writer.write(solrDocs.get(j).getFieldValue("username")+","+solrDocs.get(j).getFieldValue("shop_name")+","+solrDocs.get(j).getFieldValue("mobile")+","+solrDocs.get(j).getFieldValue("email")+"\r\n");
				}
			}
			long t2 = System.currentTimeMillis(); // 排序后取得当前时间
			// 计时结束
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(t2 - t1);
			System.out.println("耗时: " + c.get(Calendar.MINUTE) + "分 "
					+ c.get(Calendar.SECOND) + "秒 "
					+ c.get(Calendar.MILLISECOND) + " 毫秒");
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e1) {
				}
			}
		}

	}


}
