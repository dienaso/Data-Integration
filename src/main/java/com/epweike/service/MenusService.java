package com.epweike.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.epweike.mapper.MenusMapper;
import com.epweike.model.Menus;
import com.epweike.util.QueryUtils;

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
	@PostFilter("hasPermission(filterObject, 'READ') or hasRole('管理员')")
	public Collection<Menus> getFilterMenus(Menus menus) {    
	  Collection<Menus> filteredMenus = mapper.select(menus);
	  return filteredMenus;    
	}
	
	public List<Menus> getMenusByRole(String role){
		String sql = "SELECT object_id_identity AS id,has_child AS hasChild FROM acl_entry e LEFT JOIN acl_object_identity oi ON e.acl_object_identity=oi.id AND oi.object_id_class=1 LEFT JOIN acl_sid s ON e.sid=s.id LEFT JOIN menus m ON oi.object_id_identity=m.id WHERE s.sid=?";
		QueryUtils<Menus> queryRunnerUtils = new QueryUtils<Menus>(
				Menus.class);

		Object params[] = { role };
		List<Menus> list = new ArrayList<Menus>();
	
		try {
			list = queryRunnerUtils.select(sql, params, "dataSource");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
	
}
