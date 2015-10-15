//时间格式化
function formatDateTime(time) {
	var date = new Date(time);
	
    var year = date.getFullYear();
    var month = (date.getMonth()+1);
    var day = date.getDate();
    var hours = date.getHours();
    var minutes = date.getMinutes();
    var seconds = date.getSeconds();
	
  	return year +'-'+ fill(month) +'-'+ fill(day) +' '+ fill(hours) +':'+ fill(minutes) +':'+ fill(seconds);
}

//补0函数
function fill(s) {
    return s < 10 ? '0' + s: s;
}