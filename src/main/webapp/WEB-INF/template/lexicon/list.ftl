<!DOCTYPE html>
<html>
<head>
	<title>词库管理</title>
</head>
<body>
	<div id="content-header">
		<div id="breadcrumb">
			<a href="#" class="tip-bottom"> <i class="icon-home"></i>
				Home
			</a>
			<a href="#" class="current">词库管理</a>
		</div>
		<h1>词库管理</h1>
	</div>
	<div class="container-fluid">
		<div class="widget-box">
			<div class="widget-title">
				<span class="icon"> <i class="icon-th"></i>
				</span>
				<h5>词条列表</h5>
			</div>
			<div class="widget-content nopadding">

				<table id="list" class="table table-bordered data-table">
					<thead>
						<tr>
							<th>ID</th>
							<th>词条</th>
							<th>拼音</th>
							<th>词性</th>
							<th>近义词</th>
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
	                	<label class="control-label">词条 :</label>
	                	<div class="controls">
	                    	<input type="text" class="form-control" id="word" placeholder="词条">
	                    </div>
	                </div>
	                <div class="control-group">
	                	<label class="control-label">拼音 :</label>
	                	<div class="controls">
	                    	<input type="text" class="form-control" id="pinyin" placeholder="拼音">
	                    </div>
	                </div>
	                <div class="control-group">
	                	<label class="control-label">词性 :</label>
	                	<div class="controls">
	                    	<input type="text" class="form-control" id="pos" placeholder="词性">
	                    </div>
	                </div>
	                <div class="control-group">
	                	<label class="control-label">近义词 :</label>
	                	<div class="controls">
	                    	<input type="text" class="form-control" id="synonym" placeholder="近义词">
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
	        "sAjaxSource": '/lexicons/get', 
	        "aoColumns":
	           [  
					{ "mData": "id"},
		        	{ "mData": "word"}, 
		        	{ "mData": "pinyin"},
		        	{ "mData": "pos"},
		        	{ "mData": "synonym"},
	        	],
	        "columnDefs": [
	                {
	                    targets: 5,
	                    render: function (a, b, c, d) {
	                        var context =
	                        {
	                            func: [
	                                {"name": "修改", "fn": "edit(\'" + c.id + "\',\'" + c.word + "\',\'" + c.pinyin + "\',\'" + c.pos + "\',\'" + c.synonym + "\')", "type": "primary"},
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
        $("#word").val("");
        $("#pinyin").val("");
        $("#pos").val("");
        $("#synonym").val("");
    }
 
    /**
     * 添加数据
     **/
    function add() {
        var addJson = {
        	"id": $("#id").val(),
            "word": $("#word").val(),
            "pinyin": $("#pinyin").val(),
            "pos": $("#pos").val(),
            "synonym": $("#synonym").val(),
        };
 		console.log(addJson);
        ajax(addJson);
    }
    
    /**
     *编辑方法
     **/
    function edit(id,word,pinyin,pos,synonym) {
        clear();
        editFlag = true;
        $("#myModalLabel").text("修改");
        $("#id").val(id);
        $("#word").val(word);
        $("#pinyin").val(pinyin);
        $("#pos").val(pos);
        $("#synonym").val(synonym);
        $("#myModal").modal("show");
    }
 
 	/**
     *ajax提交
     **/
    function ajax(obj) {
        var url ="/lexicons/add" ;
        if(editFlag){
            url = "/lexicons/update";
        }
        $.ajax({
            "url":url ,
            "type": "post",
            "data": {
                "id": obj.id,
                "word": obj.word,
                "pinyin": obj.pinyin,
                "pos": obj.pos,
                "synonym": obj.synonym
            }
        });
    }
 
    /**
     * 删除数据
     * @param id
     */
    function del(id) {
        $.ajax({
            "url": "/lexicons/del",
            "type": "get",
            "data": {
	            "id": id
	         }
        });
    }
    
	</script>
</body>
</html>