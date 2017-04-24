package com.epweike.bank;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpClientTest {
	@Test
	public void jUnitTest() throws Exception {
		String request = null;
		Map<String, String> param = new TreeMap<String, String>();
		String day = new SimpleDateFormat("yyyyMMdd").format(new Date());
		int serno = (int) (100000000 + Math.random() * 900000000);// 测试使用随机数
																	// 商户建议使用序列号
																	// 防止重复
		String tranId = day + serno;
		// 加载参数 根据不同文档中不同接口字段定义传输参数
		param.put("tranId", tranId);
		param.put("tranTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		param.put("umsAcctType", "1");
		param.put("verifyType", "0030");
		param.put("acctNo", "6227001803662339");
		param.put("acctName", "吴小平");
		param.put("certNo", "35052119910345511");
		param.put("certType", "00");
		// 报文签名串拼接方法
		StringBuffer signData = new StringBuffer();
		for (Entry<String, String> entry : param.entrySet()) {
			signData.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		// 加签
		String signDataStr = signData.substring(0, signData.length() - 1);
		System.out.println("签名串：" + signDataStr);
		PrivateKey privateKey = CertificateUtils.getPrivateKey(
				new FileInputStream("F:/workspace/Eclipse/Data-Integration/src/test/java/com/epweike/bank/test.pfx"),
				null, "1");
		String sign = EncryptUtils.sign(signDataStr.getBytes("UTF-8"), privateKey);
		param.put("sign", sign);
		System.out.println("签名：" + sign);
		// 验签 发送时候无需调用
		// PublicKey publicKey = CertificateUtils.getPublicKey(new
		// FileInputStream("D:\\cer\\esa\\test2.cer"));
		// boolean a = EncryptUtils.verify(signDataStr.getBytes("UTF-8"),
		// Base64.decodeBase64(sign), publicKey);
		JSONObject json = JSONObject.fromObject(param);
		request = json.toString();
		System.out.println("param：" + signDataStr);
		// 请求地址 根据不同接口发送不同的地址 030000563为商户编号 ，生产时需要传递生产的商户编号
		post("http://218.5.69.214:8088/easserver/gateway/1/realNameVerify/030000563", request);
		// post("http://localhost:8080/easserver/gateway/1/realNameVerify/030000563",
		// request);
	}

	/**
	 * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
	 */
	public void post(String url, String json) {
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httppost = new HttpPost(url);
		try {
			StringEntity entity = new StringEntity(json, "utf-8");// 解决中文乱码问题
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			httppost.setEntity(entity);

			System.out.println("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity1 = response.getEntity();
				if (entity1 != null) {
					System.out.println("--------------------------------------");
					System.out.println("Response content: " + EntityUtils.toString(entity1, "UTF-8"));
					System.out.println("--------------------------------------");
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
