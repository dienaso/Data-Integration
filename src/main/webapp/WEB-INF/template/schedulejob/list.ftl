<!DOCTYPE html>
<html>
<head>
	<title>计划任务管理</title>
</head>
<body>
	<div id="content-header">
		<div id="breadcrumb">
			<a href="/" title="Go to Home" class="tip-bottom"> <i class="icon-home"></i>
				Home
			</a>
			<a href="#" class="current">计划任务管理</a>
		</div>
		<h1>计划任务管理</h1>
	</div>
	<div class="container-fluid">
		<div class="widget-box">
			<div class="widget-title">
				<span class="icon"> <i class="icon-th"></i>
				</span>
				<h5>计划任务列表</h5>
			</div>
			<div class="widget-content nopadding">

				<table id="list" class="table table-bordered data-table">
					<thead>
						<tr>
							<th>ID</th>
							<th>计划任务名称</th>
							<th>状态</th>
							<th>cron表达式</th>
							<th>执行类</th>
							<th>执行方法</th>
							<th>是否允许并行</th>
							<th>创建时间</th>
							<th>最近执行时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody></tbody>
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
	                <h4 class="modal-title" id="myModalLabel">新增</h4>
	            </div>
	            <div class="modal-body form-horizontal">
					
	                <div class="control-group">
	                	<label class="control-label">计划任务名称 :</label>
	                	<div class="controls">
	                    	<input type="text" class="form-control" id="jobName" placeholder="计划任务名称">
	                    </div>
	                </div>
	                <div class="control-group">
	                	<label class="control-label">状态 :</label>
		                <div class="controls">
			                <label>
			                  <div class="radio"><span><input type="radio" value="1" name="jobStatus" style="opacity: 0;"></span></div>
			                  	开启 </label>
			                <label>
			                  <div class="radio"><span><input type="radio" value="0" name="jobStatus" style="opacity: 0;"></span></div>
			                 	 关闭</label>
		                </div>
	                </div>
	                <div class="control-group">
	                	<label class="control-label">cron表达式:</label>
		                <div class="controls">
		                    <input type="text" class="form-control" id="cronExpression" placeholder="cron表达式">
		                </div>
	                </div>
	                <div class="control-group">
	                	<label class="control-label">执行类:</label>
		                <div class="controls">
		                    <input type="text" class="form-control" id="beanClass" placeholder="执行类">
		                </div>
	                </div>
	                <div class="control-group">
	                	<label class="control-label">执行方法:</label>
		                <div class="controls">
		                    <input type="text" class="form-control" id="methodName" placeholder="执行方法">
		                </div>
	                </div>
	                <div class="control-group">
	                	<label class="control-label">是否允许并行:</label>
		                <div class="controls">
			                <label>
			                  <div class="radio"><span><input type="radio" value="1" name="isConcurrent" style="opacity: 0;"></span></div>
			                  	是 </label>
			                <label>
			                  <div class="radio"><span><input type="radio" value="0" name="isConcurrent" style="opacity: 0;"></span></div>
			                 	 否</label>
		                </div>
	                </div>
	                <div class="control-group">
						<label class="control-label">描述:</label>
						<div class="controls">
							<textarea id="description"></textarea>
						</div>
					</div>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-info" id="initData">添加模板</button>
	                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	                <button type="button" class="btn btn-primary" id="save">保存</button>
	            </div>
	        </div>
	    </div>
	</div>
	<script src="/common/matrix/js/handlebars-v4.0.2.js"></script>
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
	var oTable;
	$(document).ready(function() {
		oTable = $("#list").dataTable({
			"bServerSide" : true,
			"bDestroy": true,
			"bStateSave": true,
			"bFilter": false,
	        "sAjaxSource": '/schedulejob/get', 
	        "aoColumns":
	           [  
					{ "mData": "id"},
		        	{ "mData": "jobName"}, 
		        	{ "mData": "jobStatus",
		        	  "mRender": function (data, type) {
	            		 if(data == 1){
	                         return "启用" 
	                      }else{
	                         return "禁用"
	                      }
	                  }
		        	},
		        	{ "mData": "cronExpression"},
		        	{ "mData": "beanClass"},
		        	{ "mData": "methodName"},
		        	{ "mData": "isConcurrent",
		        	  "mRender": function (data, type) {
	            		 if(data == 1){
	                         return "是" 
	                      }else{
	                         return "否"
	                      }
	                  }
		        	},
		        	{ "mData": "createTime",
	        		  "mRender": function (data, type) {
	        		  	return formatDateTime(data.time);
	                  }
		        	 },
		        	{ "mData": "lastSucceeTime",
		        	  "mRender": function (data, type) {
	        		  	return formatDateTime(data.time);
	                  }
		        	}
	        	],
	        "columnDefs": [
	                {
	                    targets: 9,
	                    render: function (a, b, c, d) {
	                        var context =
	                        {
	                            func: [
	                                {"name": "修改", "fn": "edit(\'" + c.jobName + "\',\'" + c.jobStatus + "\',\'" + c.cronExpression + "\',\'" + c.beanClass + "\',\'" + c.methodName + "\',\'" + c.isConcurrent + "\',\'" + c.description + "\')", "type": "primary"},
	                                {"name": "删除", "fn": "del(\'" + c.id + "\')", "type": "danger"}
	                            ]
	                        };
	                        var html = template(context);
	                        return html;
	                    }
	                }
	 
	            ],
            initComplete: function () {
				clear();
                $("#mytool").append('<button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#myModal">添加</button>');
            	$("#initData").on("click", initData);
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
     * 添加数据模板
     */
    function initData() {
        $("#jobName").val("计划任务1");
        $("input:radio[name=jobStatus][value='1']").parent("span").attr("class","checked");
        $("#cronExpression").val("30 * * * * ?");
        $("#beanClass").val("com.epweike.quartz.job.Test");
        $("input:radio[name=isConcurrent][value='1']").parent("span").attr("class","checked");
        $("#methodName").val("test");
    }
 
    /**
     * 清除
     */
    function clear() {
        $("#jobName").val("");
        $("#cronExpression").val("");
        $("#beanClass").val("");
        $("#methodName").val("");
        $("input:radio[name=jobStatus]").attr("checked",false);
        $("input:radio[name=isConcurrent]").attr("checked",false);
        editFlag = false;
    }
 
    /**
     * 添加数据
     **/
    function add() {
    	var isConcurrent;
        var jobStatus;
        //遍历选中单选框
        $(".checked").each(function(){
        	if($(this).children().attr("name") == "jobStatus") {
        		jobStatus = $(this).children().val();
        	} else{
        		isConcurrent = $(this).children().val();
        	}
		});
        var addJson = {
            "jobName": $("#jobName").val(),
            "jobStatus": jobStatus,
            "cronExpression": $("#cronExpression").val(),
            "beanClass": $("#beanClass").val(),
            "methodName": $("#methodName").val(),
            "isConcurrent": isConcurrent,
            "description": $("#description").val(),
        };
 
        ajax(addJson);
    }
    
    /**
     *编辑方法
     **/
    function edit(jobName,jobStatus,cronExpression,beanClass,methodName,isConcurrent,description) {
        
        editFlag = true;
        $("#myModalLabel").text("修改");
        $("#jobName").val(jobName);
        $("input:radio[name=jobStatus][value="+jobStatus+"]").parent("span").attr("class","checked");
        $("#cronExpression").val(cronExpression);
        $("#beanClass").val(beanClass);
        $("#methodName").val(methodName);
        $("input:radio[name=isConcurrent][value="+isConcurrent+"]").parent("span").attr("class","checked");
        $("#description").val(description);
        $("#myModal").modal("show");
        
    }
 
    function ajax(obj) {
        var url ="/add.jsp" ;
        if(editFlag){
            url = "/edit.jsp";
        }
        $.ajax({
            url:url ,
            data: {
                "name": obj.name,
                "position": obj.position,
                "salary": obj.salary,
                "start_date": obj.start_date,
                "office": obj.office,
                "extn": obj.extn
            }, success: function (data) {
                table.ajax.reload();
                $("#myModal").modal("hide");
                $("#myModalLabel").text("新增");
                clear();
                console.log("结果" + data);
            }
        });
    }
 
 
    /**
     * 删除数据
     * @param name
     */
    function del(name) {
        $.ajax({
            url: "/del.jsp",
            data: {
                "name": name
            }, success: function (data) {
                table.ajax.reload();
                console.log("删除成功" + data);
            }
        });
    }

</script>
</body>
</html>