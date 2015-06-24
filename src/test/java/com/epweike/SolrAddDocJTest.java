package com.epweike;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrAddDocJTest {

	@Test
	public void test() throws Exception {
		while (true) {
			add();
		}
	}
	int i = 339;
	public void add() throws Exception {
		String urlString = "http://solr.api.epweike.net/longword";
		SolrServer solr = new HttpSolrServer(urlString);
		i++;
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", i);
		doc.addField("longword", "实时增量索引测试" + i);
		solr.add(doc);
	}

}
