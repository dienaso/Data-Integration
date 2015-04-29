<%@ page language="java" pageEncoding="UTF-8"%>  
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<title>Data Integration</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="/common/matrix/css/bootstrap.min.css" />
<link rel="stylesheet" href="/common/matrix/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="/common/matrix/css/uniform.css" />
<link rel="stylesheet" href="/common/matrix/css/select2.css" />
<link rel="stylesheet" href="/common/matrix/css/matrix-style.css" />
<link rel="stylesheet" href="/common/matrix/css/matrix-media.css" />
<link href="/common/matrix/font-awesome/css/font-awesome.css" rel="stylesheet" />
<link href='http://fonts.useso.com/css?family=Open+Sans:400,700,800' rel='stylesheet' type='text/css'>

<script src="/common/matrix/js/jquery.min.js"></script> 
<script src="/common/matrix/js/bootstrap.min.js"></script> 
<script src="/common/matrix/js/matrix.js"></script> 

</head>
<body>

<!--Header-part-->
<div id="header">
  <h1><a href="/">Data Integration</a></h1>
</div>
<!--close-Header-part--> 


<!--top-Header-menu-->
<div id="user-nav" class="navbar navbar-inverse">
  <ul class="nav">
    <li  class="dropdown" id="profile-messages" ><a title="" href="#" data-toggle="dropdown" data-target="#profile-messages" class="dropdown-toggle"><i class="icon icon-user"></i>  <span class="text">Welcome <sec:authentication property="name"/></span><b class="caret"></b></a>
      <ul class="dropdown-menu">
        <li><a href="#"><i class="icon-user"></i> My Profile</a></li>
        <li class="divider"></li>
        <li><a href="#"><i class="icon-check"></i> My Tasks</a></li>
        <li class="divider"></li>
        <li><a href="/j_spring_security_logout"><i class="icon-key"></i> Log Out</a></li>
      </ul>
    </li>
    <li class=""><a title="" href="/j_spring_security_logout"><i class="icon icon-share-alt"></i> <span class="text">Logout</span></a></li>
  </ul>
</div>
<!--close-top-Header-menu-->
<!--start-top-serch-->
<div id="search">
  <input type="text" placeholder="Search here..."/>
  <button type="submit" class="tip-bottom" title="Search"><i class="icon-search icon-white"></i></button>
</div>
<!--close-top-serch-->
<!--sidebar-menu-->
<div id="sidebar"><a href="#" class="visible-phone"><i class="icon icon-home"></i> Dashboard</a>
  <ul>
    <li><a href="/"><i class="icon icon-home"></i> <span>控制面板</span></a> </li>
    <li class="active"> <a href="/lexicon/list"><i class="icon icon-book"></i> <span>词库管理</span></a> </li>
    <li> <a href="/users/list"><i class="icon icon-user"></i> <span>用户管理</span></a> </li>
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
  <div id="footer" class="span12"> Copyright © 2010-2015 www.epweike.com 一品威客网络科技有限公司版权所有 </div>
</div>

<!--end-Footer-part-->

</body>
</html>
