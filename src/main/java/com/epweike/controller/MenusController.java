package com.epweike.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.epweike.model.Menus;
import com.epweike.model.RetModel;
import com.epweike.service.MenusService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wuxp
 */
@Controller
@RequestMapping("/menus")
public class MenusController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(MenusController.class);

	@Autowired
	private MenusService menusService;

	public List<Menus> menusList;

	@RequestMapping(value = { "list" })
	public String list(Model model) {
		return "menus/list";
	}

	/**
	 * 递归获得jsTree的json字串
	 * 
	 * @param pid
	 * 
	 * @return
	 */
	private String getJson(int pid) {
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
							this.getJson(menus2.getPid());
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

	@RequestMapping(value = "get", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody String paginationDataTables(HttpServletRequest request)
			throws IOException {

		return getJson(0);

	}

	private int hasChild(int pid) {

		int has_child = 0;

		Menus menus = new Menus(pid);
		int count = 0;
		count = this.menusService.count(menus);
		if (count > 0)
			has_child = 1;

		return has_child;
	}

	@RequestMapping(value = "operation", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody RetModel operation(HttpServletRequest request)
			throws IOException {

		RetModel retModel = new RetModel();// 返回结果对象

		String method = request.getParameter("method");// 获取操作方法
		int id = 0;
		if (request.getParameter("id") != null)
			id = Integer.parseInt(request.getParameter("id"));
		int parent = 0;
		if (request.getParameter("parent") != null)
			parent = Integer.parseInt(request.getParameter("parent"));
		String name = request.getParameter("text");

		int hasChild = 0;// 是否含有子级

		Menus menus = new Menus();

		try {
			switch (method) {
			case "delete_node":
				Menus m1 = this.menusService.get(id);// 缓存当前菜单，以获取pid
				this.menusService.deleteByPrimaryKey(id);
				// 同时删除子级菜单
				this.menusService.delete(new Menus(id));
				// 判断是否还有子级
				hasChild = hasChild(m1.getPid());
				// 更新父级has_child字段
				Menus m2 = new Menus();
				m2.setId(m1.getPid());
				m2.setHasChild(hasChild);
				this.menusService.updateBySelective(m2);

				break;
			case "create_node":
				menus.setPid(id);
				menus.setHasChild(0);
				menus.setName(name);
				menus.setUrl("javascript:void(0)");
				menus.setOnTime(new Date());
				menus.setTarget("_self");
				this.menusService.insertUseGeneratedKeys(menus);
				retModel.setObj(menus);
				// 更新父级has_child字段
				Menus pm1 = new Menus();
				pm1.setId(id);
				pm1.setHasChild(1);
				this.menusService.updateBySelective(pm1);
				break;
			case "rename_node":
				menus.setId(id);
				menus.setName(name);
				this.menusService.updateBySelective(menus);
				break;
			case "move_node":
				menus = this.menusService.get(id);
				int oPid = menus.getPid();// 获取原父级id
				menus.setId(id);
				menus.setPid(parent);
				this.menusService.updateBySelective(menus);
				// 判断原父级是否还有子级
				hasChild = hasChild(oPid);
				// 更新原父级has_child字段
				Menus pm2 = new Menus();
				pm2.setId(oPid);
				pm2.setHasChild(hasChild);
				this.menusService.updateBySelective(pm2);
				// 更新当前父级has_child字段
				Menus pm3 = new Menus();
				pm3.setId(parent);
				pm3.setHasChild(1);
				this.menusService.updateBySelective(pm3);
				break;
			case "copy_node":
				menus = this.menusService.get(id);
				Menus m = new Menus();
				m.setPid(parent);
				m.setHasChild(0);
				m.setIcon(menus.getIcon());
				m.setName(menus.getName());
				m.setOnTime(new Date());
				m.setUrl(menus.getUrl());
				m.setTarget(menus.getTarget());
				m.setSort(menus.getSort());
				this.menusService.insert(m);
				// 更新父级has_child字段
				Menus pm4 = new Menus();
				pm4.setId(parent);
				pm4.setHasChild(1);
				this.menusService.updateBySelective(pm4);
				break;

			default:
				break;
			}
		} catch (AccessDeniedException e) {
			e.printStackTrace();
			retModel.setAccessDenied(e);
			return retModel;
		} catch (Exception e) {
			e.printStackTrace();
			retModel.setMsg("操作失败！");
			retModel.setFlag(false);
			retModel.setObj(e);
			return retModel;
		}
		retModel.setMsg("操作成功！");
		retModel.setFlag(true);
		return retModel;
	}

	@RequestMapping(value = "del", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody int del(HttpServletRequest request) throws IOException {

		// 获取删除主键
		int id = Integer.parseInt(request.getParameter("id"));

		System.out.println("--------------------" + id);

		int result = this.menusService.deleteByPrimaryKey(id);

		return result;
	}
}
