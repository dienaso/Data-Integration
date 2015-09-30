package com.epweike.util;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;

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
		
		return solr;
	}

	/**
	 * Query.
	 * 
	 * @param params
	 * @param core
	 * @return
	 */
	public static QueryResponse query(SolrParams params, String core) {
		QueryResponse response = null;
		try {
			response = getSolrServer(core).query(params);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * Detele lucene by IDs.
	 * 
	 * @param ids
	 * @param core
	 */
	public static void deleteById(List<String> ids, String core) {
		try {
			getSolrServer(core).deleteById(ids);
			commit(false, false);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Detele lucene by query.
	 * 
	 * @param query
	 * @param core
	 */
	public static void deleteByQuery(String query, String core) {
		try {
			getSolrServer(core).deleteByQuery(query);
			commit(false, false);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Update lucene.
	 * 
	 * @param object
	 * @param core
	 */
	public static void update(Object object, String core) {
		try {
			getSolrServer(core).addBean(object);
			commit(false, false);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Update lucene.
	 * 
	 * @param object
	 * @param core
	 */
	public static void update(SolrInputDocument doc, String core) {
		try {
			getSolrServer(core).add(doc);
			commit(false, false);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 
     * Commit. 
     * @param waitFlush 
     * @param waitSearcher 
     * @throws SolrServerException 
     * @throws IOException 
     */  
    public static void commit(boolean waitFlush, boolean waitSearcher) {  
        try {  
            solr.commit(waitFlush, waitSearcher);  
        } catch (SolrServerException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  

}
