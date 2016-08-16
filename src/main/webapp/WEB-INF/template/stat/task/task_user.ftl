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
			<a href="#" class="current">任务统计(按雇主)</a>
		</div>
		<h1>任务统计(按雇主)</h1>
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
							<input type="text" class="input-sm form-control" name="pub_start" placeholder="开始时间" readonly>
							<span class="input-group-addon">to</span>
							<input type="text" class="input-sm form-control" name="pub_end" placeholder="结束时间" readonly></div>
					</div>

					<label class="control-label">托管时间 :</label>
					<div class="controls">
						<div class="input-daterange" id="datepicker">
							<input type="text" class="input-sm form-control" name="cash_start" placeholder="开始时间" readonly>
							<span class="input-group-addon">to</span>
							<input type="text" class="input-sm form-control" name="cash_end" placeholder="结束时间" readonly></div>
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

					<label class="control-label">用户名:</label>
					<div class="controls">
						<input type="text" name="username" placeholder="雇主用户名"></div>
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
							<th>名称</th>
							<th>发布数量</th>
							<th>总额</th>
							<th>最大</th>
							<th>最小</th>
							<th>平均</th>
							<th>标准差</th>
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
		$("input[name='pub_start']").val(date);
		$("input[name='pub_end']").val(date);
		
		<!--dateTable-->
		var table = $('#list').DataTable({
			"bDestroy": true,
			"bStateSave": true,
			"bServerSide": true,
			"bFilter": false,
			"bPaginate": false,
	        "sAjaxSource": '/task/user/get', 
	        "aoColumns":
	           [  
					{ "mData": "name"},
		        	{ "mData": "count"},
		        	{ "mData": "sum"},
		        	{ "mData": "max"},
		        	{ "mData": "min"},
		        	{ "mData": "mean"},
		        	{ "mData": "stddev"}
	           ],
	    	"fnServerData": function(sSource, aoData, fnCallback) {
	    		aoData.push( { "name": "pub_start", "value": $("input[name='pub_start']").val() } );
	    		aoData.push( { "name": "pub_end", "value": $("input[name='pub_end']").val() } );
	    		aoData.push( { "name": "taskType", "value": $("#taskType option:selected").val() } );
	    		aoData.push( { "name": "source", "value": $("#source option:selected").val() } );
	    		aoData.push( { "name": "username", "value": $("input[name='username']").val() } );
	    		aoData.push( { "name": "cash_status", "value": $("#cash_status option:selected").val() } );
	    		aoData.push( { "name": "cash_start", "value": $("input[name='cash_start']").val() } );
	    		aoData.push( { "name": "cash_end", "value": $("input[name='cash_end']").val() } );
	    		aoData.push( { "name": "task_status", "value": $("#task_status option:selected").val() } );
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