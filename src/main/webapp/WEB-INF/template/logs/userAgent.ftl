<!DOCTYPE html>
<html>
<head>
	<title>访客来源统计</title>
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
			<a href="#" class="current">访客来源统计</a>
		</div>
		<h1>访客来源统计</h1>
	</div>
	<div class="container-fluid">
		<div class="widget-box">
			<div class="widget-title">
				<span class="icon"> <i class="icon-th"></i>
				</span>
				<h5>访客来源统计列表</h5>
			</div>
			<div class="widget-content nopadding">

				<table id="list" class="table table-bordered data-table">
					<thead>
						<tr>
							<th rowspan="2">日期</th>
							<th colspan="6">windows</th>
							<th colspan="5">other</th>
						</tr>
						<tr>
							<th>2000</th>
							<th>xp</th>
							<th>vista</th>
							<th>windows7</th>
							<th>windows8</th>
							<th>windows10</th>
							<th>mac</th>
							<th>linux</th>
							<th>android</th>
							<th>ios</th>
							<th>其它</th>
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
		        	{ "mData": "windows2000"}, 
		        	{ "mData": "windowsXp"},
		        	{ "mData": "windowsVista"},
		        	{ "mData": "windows7"},
		        	{ "mData": "windows81"}, 
		        	{ "mData": "windows10"},
		        	{ "mData": "mac"},
		        	{ "mData": "linux"},
		        	{ "mData": "android"}, 
		        	{ "mData": "ios"},
		        	{ "mData": "otheros"}
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