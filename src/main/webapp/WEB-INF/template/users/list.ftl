<!DOCTYPE html>
<html>
<head>
	<title>用户管理</title>
</head>
<body>
	<div id="content-header">
		<div id="breadcrumb">
			<a href="/" title="Go to Home" class="tip-bottom"> <i class="icon-home"></i>
				Home
			</a>
			<a href="#" class="current">用户管理</a>
		</div>
		<h1>用户管理</h1>
	</div>
	<div class="container-fluid">
		<div class="widget-box">
			<div class="widget-title">
				<span class="icon"> <i class="icon-th"></i>
				</span>
				<h5>用户列表</h5>
			</div>
			<div class="widget-content nopadding">
				<table id="list" class="table table-bordered data-table">
					<thead>
						<tr>
							<th>用户名</th>
							<th>联系电话</th>
							<th>邮箱</th>
							<th>状态</th>
							<th>注册时间</th>
							<th>操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
	

<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
	                        aria-hidden="true">&times;</span></button>
	                <h4 class="modal-title" id="myModalLabel">添加</h4>
	            </div>
	            <div class="modal-body form-horizontal">
					<input type="hidden" id="id">
	                <div class="control-group">
	                	<label class="control-label">用户名 :</label>
	                	<div class="controls">
	                    	<input type="text" class="form-control" id="username" placeholder="用户名">
	                    </div>
	                </div>
	                <div class="control-group">
	                	<label class="control-label">密码:</label>
	                	<div class="controls">
	                    	<input type="text" class="form-control" id="password" placeholder="密码">
	                    </div>
	                </div>
	                <div class="control-group">
	                	<label class="control-label">状态:</label>
	                	<div class="controls">
	                    	<label>
			                  <div class="radio"><span><input type="radio" value="1" name="enabled" style="opacity: 0;"></span></div>
			                  	正常 </label>
			                <label>
			                  <div class="radio"><span><input type="radio" value="0" name="enabled" style="opacity: 0;"></span></div>
			                 	 禁用</label>
	                    </div>
	                </div>
	                <div class="control-group">
						<label class="control-label">联系电话:</label>
						<div class="controls">
							<input type="text" class="form-control" id="tel" placeholder="联系电话">
						</div>
					</div>
	                <div class="control-group">
						<label class="control-label">邮箱:</label>
						<div class="controls">
							<input type="text" class="form-control" id="email" placeholder="邮箱">
						</div>
					</div>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-primary" id="save">保存</button>
	                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	            </div>
	        </div>
	    </div>
	</div>
	
	<!--定义操作列按钮模板-->
	<script id="tpl" type="text/x-handlebars-template">
	    {{#each func}}
	    <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
	    {{/each}}
	</script>
	<script type="text/javascript">
		var tpl = $("#tpl").html();
	    //预编译模板
	    var template = Handlebars.compile(tpl);
	    var editFlag = false;
		var table;
	
	    table = $("#list").DataTable({
			"bServerSide" : true,
			"bDestroy": true,
			"bStateSave": true,
			"bFilter": false,
	        "sAjaxSource": '/users/get', 
	        "aoColumns":
	           [  
		        	{ "mData": "userName"},
		        	{ "mData": "tel"},
		        	{ "mData": "email"},
		        	{ "mData": "enabled",
		        	  "mRender": function (data, type) {
	            		 if(data == 1){
	                         return "正常" 
	                      }else{
	                         return "禁用"
	                      }
	                  }
		        	},
		        	{ "mData": "onTime",
		        	  "mRender": function (data, type) {
		        	  	if(data == null)
		        	  		return null;
	        		  	return formatDateTime(data.time);
	                  }
		        	},
	        	],
	        "columnDefs": [
		                {
		                    targets: 5,
		                    render: function (a, b, c, d) {
		                        var context =
		                        {
		                            func: [
		                                {"name": "修改", "fn": "edit(\'" + c.id + "\',\'" + c.userName + "\',\'" + c.enabled + "\',\'" + c.email + "\',\'" + c.tel + "\')", "type": "primary"},
		                                {"name": "删除", "fn": "del(\'" + c.id + "\')", "type": "danger"}
		                            ]
		                        };
		                        var html = template(context);
		                        return html;
		                    }
		                }
		 
		            ],
	        initComplete: function () {
	            $("#mytool").append('<button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#myModal">添加</button>');
				$("#mytool").on("click", clear);
				$("#save").click(add);
			},
	        "fnServerData" : function(sSource, aoData, fnCallback) {
				$.ajax({
					"type" : "post",
					"url" : sSource,
					"dataType" : "json",
					"data" : {
						aoData : JSON.stringify(aoData)
					},
					"success" : function(resp) {
						fnCallback(resp);
					}
				});
			}
		});
	
		 /**
	     * 清除
	     */
	    function clear() {
	    	editFlag = false;
	    	$("#myModalLabel").text("添加");
	    	$("#id").val("");
	        $("#username").val("");
	        $("#password").val("");
	        $("#email").val("");
	        $("#tel").val("");
	        $(".checked").removeClass("checked");
	    }
	 
	    /**
	     * 添加数据
	     **/
	    function add() {
	    	var enabled;
	        //遍历选中单选框
	        $(".checked").each(function(){
	        	if($(this).children().attr("name") == "enabled") {
	        		enabled = $(this).children().val();
	        	} 
			});
	        var addJson = {
	        	"id": $("#id").val(),
	            "username": $("#username").val(),
	            "password": $("#password").val(),
	            "email": $("#email").val(),
	            "tel": $("#tel").val(),
	            "enabled": enabled
	        };
	 		console.log(addJson);
	        ajax(addJson);
	    }
	    
	    /**
	     *编辑方法
	     **/
	    function edit(id,username,enabled,email,tel) {
	    	//修改type属性为密码框，防止火狐自动填充用户名故采用js
			document.getElementById("password").type="password"; 
	        clear();
	        editFlag = true;
	        $("#myModalLabel").text("修改");
	        $("#id").val(id);
	        $("#username").val(username);
	        $("#password").val();
	        $("#email").val(email);
	        $("#tel").val(tel);
	        $("input:radio[name=enabled][value="+enabled+"]").parent("span").attr("class","checked");
	        $("#myModal").modal("show");
	        
	    }
	 
	 	/**
	     *ajax提交
	     **/
	    function ajax(obj) {
	        var url ="/users/add" ;
	        if(editFlag){
	            url = "/users/update";
	        }
	        $.ajax({
	            "url":url ,
	            "type": "post",
	            "data": {
	                "id": obj.id,
	                "username": obj.username,
	                "password": obj.password,
	                "email": obj.email,
	                "tel": obj.tel,
	                "enabled": obj.enabled
	            }
	        });
	    }
	 
	 
	    /**
	     * 删除数据
	     * @param id
	     */
	    function del(id) {
	        $.ajax({
	            "url": "/users/del",
	            "type": "get",
	            "data": {
		            "id": id
		         }
	        });
	    }
	</script>
</body>
</html>