/**
 * Copyright 2010-2015 epweike.com.
 * @Description: 
 * @author 吴小平
 * @date Sep 16, 2015 8:41:18 AM 
 * 
 */
package com.epweike.util;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author wuxp
 *
 */
public class FreeMarkertUtils {

	/**
	 * @param templateName
	 *            模板名字
	 * @param dataModel
	 *            用于在模板内输出结果集
	 * @param out
	 *            输出对象 具体输出到哪里
	 */
	public static void processTemplate(String templateName,
			Map<?, ?> dataModel, Writer out) {
		try {
			// 创建一个合适的Configration对象
			Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
			// 模板路径
			String templatePath = PathUtils.getWebRootPath() + File.separator
					+ "WEB-INF" + File.separator + "template";

			configuration.setDirectoryForTemplateLoading(new File(templatePath));
			configuration.setDefaultEncoding("UTF-8"); // 这个一定要设置，不然在生成的页面中 会乱码
			// 获取或创建一个模版。
			Template template = configuration.getTemplate("home.ftl");

			template.process(dataModel, out);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}

}
