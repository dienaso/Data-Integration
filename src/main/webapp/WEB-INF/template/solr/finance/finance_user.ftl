<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>数据统计</title>
</head>

<body>

	<div id="content-header">
		<div id="breadcrumb">
			<a href="/" title="Go to Home" class="tip-bottom"> <i class="icon-home"></i>
				Home
			</a>
			<a href="#" title="Go to Chart" class="tip-bottom"> <i class="icon-bar-chart"></i>
				数据统计
			</a>
			<a href="#" class="current">接单统计</a>
		</div>
		<h1>接单统计</h1>
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

					<label class="control-label">任务类型:</label>
					<div class="controls">
						<select id="taskType">
							<option>全部(不包含计件)</option>
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

					<label class="control-label">店铺等级:</label>
					<div class="controls">
						<select id="shop_level">
							<option>全部</option>
							<option>全部VIP</option>
							<option value="1">基础版本</option>
							<option value="2">VIP扩展版</option>
							<option value="3">VIP旗舰版</option>
							<option value="4">VIP白金版</option>
							<option value="5">VIP钻石版</option>
							<option value="6">VIP皇冠版</option>
							<option value="7">战略合作版</option>
						</select>
					</div>

					<label class="control-label">用户名:</label>
					<div class="controls">
						<input type="text" name="username" placeholder="威客用户名"></div>
				</div>

				<div class="form-actions">
					<button id="search-btn" class="btn">查询</button>
				</div>
			</div>

			<div class="widget-title">
				<span class="icon">
					<i class="icon-th"></i>
				</span>
				<h5>接单统计列表</h5>
			</div>
			<div class="widget-content nopadding">
				<table id="list" class="table table-bordered data-table">
					<thead>
						<tr>
							<th>名称</th>
							<th>接单量</th>
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
			"bDestroy": true,
			"bStateSave": true,
			"bServerSide": true,
			"bFilter": false,
			"bPaginate": false,
	        "sAjaxSource": '/finance/user/get', 
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
	    		aoData.push( { "name": "start", "value": $("input[name='start']").val() } );
	    		aoData.push( { "name": "end", "value": $("input[name='end']").val() } );
	    		aoData.push( { "name": "taskType", "value": $("#taskType option:selected").val() } );
	    		aoData.push( { "name": "source", "value": $("#source option:selected").val() } );
	    		aoData.push( { "name": "username", "value": $("input[name='username']").val() } );
	    		aoData.push( { "name": "shop_level", "value": $("#shop_level option:selected").val() } );
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