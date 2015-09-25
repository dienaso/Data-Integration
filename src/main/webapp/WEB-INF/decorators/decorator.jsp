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

	//拼接一二级菜单
	String title = "";
	Menus menus1 = menusService.get(Integer.parseInt(pid));
	Menus menus2 = menusService.get(Integer.parseInt(id));
	if (menus1 != null)
		title = "-" + menus1.getName();
	if (!"".equals(title)) {
		if (menus2 != null)
			title = title + "-" + menus2.getName();
	} else {
		title = "-" + menus2.getName();
	}
%>
<!DOCTYPE html>
<html>
<head>
<title>Data Integration<%=title%></title>
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
	href='/common/matrix/font-open+sans/css/font-open+sans.css'
	type='text/css'>
<link rel="stylesheet"
	href="/common/matrix/css/bootstrap-datepicker3.css" />
<link rel="stylesheet"
	href="/common/matrix/css/dataTables.tableTools.css" />
<link rel="stylesheet"
	href="/common/matrix/css/jquery.gritter.css" />

<script src="/common/matrix/js/jquery.min.js"></script>
<script src="/common/matrix/js/bootstrap.min.js"></script>
<script src="/common/matrix/js/matrix.js"></script>
<script src="/common/matrix/js/jquery.dataTables.js"></script>
<script src="/common/matrix/js/jquery.ui.custom.js"></script>
<script src="/common/matrix/js/jquery.uniform.js"></script>
<script src="/common/matrix/js/jquery.gritter.min.js"></script>
<script src="/common/matrix/js/select2.min.js"></script>
<script type="text/javascript"
	src="/common/matrix/js/dataTables.tableTools.js" charset="utf-8"></script>
<script src="/common/matrix/js/bootstrap-datepicker.js"></script>
<script src="/common/matrix/js/common.js"></script>
<script
	src="/common/matrix/js/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="/common/matrix/js/handlebars-v4.0.2.js"></script>

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
					<li><a id="showChangePassword" href="javascript:void(0)"><i class="icon-key"></i>
							修改密码</a></li>
					<li class="divider"></li>
					<li><a id="clear" href="javascript:void(0)"><i
							class="icon-refresh"></i> 刷新系统参数</a></li>
				</ul></li>
			<li class=""><a title="" href="/j_spring_security_logout"><i
					class="icon icon-share-alt"></i> <span class="text">退出</span></a></li>
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
							if (menu.getId().toString().equals(pid)) { //当前一级菜单且存在子级菜单
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

	<!-- Modal -->
	<div class="modal fade" id="myPasswordModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
	                        aria-hidden="true">&times;</span></button>
	                <h4 class="modal-title" id="myModalLabel">修改密码</h4>
	            </div>
	            <div class="modal-body form-horizontal">
					<input type="hidden" id="id">
	                <div class="control-group">
	                	<label class="control-label">原始密码 :</label>
	                	<div class="controls">
	                    	<input type="text" class="form-control" id="oldPassword" placeholder="原始密码">
	                    </div>
	                </div>
	                <div class="control-group">
	                	<label class="control-label">新密码 :</label>
	                	<div class="controls">
	                    	<input type="text" class="form-control" id="newPassword" placeholder="新密码">
	                    </div>
	                </div>
	                <div class="control-group">
	                	<label class="control-label">确认密码 :</label>
	                	<div class="controls">
	                    	<input type="text" class="form-control" id="confirmPassword" placeholder="确认密码">
	                    </div>
	                </div>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-primary" id="changePassword">保存</button>
	                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	            </div>
	        </div>
	    </div>
	</div>
	
	<!--end-Footer-part-->
	<script src="/common/matrix/js/matrix.tables.js"></script>
	<script type="text/javascript">
	$(function () {
		$("#showChangePassword").on("click", showChangePassword);
		$("#changePassword").click(changePassword);
		$("#clear").on("click", clearConfig);
	} );
	/**
     * 清空缓存
     * @param id
     */
	function clearConfig() {
        $.ajax({
            "url": "/sysconfig/clear",
            "type": "get",
            success: function (data) {
	            $.gritter.add({
					title:	'操作提示！',
					text:	data.msg,
					sticky: false
				});	
	         }
        });
    }
	/**
     * 显示修改密码界面
     * @param id
     */
	function showChangePassword() {
		clearPassword();
		$("#myPasswordModal").modal("show");
    }
	/**
     * 清除
     */
    function clearPassword() {
    	editFlag = false;
    	$("#oldPassword").val("");
        $("#newPassword").val("");
        $("#confirmPassword").val("");
    }
	/**
     * 保存
     **/
    function changePassword() {
        var addJson = {
        	"username": "<sec:authentication property="name" />",
            "oldPassword": $("#oldPassword").val(),
            "newPassword": $("#newPassword").val(),
            "confirmPassword": $("#confirmPassword").val()
        };
 		console.log(addJson);
 		ajaxPassword(addJson);
    }
	
    /**
     *ajax提交
     **/
    function ajaxPassword(obj) {
    	
    	if(obj.oldPassword == "") {
    		$("#myPasswordModal").modal("hide");
    		$.gritter.add({
				title:	'操作提示！',
				text:	'旧密码不能为空！',
				sticky: false
			});	
    		return;
    	}
    	
    	if(obj.newPassword == "") {
    		$("#myPasswordModal").modal("hide");
    		$.gritter.add({
				title:	'操作提示！',
				text:	'新密码不能为空！',
				sticky: false
			});	
    		return;
    	}
    	
    	if(obj.confirmPassword == "") {
    		$("#myPasswordModal").modal("hide");
    		$.gritter.add({
				title:	'操作提示！',
				text:	'确认密码不能为空！',
				sticky: false
			});	
    		return;
    	}
    	
    	if(obj.newPassword != obj.confirmPassword) {
    		$("#myPasswordModal").modal("hide");
    		$.gritter.add({
				title:	'操作提示！',
				text:	'新密码与确认密码不一致！',
				sticky: false
			});	
    		return;
    	}
    	
        $.ajax({
            "url": "/users/changePassword" ,
            "type": "post",
            "data": {
                "username": obj.username,
                "oldPassword": obj.oldPassword,
                "newPassword": obj.newPassword,
                "confirmPassword": obj.confirmPassword
            }, success: function (data) {
                $("#myPasswordModal").modal("hide");
                clear();
                $.gritter.add({
					title:	'操作提示！',
					text:	data.msg,
					sticky: false
				});	
            }
        });
    }
	</script>
</body>
</html>
