<!DOCTYPE html>
<html>
<head>
<title>用户管理</title>
</head>
<body>
<div id="content-header">
    <div id="breadcrumb"><a href="/" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a> <a href="#" class="current">用户管理</a></div>
    <h1>用户管理</h1>
  </div>
<div class="container-fluid">
	<div class="widget-box">
	  <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
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
<script src="/common/matrix/js/jquery.dataTables.js"></script> 
<script src="/common/matrix/js/jquery.ui.custom.js"></script> 
<script src="/common/matrix/js/jquery.uniform.js"></script> 
<script src="/common/matrix/js/select2.min.js"></script> 
<script src="/common/matrix/js/matrix.tables.js"></script>
<script type="text/javascript">
$(document).ready(function() {

    $("#list").dataTable({
			"bServerSide" : true,
			"bDestroy": true,
			"bStateSave": true,
	        "sAjaxSource": '/users/get', 
	        "aoColumns":
	           [  
		        	{ "mData": "userName"},
		        	{ "mData": "tel"},
		        	{ "mData": "email"},
		        	{ "mData": "enabled"},
		        	{ "mData": "onTime"},
	        	],
        	 "aoColumnDefs": [
                 {
                     "aTargets": [5],
                     "mData": "userName",
                     "mRender": function (data, type, full) {
                         return '<a href="javascript:void(0);" class="delete" tag=' + data + '>删除</a>';
                     }
                 }
                ],
	        "fnServerData" : function(sSource, aoData, fnCallback) {
				$.ajax({
					"type" : 'post',
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