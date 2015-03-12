package com.epweike.service;

import com.epweike.model.Sysuser;
import com.github.abel533.entity.EntityMapper;
import com.github.abel533.mapper.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wuxp
 */
@Service
public class SysuserService extends BaseService<Sysuser> {

	@Autowired
	private Mapper<Sysuser> sysuserMapper;

	@Autowired
	private EntityMapper entityMapper;

	public void test() {
		int result = sysuserMapper.selectCount(null);
		System.out.println(result);
	}

	public void testEntityMapper() {
		Sysuser sysuser = entityMapper.selectByPrimaryKey(Sysuser.class, 1);
		int count = entityMapper.count(new Sysuser());
		System.out.println(sysuser.toString());
		System.out.println(count);
	}

}
