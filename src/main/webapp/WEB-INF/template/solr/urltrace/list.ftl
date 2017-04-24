<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>索引管理</title>
</head>

<body>

	<div id="content-header">
		<div id="breadcrumb">
			<a href="#" class="tip-bottom"> <i class="icon-home"></i>
				Home
			</a>
			<a href="#" class="tip-bottom"> <i class="icon-cloud"></i>
				数据统计
			</a>
			<a href="#" class="current">访问列表</a>
		</div>
		<h1>访问列表</h1>
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
					<label class="control-label">时间区间 :</label>
					<div class="controls">
						<div class="input-daterange" id="datepicker">
							<input type="text" class="input-sm form-control" name="start" placeholder="开始时间" readonly>
							<span class="input-group-addon">to</span>
							<input type="text" class="input-sm form-control" name="end" placeholder="结束时间" readonly></div>
					</div>

				</div>
				
				<label class="control-label">页面类型:</label>
					<div class="controls">
						<select id="do_view">
							<option>全部</option>
							<option>首页</option>
							<option>任务列表</option>
							<option>任务详情</option>
							<option>人才列表</option>
							<option>人才详情</option>
							<option>服务列表</option>
							<option>服务详情</option>
						</select>
					</div>

				<div class="form-actions">
					<button id="search-btn" class="btn">查询</button>
				</div>
			</div>

			<div class="widget-title">
				<span class="icon">
					<i class="icon-th"></i>
				</span>
				<h5>访问统计列表</h5>
			</div>
			<div class="widget-content nopadding">
				<table id="list" class="table table-bordered data-table">
					<thead>
						<tr>
							<th>链接</th>
							<th>访问量</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>

	</div>

	<script type="text/javascript">
	$(function () {
		$('.input-daterange').datepicker({
		    format: "yyyy-m-d",
    		language: "zh-CN",
    		todayHighlight: true,
    		autoclose: true
		});
		
		//获取当前时间
		var date = new Date().Format("yyyy-MM-dd");
		$("input[name='start']").val(date);
		$("input[name='end']").val(date);
		
		<!--dateTable-->
		var table = $('#list').DataTable({
			"bServerSide" : true,
			"bDestroy": true,
			"bStateSave": true,
			"bFilter": false,
			"bPaginate": false,
	        "sAjaxSource": '/urlTrace/get', 
	        "aoColumns":
	           [  
					{ "mData": "name"},
		        	{ "mData": "count"},
	        	],
	    	"fnServerData" : function(sSource, aoData, fnCallback) {
	    		aoData.push( { "name": "start", "value": $("input[name='start']").val() } );
	    		aoData.push( { "name": "end", "value": $("input[name='end']").val() } );
	    		aoData.push( { "name": "do_view", "value": $("#do_view option:selected").val() } );
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
		<!--search-->
		$('#search-btn').on( 'click', function () {
		    table.ajax.reload();
		} );
	} );
	
	</script>
</body>
</html>