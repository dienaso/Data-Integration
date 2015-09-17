<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>数据统计</title>
	</head>

	<body>

	  <div id="content-header">
	    <div id="breadcrumb"><a href="/" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a> <a href="#" title="Go to Chart" class="tip-bottom"><i class="icon-bar-chart"></i> 数据统计</a> <a href="#" class="current">用户注册统计</a></div>
	    <h1>用户注册统计</h1>
	  </div>
	  
      <div class="widget-box">
        
      </div>
	  
	  <div class="container-fluid">
	  	<div class="widget-box">
	  	
	  	<div class="widget-title"> <span class="icon"> <i class="icon-search"></i> </span>
          <h5>搜索区域</h5>
        </div>
        <div class="widget-content nopadding form-horizontal">
            <div class="control-group">
              <label class="control-label">注册时间 :</label>
              <div class="controls">
	              <div class="input-daterange" id="datepicker">
				    <input type="text" class="input-small" name="reg_start" placeholder="开始时间" readonly>
				    <span class="add-on">to</span>
				    <input type="text" class="input-small" name="reg_end" placeholder="结束时间" readonly>
				  </div>
			  </div>
            </div>
         
            <div class="form-actions">
              <button id="search-btn" class="btn">查询</button>
            </div>
        </div>	
	  	
		  <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
		    <h5>用户组注册统计列表</h5>
		  </div>
		  <div class="widget-content nopadding">
		    <table id="list" class="table table-bordered data-table">
		        <thead>
		        	<tr>
			          	<th rowspan="2">日期</th>
			          	<th colspan="8">注册数</th>
		        	</tr>
		        	<tr>
		        		<th>TOTAL</th>
		                <th>WEB</th>
		                <th>CPM</th>
		                <th>APP</th>
		                <th>WAP</th>
		                <th>酷贝街</th>
		                <th>后台</th>
		                <th>云创平台</th>
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
		
		// 对Date的扩展，将 Date 转化为指定格式的String
		// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
		// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
		// 例子： 
		// (new Date()).Format("yyyy-MM-dd") ==> 2006-07-02 08:09:04.423 
		// (new Date()).Format("yyyy-MM-dd")      ==> 2006-7-2 8:9:4.18 
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
		$("input[name='reg_start']").val(date);
		$("input[name='reg_end']").val(date);
		
		<!--dateTable-->
		var table = $('#list').dataTable({
			"bServerSide" : true,
			"bDestroy": true,
			"bStateSave": true,
			"bFilter": false,
			"bPaginate": false,
	        "sAjaxSource": '/users/register/get', 
	        "aoColumns":
	           [  
					{ "mData": "label"},
					{ "mData": "TOTAL"},
		        	{ "mData": "WEB"},
		        	{ "mData": "cpm"},
		        	{ "mData": "APP"},
		        	{ "mData": "WAP"},
		        	{ "mData": "mall"},
		        	{ "mData": "background"},
		        	{ "mData": "yun"}
	        	],
	    	"fnServerData" : function(sSource, aoData, fnCallback) {
	    		aoData.push( { "name": "reg_start", "value": $("input[name='reg_start']").val() } );
	    		aoData.push( { "name": "reg_end", "value": $("input[name='reg_end']").val() } );
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
		    table.fnDraw();
		} );
	} );
	
	</script>
	</body>
</html>