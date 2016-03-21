<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>数据统计</title>
</head>

<body>

	<div id="content-header">
		<div id="breadcrumb">
			<a href="#" class="tip-bottom"> <i class="icon-home"></i>
				Home
			</a>
			<a href="#" class="tip-bottom"> <i class="icon-bar-chart"></i>
				数据统计
			</a>
			<a href="#" class="current">用户注册统计</a>
		</div>
		<h1>用户注册统计</h1>
	</div>

	<div class="widget-box"></div>

	<div class="container-fluid">
		<div class="widget-box">

			<div class="widget-title">
				<span class="icon">
					<i class="icon-search"></i>
				</span>
				<h5>搜索区域</h5>
			</div>
			<div class="widget-content nopadding form-horizontal">
				<div class="control-group">
					<label class="control-label">注册时间 :</label>
					<div class="controls">
						<div class="input-daterange" id="datepicker">
							<input type="text" class="input-sm form-control" name="reg_start" placeholder="开始时间" readonly>
							<span class="input-group-addon">to</span>
							<input type="text" class="input-sm form-control" name="reg_end" placeholder="结束时间" readonly></div>
					</div>
				</div>

				<div class="form-actions">
					<button id="search-btn" class="btn">查询</button>
				</div>
			</div>

			<div class="widget-title">
				<span class="icon">
					<i class="icon-th"></i>
				</span>
				<h5>用户组注册统计列表</h5>
			</div>
			<div class="widget-content nopadding">
				<table id="list" class="table table-bordered data-table">
					<thead>
						<tr>
							<th rowspan="2">日期</th>
							<th rowspan="2">TOTAL</th>
							<th colspan="4">身份类型</th>
							<th colspan="8">注册渠道</th>
						</tr>
						<tr>
							<th>未确定</th>
							<th>威客</th>
							<th>雇主</th>
							<th>威客雇主</th>
							<th>WEB</th>
							<th>CPM</th>
							<th>APP</th>
							<th>WAP</th>
							<th>云创平台</th>
							<th>酷贝街</th>
							<th>后台</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>

	</div>

	<script type="text/javascript">
	$(document).ready(function() {
		$('.input-daterange').datepicker({
		    format: "yyyy-mm-dd",
    		language: "zh-CN",
    		todayHighlight: true,
    		autoclose: true
		});
		
		//获取当前时间
		var date = new Date().Format("yyyy-MM-dd");
		$("input[name='reg_start']").val(date);
		$("input[name='reg_end']").val(date);
		
		<!--dateTable-->
		var table = $('#list').DataTable({
			"bServerSide" : true,
			"bDestroy": true,
			"bStateSave": true,
			"bFilter": false,
			"bPaginate": false,
	        "sAjaxSource": '/talent/register/get', 
	        "aoColumns":
	           [  
					{ "mData": "label"},
					{ "mData": "TOTAL"},
					{ "mData": "uncertain"},
					{ "mData": "witkey"},
					{ "mData": "employer"},
					{ "mData": "both"},
		        	{ "mData": "WEB"},
		        	{ "mData": "cpm"},
		        	{ "mData": "APP"},
		        	{ "mData": "WAP"},
		        	{ "mData": "yun"},
		        	{ "mData": "mall"},
		        	{ "mData": "background"}
	        	],
	    	"fnServerData" : function(sSource, aoData, fnCallback) {
	    		aoData.push( { "name": "reg_start", "value": $("input[name='reg_start']").val() } );
	    		aoData.push( { "name": "reg_end", "value": $("input[name='reg_end']").val() } );
	    		aoData.push( { "name": "statType", "value": $("#statType option:selected").val() } );
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
		//查询方法
		$('#search-btn').on( 'click', function () {
		    table.ajax.reload();
		} );
	} );
	
	</script>
</body>
</html>