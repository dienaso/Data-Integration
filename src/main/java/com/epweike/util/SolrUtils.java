package com.epweike.util;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

public class SolrUtils {
	public final static Logger log = Logger.getLogger(SolrUtils.class);

	// 最终路由地址
	protected static String solr_url;
	
	protected static SolrServer solr = null;
	
	/**
	 * @Description:连接solr服务器
	 * 
	 * @author 吴小平
	 * @version 创建时间：2015年6月11日 上午9:32:13
	 */
	public static SolrServer getSolrServer(String core) {
		String solr1_url = SysconfigUtils.getVarValue("solr1_url");
		String solr2_url = SysconfigUtils.getVarValue("solr2_url");
		String solr1_cores = SysconfigUtils.getVarValue("solr1_cores");

		// core路由
		if (solr1_cores.contains(core)) {
			solr_url = solr1_url;
		} else {
			solr_url = solr2_url;
		}

		solr = new HttpSolrServer(solr_url + core);
		log.info("SOLR URL IS:" + solr_url + core);
		return solr;
	}

}
