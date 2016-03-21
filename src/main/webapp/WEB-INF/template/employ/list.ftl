<!DOCTYPE html>
<html>
<head>
	<title>招聘信息</title>
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
			<a href="#" class="current">招聘信息</a>
		</div>
		<h1>招聘信息</h1>
	</div>
	
	<div class="container-fluid">
		<div class="widget-box">
			<div class="widget-title">
				<span class="icon"> <i class="icon-th"></i>
				</span>
				<h5>招聘信息列表</h5>
			</div>
			<div class="widget-content nopadding">

				<table id="list" class="table table-bordered data-table">
					<thead>
						<tr>
							<th>ID</th>
							<th>标题</th>
							<th>公司名称</th>
							<th>地址</th>
							<th>联系人</th>
							<th>电话</th>
							<th>邮箱</th>
							<th>工作地点</th>
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
	        "sAjaxSource": '/employ/get', 
	        "aoColumns":
	           [  
					{ "mData": "id"},
		        	{ "mData": "title"}, 
		        	{ "mData": "companyName"},
		        	{ "mData": "address"},
		        	{ "mData": "linkman"},
		        	{ "mData": "phone"},
		        	{ "mData": "email"},
		        	{ "mData": "workplace"},
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