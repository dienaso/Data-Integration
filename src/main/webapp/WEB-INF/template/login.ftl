<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>登陆页面-<sitemesh:write property='title' /></title>
		<meta name="keywords" content="" />
		<meta name="description" content="" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<!-- basic styles -->
		
		<link rel="stylesheet" href="/common/static/css/basic.css" />

		<!--[if IE 7]>
		  <link rel="stylesheet" href="/common/bootstrap/assets/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!-- page specific plugin styles -->

		<!-- ace styles -->

		<link rel="stylesheet" href="/common/static/css/ace.css" />

		<!--[if lte IE 8]>
		<link rel="stylesheet" href="/common/bootstrap/assets/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->

		<script src="/common/bootstrap/assets/js/ace-extra.min.js"></script>

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="/common/bootstrap/assets/js/html5shiv.js"></script>
		<script src="/common/bootstrap/assets/js/respond.min.js"></script>
		<![endif]-->
	</head>

	<body class="login-layout">
		<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">
							<div class="center">
								<h1>
									<i class="icon-leaf green"></i>
									<span class="red">Solr</span>
									<span class="white">后台管理</span>
								</h1>
								<h4 class="blue">&copy; 一品威客</h4>
							</div>

							<div class="space-6"></div>
							
							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="icon-coffee green"></i>
												Please Enter Your Information
											</h4>
											
											<div class="space-6"></div>
										<#if (SPRING_SECURITY_LAST_EXCEPTION.message)?if_exists>
											<div class="alert alert-danger">
												<button type="button" class="close" data-dismiss="alert">
													<i class="icon-remove"></i>
												</button>

												<strong>
													<i class="icon-remove"></i>
													Oh snap!
												</strong>
												${SPRING_SECURITY_LAST_EXCEPTION.message}  <!-- 输出异常信息 -->
												<br />
											</div>
										</#if>	
											<form action="/springAuthority/j_spring_security_check" method="post">
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" name="j_username" class="form-control" placeholder="用户名" />
															<i class="icon-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" name="j_password" class="form-control" placeholder="密码" />
															<i class="icon-lock"></i>
														</span>
													</label>

													<div class="space"></div>

													<div class="clearfix">
														<button type="submit" class="width-35 pull-right btn btn-sm btn-primary">
															<i class="icon-key"></i>
															登陆
														</button>
													</div>

													<div class="space-4"></div>
												</fieldset>
											</form>

										</div><!-- /widget-main -->

									</div><!-- /widget-body -->
								</div><!-- /login-box -->

							</div><!-- /position-relative -->
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div>
		</div><!-- /.main-container -->

		<!-- basic scripts -->

		<!--[if !IE]> -->

		<script src="/common/bootstrap/assets/js/jquery-2.0.3.min.js"></script>

		<!-- <![endif]-->

		<!--[if IE]>
		
		<script src="/common/bootstrap/assets/js/jquery-1.10.2.min.js"></script>
		
		<![endif]-->

		<!--[if !IE]> -->
		

		<script type="text/javascript">
			window.jQuery || document.write("<script src='/common/bootstrap/assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
		<script type="text/javascript">
		 window.jQuery || document.write("<script src='/common/bootstrap/assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
		</script>
		<![endif]-->

		<!-- ace scripts -->

		<script src="/common/static/js/ace.js"></script>

		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='/common/bootstrap/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="/common/bootstrap/assets/js/bootstrap.min.js"></script>
		<script src="/common/bootstrap/assets/js/typeahead-bs2.min.js"></script>
	
		<!-- inline scripts related to this page -->

		<script type="text/javascript">
		</script>
</body>
</html>
