package com.epweike.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.epweike.mapper.WebpageMapper;
import com.epweike.model.Webpage;

public class WebpageUtils {
	public final static Logger log = Logger.getLogger(WebpageUtils.class);
	
	private static WebpageMapper webpageMapper = (WebpageMapper) SpringUtil2.getInstance().getBean("webpageMapper");  

	public static HashMap<String, String> sysconfigMap;

	/**
	 * 获取已采集页面
	 * 
	 * 
	 */
	public static List<Webpage> selectByPage(int offset, int limit) {
		System.out.println("offset:"+offset+",limit:"+limit);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("offset", offset);
		map.put("limit", limit);
		List<Webpage> list = webpageMapper.selectByPage(map);
		return list;
	}
	
	public static int count() {
		int count = webpageMapper.selectCount(new Webpage());
		return count;
	}
}
