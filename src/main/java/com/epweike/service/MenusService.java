package com.epweike.service;

import java.util.Collection;

import com.epweike.mapper.MenusMapper;
import com.epweike.model.Menus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
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
		mapper.insertUseGeneratedKeys(menus);
	}
	
	/**  
	* @Description:过滤菜单权限
	*  
	* @author  吴小平
	* @version 创建时间：Nov 24, 2015 10:53:50 AM
	*/  
	@PostFilter("hasPermission(filterObject, 'READ') or hasRole('ADMIN')")
	public Collection<Menus> getFilterMenus(Menus menus) {    
	  Collection<Menus> filteredMenus = mapper.select(menus);
	  return filteredMenus;    
	}
}
