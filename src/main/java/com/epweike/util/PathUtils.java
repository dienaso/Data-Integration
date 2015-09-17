/**
 * Copyright 2010-2015 epweike.com.
 * @Description: TODO
 * @author 吴小平
 * @date Sep 17, 2015 9:45:04 AM 
 * 
 */
package com.epweike.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * java路径处理
 * 
 * @author wuxp
 * 
 */
public class PathUtils {
	
	/**
	 * 返回当前webroot路径
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getWebRootPath() {
		return System.getProperty("webapp.root");
	}

	/**
	 * 返回当前classpath路径
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getClassPath() throws UnsupportedEncodingException {
		URL url = PathUtils.class.getProtectionDomain().getCodeSource()
				.getLocation();
		String filePath = URLDecoder.decode(url.getPath(), "UTF-8");
		if (filePath.endsWith(".jar"))
			filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
		return filePath;
	}

	/**
	 * 返回当前classpath路径
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getClassPath(String relativePath)
			throws UnsupportedEncodingException {
		URL url = PathUtils.class.getProtectionDomain().getCodeSource()
				.getLocation();
		String filePath = URLDecoder.decode(url.getPath(), "UTF-8");
		if (filePath.endsWith(".jar"))
			filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
		return filePath;
	}

	/**
	 * 返回当前项目路径
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String getProjectPath() throws IOException {
		File directory = new File(". ");
		// 取得当前路径
		return directory.getCanonicalPath();
	}

	/**
	 * 返回当WEB-INF路径
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String getXmlPath() {
		String path = Thread.currentThread().getContextClassLoader()
				.getResource("").toString();
		path = path.replace('/', '\\'); // 将/换成\
		path = path.replace("file:", ""); // 去掉file:
		path = path.replace("classes\\", ""); // 去掉class\
		path = path.substring(1); // 去掉第一个\,如 \D:\JavaWeb...
		return path;
	}

	/**
	 * 获取项目在服务其中的真实路径的工具类
	 * 
	 * 这是在web项目中，获取项目实际路径的最佳方式，在windows和linux系统下均可正常使用
	 * 
	 */
	public static String getRootPath() {

		String classPath = PathUtils.class.getResource("/")
				.getPath();
		System.out.println("classPath---" + classPath);
		String rootPath = "";
		// windows下
		if ("\\".equals(File.separator)) {
			rootPath = classPath.substring(1,
					classPath.indexOf("/WEB-INF/classes"));
			rootPath = rootPath.replace("/", "\\");
		}
		// linux下
		if ("/".equals(File.separator)) {
			rootPath = classPath.substring(0,
					classPath.indexOf("/WEB-INF/classes"));
			rootPath = rootPath.replace("\\", "/");
		}
		return rootPath;
	}

	/**
	 * 获取一个Class的绝对路径
	 * 
	 * @param clazz
	 * @return Class的绝对路径
	 * 
	 */
	public static String getPathByClass(Class<?> clazz) {
		String path = null;
		try {
			URI uri = clazz.getResource("").toURI();
			File file = new File(uri);
			path = file.getCanonicalPath();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return path;
	}

	/**
	 * 获取一个文件相对于一个Class相对的绝对路径
	 * 
	 * @param clazz
	 *            Class对象
	 * @param relativePath
	 *            Class对象的相对路径
	 * @return 文件绝对路径
	 */
	public static String getPathByClass(Class<?> clazz, String relativePath) {
		String filePath = null;
		String clazzPath = getPathByClass(clazz);
		StringBuffer sbPath = new StringBuffer(clazzPath);
		sbPath.append(File.separator);
		sbPath.append(relativePath);
		File file = new File(sbPath.toString());
		try {
			filePath = file.getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return filePath;
	}

	public static void main(String[] args) throws IOException {
		System.out.println(PathUtils.getClassPath());
	}
}
