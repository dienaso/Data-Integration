<!DOCTYPE html>
<html>
<head>
	<title>系统参数管理</title>
</head>
<body>
	<div id="content-header">
		<div id="breadcrumb">
			<a href="#" class="tip-bottom"> <i class="icon-home"></i>
				Home
			</a>
			<a href="#" class="tip-bottom"> <i class="icon-bar-key"></i>
				权限管理
			</a>
			<a href="#" class="current">角色管理</a>
		</div>
		<h1>角色管理</h1>
	</div>
	<div class="container-fluid">
		<div class="widget-box">
			<div class="widget-title">
				<span class="icon"> <i class="icon-th"></i>
				</span>
				<h5>角色列表</h5>
			</div>
			<div class="widget-content nopadding">

				<table id="list" class="table table-bordered data-table">
					<thead>
						<tr>
							<th>ID</th>
							<th>角色名</th>
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
	                <h4 class="modal-title" id="myModalLabel">添加</h4>
	            </div>
	            <div class="modal-body form-horizontal">
					<input type="hidden" id="id">
	                <div class="control-group">
	                	<label class="control-label">角色名 :</label>
	                	<div class="controls">
	                    	<input type="text" class="form-control" id="roleName" placeholder="角色名">
	                    </div>
	                </div>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-primary" id="save">保存</button>
	                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
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
	        "sAjaxSource": '/roles/get', 
	        "aoColumns":
	           [  
	           		{ "mData": "id"},
		        	{ "mData": "roleName"}		        	
	        	],
	        "columnDefs": [
	                {
	                    targets: 2,
	                    render: function (a, b, c, d) {
	                        var context =
	                        {
	                            func: [
	                                {"name": "修改", "fn": "edit(\'" + c.id + "\', \'" + c.roleName + "\')", "type": "primary"},
	                                {"name": "删除", "fn": "del(\'" + c.id + "\')", "type": "danger"}
	                            ]
	                        };
	                        var html = template(context);
	                        return html;
	                    }
	                }
	 
	            ],
            initComplete: function () {
                $("#mytool").append('<button type="button" class="btn btn-success btn-sm" data-toggle="modal" data-target="#myModal">添加</button>');
				$("#mytool").on("click", clear);
				$("#save").click(add);
			},
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
     * 清除
     */
    function clear() {
    	editFlag = false;
    	$("#myModalLabel").text("添加");
        $("#roleName").val("");
    }
 
    /**
     * 添加数据
     **/
    function add() {
        var addJson = {
            "id": $("#id").val(),
            "roleName": $("#roleName").val()
        };
        ajax(addJson);
    }
    
    /**
     *编辑方法
     **/
    function edit(id, roleName) {
        clear();
        editFlag = true;
        $("#myModalLabel").text("修改");
        $("#id").val(id);
        $("#roleName").val(roleName);
        $("#myModal").modal("show");
        
    }
 
 	/**
     *ajax提交
     **/
    function ajax(obj) {
        var url ="/roles/add" ;
        if(editFlag){
            url = "/roles/update";
        }
        $.ajax({
            "url":url ,
            "type": "post",
            "data": {
            	"id": obj.id,
                "roleName": obj.roleName
            }
        });
    }
 
 
    /**
     * 删除数据
     * @param id
     */
    function del(id) {
        $.ajax({
            "url": "/roles/del",
            "type": "get",
            "data": {
	            "id": id
	         }
        });
    }
    
    
</script>
</body>
</html>