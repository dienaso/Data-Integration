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
			<a href="#" class="current">任务统计(按时间)</a>
		</div>
		<h1>任务统计(按时间)</h1>
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
					<label class="control-label">发布时间 :</label>
					<div class="controls">
						<div class="input-daterange" id="datepicker">
							<input type="text" class="input-sm form-control" name="start" placeholder="开始时间" readonly>
							<span class="input-group-addon">to</span>
							<input type="text" class="input-sm form-control" name="end" placeholder="结束时间" readonly></div>
					</div>

					<label class="control-label">任务类型:</label>
					<div class="controls">
						<select id="taskType">
							<option>全部</option>
							<option>单赏</option>
							<option>多赏</option>
							<option>计件</option>
							<option>招标</option>
							<option>雇佣</option>
							<option>服务</option>
							<option>直接雇佣</option>
						</select>
					</div>

					<label class="control-label">任务来源:</label>
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

					<label class="control-label">托管状态:</label>
					<div class="controls">
						<select id="cash_status">
							<option>全部</option>
							<option>未托管</option>
							<option>已托管</option>
						</select>
					</div>

					<label class="control-label">任务状态:</label>
					<div class="controls">
						<select id="task_status">
							<option>全部</option>
							<option value="\-1">未确认</option>
							<option value="0">未付款</option>
							<option value="1">待审核</option>
							<option value="2">投稿中</option>
							<option value="3">选稿中</option>
							<option value="4">摇奖中</option>
							<option value="5">公示中</option>
							<option value="6">交付</option>
							<option value="7">冻结</option>
							<option value="8">结束</option>
							<option value="9">失败</option>
							<option value="10">审核失败</option>
							<option value="11">仲裁</option>
						</select>
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
				<h5>任务统计列表</h5>
			</div>
			<div class="widget-content nopadding">
				<table id="list" class="table table-bordered data-table">
					<thead>
						<tr>
							<th>发布时间</th>
							<th>发布数量</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>

	</div>

	<script type="text/javascript">
	$(document).ready(function() {
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
	        "sAjaxSource": '/task/date/get', 
	        "aoColumns":
	           [  
					{ "mData": "date"},
		        	{ "mData": "count"},
	        	],
	    	"fnServerData" : function(sSource, aoData, fnCallback) {
	    		aoData.push( { "name": "start", "value": $("input[name='start']").val() } );
	    		aoData.push( { "name": "end", "value": $("input[name='end']").val() } );
	    		aoData.push( { "name": "taskType", "value": $("#taskType option:selected").val() } );
	    		aoData.push( { "name": "source", "value": $("#source option:selected").val() } );
	    		aoData.push( { "name": "statType", "value": $("#statType option:selected").val() } );
	    		aoData.push( { "name": "task_status", "value": $("#task_status option:selected").val() } );
	    		aoData.push( { "name": "cash_status", "value": $("#cash_status option:selected").val() } );
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