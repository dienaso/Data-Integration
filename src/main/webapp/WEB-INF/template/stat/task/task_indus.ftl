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
			<a href="#" class="current">任务统计(按分类)</a>
		</div>
		<h1>任务统计(按分类)</h1>
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
							<input type="text" class="input-small" name="pub_start" placeholder="开始时间" readonly>
							<span class="input-group-addon">to</span>
							<input type="text" class="input-small" name="pub_end" placeholder="结束时间" readonly></div>
					</div>

					<label class="control-label">托管时间 :</label>
					<div class="controls">
						<div class="input-daterange" id="datepicker">
							<input type="text" class="input-small" name="cash_start" placeholder="开始时间" readonly>
							<span class="input-group-addon">to</span>
							<input type="text" class="input-small" name="cash_end" placeholder="结束时间" readonly></div>
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
							<option>web</option>
							<option>iphone</option>
							<option>ipad</option>
							<option>android</option>
							<option>wap</option>
							<option>akey_pub</option>
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

					<label class="control-label">分类名称:</label>
					<div class="controls">
						<input type="text" name="indus_name" placeholder="分类名称"></div>

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
		
		// 对Date的扩展，将 Date 转化为指定格式的String
		// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
		// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
		// 例子： 
		// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
		// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
		Date.prototype.Format = function (fmt) { //author: meizz 
		    var o = {
		        "M+": this.getMonth() + 1, //月份 
		        "d+": this.getDate(), //日 
		        "h+": this.getHours(), //小时 
		        "m+": this.getMinutes(), //分 
		        "s+": this.getSeconds(), //秒 
		        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
		        "S": this.getMilliseconds() //毫秒 
		    };
		    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		    for (var k in o)
		    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		    return fmt;
		}
		
		//获取当前时间
		var date = new Date().Format("yyyy-MM-dd");
		$("input[name='cash_start']").val(date);
		$("input[name='cash_end']").val(date);
		
		<!--dateTable-->
		var table = $('#list').DataTable({
			"bDestroy": true,
			"bStateSave": true,
			"bServerSide": true,
			"bFilter": false,
			"bPaginate": false,
	        "sAjaxSource": '/task/indus/get', 
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
	    		aoData.push( { "name": "indus_name", "value": $("input[name='indus_name']").val() } );
	    		aoData.push( { "name": "cash_status", "value": $("#cash_status option:selected").val() } );
	    		aoData.push( { "name": "cash_start", "value": $("input[name='cash_start']").val() } );
	    		aoData.push( { "name": "cash_end", "value": $("input[name='cash_end']").val() } );
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