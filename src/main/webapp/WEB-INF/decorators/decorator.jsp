<%@page import="java.util.Iterator"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page
	import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@ page import="com.epweike.service.MenusService"%>
<%@ page import="com.epweike.model.Menus"%>
<%@ page import="java.util.List"%>

<%
	@SuppressWarnings("resource")
	ApplicationContext context = new ClassPathXmlApplicationContext(
			new String[] { "classpath:applicationContext.xml" });
	MenusService menusService = (MenusService) context
			.getBean("menusService");

	String id = "1";
	String pid = "0";
	if (request.getParameter("id") != null
			&& !request.getParameter("id").toString().equals("")) {
		id = request.getParameter("id");
	}
	if (request.getParameter("pid") != null
			&& !request.getParameter("pid").toString().equals("")) {
		pid = request.getParameter("pid");
	}

	System.out.println("id:" + id + " pid:" + pid);

	//一级菜单
	List<Menus> menus = menusService.select(new Menus(new Byte("1")));
%>
<!DOCTYPE html>
<html>
<head>
<title>Data Integration-数据集中查询分析平台</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="/common/matrix/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="/common/matrix/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="/common/matrix/css/uniform.css" />
<link rel="stylesheet" href="/common/matrix/css/select2.css" />
<link rel="stylesheet" href="/common/matrix/css/matrix-style.css" />
<link rel="stylesheet" href="/common/matrix/css/matrix-media.css" />
<link rel="stylesheet"
	href="/common/matrix/font-awesome/css/font-awesome.css" />
<link rel='stylesheet'
	href='http://fonts.useso.com/css?family=Open+Sans:400,700,800'
	type='text/css'>
<link rel="stylesheet"
	href="/common/matrix/css/bootstrap-datepicker3.css" />
<link rel="stylesheet"
	href="/common/matrix/css/dataTables.tableTools.css" />

<script src="/common/matrix/js/jquery.min.js"></script>
<script src="/common/matrix/js/bootstrap.min.js"></script>
<script src="/common/matrix/js/matrix.js"></script>
<script src="/common/matrix/js/jquery.dataTables.js"></script>
<script src="/common/matrix/js/jquery.ui.custom.js"></script>
<script src="/common/matrix/js/jquery.uniform.js"></script>
<script src="/common/matrix/js/select2.min.js"></script>
<script src="/common/matrix/js/dataTables.tableTools.js"></script>
<script src="/common/matrix/js/bootstrap-datepicker.js"></script>
<script
	src="/common/matrix/js/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="/common/jqGrid/js/jquery.jqGrid.min.js"></script>

<sitemesh:write property='head' />
</head>
<body>

	<!--Header-part-->
	<div id="header">
		<h1>
			<a href="/">Data Integration</a>
		</h1>
	</div>
	<!--close-Header-part-->

	<!--top-Header-menu-->
	<div id="user-nav" class="navbar navbar-inverse">
		<ul class="nav">
			<li class="dropdown" id="profile-messages"><a title="" href="#"
				data-toggle="dropdown" data-target="#profile-messages"
				class="dropdown-toggle"><i class="icon icon-user"></i> <span
					class="text">Welcome <sec:authentication property="name" /></span><b
					class="caret"></b></a>
				<ul class="dropdown-menu">
					<li><a href="javascript:void(0)"><i class="icon-user"></i>
							My Profile</a></li>
					<li class="divider"></li>
					<li><a href="javascript:void(0)"><i class="icon-check"></i>
							My Tasks</a></li>
					<li class="divider"></li>
					<li><a href="/j_spring_security_logout"><i
							class="icon-key"></i> Log Out</a></li>
				</ul></li>
			<li class=""><a title="" href="/j_spring_security_logout"><i
					class="icon icon-share-alt"></i> <span class="text">Logout</span></a></li>
		</ul>
	</div>
	<!--close-top-Header-menu-->
	<!--start-top-serch
<div id="search">
  <input type="text" placeholder="Search here..."/>
  <button type="submit" class="tip-bottom" title="Search"><i class="icon-search icon-white"></i></button>
</div>-->
	<!--close-top-serch-->
	<!--sidebar-menu-->
	<div id="sidebar">
		<a href="#" class="visible-phone"><i class="icon icon-home"></i>
			Dashboard</a>
		<ul>
			<%
				//循环遍历一级菜单
				if (menus != null && menus.size() > 0) {
					for (Iterator<Menus> it = menus.iterator(); it.hasNext();) {
						Menus menu = it.next();
						System.out.println("一级菜单对象：" + menu.toString());
						String url = menu.getUrl();
						if (!"#".equals(url)) {
							url = menu.getUrl() + "?id=" + menu.getId() + "&pid="
									+ menu.getPid();
						}
						String icon = menu.getIcon();
						String name = menu.getName();
			%>
			<%
				//二级菜单
						List<Menus> two_menus = menusService.select(new Menus(menu
								.getId()));
						if (two_menus != null && two_menus.size() > 0) { //存在二级菜单
							if (menu.getId().toString().equals(pid)) { //当一级前菜单且存在子级菜单
			%>

			<li class="submenu open active"><a href="<%=url%>"><i
					class="<%=icon%>"></i> <span><%=name%></span><span
					class="label label-important"><%=two_menus.size()%></span></a>
				<ul style="display: block;">

					<%
						} else {
					%>

					<li class="submenu"><a href="<%=url%>"><i
							class="<%=icon%>"></i> <span><%=name%></span><span
							class="label label-important"><%=two_menus.size()%></span></a>
						<ul style="display: none;">

							<%
								}
							%>
							<%
								for (Iterator<Menus> two_it = two_menus.iterator(); two_it
													.hasNext();) {
												Menus two_menu = two_it.next();
												System.out
														.println("二级菜单对象：" + two_menus.toString());
												String two_url = two_menu.getUrl() + "?id="
														+ two_menu.getId() + "&pid="
														+ two_menu.getPid();
												String two_name = two_menu.getName();
												if (two_menu.getId().toString().equals(id)) { //当前二级菜单
							%>

							<li class="active"><a href="<%=two_url%>"><%=two_name%></a></li>

							<%
								} else {
							%>

							<li><a href="<%=two_url%>"><%=two_name%></a></li>


							<%
								}
											}
							%>

						</ul> <%
 	} else {
 				if (menu.getId().toString().equals(id)) { //当一级前菜单且没有子级菜单
 %>
					<li class="active"><a href="<%=url%>"><i class="<%=icon%>"></i>
							<span><%=name%></span></a> <%
 	} else {
 %>
					<li><a href="<%=url%>"><i class="<%=icon%>"></i> <span><%=name%></span></a>

						<%
							}
									}
						%></li>

					<%
						}
						}
					%>
				</ul>
		</ul>
	</div>
	<!--sidebar-menu-->

	<!--main-container-part-->
	<div id="content">
		<!-- PAGE CONTENT BEGINS -->
		<sitemesh:write property='body' />
		<!-- PAGE CONTENT ENDS -->
	</div>

	<!--end-main-container-part-->

	<!--Footer-part-->

	<div class="row-fluid">
		<div id="footer" class="span12">Copyright © 2010-2015
			www.epweike.com 一品威客网络科技有限公司版权所有</div>
	</div>

	<!--end-Footer-part-->
	<script src="/common/matrix/js/matrix.tables.js"></script>
</body>
</html>
