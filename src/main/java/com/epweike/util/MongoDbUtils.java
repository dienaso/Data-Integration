/**
 * 
 */
package com.epweike.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

/**
 * 
 * @author wuxp
 */
public class MongoDbUtils {

	private static MongoClient mongoClient;
	private static MongoDatabase db;

	/**
	 * 
	 * 
	 * @throws Exception
	 */
	static {
		// 连接MongoDB
		mongoClient = new MongoClient("10.0.100.121");
		// 访问数据库
		db = mongoClient.getDatabase("epweike");
	}

	public void insertMany(List<Document> docs, String collectionName) {
		System.err.println("===========going to insert==========");
		MongoCollection<Document> collection = db.getCollection(collectionName);
		collection.insertMany(docs);
	}

	public void find() {
		System.err.println("===========going to find==========");
		MongoCollection<Document> collection = db.getCollection("msg");

		FindIterable<Document> iterable = collection.find(new Document(
				"to_username", "吴小平"));
		MongoCursor<Document> cursor = iterable.iterator();
		while (cursor.hasNext()) {
			Document doc = cursor.next();
			System.out.println(doc.toString());
		}

	}

	public void importFromMysql() throws SolrServerException {

		String urlString = "http://solr.api.epweike.com:8080/msg";
		SolrServer solr = new HttpSolrServer(urlString);
		SolrQuery params = new SolrQuery("*:*");

		QueryResponse response = null;

		long total = 15830949;// 消息总数
		int size = 10000;// 单次遍历数据量

		// 遍历所有msg数据
		long lenth = (total / size) + 1;
		for (int i = 0; i <= lenth; i++) {

			params.setStart(i * size);
			params.setRows(size);
			response = solr.query(params);
			
			List<SolrDocument> solrDocs = null;
			solrDocs = response.getResults();
			
			List<Document> docs = new ArrayList<Document>();//每次插入mongodb的数据集
			// 拼装document
			for (int j = 0; j < size; j++) {
				Document doc = new Document();
				doc.put("msg_id", solrDocs.get(j).getFieldValue("msg_id"));
				doc.put("msg_pid", solrDocs.get(j).getFieldValue("msg_pid"));
				doc.put("uid", solrDocs.get(j).getFieldValue("uid"));
				doc.put("username", solrDocs.get(j).getFieldValue("username"));
				doc.put("to_uid", solrDocs.get(j).getFieldValue("to_uid"));
				doc.put("to_username",
						solrDocs.get(j).getFieldValue("to_username"));
				doc.put("msg_status",
						solrDocs.get(j).getFieldValue("msg_status"));
				doc.put("view_status",
						solrDocs.get(j).getFieldValue("view_status"));
				doc.put("title", solrDocs.get(j).getFieldValue("title"));
				doc.put("content", solrDocs.get(j).getFieldValue("content"));
				doc.put("view_status",
						solrDocs.get(j).getFieldValue("view_status"));
				doc.put("is_del", solrDocs.get(j).getFieldValue("is_del"));
				doc.put("classify", solrDocs.get(j).getFieldValue("classify"));
				doc.put("collect", solrDocs.get(j).getFieldValue("collect"));
				doc.put("update_time",
						solrDocs.get(j).getFieldValue("update_time"));
				docs.add(doc);
			}
			insertMany(docs, "msg");
		}
	}

	public static void main(String[] paramArrayOfString) {
		try {
			// 计时开始
			long t1 = System.currentTimeMillis();
			MongoDbUtils mDbUtils = new MongoDbUtils();
			mDbUtils.importFromMysql();

			// 计时结束
			long t2 = System.currentTimeMillis(); // 排序后取得当前时间
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(t2 - t1);
			System.out.println("耗时: " + c.get(Calendar.MINUTE) + "分 "
					+ c.get(Calendar.SECOND) + "秒 "
					+ c.get(Calendar.MILLISECOND) + " 毫秒");
		} catch (Exception localException) {
			System.out.println(localException);
		}
	}
}