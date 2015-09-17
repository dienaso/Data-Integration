<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>数据统计</title>
	</head>

	<body>

	  <div id="content-header">
	    <div id="breadcrumb"><a href="/" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a> <a href="#" title="Go to Chart" class="tip-bottom"><i class="icon-bar-chart"></i> 数据统计</a> <a href="#" class="current">用户登录类型统计</a></div>
	    <h1>用户登录类型统计</h1>
	  </div>
	  <div class="container-fluid">
	    <hr>
	    <div class="row-fluid"> 
	      <div class="span12">
	        <div class="widget-box">
	          <div class="widget-title"> <span class="icon"> <i class="icon-signal"></i> </span>
	            <h5>柱状图(总数：${total}次)</h5>
	          </div>
	          <div class="widget-content">
	            <div class="bars"></div>
	          </div>
	        </div>
	      </div>
	     </div>
	     
	     <div class="row-fluid">
	      <div class="span12">
	        <div class="widget-box">
	          <div class="widget-title"> <span class="icon"> <i class="icon-signal"></i> </span>
	            <h5>饼状图(总数：${total}次)</h5>
	          </div>
	          <div class="widget-content">
	            <div class="pie"></div>
	          </div>
	        </div>
	      </div>
	     </div>
	    
	  </div>
		
	<script src="/common/matrix/js/jquery.flot.min.js"></script> 
	<script src="/common/matrix/js/jquery.flot.pie.min.js"></script> 
	<script src="/common/matrix/js/jquery.flot.resize.min.js"></script> 
	<script src="/common/matrix/js/jquery.flot.axislabels.js"></script> 
	<script src="/common/matrix/js/jquery.peity.min.js"></script> 
	<script type="text/javascript">
	<!--Pie-chart-js-->
	var data = ${pieData};
    var pie = $.plot($(".pie"), data,{
        series: {
            pie: {
                show: true,
                radius: 3/4,
                label: {
                    show: true,
                    radius: 3/4,
                    formatter: function(label, series){
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'+label+'<br/>'+Math.round(series.percent)+'%</div>';
                    },
                    background: {
                        opacity: 0.5,
                        color: '#000'
                    }
                },
                innerRadius: 0.2
            },
			legend: {
				show: false
			}
		}
	});
	<!--Pie-chart-js-->	
	<!--Bar-chart-js-->
	var barData = new Array(); //数据  
    var ticks = new Array(); //横坐标值  
    <#list barData as list>
    	barData.push([${list_index},'${list.count}']);
    	ticks.push([${list_index},'${list.name}']);
    </#list>
	var dataset = [{label: "用户登录类型", data: barData, color: "#5482FF" }];
	
	var options = {
        series: {
            bars: {
                show: true
            }
        },
        bars: {
            align: "center",
            barWidth: 0.5
        },
        xaxis: {
            axisLabel: "登陆类型",
            axisLabelUseCanvas: true,
            axisLabelFontSizePixels: 12,
            axisLabelFontFamily: 'Verdana, Arial',
            axisLabelPadding: 10,
            ticks: ticks
        },
        yaxis: {
            axisLabel: "登陆次数",
            axisLabelUseCanvas: true,
            axisLabelFontSizePixels: 12,
            axisLabelFontFamily: 'Verdana, Arial',
            axisLabelPadding: 3,
            tickFormatter: function (v, axis) {
                return v + "次";
            }
        },
        legend: {
            noColumns: 0,
            //labelBoxBorderColor: "#000000",
            position: "ne"
        },
        labelFormatter: function(label, series) {
		    // series is the series object for the label
		    return '<a href="#' + label + '">' + label + '</a>';
		},
        grid: {
            hoverable: true,
            borderWidth: 2,
            backgroundColor: { colors: ["#ffffff", "#EDF5FF"] }
        }
    };
    
	$(document).ready(function () {
	    //Display graph
	    $.plot($(".bars"), dataset, options, {
			legend: true
		});
		$(".bars").UseTooltip();
    });
    
    var previousPoint = null, previousLabel = null;
	$.fn.UseTooltip = function () {
	        $(this).bind("plothover", function (event, pos, item) {
	            if (item) {
	                if ((previousLabel != item.series.label) || (previousPoint != item.dataIndex)) {
	                    previousPoint = item.dataIndex;
	                    previousLabel = item.series.label;
	                    $("#tooltip").remove();
	
	                    var x = item.datapoint[0];
	                    var y = item.datapoint[1];
	                    var color = item.series.color;
	
	                    //console.log(item.series.xaxis.ticks[x].label);                
	
	                    showTooltip(item.pageX,
		                    item.pageY,
		                    color,
		                    "<strong>" + item.series.label + "</strong><br>" + item.series.xaxis.ticks[x].label + " : <strong>" + y + "</strong> 人");
		                }
	            } else {
	                $("#tooltip").remove();
	                previousPoint = null;
	            }
	        });
	    };

    function showTooltip(x, y, color, contents) {
        $('<div id="tooltip">' + contents + '</div>').css({
            position: 'absolute',
            display: 'none',
            top: y - 40,
            left: x - 120,
            border: '2px solid ' + color,
            padding: '3px',
            'font-size': '9px',
            'border-radius': '5px',
            'background-color': '#fff',
            'font-family': 'Verdana, Arial, Helvetica, Tahoma, sans-serif',
            color: color,
            opacity: 0.9
        }).appendTo("body").fadeIn(200);
    }
	<!--Bar-chart-js-->	
	</script>
	</body>
</html>