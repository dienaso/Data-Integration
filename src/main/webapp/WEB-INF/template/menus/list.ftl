<!DOCTYPE html>
<html>
<head>
	<title>菜单管理</title>
</head>
<body>
	<div id="content-header">
		<div id="breadcrumb">
			<a href="/" title="Go to Home" class="tip-bottom"> <i class="icon-home"></i>
				Home
			</a>
			<a href="#" class="current">菜单管理</a>
		</div>
		<h1>菜单管理</h1>
	</div>
	<div class="container-fluid">
		<div class="widget-box">
			<div class="widget-title">
				<span class="icon"> <i class="icon-th"></i>
				</span>
				<h5>菜单列表</h5>
			</div>
			<div class="widget-content nopadding">
				<table id="list" class="table table-bordered data-table">
					<thead>
						<tr>
							<th>ID</th>
							<th>父级ID</th>
							<th>菜单名称</th>
							<th>级别</th>
							<th>url</th>
							<th>操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>

<script type="text/javascript">
$(function () {

	var table = $("#list").DataTable({
			"bServerSide" : true,
			"bDestroy": true,
			"bStateSave": true,
			"bFilter": false,
	        "sAjaxSource": '/menus/get', 
	        "aoColumns":
	           [  
					{ "mData": "id"},
		        	{ "mData": "pid"}, 
		        	{ "mData": "name"},
		        	{ "mData": "level"},
		        	{ "mData": "url"},
	        	],
        	 "aoColumnDefs": [
                 {
                     "aTargets": [5],
                     "mData": "id",
                     "mRender": function (data, type, full) {
                         return '<a href="#" class="delete" tag=' + data + '></a>';
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

</script>
</body>
</html>