<!DOCTYPE html>
<html>
<head>
	<title>服务管理</title>
</head>
<body>
	<div id="content-header">
		<div id="breadcrumb">
			<a href="/" title="Go to Home" class="tip-bottom"> <i class="icon-home"></i>
				Home
			</a>
			<a href="#" title="Go to Chart" class="tip-bottom"> <i class="icon-cloud"></i>
				索引管理
			</a>
			<a href="#" class="current">服务管理</a>
		</div>
		<h1>服务管理</h1>
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
					<label class="control-label">用户名:</label>
					<div class="controls">
						<input type="text" name="username" placeholder="用户名"></div>

					<label class="control-label">UID:</label>
					<div class="controls">
						<input type="text" name="uid" placeholder="UID" value=""></div>
						
					<label class="control-label">SERVICE_ID:</label>
					<div class="controls">
						<input type="text" name="service_id" placeholder="SERVICE_ID" value=""></div>
				</div>

				<div class="form-actions">
					<button id="search-btn" class="btn">查询</button>
				</div>
			</div>
	
		<div class="widget-box">
			<div class="widget-title">
				<span class="icon"> <i class="icon-th"></i>
				</span>
				<h5>服务列表</h5>
			</div>
			<div class="widget-content nopadding">
				<table id="list" class="table table-bordered data-table">
					<thead>
						<tr>
							<th>ID</th>
							<th>UID</th>
							<th>用户名</th>
							<th>商铺名</th>
							<th>标题</th>
							<th>价格</th>
							<th>操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>

<script src="/common/matrix/js/jquery.dataTables.js"></script>
<script src="/common/matrix/js/jquery.ui.custom.js"></script>
<script src="/common/matrix/js/jquery.uniform.js"></script>
<script src="/common/matrix/js/select2.min.js"></script>
<script src="/common/matrix/js/matrix.tables.js"></script>
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
	        "sAjaxSource": '/service/get', 
	        "aoColumns":
	           [  
					{ "mData": "service_id"},
					{ "mData": "uid"}, 
		        	{ "mData": "username"}, 
		        	{ "mData": "shop_name"},
		        	{ "mData": "title"},
		        	{ "mData": "price"},
	        	],
	        "columnDefs": [
	        		{
					 	sDefaultContent: '',
					 	aTargets: [ '_all' ]
					},
	                {
	                    targets: 6,
	                    render: function (a, b, c, d) {
	                        var context =
	                        {
	                            func: [
	                            	{"name": "更新", "fn": "update(\'" + c.service_id + "\')", "type": "info"},
	                                {"name": "删除", "fn": "del(\'" + c.service_id + "\')", "type": "danger"}
	                            ]
	                        };
	                        var html = template(context);
	                        return html;
	                    }
	                }
	 
	            ],
	        "fnServerData" : function(sSource, aoData, fnCallback) {
	        	aoData.push( { "name": "username", "value": $("input[name='username']").val() } );
	    		aoData.push( { "name": "uid", "value": $("input[name='uid']").val() } );
	    		aoData.push( { "name": "service_id", "value": $("input[name='service_id']").val() } );
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
	function del(service_id) {
        $.ajax({
            "url": "/service/del",
            "type": "get",
            "data": {
	            "service_id": service_id
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