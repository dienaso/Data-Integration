/**
 * Copyright 2010-2015 epweike.com.
 * @author 吴小平
 * @date Sep 16, 2015 3:17:32 PM 
 * 
 */
package com.epweike.quartz.job;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.epweike.mapper.EmployMapper;
import com.epweike.model.Employ;
import com.epweike.model.Webpage;
import com.epweike.util.SpringUtils;
import com.epweike.util.WebpageUtils;

/**
 * @author wuxp
 *
 */
public class ParseEmploy {

	private static EmployMapper employMapper = SpringUtils
			.getBean("employMapper");

	public List<Webpage> webpageList;

	/**
	 * 实现execute方法
	 * 
	 * @throws IOException
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public void execute() throws IOException {

		long t1 = System.currentTimeMillis();// 计时开始

		// 获取数据
		int count = WebpageUtils.count();
		int limit = 2000;
		// 分页处理防止内存溢出
		for (int i = 0; i <= count / limit; i++) {
			List<Webpage> list = new ArrayList<Webpage>();

			list = WebpageUtils.selectByPage(limit * i, limit);

			// 批量插入列表
			List<Employ> record = new ArrayList<Employ>();

			// 遍历列表
			Iterator<Webpage> it = list.iterator();
			while (it.hasNext()) {

				Webpage webpage = it.next();

				if (webpage != null && webpage.getBaseurl() != null
						&& webpage.getBaseurl().contains("employ/show")) {
					Employ employ = new Employ();
					// 开始解析html
					Document doc = null;
					try {
						doc = Jsoup.parse(new String(webpage.getContent(),
								"UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}

					Elements mainwrap = doc.select(".mainwrap");

					if (mainwrap != null) {

						employ.setTitle(mainwrap.select("h2").text());
						employ.setNum(mainwrap
								.select(".col-sm-3.col-md-3.col-lg-3").first()
								.select("span").text());// 招聘人数
						employ.setWorkplace(mainwrap
								.select(".col-sm-3.col-md-3.col-lg-3").eq(1)
								.select("span").text());// 工作地点
						employ.setCompanyName(mainwrap
								.select(".col-sm-6.col-md-6.col-lg-6")
								.select("span").text());// 公司名称
						int views = Integer.parseInt(mainwrap.select("h3")
								.select("span").text());// 人气
						employ.setViews(views);// 人气
						String pubDate = mainwrap.select(".employ-head-date")
								.select("div").select(".row.nopadding").text();// 发布日期
						employ.setPubDate(pubDate);
						employ.setContent(mainwrap.select(".projectcontent")
								.text());// 内容描述

						employ.setPublisher(mainwrap
								.select(".rightbar_title.rightbar-author.nowrap")
								.select("a").text());// 发布人

						Elements rightbarLi = mainwrap.select(
								".rightbar-author-content").select("li");
						employ.setLinkman(rightbarLi.eq(0).select("span")
								.text());// 联系人
						employ.setAddress(rightbarLi.eq(1).select("span")
								.text());// 地址
						employ.setPhone(rightbarLi.eq(2).select("span").text());// 电话
						employ.setEmail(rightbarLi.eq(3).select("span").text());// 邮箱
						employ.setZipCode(rightbarLi.eq(4).select("span")
								.text());// 邮编
						employ.setFax(rightbarLi.eq(5).select("span").text());// 传真

						record.add(employ);
					}

				}

			}
			// 插入数据库
			if (record != null && record.size() > 0) {
				int result = employMapper.insertBatch(record);
				System.out.println("成功插入数量：" + result);
			}
		}
		
		
		long t2 = System.currentTimeMillis(); // 计时结束
		// 计时结束
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(t2 - t1);
		System.out.println("耗时: " + c.get(Calendar.MINUTE) + "分 "
				+ c.get(Calendar.SECOND) + "秒 " + c.get(Calendar.MILLISECOND)
				+ " 毫秒");
	}

	public static void main(String[] args) throws IOException {
		ParseEmploy chJob = new ParseEmploy();
		chJob.execute();
	}

}
