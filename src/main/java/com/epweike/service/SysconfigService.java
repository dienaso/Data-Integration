package com.epweike.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.epweike.model.Sysconfig;

/**
 * 
 * @Description: 系统参数管理
 * @author wuxp
 * 
 */
@Service
public class SysconfigService extends BaseService<Sysconfig> {
	public final Logger log = Logger.getLogger(this.getClass());

}
