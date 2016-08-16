<!DOCTYPE html>
<html>
<head>
	<title>求职信息</title>
</head>
<body>
	<div id="content-header">
		<div id="breadcrumb">
			<a href="#" class="tip-bottom"> <i class="icon-home"></i>
				Home
			</a>
			<a href="#" class="tip-bottom"> <i class="icon-star"></i>
				网页采集
			</a>
			<a href="#" class="current">求职信息</a>
		</div>
		<h1>求职信息</h1>
	</div>
	
	<div class="container-fluid">
		<div class="widget-box">
			<div class="widget-title">
				<span class="icon"> <i class="icon-th"></i>
				</span>
				<h5>求职信息列表</h5>
			</div>
			<div class="widget-content nopadding">

				<table id="list" class="table table-bordered data-table">
					<thead>
						<tr>
							<th>ID</th>
							<th>标题</th>
							<th>联系人</th>
							<th>电话</th>
							<th>邮箱</th>
							<th>性别</th>
							<th>年龄</th>
							<th>学历</th>
							<th>发布人</th>
							<th>发布日期</th>
						</tr>
					</thead>

				</table>
			</div>
		</div>
	</div>
	
	<script src="/common/matrix/js/ajaxsetup.js"></script>
	<script type="text/javascript">
	var table;
	$(function () {
		table = $("#list").DataTable({
			"bServerSide" : true,
			"bDestroy": true,
			"bStateSave": true,
			"bFilter": false,
	        "sAjaxSource": '/employ/getEmployee', 
	        "aoColumns":
	           [  
					{ "mData": "id"},
		        	{ "mData": "title"}, 
		        	{ "mData": "linkman"},
		        	{ "mData": "phone"},
		        	{ "mData": "email"},
		        	{ "mData": "sex"},
		        	{ "mData": "age"},
		        	{ "mData": "education"},
		        	{ "mData": "publisher"},
		        	{ "mData": "pubDate"}
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