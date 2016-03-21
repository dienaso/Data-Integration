<!DOCTYPE html>
<html>
<head>
	<title>个人信息</title>
</head>
<body>

	<div id="content-header">
		<div id="breadcrumb">
			<a href="#" class="tip-bottom"> <i class="icon-home"></i>
				Home
			</a>
			<a href="#" title="Go to User Info" class="current"> <i class="icon-user"></i>
				个人信息
			</a>
		</div>
		<h1>修改个人信息</h1>
	</div>


	<div class="container-fluid">
	  <hr>
	  <div class="row-fluid">
	    <div class="span12">
	      <div class="widget-box">
	        <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
	          <h5>个人信息</h5>
	        </div>
	        <div class="widget-content nopadding">
	          <form action="#" method="get" class="form-horizontal">
	            <div class="control-group">
	              <label class="control-label">账号 :</label>
	              <div class="controls">
	                <input id="username" type="text" class="span11" placeholder="First name" value="${user.username}" readonly/>
	              </div>
	            </div>
	            <div class="control-group">
	              <label class="control-label">旧密码</label>
	              <div class="controls">
	                <input id="oldPassword" type="password"  class="span11" placeholder="输入原始密码"  />
	              </div>
	            </div>
	            <div class="control-group">
	              <label class="control-label">新密码</label>
	              <div class="controls">
	                <input id="newPassword" type="password"  class="span11" placeholder="输入新密码"  />
	              </div>
	            </div>
	            <div class="control-group">
	              <label class="control-label">确认密码</label>
	              <div class="controls">
	                <input id="confirmPassword" type="password"  class="span11" placeholder="输入确认密码"  />
	              </div>
	            </div>
	            <div class="form-actions">
	              <button id="changePassword" type="submit" class="btn btn-success">保存</button>
	            </div>
	          </form>
	        </div>
	      </div>
	    </div>  
	  </div> 
    </div>   
<script type="text/javascript">
	$(function () {
	     $("#changePassword").click(changePassword);
	} );
	/**
	* 保存
	**/
	function changePassword() {
	    var addJson = {
	        "username": $("#username").val(),
	        "oldPassword": $("#oldPassword").val(),
	        "newPassword": $("#newPassword").val(),
	        "confirmPassword": $("#confirmPassword").val()
	    };
	    ajaxPassword(addJson);
	}
	
	/**
	*ajax提交
	**/
	function ajaxPassword(obj) {
	    
	    if(obj.oldPassword == "") {
	        $("#myPasswordModal").modal("hide");
	        $.gritter.add({
	            title:    '操作提示！',
	            text:    '旧密码不能为空！',
	            sticky: false
	        });
	        return;
	    }
	    
	    if(obj.newPassword == "") {
	        $("#myPasswordModal").modal("hide");
	        $.gritter.add({
	            title:    '操作提示！',
	            text:    '新密码不能为空！',
	            sticky: false
	        });
	        return;
	    }
	    
	    if(obj.confirmPassword == "") {
	        $("#myPasswordModal").modal("hide");
	        $.gritter.add({
	            title:    '操作提示！',
	            text:    '确认密码不能为空！',
	            sticky: false
	        });
	        return;
	    }
	    
	    if(obj.newPassword != obj.confirmPassword) {
	        $("#myPasswordModal").modal("hide");
	        $.gritter.add({
	            title:    '操作提示！',
	            text:    '新密码与确认密码不一致！',
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
		            $.gritter.add({
		                title:    '操作提示！',
		                text:    data.msg,
		                sticky: false
		            });
	        }
	    });
	}

</script>	  
	  
</body>
</html>