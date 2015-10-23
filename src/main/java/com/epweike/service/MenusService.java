package com.epweike.service;

import com.epweike.mapper.MenusMapper;
import com.epweike.model.Menus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wuxp
 */
@Service
public class MenusService extends BaseService<Menus> {

	@Autowired
	private MenusMapper mapper;
	
	/**  
	* @Description:
	*  
	* @author  吴小平
	* @version 创建时间：Oct 22, 2015 5:23:54 PM
	*/  
	public void insertUseGeneratedKeys(Menus menus) {
		// TODO Auto-generated method stub
		mapper.insertUseGeneratedKeys(menus);
	}
}
