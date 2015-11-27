/**
  * 设置全局AJAX请求默认选项
  * 主要设置了AJAX请求遇到Session过期的情况
  */
$.ajaxSetup({
    complete: function(xhr,status) {
        if(status == "parsererror") {
        	window.location.href="/"; 
        }
  	},success: function (data) {
  		if(data !== null && data.msg !== undefined && data.msg !== ''){
  			table.ajax.reload();
  	        $("#myModal").modal("hide");
  	        $.gritter.add({
  				title:	'操作提示！',
  				text:	data.msg,
  				sticky: false
  			});	
  		}
     }
});