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
			<a href="#" class="tip-bottom"> <i class="icon-bar-chart"></i>
				数据统计
			</a>
			<a href="#" class="current">APP下载量</a>
		</div>
		<h1>APP下载量</h1>
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
				
				<label class="control-label">应用类型:</label>
				<div class="controls">
					<select id="app_name">
						<option>全部</option>
						<#list appTypeList as list>
							<option>${list.name}</option>
						</#list>
					</select>
				</div>
				
				<label class="control-label">来源:</label>
				<div class="controls">
					<select id="source">
						<option>全部</option>
						<#list sourceList as list>
							<option>${list.name}</option>
						</#list>
					</select>
				</div>
				
				<label class="control-label">统计类型:</label>
				<div class="controls">
					<select id="statType">
						<option value="+1DAY">按日</option>
						<option value="+1MONTH">按月</option>
						<option value="+1YEAR">按年</option>
					</select>
				</div>
				
				<label class="control-label">是否去重</label>
                <div class="controls">
                  <label>
                    <input type="radio" name="distinct" value="1"/>是</label>
                  <label>
                    <input type="radio" name="distinct" value="0" checked/>否</label>
                </div>
					
				<div class="form-actions">
					<button id="search-btn" class="btn">查询</button>
				</div>
			</div>

			<div class="widget-title">
				<span class="icon">
					<i class="icon-th"></i>
				</span>
				<h5>APP下载量统计列表</h5>
			</div>
			<div class="widget-content nopadding">
				<table id="list" class="table table-bordered data-table">
					<thead>
						<tr>
							<th>日期</th>
							<th>下载量</th>
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
	        "sAjaxSource": '/appDown/get', 
	        "aoColumns":
	           [  
					{ "mData": "date"},
		        	{ "mData": "count"},
	        	],
	    	"fnServerData" : function(sSource, aoData, fnCallback) {
	    		aoData.push( { "name": "start", "value": $("input[name='start']").val() } );
	    		aoData.push( { "name": "end", "value": $("input[name='end']").val() } );
	    		aoData.push( { "name": "app_name", "value": $("#app_name option:selected").val() } );
	    		aoData.push( { "name": "source", "value": $("#source option:selected").val() } );
	    		aoData.push( { "name": "statType", "value": $("#statType option:selected").val() } );
	    		aoData.push( { "name": "distinct", "value": $(".checked").children().val() } );
	    		
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