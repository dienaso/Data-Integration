<!DOCTYPE html>
<html>
<head>
	<title>消息管理</title>
</head>
<body>
	<div id="content-header">
		<div id="breadcrumb">
			<a href="#" class="tip-bottom"> <i class="icon-home"></i>
				Home
			</a>
			<a href="#" class="tip-bottom"> <i class="icon-cloud"></i>
				索引管理
			</a>
			<a href="#" class="current">消息管理</a>
		</div>
		<h1>消息管理</h1>
	</div>
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
					<label class="control-label">标题:</label>
					<div class="controls">
						<input type="text" name="title" placeholder="标题"></div>
						
					<label class="control-label">邮箱/手机号:</label>
					<div class="controls">
						<input type="text" name="targetno" placeholder="邮箱/手机号"></div>

					<label class="control-label">时间区间 :</label>
					<div class="controls">
						<div class="input-daterange" id="datepicker">
							<input type="text" class="input-sm form-control" name="start" placeholder="开始时间" readonly>
							<span class="input-group-addon">to</span>
							<input type="text" class="input-sm form-control" name="end" placeholder="结束时间" readonly></div>
					</div>
						
					<label class="control-label">消息类型:</label>
					<div class="controls">
						<select id="messagetype">
							<option>全部</option>
							<option value="email">邮件</option>
							<option value="sms">短信</option>
						</select>
					</div>
				</div>

				<div class="form-actions">
					<button id="search-btn" class="btn">查询</button>
				</div>
			</div>
	
		<div class="widget-box">
			<div class="widget-title">
				<span class="icon"> <i class="icon-th"></i>
				</span>
				<h5>消息列表</h5>
			</div>
			<div class="widget-content nopadding">
				<table id="list" class="table table-bordered data-table">
					<thead>
						<tr>
							<th>ID</th>
							<th>标题</th>
							<th>类型</th>
							<th>邮箱/手机号</th>
							<th>状态</th>
							<th>时间</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</div>
<!--定义操作列按钮模板-->
<script id="tpl" type="text/x-handlebars-template">
    {{#each func}}
    <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
    {{/each}}
</script>
<script type="text/javascript">
var tpl = $("#tpl").html();
//预编译模板
var template = Handlebars.compile(tpl);
var table;
$(function () {

	$('.input-daterange').datepicker({
	    format: "yyyy-mm-dd",
		language: "zh-CN",
		todayHighlight: true,
		autoclose: true
	});
	
	//获取当前时间
	var date = new Date().Format("yyyy-MM-dd");
	$("input[name='start']").val(date);
	$("input[name='end']").val(date);

	table = $("#list").DataTable({
			"bServerSide" : true,
			"bDestroy": true,
			"bStateSave": true,
			"bFilter": false,
	        "sAjaxSource": '/messqueue/get', 
	        "aoColumns":
	           [  
					{ "mData": "messageid"},
					{ "mData": "title"}, 
		        	{ "mData": "messagetype"}, 
		        	{ "mData": "targetno"},
		        	{ "mData": "returncode"},
		        	{ "mData": "intime",
		        	  "mRender": function (data, type) {
		        	  	if (data == null)
	        		  		return null;
	        		  	return formatDateTime(data * 1000);
	                  }
		        	}
	        	],
	        "fnServerData" : function(sSource, aoData, fnCallback) {
	    		aoData.push( { "name": "title", "value": $("input[name='title']").val() } );
	    		aoData.push( { "name": "targetno", "value": $("input[name='targetno']").val() } );
	    		aoData.push( { "name": "messagetype", "value": $("#messagetype option:selected").val() } );
	    		aoData.push( { "name": "start", "value": $("input[name='start']").val() } );
	    		aoData.push( { "name": "end", "value": $("input[name='end']").val() } );
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