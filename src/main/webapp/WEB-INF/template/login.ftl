<!DOCTYPE html>
<html>

<head>
	<title>Data Integration</title>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<link rel="stylesheet" href="/common/matrix/css/bootstrap.min.css" />
	<link rel="stylesheet" href="/common/matrix/css/bootstrap-responsive.min.css" />
	<link rel="stylesheet" href="/common/matrix/css/matrix-login.css" />
	<link href="/common/matrix/font-awesome/css/font-awesome.css" rel="stylesheet" />
<body>
	<div id="loginbox">
		<form id="loginform" class="form-vertical" action="/j_spring_security_check" method="post">
			<div class="control-group normal_text">
				<h3>
					<img src="/common/matrix/img/logo.png" alt="Logo" />
				</h3>
			</div>
			<div class="control-group">
				<div class="controls">
					<div class="main_input_box">
						<span class="add-on bg_lg"> <i class="icon-user"></i>
						</span>
						<input type="text" placeholder="Username" name="j_username" />
					</div>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<div class="main_input_box">
						<span class="add-on bg_ly"> <i class="icon-lock"></i>
						</span>
						<input type="password" placeholder="Password" name="j_password" />
					</div>
				</div>
			</div>
			<div class="form-actions">
				<span class="pull-left">
					<a href="#" class="flip-link btn btn-info" id="to-recover">Lost password?</a>
				</span>
				<span class="pull-right">
					<button type="submit" href="#" class="btn btn-success" />
					Login
				</button>
			</span>
		</div>
	</form>
	<form id="recoverform" action="#" class="form-vertical">
		<p class="normal_text">
			Enter your e-mail address below and we will send you instructions how to recover a password.
		</p>

		<div class="controls">
			<div class="main_input_box">
				<span class="add-on bg_lo">
					<i class="icon-envelope"></i>
				</span>
				<input type="text" placeholder="E-mail address" />
			</div>
		</div>

		<div class="form-actions">
			<span class="pull-left">
				<a href="#" class="flip-link btn btn-success" id="to-login">&laquo; Back to login</a>
			</span>
			<span class="pull-right">
				<a class="btn btn-info"/>
				Reecover
			</a>
		</span>
	</div>
</form>
<#if (SPRING_SECURITY_LAST_EXCEPTION.message)?if_exists>
<div class="alert alert-danger"> <strong><i class="icon-remove"></i>
		${SPRING_SECURITY_LAST_EXCEPTION.message}
		<!-- 输出异常信息 --></strong> 
	<br />
</div>
</#if>
</div>

<script src="/common/matrix/js/jquery.min.js"></script>
<script src="/common/matrix/js/browser.js"></script>
<script src="/common/matrix/js/matrix.login.js"></script>
</body>

</html>