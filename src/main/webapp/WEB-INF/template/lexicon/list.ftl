<!DOCTYPE html>
<html>
<head>
<title>词库管理</title>
</head>
<body>
<div id="content-header">
    <div id="breadcrumb"><a href="/" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a> <a href="#" class="current">词库管理</a></div>
    <h1>词库管理</h1>
  </div>
<div class="container-fluid">
	<div class="widget-box">
	  <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
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

<script src="/common/matrix/js/jquery.dataTables.js"></script> 
<!--
<script src="/common/matrix/js/dataTables.tableTools.js"></script> 
<script src="/common/matrix/js/dataTables.editor.js"></script> 
<script src="/common/matrix/js/dataTables.bootstrap.js"></script> 
<script src="/common/matrix/js/editor.bootstrap.js"></script> 
-->
<script src="/common/matrix/js/jquery.ui.custom.js"></script> 
<script src="/common/matrix/js/jquery.uniform.js"></script> 
<script src="/common/matrix/js/select2.min.js"></script> 
<script src="/common/matrix/js/matrix.tables.js"></script>
<script type="text/javascript">
$(document).ready(function() {

$("#list").dataTable({
		"bServerSide" : true,
		"bDestroy": true,
		"bStateSave": true,
        "sAjaxSource": '/lexicon/get', 
        "aoColumns":
           [  
				{ "mData": "id"},
	        	{ "mData": "word"}, 
	        	{ "mData": "pinyin"},
	        	{ "mData": "pos"},
	        	{ "mData": "synonym"},
        	],
    	 "aoColumnDefs": [
             {
                 "aTargets": [5],
                 "mData": "id",
                 "mRender": function (data, type, full) {
                     return '<a href="#" class="delete" tag=' + data + '>删除</a>';
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

$(".delete").live('click', function() {
  var id = $(this).attr('tag');
  alert(id);
  $.ajax({    
        type:'post',        
        url:'/lexicon/del',    
        data:'id='+id,    
        cache:false,    
        dataType:'json',    
        success:function(data){   
        	location.reload();
        }    
    });    
});

</script> 
</body>
</html>