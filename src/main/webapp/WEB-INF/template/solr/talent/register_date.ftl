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
		<h1>用户注册统计(按日期)</h1>
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
							<input type="text" class="input-sm form-control" name="start" placeholder="开始时间" readonly>
							<span class="input-group-addon">to</span>
							<input type="text" class="input-sm form-control" name="end" placeholder="结束时间" readonly></div>
					</div>

					<label class="control-label">身份类型:</label>
					<div class="controls">
						<select id="user_role">
							<option>全部</option>
							<option value="0">未确定</option>
							<option value="1">威客</option>
							<option value="2">雇主</option>
							<option value="3">威客雇主</option>
						</select>
					</div>
					
					<label class="control-label">注册渠道:</label>
					<div class="controls">
						<select id="come">
						    <option>全部</option>
							<option value="WEB">WEB</option>
							<option value="cpm">CPM</option>
							<option value="APP">APP</option>
							<option value="WAP">WAP</option>
							<option value="yun">云创平台</option>
							<option value="mall">酷贝街</option>
							<option value="background">后台</option>
							
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
				</div>

				<div class="form-actions">
					<button id="search-btn" class="btn">查询</button>
				</div>
			</div>

			<div class="widget-title">
				<span class="icon">
					<i class="icon-th"></i>
				</span>
				<h5>注册统计列表</h5>
			</div>
			<div class="widget-content nopadding">
				<table id="list" class="table table-bordered data-table">
					<thead>
						<tr>
							<th>注册时间</th>
							<th>数量</th>
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
	        "sAjaxSource": '/talent/register/date/get', 
	        "aoColumns":
	           [  
					{ "mData": "date"},
		        	{ "mData": "count"},
	        	],
	    	"fnServerData" : function(sSource, aoData, fnCallback) {
	    		aoData.push( { "name": "start", "value": $("input[name='start']").val() } );
	    		aoData.push( { "name": "end", "value": $("input[name='end']").val() } );
	    		aoData.push( { "name": "come", "value": $("#come option:selected").val() } );
	    		aoData.push( { "name": "user_role", "value": $("#user_role option:selected").val() } );
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
		<!--search-->
		$('#search-btn').on( 'click', function () {
		    table.ajax.reload();
		} );
	} );
	
	</script>
</body>
</html>