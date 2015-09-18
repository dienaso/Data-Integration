<!DOCTYPE html>
<html>
<head>
<title>计划任务管理</title>
</head>
<body>
<div id="content-header">
    <div id="breadcrumb"><a href="/" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a> <a href="#" class="current">计划任务管理</a></div>
    <h1>计划任务管理</h1>
  </div>
<div class="container-fluid">
	<div class="widget-box">
	  <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
	    <h5>计划任务列表</h5>
	  </div>
	  <div class="widget-content nopadding">
	  	
	    <table id="list" class="table table-bordered data-table">
	        <thead>
	        	<tr>
		          	<th>ID</th>
		          	<th>计划任务名称</th>
		          	<th>状态</th>
		          	<th>cron表达式</th>
		          	<th>执行类</th>
		          	<th>执行方法</th>
		          	<th>创建时间</th>
		          	<th>修改时间</th>
	        	</tr>
	     	</thead>
	     	
	    </table>
	  </div>
	</div>
</div>
<script type="text/javascript">
var oTable;
$(document).ready(function() {
	oTable = $("#list").dataTable({
		"bServerSide" : true,
		"bDestroy": true,
		"bStateSave": true,
		"bFilter": false,
        "sAjaxSource": '/schedulejob/get', 
        "aoColumns":
           [  
				{ "mData": "id"},
	        	{ "mData": "jobName"}, 
	        	{ "mData": "jobStatus",
	        	  "mRender": function (data, type) {
            		 if(data == 1){
                         return "启用" 
                      }else{
                         return "禁用"
                      }
                  }
	        	},
	        	{ "mData": "cronExpression"},
	        	{ "mData": "beanClass"},
	        	{ "mData": "methodName"},
	        	{ "mData": "createTime",
        		  "mRender": function (data, type) {
        		  	return formatDateTime(data.time);
                  }
	        	 },
	        	{ "mData": "updateTime",
	        	  "mRender": function (data, type) {
        		  	return formatDateTime(data.time);
                  }
	        	}
        	],
        "fnServerData" : function(sSource, aoData, fnCallback) {
			$.ajax({
				"type" : "get",
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
        url:'/scheduleJob/del',    
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