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
	        	</tr>
	     	</thead>
	     	
	    </table>
	  </div>
	</div>
</div>
<script type="text/javascript">
var oTable;
$(document).ready(function() {
	oTable = $("#list1").dataTable({
		"bServerSide" : true,
		"bDestroy": true,
		"bStateSave": true,
		"bFilter": false,
        "sAjaxSource": '/lexicon/get', 
        "aoColumns":
           [  
				{ "mData": "id"},
	        	{ "mData": "word"}, 
	        	{ "mData": "pinyin"},
	        	{ "mData": "pos"},
	        	{ "mData": "synonym"},
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
	
	
	$("#list").jqGrid({
            url: '/lexicon/get',
            mtype: "GET",
			styleUI : 'Bootstrap',
            datatype: "jsonp",
            colModel: [
                { label: 'ID', name: 'id', key: true, width: 75 },
                { label: '词条', name: 'word', width: 150 },
                { label: '拼音', name: 'pinyin', width: 150 },
                { label: '词性', name: 'pos', width: 150 },
                { label: '近义词', name: 'synonym', width: 150 }
            ],
			viewrecords: true,
            height: 250,
            rowNum: 20,
            pager: "#jqGridPager"
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