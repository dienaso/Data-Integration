<!DOCTYPE html>
<html>
<head>
	<title>流量分析</title>
</head>
<body>
	<div id="content-header">
		<div id="breadcrumb">
			<a href="#" class="tip-bottom"> <i class="icon-home"></i>
				Home
			</a>
			<a href="#" class="tip-bottom"> <i class="icon-hdd"></i>
				日志挖掘
			</a>
			<a href="#" class="current">网站流量统计</a>
		</div>
		<h1>网站流量统计</h1>
	</div>
	<div class="container-fluid">
		<div class="widget-box">
			<div class="widget-title">
				<span class="icon"> <i class="icon-th"></i>
				</span>
				<h5>网站流量统计列表</h5>
			</div>
			<div class="widget-content nopadding">

				<table id="list" class="table table-bordered data-table">
					<thead>
						<tr>
							<th>日期</th>
							<th>PV</th>
							<th>注册意向数</th>
							<th>独立IP数</th>
							<th>跳出数</th>
							<th>搜索引擎抓取数</th>
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
	        "sAjaxSource": '/logs/get', 
	        "aoColumns":
	           [  
					{ "mData": "onTime"},
		        	{ "mData": "pv"}, 
		        	{ "mData": "reguser"},
		        	{ "mData": "ip"},
		        	{ "mData": "jumper"},
		        	{ "mData": "spider"}
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