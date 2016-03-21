<%@page import="java.util.Iterator"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.epweike.service.MenusService"%>
<%@ page import="com.epweike.model.Menus"%>
<%@ page import="com.epweike.util.SpringUtils"%>
<%@ page import="com.epweike.util.SysconfigUtils"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Collection"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%
MenusService menusService = SpringUtils.getBean("menusService");

String id = "160";
String pid = "0";
if (request.getParameter("id") != null
&& !request.getParameter("id").toString().equals("")) {
    id = request.getParameter("id");
}
if (request.getParameter("pid") != null
&& !request.getParameter("pid").toString().equals("")) {
    pid = request.getParameter("pid");
}

//一级菜单
System.out.println("select menus");
Collection<Menus> menus = menusService.getFilterMenus(new Menus(0));
System.out.println("menus:"+menus.toString());

//拼接一二级菜单作为网页标题
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

//网站名称
String webName = SysconfigUtils.getVarValue("web_name");
//网站版权
String webCopyright = SysconfigUtils.getVarValue("web_copyright");

request.setAttribute("menus", menus);
%>
<!DOCTYPE html>
<html>
<head>
<title><%=webName%>
<%=title%></title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="/common/matrix/css/bootstrap.min.css" />
<link rel="stylesheet"
href="/common/matrix/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="/common/matrix/css/uniform.css" />
<link rel="stylesheet" href="/common/select2/select2.css" />
<link rel="stylesheet" href="/common/select2/select2-bootstrap.css" />
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
<script src="/common/matrix/js/browser.js"></script>
<script src="/common/matrix/js/bootstrap.min.js"></script>
<script src="/common/matrix/js/matrix.js"></script>
<script src="/common/matrix/js/jquery.dataTables.js"></script>
<script src="/common/matrix/js/jquery.ui.custom.js"></script>
<script src="/common/matrix/js/jquery.uniform.js"></script>
<script src="/common/matrix/js/jquery.gritter.min.js"></script>
<script src="/common/select2/select2.min.js"></script>
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
<li class="dropdown" id="profile-messages">
<a title="" href="#"
data-toggle="dropdown" data-target="#profile-messages"
class="dropdown-toggle"> <i class="icon icon-user"></i>
<span
class="text">
Welcome
<sec:authentication property="name" />
</span>
<b
class="caret"></b>
</a>
<ul class="dropdown-menu">
<li class="divider"></li>
<li>
<a id="clear" href="javascript:void(0)">
<i
class="icon-refresh"></i>
刷新系统参数
</a>
</li>
</ul>
</li>
<li class="">
<a title="退出" href="/j_spring_security_logout">
<i
class="icon icon-share-alt"></i>
<span class="text">退出</span>
</a>
</li>
</ul>
</div>
<!--close-top-Header-menu-->
<!--start-top-serch
<div id="search">
<input type="text" placeholder="Search here..."/>
<button type="submit" class="tip-bottom" title="Search">
<i class="icon-search icon-white"></i>
</button>
</div>
-->
<!--close-top-serch-->
<!--sidebar-menu-->
<div id="sidebar">
<ul>
<%
//循环遍历一级菜单
if (menus != null && menus.size() >
0) {
    for (Iterator
    <Menus>
    it = menus.iterator(); it.hasNext();) {
        Menus menu = it.next();
        System.out.println("一级菜单对象：" + menu.toString());
        String url = "";
        url = menu.getUrl() + "?id=" + menu.getId() + "&pid="
        + menu.getPid();
        String icon = menu.getIcon();
        String name = menu.getName();
        //二级菜单
        Collection<Menus>
        two_menus = menusService.getFilterMenus(new Menus(menu
        .getId()));
        if (two_menus != null && two_menus.size() > 0) { //存在二级菜单
            if (menu.getId().toString().equals(pid)) { //当前一级菜单且存在子级菜单
                %>
                <li class="submenu open active">
                <a href="<%=url%>
                ">
                <i
                class="<%=icon%>"></i>
                <span>
                <%=name%></span>
                <span
                class="label label-important">
                <%=two_menus.size()%></span>
                </a>
                <ul style="display: block;">
                
                <%
                } else {
                %>
                
                <li class="submenu">
                <a href="<%=url%>
                ">
                <i
                class="<%=icon%>"></i>
                <span>
                <%=name%></span>
                <span
                class="label label-important">
                <%=two_menus.size()%></span>
                </a>
                <ul style="display: none;">
                
                <%
            }
            %>
            <%
            for (Iterator<Menus>
            two_it = two_menus.iterator();
            two_it.hasNext();) {
                Menus two_menu = two_it.next();
                System.out
                .println("二级菜单对象：" + two_menus.toString());
                String two_url = two_menu.getUrl() + "?id="
                + two_menu.getId() + "&pid="
                + two_menu.getPid();
                String two_name = two_menu.getName();
                if (two_menu.getId().toString().equals(id)) { //当前二级菜单
                    %>
                    <li class="active">
                    <a href="<%=two_url%>
                    ">
                    <%=two_name%></a>
                    </li>
                    
                    <%
                    } else {
                    %>
                    
                    <li>
                    <a href="<%=two_url%>
                    ">
                    <%=two_name%></a>
                    </li>
                    
                    <%
                }
            }
            %></ul>
            <%
            } else {
            if (menu.getId().toString().equals(id)) { //当一级前菜单且没有子级菜单
                %>
                <li class="active">
                <a href="<%=url%>
                ">
                <i class="<%=icon%>"></i>
                <span>
                <%=name%></span>
                </a>
                <%
                } else {
                %>
                <li>
                <a href="<%=url%>
                ">
                <i class="<%=icon%>"></i>
                <span>
                <%=name%></span>
                </a>
                
                <%
            }
        }
        %></li>
        
        <%
    }
}
%></ul>
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
<div id="footer" class="span12">
<%=webCopyright %></div>
</div>

<!--end-Footer-part-->
<script src="/common/matrix/js/matrix.tables.js"></script>
<script type="text/javascript">
$(function () {
    $("#clear").on("click", clearConfig);
} );
/**
* 清空缓存
*/
function clearConfig() {
    $.ajax({
        "url": "/sysconfig/clear",
        "type": "get",
        success: function (data) {
            $.gritter.add({
                title:    '操作提示！',
                text:    data.msg,
                sticky: false
            });
        }
    });
}

function getSessionId(){
	var c_name = 'JSESSIONID';
	if(document.cookie.length>0){
	  c_start=document.cookie.indexOf(c_name + "=")
	  if(c_start!=-1){ 
	    c_start=c_start + c_name.length+1 
	    c_end=document.cookie.indexOf(";",c_start)
	    if(c_end==-1) c_end=document.cookie.length
	    return unescape(document.cookie.substring(c_start,c_end));
	  }
	}
}

</script>
</body>
</html>