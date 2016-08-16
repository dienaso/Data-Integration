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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.epweike.mapper.CompanyMapper;
import com.epweike.model.Company;
import com.epweike.model.Webpage;
import com.epweike.util.OcrUtils;
import com.epweike.util.SpringUtils;
import com.epweike.util.WebpageUtils;

/**
 * @author wuxp
 *
 */
public class ParseCompany {

	private static CompanyMapper companyMapper = SpringUtils
			.getBean("companyMapper");

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
			List<Company> record = new ArrayList<Company>();

			// 遍历列表
			Iterator<Webpage> it = list.iterator();
			while (it.hasNext()) {
				
				Webpage webpage = it.next();

				Company company = new Company();

				Document doc = null;
				try {
					if (webpage != null && webpage.getBaseurl() != null
							&& webpage.getBaseurl().contains("/co/") && webpage.getContent() != null) {
						doc = Jsoup.parse(new String(webpage.getContent(), "GBK"));
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				
				if(doc != null) {
					company.setRemarks(doc.select("#contact").html());// 基本资料原始html存作为备注

					Element h1 = doc.select("h1").first();// 公司名称

					try {
						if (h1 != null) {
							company.setName(h1.text());
							String intro = doc.select(".boxcontent").text();// 公司名称简介
							company.setIntro(intro);

							// 解析基本资料
							Elements names = doc.select("dt");
							Elements values = doc.select("dd");
							// 基本资料封装map
							Map<String, String> companyMap = new HashMap<String, String>();
							int index = 0;
							for (Element name : names) {
								if ("电话".equals(name.text())
										|| "总经理手机".equals(name.text())
										|| "座机电话".equals(name.text())
										|| "联系人电话".equals(name.text())
										|| "联系人手机".equals(name.text())
										|| "销售经理".equals(name.text())
										|| "业务经理手机".equals(name.text())
										|| "厂长手机".equals(name.text())
										|| "经理".equals(name.text())) {

									companyMap.put("phone_img", values.get(index)
											.html());

									// 获取图片路径
									String imgUrl = values.get(index)
											.getElementsByTag("img").attr("src");
									// 图片识别
									String phone = "";
									if (imgUrl != null
											&& imgUrl.contains("http://")) {
										phone = OcrUtils.ocr(imgUrl);
									} else {// 不是图片直接取文本
										phone = values.get(index).text();
									}

									companyMap.put(name.text(), phone);
								} else if ("电子邮件".equals(name.text())) {
									companyMap.put(name.text(), values.get(index)
											.html());
								} else {
									companyMap.put(name.text(), values.get(index)
											.text());
								}
								index++;
							}

							// map中取出数据赋值于company对象
							company = parse(companyMap, company);

							// companyMapper.insert(company);
							record.add(company);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
			// 插入数据库
			if(record != null && record.size() > 0){
				int result = companyMapper.insertBatch(record);
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

	public static Company parse(Map<String, String> companyMap, Company company) {

		if (companyMap.get("主营产品") != null) {
			company.setMainProducts(companyMap.get("主营产品"));
		} else if (companyMap.get("主要生产") != null) {
			company.setMainProducts(companyMap.get("主要生产"));
		}

		company.setRegisterDate(companyMap.get("成立日期"));

		if (companyMap.get("公司注册地址") != null) {
			company.setRegisterAddr(companyMap.get("公司注册地址"));
		} else if (companyMap.get("单位注册地址") != null) {
			company.setRegisterAddr(companyMap.get("单位注册地址"));
		} else if (companyMap.get("工厂注册地址") != null) {
			company.setRegisterAddr(companyMap.get("工厂注册地址"));
		} else if (companyMap.get("旅行社注册地址") != null) {
			company.setRegisterAddr(companyMap.get("旅行社注册地址"));
		} else if (companyMap.get("店铺注册地址") != null) {
			company.setRegisterAddr(companyMap.get("店铺注册地址"));
		} else if (companyMap.get("书店注册地址") != null) {
			company.setRegisterAddr(companyMap.get("书店注册地址"));
		} else if (companyMap.get("学校注册地址") != null) {
			company.setRegisterAddr(companyMap.get("学校注册地址"));
		}

		if (companyMap.get("联系人") != null) {
			company.setPrincipal(companyMap.get("联系人"));
		} else if (companyMap.get("经理") != null) {
			company.setPrincipal(companyMap.get("经理"));
		} else if (companyMap.get("厂长") != null) {
			company.setPrincipal(companyMap.get("厂长"));
		} else if (companyMap.get("老板") != null) {
			company.setPrincipal(companyMap.get("老板"));
		} else if (companyMap.get("总经理") != null) {
			company.setPrincipal(companyMap.get("总经理"));
		} else if (companyMap.get("业务经理") != null) {
			company.setPrincipal(companyMap.get("业务经理"));
		} else if (companyMap.get("销售经理") != null) {
			company.setPrincipal(companyMap.get("销售经理"));
		} else if (companyMap.get("加盟经理") != null) {
			company.setPrincipal(companyMap.get("加盟经理"));
		} else if (companyMap.get("销售工程师") != null) {
			company.setPrincipal(companyMap.get("销售工程师"));
		} else if (companyMap.get("董事长") != null) {
			company.setPrincipal(companyMap.get("董事长"));
		}

		company.setZipCode(companyMap.get("邮政编码"));

		if (companyMap.get("座机电话") != null) {
			company.setPhone(companyMap.get("座机电话"));
		} else if (companyMap.get("联系人电话") != null) {
			company.setPhone(companyMap.get("联系人电话"));
		} else if (companyMap.get("联系人手机") != null) {
			company.setPhone(companyMap.get("联系人手机"));
		} else if (companyMap.get("销售经理") != null) {
			company.setPhone(companyMap.get("销售经理"));
		} else if (companyMap.get("业务经理手机") != null) {
			company.setPhone(companyMap.get("业务经理手机"));
		} else if (companyMap.get("销售经理手机") != null) {
			company.setPhone(companyMap.get("销售经理手机"));
		} else if (companyMap.get("厂长手机") != null) {
			company.setPhone(companyMap.get("厂长手机"));
		} else if (companyMap.get("电话") != null) {
			company.setPhone(companyMap.get("电话"));
		} else if (companyMap.get("经理") != null) {
			company.setPhone(companyMap.get("经理"));
		}

		if (companyMap.get("电子邮件") != null) {
			company.setEmail(companyMap.get("电子邮件"));
		}

		if (companyMap.get("在线留言QQ") != null) {
			company.setChat(companyMap.get("在线留言QQ"));
		}

		if (companyMap.get("注册资金") != null) {
			company.setRegisterCapital(companyMap.get("注册资金"));
		}

		if (companyMap.get("员工数量") != null) {
			company.setStaff(companyMap.get("员工数量"));
		}

		if (companyMap.get("法人代表") != null) {
			company.setLegalPerson(companyMap.get("法人代表"));
		}

		if (companyMap.get("经营模式") != null) {
			company.setBusinessModel(companyMap.get("经营模式"));
		}

		if (companyMap.get("营业范围") != null) {
			company.setBusinessScope(companyMap.get("营业范围"));
		}

		if (companyMap.get("网站") != null) {
			company.setWebsite(companyMap.get("网站"));
		}

		company.setCreditRating(companyMap.get("信用等级"));

		// String regEx="[^0-9]";
		// Pattern p = Pattern.compile(regEx);
		// Matcher m = p.matcher(companyMap.get("企业人气"));
		company.setViews(companyMap.get("企业人气"));

		if (companyMap.get("所属分类") != null) {
			company.setCategory(companyMap.get("所属分类").replace("企业名录", ""));
		}

		if (companyMap.get("所属城市") != null) {
			company.setCity(companyMap.get("所属城市").replace("企业名录", ""));
		}

		company.setType("1");

		company.setPhoneImg(companyMap.get("phone_img"));

		return company;
	}

	public static void main(String[] args) throws IOException {
		ParseCompany chJob = new ParseCompany();
		chJob.execute();
	}

}
