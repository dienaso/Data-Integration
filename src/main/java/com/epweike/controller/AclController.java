package com.epweike.controller;

import com.epweike.model.Menus;
import com.epweike.model.RetModel;
import com.epweike.model.Roles;
import com.epweike.service.MenusService;
import com.epweike.service.RolesService;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuxp
 */
@Controller
@RequestMapping("/acl")
public class AclController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(AclController.class);

	@Autowired
	private MenusService menusService;

	@Autowired
	private RolesService rolesService;

	@Autowired
	MutableAclService mutableAclService;
	
	@Autowired
	JdbcMutableAclService jdbcMutableAclService;

	/**
	 * @Description:获取角色
	 * 
	 * @author 吴小平
	 * @version 创建时间：Nov 26, 2015 3:05:16 PM
	 */
	public List<Roles> getRoles() {
		List<Roles> roles = this.rolesService.selectAll();
		return roles;
	}

	@RequestMapping(value = { "menu" })
	public String list(Model model) {
		model.addAttribute("roles", getRoles());
		return "acl/menu";
	}

	@RequestMapping(value = "getMenu", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String menu(HttpServletRequest request)
			throws IOException {

		return getJsonTree(0);

	}
	
	@RequestMapping(value = "getAceByRole", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String getAceByRole(HttpServletRequest request)
			throws IOException {
		// 获取角色
		String role = request.getParameter("role");
		List<Menus> list = this.menusService.getMenusByRole(role);
		
		JSONArray  json = JSONArray.fromObject(list);
		
		return json.toString();
	}

	@RequestMapping(value = "saveMenuAcl", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, readOnly = false)
	public @ResponseBody RetModel saveMenuAcl(HttpServletRequest request)
			throws IOException {

		RetModel retModel = new RetModel();// 返回结果对象

		// 获取角色
		String role = request.getParameter("role");
		// 获取选中菜单权限
		String menu_ids = request.getParameter("menu_ids");

		if (role == null || menu_ids == null || "".equals(role)
				|| "".equals(menu_ids)) {
			retModel.setMsg("请选择角色或菜单权限！");
			retModel.setFlag(false);
			return retModel;
		}

		// 清空该角色所有菜单权限
		List<Menus> menuList = this.menusService.selectAll();
		Iterator<Menus> it2 = menuList.iterator();
		while (it2.hasNext()) {
			Menus menus = (Menus) it2.next();
			deletePermission(menus,
					new GrantedAuthoritySid(role),
					BasePermission.READ);
		}

		// 遍历新增ace记录
		String[] mArr = menu_ids.split(",");

		for (int j = 0; j < mArr.length; j++) {
			System.out.println("ace--:"+mArr[j]);
			Menus menus = new Menus();
			menus.setId(Integer.parseInt(mArr[j]));
			addPermission(menus,
					new GrantedAuthoritySid(role),
					BasePermission.READ);
		}

		retModel.setUpdateFucceed();
		return retModel;
	}

	public void addPermission(Menus menus, Sid recipient, Permission permission) {
		MutableAcl acl;
		ObjectIdentity oid = new ObjectIdentityImpl(Menus.class, menus.getId());
		try {
			acl = (MutableAcl) mutableAclService.readAclById(oid);
		} catch (NotFoundException nfe) {
			acl = mutableAclService.createAcl(oid);
		}
		acl.insertAce(0, permission, recipient, true);
		mutableAclService.updateAcl(acl);
		logger.debug("Added permission " + permission + " for Sid " + recipient
				+ " menus " + menus);
	}

	public void deletePermission(Menus menus, Sid recipient,
			Permission permission) {
		ObjectIdentity oid = new ObjectIdentityImpl(Menus.class, menus.getId());
		MutableAcl acl = null;
		try {
			acl = (MutableAcl) mutableAclService.readAclById(oid);
			List<AccessControlEntry> entries = acl.getEntries();
			for (int i = 0; i < entries.size(); i++) {
				if (entries.get(i).getSid().equals(recipient)
						&& entries.get(i).getPermission().equals(permission)) {
					acl.deleteAce(i);
				}
			}
			mutableAclService.updateAcl(acl);
		} catch (NotFoundException nfe) {
		}
		logger.debug("Deleted permission " + permission + " for Sid " + recipient
				+ " menus " + menus);
	}

	/**
	 * 递归获得jsTree的json字串
	 * 
	 * @param pid
	 * 
	 * @return
	 */
	private String getJsonTree(int pid) {
		StringBuffer str = new StringBuffer();
		str.append("[{\"id\":\"0\",\"text\":\"菜单\", \"state\" : { \"opened\" : \"true\", \"selected\":\"true\"}, \"children\":[");// 起始
		// 一级菜单
		List<Menus> menusList1 = menusService.select(new Menus(pid));
		if (menusList1 != null) {
			for (int i = 0; i < menusList1.size(); i++) {
				Menus menus = menusList1.get(i);
				// 有子节点
				if (menus.getHasChild() == 1) {
					str.append("{\"id\":\"" + menus.getId() + "\", \"text\":\""
							+ menus.getName() + "\", \"icon\":\""
							+ menus.getIcon() + "\", ");
					str.append("\"children\":[");
					// 查出它的子节点
					List<Menus> menusList2 = menusService.select(new Menus(
							menus.getId()));
					// 遍历它的子节点
					for (int j = 0; j < menusList2.size(); j++) {
						Menus menus2 = menusList2.get(j);
						// 还有子节点(递归调用)
						if (menus2.getHasChild() == 1) {
							this.getJsonTree(menus2.getPid());
						} else {

							str.append("{\"id\":\"" + menus2.getId()
									+ "\", \"text\":\"" + menus2.getName()
									+ "\", \"icon\":\"" + menus2.getIcon()
									+ "\"}");
							if (j < menusList2.size() - 1) {
								str.append(",");
							}
						}
					}
					str.append("]}");
				} else {
					str.append("{\"id\":\"" + menus.getId() + "\", \"text\":\""
							+ menus.getName() + "\", \"icon\":\""
							+ menus.getIcon() + "\"}");
				}
				if (i < menusList1.size() - 1) {
					str.append(",");
				}
			}
		}
		str.append("]}]");// 截止

		logger.info("-----------------------" + str.toString());

		return str.toString();
	}

}
