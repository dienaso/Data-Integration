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
			<a href="#" class="current">能力品级x商铺等级</a>
		</div>
		<h1>能力品级x商铺等级</h1>
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
				<h5>能力品级x商铺等级统计列表</h5>
			</div>
			<div class="widget-content nopadding">
				<table id="list" class="table table-bordered data-table">
					<thead>
						<tr>
							<th>能力品级</th>
							<th>总计</th>
							<th>至尊皇冠</th>
							<th>金尊皇冠</th>
							<th>皇冠版</th>
							<th>钻石版</th>
							<th>白金版</th>
                            <th>旗舰版</th>
							<th>拓展版</th>
							<th>基础版</th>
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
	        "sAjaxSource": '/talent/wAndShopLevel/get', 
	        "aoColumns":
	           [  
					{ "mData": "label",
		        	  "mRender": function (data, type) {
	            		 if(data == 1){
	                         return "九品" 
	                      }else if(data == 2){
	                         return "八品"
	                      }else if(data == 3){
	                         return "七品"
	                      }else if(data == 4){
	                         return "六品"
	                      }else if(data == 5){
	                         return "五品"
	                      }else if(data == 6){
	                         return "四品"
	                      }else if(data == 7){
	                         return "三品"
	                      }else if(data == 8){
	                         return "二品"
	                      }else if(data == 9){
	                         return "一品"
	                      }else{
	                         return data
	                      }
	                    }
	                 },
					{ "mData": "TOTAL"},
					{ "mData": "至尊皇冠"},
					{ "mData": "金尊皇冠"},
					{ "mData": "VIP皇冠"},
					{ "mData": "VIP钻石"},
		        	{ "mData": "VIP白金"},
		        	{ "mData": "VIP旗舰"},
                    { "mData": "VIP拓展"},
		        	{ "mData": "基础版"}
	        	],
	    	"fnServerData" : function(sSource, aoData, fnCallback) {
	    		aoData.push( { "name": "reg_start", "value": $("input[name='reg_start']").val() } );
	    		aoData.push( { "name": "reg_end", "value": $("input[name='reg_end']").val() } );
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