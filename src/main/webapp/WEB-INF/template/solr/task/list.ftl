<!DOCTYPE html>
<html>
<head>
	<title>任务管理</title>
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
			<a href="#" class="current">任务管理</a>
		</div>
		<h1>任务管理</h1>
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
						<input type="text" name="task_title" placeholder="标题"></div>

					<label class="control-label">UID:</label>
					<div class="controls">
						<input type="text" name="uid" placeholder="UID" value=""></div>
						
					<label class="control-label">TASK_ID:</label>
					<div class="controls">
						<input type="text" name="task_id" placeholder="TASK_ID" value=""></div>
				</div>

				<div class="form-actions">
					<button id="search-btn" class="btn">查询</button>
				</div>
			</div>
	
		<div class="widget-box">
			<div class="widget-title">
				<span class="icon"> <i class="icon-th"></i>
				</span>
				<h5>任务列表</h5>
			</div>
			<div class="widget-content nopadding">
				<table id="list" class="table table-bordered data-table">
					<thead>
						<tr>
							<th>ID</th>
							<th>UID</th>
							<th>标题</th>
							<th>价格</th>
							<th>状态</th>
							<th>发布时间</th>
							<th>截止时间</th>
							<th>浏览量</th>
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
	        "sAjaxSource": '/task/get', 
	        "aoColumns":
	           [  
					{ "mData": "task_id"},
					{ "mData": "uid"}, 
		        	{ "mData": "task_title"}, 
		        	{ "mData": "task_cash"},
		        	{ "mData": "task_status",
		        	  "mRender": function (data, type) {
	            		 if(data == -1){
	                         return "未确认" 
	                      }else if(data == 0){
	                         return "未付款"
	                      }else if(data == 1){
	                         return "待审核"
	                      }else if(data == 2){
	                         return "投稿中"
	                      }else if(data == 3){
	                         return "选稿中"
	                      }else if(data == 4){
	                         return "摇奖中"
	                      }else if(data == 5){
	                         return "公示中"
	                      }else if(data == 6){
	                         return "交付"
	                      }else if(data == 7){
	                         return "冻结"
	                      }else if(data == 8){
	                         return "结束"
	                      }else if(data == 9){
	                         return "失败"
	                      }else if(data == 10){
	                         return "审核失败"
	                      }else if(data == 11){
	                         return "仲裁"
	                      }
	                  }
		        	},
		        	{ "mData": "pub_time",
		        	  "mRender": function (data, type) {
		        	  	if (data == null)
	        		  		return null;
	        		  	return formatDateTime(data * 1000);
	                  }
		        	},
		        	{ "mData": "end_time",
		        	  "mRender": function (data, type) {
		        	  	if (data == null)
	        		  		return null;
	        		  	return formatDateTime(data * 1000);
	                  }
		        	},
		        	{ "mData": "view_num"}
	        	],
	        "columnDefs": [
	        		{
					 	sDefaultContent: '',
					 	aTargets: [ '_all' ]
					},
	                {
	                    targets: 8,
	                    render: function (a, b, c, d) {
	                        var context =
	                        {
	                            func: [
	                            	{"name": "更新", "fn": "update(\'" + c.task_id + "\')", "type": "info"},
	                                {"name": "删除", "fn": "del(\'" + c.task_id + "\')", "type": "danger"}
	                            ]
	                        };
	                        var html = template(context);
	                        return html;
	                    }
	                }
	 
	            ],
	        "fnServerData" : function(sSource, aoData, fnCallback) {
	        	aoData.push( { "name": "task_title", "value": $("input[name='task_title']").val() } );
	    		aoData.push( { "name": "uid", "value": $("input[name='uid']").val() } );
	    		aoData.push( { "name": "task_id", "value": $("input[name='task_id']").val() } );
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
     * @param task_id
     */
	function del(task_id) {
        $.ajax({
            "url": "/task/del",
            "type": "get",
            "data": {
	            "task_id": task_id
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
	function update(task_id) {
        $.ajax({
            "url": "/task/update",
            "type": "post",
            "data": {
	            "task_id": task_id
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

</script>
</body>
</html>