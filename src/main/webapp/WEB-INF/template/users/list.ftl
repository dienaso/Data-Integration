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
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
	<script type="text/javascript">
$(document).ready(function() {

    $("#list").DataTable({
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
        		  	return formatDateTime(data.time);
                  }
	        	},
        	],
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
} );

$(".delete").live('click', function() {
  var id = $(this).attr('tag');
  alert(id);
  $.ajax({    
        type:'post',        
        url:'/users/del',    
        data:'id='+id,    
        cache:false,    
        dataType:'json',    
        success:function(data){   
        	location.reload();
        }    
    });    
});
</script>
</body>
</html>