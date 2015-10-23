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
							<th>任务名称</th>
							<th>任务组</th>
							<th>状态</th>
							<th>cron表达式</th>
							<th>执行类</th>
							<th>执行方法</th>
							<th>是否允许并行</th>
							<th>最近执行时间</th>
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
	                	<label class="control-label">任务名称 :</label>
	                	<div class="controls">
	                    	<input type="text" class="form-control" id="jobName" placeholder="计划任务名称">
	                    </div>
	                </div>
	                <div class="control-group">
	                	<label class="control-label">任务组 :</label>
	                	<div class="controls">
	                    	<input type="text" class="form-control" id="jobGroup" placeholder="任务组">
	                    </div>
	                </div>
	                <div class="control-group">
	                	<label class="control-label">状态 :</label>
		                <div class="controls">
			                <label>
			                  <div class="radio"><span><input type="radio" value="1" name="jobStatus" style="opacity: 0;"></span></div>
			                  	启用</label>
			                <label>
			                  <div class="radio"><span><input type="radio" value="0" name="jobStatus" style="opacity: 0;"></span></div>
			                 	 禁用</label>
		                </div>
	                </div>
	                <div class="control-group">
	                	<label class="control-label">cron表达式:</label>
		                <div class="controls">
		                    <input type="text" class="form-control" id="cronExpression" placeholder="cron表达式">
		                </div>
	                </div>
	                <div class="control-group">
	                	<label class="control-label">SpringID:</label>
		                <div class="controls">
		                    <input type="text" class="form-control" id="springId" placeholder="SpringID">
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
	        "sAjaxSource": '/schedulejob/get', 
	        "aoColumns":
	           [  
					{ "mData": "id"},
		        	{ "mData": "jobName"}, 
		        	{ "mData": "jobGroup"}, 
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
		        	{ "mData": "lastSucceeTime",
		        	  "mRender": function (data, type) {
		        	  	if (data == null)
	        		  		return null;
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
	                            	{"name": "执行", "fn": "run(\'" + c.id + "\')", "type": "info"},
	                                {"name": "修改", "fn": "edit(\'" + c.id + "\',\'" + c.jobName + "\',\'" + c.jobGroup + "\',\'" + c.jobStatus + "\',\'" + c.cronExpression + "\',\'" + c.springId + "\',\'" + c.beanClass + "\',\'" + c.methodName + "\',\'" + c.isConcurrent + "\',\'" + c.description + "\')", "type": "primary"},
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
            	$("#initData").on("click", initData);
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
     * 添加数据模板
     */
    function initData() {
    	clear();
    	editFlag = false;
    	$("#myModalLabel").text("添加");
        $("#jobName").val("taskJob1");
        $("#jobGroup").val("test");
        $("input:radio[name=jobStatus][value='1']").parent("span").attr("class","checked");
        $("#cronExpression").val("30 * * * * ?");
        //$("#springId").val("com.epweike.quartz.job.Test");
        $("#beanClass").val("com.epweike.quartz.job.Test");
        $("input:radio[name=isConcurrent][value='0']").parent("span").attr("class","checked");
        $("#methodName").val("test");
        $("#description").val("计划任务1");
    }
 
    /**
     * 清除
     */
    function clear() {
    	editFlag = false;
    	$("#myModalLabel").text("添加");
    	$("#id").val("");
        $("#jobName").val("");
        $("#jobGroup").val("");
        $("#cronExpression").val("");
        $("#springId").val("");
        $("#beanClass").val("");
        $("#methodName").val("");
        $(".checked").removeClass("checked");
        $("#description").val("");
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
        	"id": $("#id").val(),
            "jobName": $("#jobName").val(),
            "jobGroup": $("#jobGroup").val(),
            "jobStatus": jobStatus,
            "cronExpression": $("#cronExpression").val(),
            "springId": $("#springId").val(),
            "beanClass": $("#beanClass").val(),
            "methodName": $("#methodName").val(),
            "isConcurrent": isConcurrent,
            "description": $("#description").val()
        };
 		console.log(addJson);
        ajax(addJson);
    }
    
    /**
     *编辑方法
     **/
    function edit(id,jobName,jobGroup,jobStatus,cronExpression,springId,beanClass,methodName,isConcurrent,description) {
        clear();
        editFlag = true;
        $("#myModalLabel").text("修改");
        $("#id").val(id);
        $("#jobName").val(jobName);
        $("#jobGroup").val(jobGroup);
        $("input:radio[name=jobStatus][value="+jobStatus+"]").parent("span").attr("class","checked");
        $("#cronExpression").val(cronExpression);
        $("#springId").val(springId);
        $("#beanClass").val(beanClass);
        $("#methodName").val(methodName);
        $("input:radio[name=isConcurrent][value="+isConcurrent+"]").parent("span").attr("class","checked");
        $("#description").val(description);
        $("#myModal").modal("show");
        
    }
 
 	/**
     *ajax提交
     **/
    function ajax(obj) {
        var url ="/schedulejob/add" ;
        if(editFlag){
            url = "/schedulejob/update";
        }
        $.ajax({
            "url":url ,
            "type": "post",
            "data": {
                "id": obj.id,
                "jobName": obj.jobName,
                "jobGroup": obj.jobGroup,
                "jobStatus": obj.jobStatus,
                "cronExpression": obj.cronExpression,
                "springId": obj.springId,
                "methodName": obj.methodName,
                "beanClass": obj.beanClass,
                "isConcurrent": obj.isConcurrent,
                "description": obj.description
            }
        });
    }
 
 
    /**
     * 删除数据
     * @param id
     */
    function del(id) {
        $.ajax({
            "url": "/schedulejob/del",
            "type": "get",
            "data": {
	            "id": id
	         }
        });
    }
    
    /**
     * 立即执行
     * @param id
     */
	function run(id) {
        $.ajax({
            "url": "/schedulejob/run",
            "type": "get",
            "data": {
	            "id": id
	         }
        });
    }
</script>
</body>
</html>