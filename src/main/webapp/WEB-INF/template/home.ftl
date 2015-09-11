<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>主页</title>
		<meta name="keywords" content="" />
		<meta name="description" content="" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	</head>

	<body>

	  <div id="content-header">
	    <div id="breadcrumb"><a href="/" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a> <a href="#" class="current">主页</a></div>
	    <h1>主页</h1>
	  </div>
	  <div class="container-fluid">
	    <hr>
	    <div class="row-fluid">
	      <div class="span6">
	        <div class="widget-box">
	          <div class="widget-title"> <span class="icon"> <i class="icon-signal"></i> </span>
	            <h5>用户注册渠道统计</h5>
	          </div>
	          <div class="widget-content">
	            <div id="reg_placeholder" class="chart"></div>
	            <p id="reg_choices" class="choices"></p>
	          </div>
	        </div>
	      </div>
	      
	      <div class="span6">
	        <div class="widget-box">
	          <div class="widget-title"> <span class="icon"> <i class="icon-signal"></i> </span>
	            <h5>用户登陆渠道统计</h5>
	          </div>
	          <div class="widget-content">
	            <div id="login_placeholder" class="chart"></div>
	            <p id="login_choices" class="choices"></p>
	          </div>
	        </div>
	      </div>
	    </div>
	    <hr>
	    <div class="row-fluid">
	      <div class="span6">
	        <div class="widget-box">
	          <div class="widget-title"> <span class="icon"> <i class="icon-signal"></i> </span>
	            <h5>任务发布统计</h5>
	          </div>
	        </div>
	      </div>
	      <div class="span6">
	        <div class="widget-box">
	          <div class="widget-title"> <span class="icon"> <i class="icon-signal"></i> </span>
	            <h5>任务接单统计</h5>
	          </div>
	        </div>
	      </div>
	    </div>
	  </div>
		
	<script src="/common/matrix/js/jquery.flot.min.js"></script> 
	<script src="/common/matrix/js/jquery.flot.pie.min.js"></script> 
	<script src="/common/matrix/js/jquery.flot.resize.min.js"></script> 
	<script src="/common/matrix/js/jquery.peity.min.js"></script> 
	<!--Turning-series-chart-js-->
	<script type="text/javascript">
	//用户注册渠道统计
	$(function () {
		var total = new Array();
		var web = new Array();
		var cpm = new Array();
		var app = new Array();
		var wap = new Array();
		var mall = new Array();
		var background = new Array();
		var yun = new Array();
		
		$.each(${totalReg}, function(i, item) {
            total.push([item.date,item.count]);
        });
		$.each(${webReg}, function(i, item) {
            web.push([item.date,item.count]);
        });
        $.each(${cpmReg}, function(i, item) {
            cpm.push([item.date,item.count]);
        });
        $.each(${appReg}, function(i, item) {
            app.push([item.date,item.count]);
        });
        $.each(${wapReg}, function(i, item) {
            wap.push([item.date,item.count]);
        });
        $.each(${mallReg}, function(i, item) {
            mall.push([item.date,item.count]);
        });
        $.each(${backgroundReg}, function(i, item) {
            background.push([item.date,item.count]);
        });
        $.each(${yunReg}, function(i, item) {
            yun.push([item.date,item.count]);
        });
	
	    var datasets = {
	    	"TOTAL": {
	            label: "TOTAL",
	            data: total
	        }, 
	    	"WEB": {
	            label: "WEB",
	            data: web
	        },        
	        "CPM": {
	            label: "CPM",
	            data: cpm
	        },
	        "APP": {
	            label: "APP",
	            data: app
	        },
	        "WAP": {
	            label: "WAP",
	            data: wap
	        },
	        "酷贝街": {
	            label: "酷贝街",
	            data: mall
	        },
	        "后台注册": {
	            label: "后台",
	            data: background
	        },
	        "云创平台": {
	            label: "云创平台",
	            data: yun
	        }
	    };
	    
	    // hard-code color indices to prevent them from shifting as
	    // countries are turned on/off
	    var i = 0;
	    $.each(datasets, function(key, val) {
	        val.color = i;
	        ++i;
	    });
	    
	    // insert checkboxes 
	    var choiceContainer = $("#reg_choices");
	    $.each(datasets, function(key, val) {
	        choiceContainer.append('<br/><input type="checkbox" name="' + key +
	                               '" checked="checked" id="id' + key + '">' +
	                               '<label for="id' + key + '">'
	                                + val.label + '</label>');
	    });
	    choiceContainer.find("input").click(plotAccordingToChoices);
	    
	    function plotAccordingToChoices() {
	        var data = [];
	
	        choiceContainer.find("input:checked").each(function () {
	            var key = $(this).attr("name");
	            if (key && datasets[key])
	                data.push(datasets[key]);
	        });
	        if (data.length > 0)
	            $.plot($("#reg_placeholder"), data, {
	                yaxis: { min: 0 },
	                grid: { hoverable: true, clickable: true },
	                xaxis: { tickDecimals: 0 }
	            });
	    }
	
	    plotAccordingToChoices();
	    
	});
	//用户登陆渠道统计
	$(function () {
		var total = new Array();
		var web = new Array();
		var app = new Array();
		var wap = new Array();
		var webqq = new Array();
		var appqzone = new Array();
		var websina = new Array();
		var other = new Array();
		
		$.each(${totalLogin}, function(i, item) {
            total.push([item.date,item.count]);
        });
		$.each(${webLogin}, function(i, item) {
            web.push([item.date,item.count]);
        });
        $.each(${appLogin}, function(i, item) {
            app.push([item.date,item.count]);
        });
        $.each(${wapLogin}, function(i, item) {
            wap.push([item.date,item.count]);
        });
        $.each(${webqqLogin}, function(i, item) {
            webqq.push([item.date,item.count]);
        });
        $.each(${appqzoneLogin}, function(i, item) {
            appqzone.push([item.date,item.count]);
        });
        $.each(${websinaLogin}, function(i, item) {
            websina.push([item.date,item.count]);
        });
        $.each(${otherLogin}, function(i, item) {
            other.push([item.date,item.count]);
        });
	
	    var datasets = {
	    	"TOTAL": {
	            label: "TOTAL",
	            data: total
	        },  
	    	"WEB": {
	            label: "WEB",
	            data: web
	        },  
	        "APP": {
	            label: "APP",
	            data: app
	        },
	        "WAP": {
	            label: "WAP",
	            data: wap
	        },        
	        "QQ": {
	            label: "QQ",
	            data: webqq
	        },
	        "QQ空间": {
	            label: "QQ空间",
	            data: appqzone
	        },
	        "新浪": {
	            label: "新浪",
	            data: websina
	        },
	        "其它": {
	            label: "其它",
	            data: other
	        }
	    };
	    
	    // hard-code color indices to prevent them from shifting as
	    // countries are turned on/off
	    var i = 0;
	    $.each(datasets, function(key, val) {
	        val.color = i;
	        ++i;
	    });
	    
	    // insert checkboxes 
	    var choiceContainer = $("#login_choices");
	    $.each(datasets, function(key, val) {
	        choiceContainer.append('<br/><input type="checkbox" name="' + key +
	                               '" checked="checked" id="id' + key + '">' +
	                               '<label for="id' + key + '">'
	                                + val.label + '</label>');
	    });
	    choiceContainer.find("input").click(plotAccordingToChoices);
	    
	    function plotAccordingToChoices() {
	        var data = [];
	
	        choiceContainer.find("input:checked").each(function () {
	            var key = $(this).attr("name");
	            if (key && datasets[key])
	                data.push(datasets[key]);
	        });
	        console.log(data);
	        if (data.length > 0)
	            $.plot($("#login_placeholder"), data, {
	                yaxis: { min: 0 },
	                grid: { hoverable: true, clickable: true },
	                xaxis: { tickDecimals: 0 }
	            });
	    }
	
	    plotAccordingToChoices();
	    
	});
	
	
	// === Point hover in chart === //
    var previousPoint = null;
    $(".chart").bind("plothover", function (event, pos, item) {
        if (item) {
            if (previousPoint != item.dataIndex) {
                previousPoint = item.dataIndex;
                
                $('#tooltip').fadeOut(200,function(){
					$(this).remove();
				});
                var x = item.datapoint[0],
					y = item.datapoint[1];
                    
                maruti.flot_tooltip(item.pageX, item.pageY,item.series.label + " of " + x + " = " + y);
            }
            
        } else {
			$('#tooltip').fadeOut(200,function(){
					$(this).remove();
				});
            previousPoint = null;           
        }   
    });	
	maruti = {
		// === Tooltip for flot charts === //
		flot_tooltip: function(x, y, contents) {
			
			$('<div id="tooltip">' + contents + '</div>').css( {
				top: y + 5,
				left: x + 5
			}).appendTo("body").fadeIn(200);
		}
	}
	<!--Turning-series-chart-js-->
	</script>
	</body>
</html>