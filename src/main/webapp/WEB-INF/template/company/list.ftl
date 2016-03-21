<!DOCTYPE html>
<html>
<head>
	<title>企业信息</title>
</head>
<body>
	<div id="content-header">
		<div id="breadcrumb">
			<a href="#" class="tip-bottom"> <i class="icon-home"></i>
				Home
			</a>
			<a href="#" class="tip-bottom"> <i class="icon-star"></i>
				网页采集
			</a>
			<a href="#" class="current">企业信息</a>
		</div>
		<h1>企业信息</h1>
	</div>
	
	<div class="container-fluid">
		<div class="widget-box">
			<div class="widget-title">
				<span class="icon"> <i class="icon-th"></i>
				</span>
				<h5>企业信息列表</h5>
			</div>
			<div class="widget-content nopadding">

				<table id="list" class="table table-bordered data-table">
					<thead>
						<tr>
							<th>ID</th>
							<th>公司名称</th>
							<th>主营产品</th>
							<th>注册地址</th>
							<th>负责人</th>
							<th>电话</th>
							<th>注册资金</th>
							<th>注册日期</th>
							<th>所属分类</th>
							<th>所属城市</th>
							<th>操作</th>
						</tr>
					</thead>

				</table>
			</div>
		</div>
	</div>
	
	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
	                        aria-hidden="true">&times;</span></button>
	                <h4 class="modal-title" id="myModalLabel">详情</h4>
	            </div>
	            <div class="modal-body form-horizontal">
	                <div class="control-group">
	                	<div class="controls">
	                    	<div id="remarks"></div>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
	
	<script src="/common/matrix/js/ajaxsetup.js"></script>
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
    var editFlag = false;
	var table;
	$(function () {
		table = $("#list").DataTable({
			"bServerSide" : true,
			"bDestroy": true,
			"bStateSave": true,
			"bFilter": false,
	        "sAjaxSource": '/company/get', 
	        "aoColumns":
	           [  
					{ "mData": "id"},
		        	{ "mData": "name"}, 
		        	{ "mData": "mainProducts"},
		        	{ "mData": "registerAddr"},
		        	{ "mData": "principal"},
		        	{ "mData": "phone"},
		        	{ "mData": "registerCapital"},
		        	{ "mData": "registerDate"},
		        	{ "mData": "category"},
		        	{ "mData": "city"}
	        	],
	        "columnDefs": [
	                {
	                    targets: 10,
	                    render: function (a, b, c, d) {
	                        var context =
	                        {
	                            func: [
	                                {"name": "查看详情", "fn": "view(\'" + encodeURI(c.remarks) + "\')", "type": "primary"},
	                            ]
	                        };
	                        var html = template(context);
	                        return html;
	                    }
	                }
	 
	            ],
	        "fnServerData" : function(sSource, aoData, fnCallback) {
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
	} );
	
    /**
     *编辑方法
     **/
    function view(remarks) {
    	$("#remarks").html(decodeURI(remarks));
        $("#myModal").modal("show");
    }
 
	</script>
</body>
</html>