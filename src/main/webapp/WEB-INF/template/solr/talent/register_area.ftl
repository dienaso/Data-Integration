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
		<h1>用户注册统计(按地区)</h1>
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

					<label class="control-label">行业分类:</label>
					<div class="controls">
						<select id="indus1" onchange="get2Child(this)">
							<option>全部</option>
							<option value="1">设计</option>
							<option value="2">开发</option>
							<option value="3">文案</option>
							<option value="4">装修</option>
							<option value="5">营销</option>
							<option value="6">商务</option>
							<option value="7">生活</option>
						</select>
						<select id="indus2" onchange="get3Child(this)">
						</select>
						<select id="indus3">
						</select>
					</div>
					
					<label class="control-label">店铺等级:</label>
					<div class="controls">
						<select id="shop_level">
							<option>全部</option>
							<option>全部VIP</option>
							<option value="1">基础版本</option>
							<option value="2">扩展版</option>
							<option value="3">旗舰版</option>
							<option value="4">白金版</option>
							<option value="5">钻石版</option>
							<option value="6">皇冠版</option>
							<option value="7">金尊皇冠版</option>
							<option value="8">至尊皇冠版</option>
						</select>
					</div>
					
					<label class="control-label">区域类型:</label>
					<div class="controls">
						<select id="area_type">
							<option>按省份</option>
							<option>按城市</option>
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
							<th rowspan="2">地区</th>
							<th rowspan="2">TOTAL</th>
							<th colspan="4">身份类型</th>
						</tr>
						<tr>
							<th>未确定</th>
							<th>威客</th>
							<th>雇主</th>
							<th>威客雇主</th>
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
	        "sAjaxSource": '/talent/register/area/get', 
	        "aoColumns":
	           [  
					{ "mData": "label"},
					{ "mData": "TOTAL"},
					{ "mData": "uncertain"},
					{ "mData": "witkey"},
					{ "mData": "employer"},
					{ "mData": "both"}
	        	],
	    	"fnServerData" : function(sSource, aoData, fnCallback) {
	    		aoData.push( { "name": "start", "value": $("input[name='start']").val() } );
	    		aoData.push( { "name": "end", "value": $("input[name='end']").val() } );
	    		aoData.push( { "name": "come", "value": $("#come option:selected").val() } );
	    		aoData.push( { "name": "user_role", "value": $("#user_role option:selected").val() } );
	    		aoData.push( { "name": "indus1", "value": $("#indus1 option:selected").val() } );
	    		aoData.push( { "name": "indus2", "value": $("#indus2 option:selected").val() } );
	    		aoData.push( { "name": "indus3", "value": $("#indus3 option:selected").val() } );
	    		aoData.push( { "name": "shop_level", "value": $("#shop_level option:selected").val() } );
	    		aoData.push( { "name": "area_type", "value": $("#area_type option:selected").val() } );
	    		aoData.push( { "name": "user_role", "value": $("#user_role option:selected").val() } );
	    		
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
	
	
	 /**
     * 获取二级菜单
     * @param obj
     */
    function get2Child(obj) {
    	$("#indus2").select2("val", "");
    	$("#indus3").select2("val", "");
    	var g_id = obj.value;
    	if(g_id == "全部") {
    		$("#indus2").select2("val","");
    		$("#indus3").select2("val","");
    		return;
    	}
        $.ajax({
            "url": "/industry/getChild",
            "type": "get",
            "data": {
	            "g_id": g_id
	         },
	         "success" : function(resp) {
	         	var optionHtml = "";
			 	$.each(resp, function(index, content){ 
				  optionHtml += "<option value=" + content.indus_id + ">" + content.indus_name + "</option>";
				}); 
				$("#indus2").html(optionHtml);
			 }
        });
        $("#indus2").change();
    }
    /**
     * 获取三级菜单
     * @param obj
     */
    function get3Child(obj) {
    	$("#indus3").select2("val", "");
    	var indus_pid = obj.value;
        $.ajax({
            "url": "/industry/getChild",
            "type": "get",
            "data": {
	            "indus_pid": indus_pid
	         },
	         "success" : function(resp) {
			 	var optionHtml = "";
			 	$.each(resp, function(index, content){ 
				  optionHtml += "<option value=" + content.indus_id + ">" + content.indus_name + "</option>";
				}); 
				$("#indus3").html(optionHtml);
			 }
        });
    }
	</script>
</body>
</html>