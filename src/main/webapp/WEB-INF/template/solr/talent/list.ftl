<!DOCTYPE html>
<html>
<head>
	<title>人才管理</title>
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
			<a href="#" class="current">人才管理</a>
		</div>
		<h1>人才管理</h1>
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
				
					<label class="control-label">UID:</label>
					<div class="controls">
						<input type="text" name="uid" placeholder="UID" autocomplete="off" value=""></div>
				
					<label class="control-label">用户名:</label>
					<div class="controls">
						<input type="text" name="username" placeholder="用户名"></div>
						
					<label class="control-label">商铺名:</label>
					<div class="controls">
						<input type="text" name="shop_name" placeholder="商铺名"></div>

					<label class="control-label">电话:</label>
					<div class="controls">
						<input type="text" name="mobile" placeholder="电话" autocomplete="off" value=""></div>
						
					<label class="control-label">商铺等级:</label>
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
					
					<label class="control-label">能力品级:</label>
					<div class="controls">
						<select id="w_level">
							<option>全部</option>
							<option value="9">一品</option>
							<option value="8">二品</option>
							<option value="7">三品</option>
							<option value="6">四品</option>
							<option value="5">五品</option>
							<option value="4">六品</option>
							<option value="3">七品</option>
							<option value="2">八品</option>
							<option value="1">九品</option>
						</select>
					</div>
					
					<div class="control-group">
		              <label class="control-label">已认证信息:</label>
		              <div class="controls">
		                <select multiple id="auth" placeholder="请选择">
		                  <option value="auth_realname">实名认证</option>
		                  <option value="auth_bank">银行卡认证</option>
		                  <option value="auth_email">邮箱认证</option>
		                  <option value="auth_mobile">手机认证</option>
		                </select>
		              </div>
		            </div>
		            
		            <div class="control-group">
		              <label class="control-label">未认证信息:</label>
		              <div class="controls">
		                <select multiple id="no_auth" placeholder="请选择">
		                  <option value="no_auth_realname">实名认证</option>
		                  <option value="no_auth_bank">银行卡认证</option>
		                  <option value="no_auth_email">邮箱认证</option>
		                  <option value="no_auth_mobile">手机认证</option>
		                </select>
		              </div>
		            </div>
		            
		            <label class="control-label">范围时间登陆过:</label>
					<div class="controls">
						<select id="login_time" onchange="reset1()">
							<option>全部</option>
							<option value="[NOW/DAY-1DAY TO *]">1天</option>
							<option value="[NOW/DAY-7DAY TO *]">7天</option>
							<option value="[NOW/MONTH TO *]">1个月</option>
							<option value="[NOW/MONTH-2MONTH TO *]">3个月</option>
							<option value="[NOW/MONTH-5MONTH TO *]">半年</option>
							<option value="[NOW/YEAR-1YEAR TO *]">1年</option>
						</select>
					</div>
					
					<label class="control-label">范围时间未登陆:</label>
					<div class="controls">
						<select id="no_login_time" onchange="reset2()">
							<option>全部</option>
							<option value="[* TO NOW/DAY-1DAY]">1天</option>
							<option value="[* TO NOW/DAY-7DAY]">7天</option>
							<option value="[* TO NOW/MONTH]">1个月</option>
							<option value="[* TO NOW/MONTH-2MONTH]">3个月</option>
							<option value="[* TO NOW/MONTH-5MONTH]">半年</option>
							<option value="[* TO NOW/YEAR-1YEAR]">1年</option>
						</select>
					</div>
					
					<label class="control-label">注册时间 :</label>
					<div class="controls">
						<div class="input-daterange" id="datepicker">
							<input type="text" class="input-sm form-control" name="start" placeholder="开始时间" readonly>
							<span class="input-group-addon">to</span>
							<input type="text" class="input-sm form-control" name="end" placeholder="结束时间" readonly></div>
					</div>
					
					<label class="control-label">VIP开通时间 :</label>
					<div class="controls">
						<div class="input-daterange" id="datepicker2">
							<input type="text" class="input-sm form-control" name="vip_start_start" placeholder="开始时间" readonly>
							<span class="input-group-addon">to</span>
							<input type="text" class="input-sm form-control" name="vip_start_end" placeholder="结束时间" readonly></div>
					</div>
					
					<label class="control-label">VIP到期时间 :</label>
					<div class="controls">
						<div class="input-daterange" id="datepicker3">
							<input type="text" class="input-sm form-control" name="vip_end_start" placeholder="开始时间" readonly>
							<span class="input-group-addon">to</span>
							<input type="text" class="input-sm form-control" name="vip_end_end" placeholder="结束时间" readonly></div>
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
				<h5>人才列表</h5>
			</div>
			<div class="widget-content nopadding">
				<table id="list" class="table table-bordered data-table">
					<thead>
						<tr>
							<th>ID</th>
							<th>用户名</th>
							<th>商铺名</th>
							<th>商铺等级</th>
							<th>商铺链接</th>
							<th>能力品级</th>
							<th>实名认证</th>
							<th>银行卡认证</th>
							<th>邮箱认证</th>
							<th>手机认证</th>
							<th>QQ</th>
							<th>电话</th>
							<th>威客信用</th>
							<th>注册时间</th>
							<th>VIP开通时间</th>
							<th>VIP截止时间</th>
							<th>最近登录时间</th>
							<th>操作</th>
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

	$('.input-daterange').datepicker({
	    format: "yyyy-m-d",
		language: "zh-CN",
		todayHighlight: true,
		autoclose: true
	});
		
	//获取当前时间
	//var date = new Date().Format("yyyy-MM-dd");
	//$("input[name='start']").val(date);
	//$("input[name='end']").val(date);
	//$("input[name='vip_start_start']").val(date);
	//$("input[name='vip_start_end']").val(date);
	//$("input[name='vip_end_start']").val(date);
	//$("input[name='vip_end_end']").val(date);

