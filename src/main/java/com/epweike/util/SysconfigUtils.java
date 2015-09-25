package com.epweike.util;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.epweike.mapper.SysconfigMapper;
import com.epweike.model.Sysconfig;

public class SysconfigUtils {
	public final static Logger log = Logger.getLogger(SysconfigUtils.class);

	private static SysconfigMapper sysconfigMapper = SpringUtils
			.getBean("sysconfigMapper");

	public static HashMap<String, String> sysconfigMap;

	/**
	 * 根据参数名获取参数值
	 * 
	 * @param varName
	 */
	public static String getVarValue(String varName) {

		// 初始化参数
		initSysconfig();
		String varValue = "";
		if (sysconfigMap != null && sysconfigMap.get(varName) != null) {
			varValue = sysconfigMap.get(varName).toString();
		}
		return varValue;
	}

	public static void initSysconfig() {
		if (sysconfigMap == null) {
			sysconfigMap = new HashMap<String, String>();
			//获取所有系统参数
			List<Sysconfig> list = sysconfigMapper.selectAll();
			String varName = "", varValue = "";
			if (list != null && list.size() > 0) {
				Sysconfig sysconfig = new Sysconfig();
				for (int i = 0; i < list.size(); i++) {
					sysconfig = (Sysconfig) list.get(i);
					if(sysconfig != null) {
						varName = sysconfig.getVarName();
						varValue = sysconfig.getVarValue();
					}
					sysconfigMap.put(varName, varValue);
				}
			}
		}
	}

}