var tpl = $("#tpl").html();
//预编译模板
var template = Handlebars.compile(tpl);
var table;
$(function () {

	table = $("#list").DataTable({
			"bServerSide" : true,
			"bDestroy": true,
			"bStateSave": true,
			"bFilter": false,
	        "sAjaxSource": '/talent/get', 
	        "aoColumns":
	           [  
					{ "mData": "uid"},
		        	{ "mData": "username"}, 
		        	{ "mData": "shop_name"},
		        	{ "mData": "shop_level",
		        	  "mRender": function (data, type) {
	            		 if(data == 1){
	                         return "基础版本" 
	                      }else if(data == 2){
	                         return "VIP扩展版"
	                      }else if(data == 3){
	                         return "VIP旗舰版"
	                      }else if(data == 4){
	                         return "VIP白金版"
	                      }else if(data == 5){
	                         return "VIP钻石版"
	                      }else if(data == 6){
	                         return "VIP皇冠版"
	                      }else if(data == 7){
	                         return "战略合作版"
	                      }
	                  }
		        	},
		        	{ "mData": "shop_id",
		        	  "mRender": function (data, type) {
		        	  	if (data == 0)
	        		  		return "未开通商铺";
	        		  	return "<a href='http://shop.epwk.com/"+data+"/' target='_blank'>http://shop.epwk.com/"+data+"/</a>";
	                  }
		        	},
		        	{ "mData": "w_level",
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
	                      }
	                  }
		        	},
		        	{ "mData": "auth_realname",
		        	  "mRender": function (data, type) {
	            		 if(data == 1)
	                         return "是" 
	                     return "否"
	                  }
		        	},
		        	{ "mData": "auth_bank",
		        	  "mRender": function (data, type) {
	            		 if(data == 1)
	                         return "是" 
	                     return "否"
	                  }
		        	},
		        	{ "mData": "auth_email",
		        	  "mRender": function (data, type) {
	            		 if(data == 1)
	                         return "是" 
	                     return "否"
	                  }
		        	},
		        	{ "mData": "auth_mobile",
		        	  "mRender": function (data, type) {
	            		 if(data == 1)
	                         return "是" 
	                     return "否"
	                  }
		        	},
		        	{ "mData": "qq"}, 
		        	{ "mData": "mobile"}, 
		        	{ "mData": "credit_score"},
		        	{ "mData": "reg_time_date",
		        	  "mRender": function (data, type) {
		        	  	if (data == null)
	        		  		return null;
	        		  	return formatDateTime(data.time);
	                  }
		        	},
		        	{ "mData": "vip_start_time_date",
		        	  "mRender": function (data, type) {
		        	  	if (data == null)
	        		  		return null;
	        		  	return formatDateTime(data.time);
	                  }
		        	},
		        	{ "mData": "vip_end_time_date",
		        	  "mRender": function (data, type) {
		        	  	if (data == null)
	        		  		return null;
	        		  	return formatDateTime(data.time);
	                  }
		        	},
		        	{ "mData": "last_login_time",
		        	  "mRender": function (data, type) {
		        	  	if (data == null)
	        		  		return null;
	        		  	return formatDateTime(data.time);
	                  }
		        	},
	        	],
	        "columnDefs": [
	        		{
					 	sDefaultContent: '',
					 	aTargets: [ '_all' ]
					},
	                {
	                    targets: 17,
	                    render: function (a, b, c, d) {
	                        var context =
	                        {
	                            func: [
	                            	{"name": "更新", "fn": "update(\'" + c.uid + "\')", "type": "info"},
	                                {"name": "删除", "fn": "del(\'" + c.uid + "\')", "type": "danger"}
	                            ]
	                        };
	                        var html = template(context);
	                        return html;
	                    }
	                }
	 
	            ],
	        "fnServerData" : function(sSource, aoData, fnCallback) {
	        	var auth = new Array();
				$("#auth option:selected").each(function(){
		            auth.push($(this).val());
		        });
		        var no_auth = new Array();
				$("#no_auth option:selected").each(function(){
		            no_auth.push($(this).val());
		        });
	        	aoData.push( { "name": "username", "value": $("input[name='username']").val() } );
	        	aoData.push( { "name": "shop_name", "value": $("input[name='shop_name']").val() } );
	    		aoData.push( { "name": "uid", "value": $("input[name='uid']").val() } );
	    		aoData.push( { "name": "mobile", "value": $("input[name='mobile']").val() } );
	    		aoData.push( { "name": "shop_level", "value": $("#shop_level option:selected").val() } );
	    		aoData.push( { "name": "w_level", "value": $("#w_level option:selected").val() } );
	    		aoData.push( { "name": "no_login_time", "value": $("#no_login_time option:selected").val() } );
	    		aoData.push( { "name": "login_time", "value": $("#login_time option:selected").val() } );
	    		aoData.push( { "name": "auth", "value": auth.toString() } );
	    		aoData.push( { "name": "no_auth", "value": no_auth.toString() } );
	    		aoData.push( { "name": "start", "value": $("input[name='start']").val() } );
	    		aoData.push( { "name": "end", "value": $("input[name='end']").val() } );
	    		aoData.push( { "name": "vip_start_start", "value": $("input[name='vip_start_start']").val() } );
	    		aoData.push( { "name": "vip_start_end", "value": $("input[name='vip_start_end']").val() } );
	    		aoData.push( { "name": "vip_end_start", "value": $("input[name='vip_end_start']").val() } );
	    		aoData.push( { "name": "vip_end_end", "value": $("input[name='vip_end_end']").val() } );
	    		
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
     * 删除索引
     * @param uid
     */
	function del(uid) {
        $.ajax({
            "url": "/talent/del",
            "type": "get",
            "data": {
	            "uid": uid
	         }, success: function (data) {
	            table.ajax.reload();
	            $.gritter.add({
					title:	'操作提示！',
					text:	data.msg,
					sticky: false
				});	
	         }
        });
    }
    
    /**
     * 修改索引
     * @param uid
     */
	function update(uid) {
        $.ajax({
            "url": "/talent/update",
            "type": "post",
            "data": {
	            "uid": uid
	         }, success: function (data) {
	            table.ajax.reload();
	            $.gritter.add({
					title:	'操作提示！',
					text:	data.msg,
					sticky: false
				});	
	         }
        });
    }

	function reset2() {
    	$("#login_time").select2("val", "");
    }

    function reset1() {
    	$("#no_login_time").select2("val", "");
    }
</script>
</body>
</html>