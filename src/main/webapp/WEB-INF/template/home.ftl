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
		<div id="breadcrumb">
			<a href="/" class="tip-bottom"> <i class="icon-home"></i>
				Home
			</a>
		</div>
		<h1>主页</h1>
	</div>
	<div class="container-fluid">
		<hr>
		<div class="row-fluid">
			<div class="span6">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon"> <i class="icon-signal"></i>
						</span>
						<h5>注册渠道统计</h5>
					</div>
					<div class="widget-content">
						<div id="reg_placeholder" class="chart"></div>
						<p id="reg_choices" class="choices"></p>
					</div>
				</div>
			</div>
			<div class="span6">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon">
							<i class="icon-signal"></i>
						</span>
						<h5>注册身份类型统计</h5>
					</div>
					<div class="widget-content">
						<div id="regType_placeholder" class="chart"></div>
						<p id="regType_choices" class="choices"></p>
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon">
							<i class="icon-signal"></i>
						</span>
						<h5>登陆渠道统计</h5>
					</div>
					<div class="widget-content">
						<div id="login_placeholder" class="chart"></div>
						<p id="login_choices" class="choices"></p>
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon">
							<i class="icon-signal"></i>
						</span>
						<h5>能力品级统计</h5>
					</div>
					<div class="widget-content">
						<div id="wbar" class="bars"></div>
					</div>
				</div>
			</div>
			<div class="span6">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon">
							<i class="icon-signal"></i>
						</span>
						<h5>商铺等级统计</h5>
					</div>
					<div class="widget-content">
						<div id="sbar" class="bars"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon">
							<i class="icon-signal"></i>
						</span>
						<h5>任务发布数统计</h5>
					</div>
					<div class="widget-content">
						<div id="taskpub_placeholder" class="chart"></div>
						<p id="taskpub_choices" class="choices"></p>
					</div>
				</div>
			</div>
			<div class="span6">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon">
							<i class="icon-signal"></i>
						</span>
						<h5>任务圆满完成数统计</h5>
					</div>
					<div class="widget-content">
						<div id="taskdone_placeholder" class="chart"></div>
						<p id="taskdone_choices" class="choices"></p>
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
	<!--Turning-series-chart-js-->
	<script type="text/javascript">
	//日期转时间戳
	function gd(datetime) {
		var tmp = datetime.split('-');
		return new Date(tmp[0], tmp[1] - 1, tmp[2]).getTime();
	}
	//注册渠道统计
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
			total.push([gd(item.date),item.count]);
		});
		$.each(${webReg}, function(i, item) {
			web.push([gd(item.date),item.count]);
		});
		$.each(${cpmReg}, function(i, item) {
			cpm.push([gd(item.date),item.count]);
		});
		$.each(${appReg}, function(i, item) {
			app.push([gd(item.date),item.count]);
		});
		$.each(${wapReg}, function(i, item) {
			wap.push([gd(item.date),item.count]);
		});
		$.each(${mallReg}, function(i, item) {
			mall.push([gd(item.date),item.count]);
		});
		$.each(${backgroundReg}, function(i, item) {
			background.push([gd(item.date),item.count]);
		});
		$.each(${yunReg}, function(i, item) {
			yun.push([gd(item.date),item.count]);
		});
		
		var datasets = {
			"TOTAL1": {
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
			"云创平台": {
				label: "云创平台",
				data: yun
			},
			"酷贝街": {
				label: "酷贝街",
				data: mall
			},
			"后台": {
				label: "后台",
				data: background
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
	    			xaxis:{
	    				mode: "time",
	    				timeformat: "%m/%d",
	    				tickSize: [3, "day"]
	    			}
	    		});
	    }
	    
	    plotAccordingToChoices();
	    
	});
	//注册身份类型统计
	$(function () {
		var total = new Array();
		var witkey = new Array();
		var employer = new Array();
		var both = new Array();
		var uncertain = new Array();
		
		$.each(${totalReg}, function(i, item) {
			total.push([gd(item.date),item.count]);
		});
		$.each(${witkeyReg}, function(i, item) {
			witkey.push([gd(item.date),item.count]);
		});
		$.each(${employerReg}, function(i, item) {
			employer.push([gd(item.date),item.count]);
		});
		$.each(${bothReg}, function(i, item) {
			both.push([gd(item.date),item.count]);
		});
		$.each(${uncertainReg}, function(i, item) {
			uncertain.push([gd(item.date),item.count]);
		});
		
		var datasets = {
			"TOTAL2": {
				label: "TOTAL",
				data: total
			}, 
			"uncertain": {
				label: "未确定",
				data: uncertain
			},
			"witkey": {
				label: "威客",
				data: witkey
			}, 
			"employer": {
				label: "雇主",
				data: employer
			},        
			"both": {
				label: "雇主威客",
				data: both
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
	    var choiceContainer = $("#regType_choices");
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
	    		$.plot($("#regType_placeholder"), data, {
	    			yaxis: { min: 0 },
	    			grid: { hoverable: true, clickable: true },
	    			xaxis:{
	    				mode: "time",
	    				timeformat: "%m/%d",
	    				tickSize: [3, "day"]
	    			}
	    		});
	    }
	    
	    plotAccordingToChoices();
	    
	});
	//登陆渠道统计
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
			total.push([gd(item.date),item.count]);
		});
		$.each(${webLogin}, function(i, item) {
			web.push([gd(item.date),item.count]);
		});
		$.each(${appLogin}, function(i, item) {
			app.push([gd(item.date),item.count]);
		});
		$.each(${wapLogin}, function(i, item) {
			wap.push([gd(item.date),item.count]);
		});
		$.each(${webqqLogin}, function(i, item) {
			webqq.push([gd(item.date),item.count]);
		});
		$.each(${appqzoneLogin}, function(i, item) {
			appqzone.push([gd(item.date),item.count]);
		});
		$.each(${websinaLogin}, function(i, item) {
			websina.push([gd(item.date),item.count]);
		});
		$.each(${otherLogin}, function(i, item) {
			other.push([gd(item.date),item.count]);
		});
		
		var datasets = {
			"TOTAL3": {
				label: "TOTAL",
				data: total
			},  
			"WEB2": {
				label: "WEB",
				data: web
			},  
			"APP2": {
				label: "APP",
				data: app
			},
			"WAP2": {
				label: "WAP",
				data: wap
			},        
			"QQ2": {
				label: "QQ",
				data: webqq
			},
			"QQ空间2": {
				label: "QQ空间",
				data: appqzone
			},
			"新浪2": {
				label: "新浪",
				data: websina
			},
			"其它2": {
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
	    	if (data.length > 0)
	    		$.plot($("#login_placeholder"), data, {
	    			yaxis: { min: 0 },
	    			grid: { hoverable: true, clickable: true },
	    			xaxis:{
	    				mode: "time",
	    				timeformat: "%m/%d",
	    				tickSize: [3, "day"]
	    			}
	    		});
	    }
	    
	    plotAccordingToChoices();
	    
	});    
	//任务发布数统计
	$(function () {
		var total = new Array();
		var danshang = new Array();
		var duoshang = new Array();
		var jijian = new Array();
		var zhaobiao = new Array();
		var guyong = new Array();
		var fuwu = new Array();
		var zhijieguyong = new Array();
		$.each(${totaltaskPub}, function(i, item) {
			total.push([gd(item.date),item.count]);
		});
		$.each(${danshangtaskPub}, function(i, item) {
			danshang.push([gd(item.date),item.count]);
		});
		$.each(${duoshangtaskPub}, function(i, item) {
			duoshang.push([gd(item.date),item.count]);
		});
		$.each(${jijiantaskPub}, function(i, item) {
			jijian.push([gd(item.date),item.count]);
		});
		$.each(${zhaobiaotaskPub}, function(i, item) {
			zhaobiao.push([gd(item.date),item.count]);
		});
		$.each(${guyongtaskPub}, function(i, item) {
			guyong.push([gd(item.date),item.count]);
		});
		$.each(${fuwutaskPub}, function(i, item) {
			fuwu.push([gd(item.date),item.count]);
		});
		$.each(${zhijieguyongtaskPub}, function(i, item) {
			zhijieguyong.push([gd(item.date),item.count]);
		});
		
		var datasets = {
			"TOTAL4": {
				label: "TOTAL",
				data: total
			},  
			"danshang1": {
				label: "单赏",
				data: danshang
			},  
			"duoshang1": {
				label: "多赏",
				data: duoshang
			},
			"jijian1": {
				label: "计件",
				data: jijian
			},        
			"zhaobiao1": {
				label: "招标",
				data: zhaobiao
			},
			"guyong1": {
				label: "雇佣",
				data: guyong
			},
			"fuwu1": {
				label: "服务",
				data: fuwu
			},
			"zhijieguyong1": {
				label: "直接雇佣",
				data: zhijieguyong
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
	    var choiceContainer = $("#taskpub_choices");
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
	    		$.plot($("#taskpub_placeholder"), data, {
	    			yaxis: { min: 0 },
	    			grid: { hoverable: true, clickable: true },
	    			xaxis:{
	    				mode: "time",
	    				timeformat: "%m/%d",
	    				tickSize: [3, "day"]
	    			}
	    		});
	    }
	    
	    plotAccordingToChoices();
	    
	});
	//任务圆满完成统计
	$(function () {
		var total = new Array();
		var danshang = new Array();
		var duoshang = new Array();
		var jijian = new Array();
		var zhaobiao = new Array();
		var guyong = new Array();
		var fuwu = new Array();
		var zhijieguyong = new Array();
		$.each(${totaltaskDone}, function(i, item) {
			total.push([gd(item.date),item.count]);
		});
		$.each(${danshangtaskDone}, function(i, item) {
			danshang.push([gd(item.date),item.count]);
		});
		$.each(${duoshangtaskDone}, function(i, item) {
			duoshang.push([gd(item.date),item.count]);
		});
		$.each(${jijiantaskDone}, function(i, item) {
			jijian.push([gd(item.date),item.count]);
		});
		$.each(${zhaobiaotaskDone}, function(i, item) {
			zhaobiao.push([gd(item.date),item.count]);
		});
		$.each(${guyongtaskDone}, function(i, item) {
			guyong.push([gd(item.date),item.count]);
		});
		$.each(${fuwutaskDone}, function(i, item) {
			fuwu.push([gd(item.date),item.count]);
		});
		$.each(${zhijieguyongtaskDone}, function(i, item) {
			zhijieguyong.push([gd(item.date),item.count]);
		});
		
		var datasets = {
			"TOTAL5": {
				label: "TOTAL",
				data: total
			},  
			"danshang2": {
				label: "单赏",
				data: danshang
			},  
			"duoshang2": {
				label: "多赏",
				data: duoshang
			},
			"jijian2": {
				label: "计件",
				data: jijian
			},        
			"zhaobiao2": {
				label: "招标",
				data: zhaobiao
			},
			"guyong2": {
				label: "雇佣",
				data: guyong
			},
			"fuwu2": {
				label: "服务",
				data: fuwu
			},
			"zhijieguyong2": {
				label: "直接雇佣",
				data: zhijieguyong
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
	    var choiceContainer = $("#taskdone_choices");
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
	    		$.plot($("#taskdone_placeholder"), data, {
	    			yaxis: { min: 0 },
	    			grid: { hoverable: true, clickable: true },
	    			xaxis:{
	    				mode: "time",
	    				timeformat: "%m/%d",
	    				tickSize: [3, "day"]
	    			}
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
				var date = new Date(x);     
				maruti.flot_tooltip(item.pageX, item.pageY,item.series.label + " of " + (date.getMonth() + 1) + "/" + date.getDate() + " = " + y);
			}
			
		} else {
			//$('#tooltip').fadeOut(200,function(){
				//$(this).remove();
			//});
			$("#tooltip").remove();
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
	<!--Bar-chart-js-->
	//能力品级统计
	var wbar = new Array(); //数据  
    var wticks = new Array(); //横坐标值  
    <#list wlevel as list>
    wbar.push([${list_index},'${list.count?c}']);
    wticks.push([${list_index},'${list.name}']);
    </#list>
    var wdataset = [{label: "", data: wbar, color: "#2E363F" }];
    console.log(wbar);
    console.log(wticks);
    var woptions = {
    	series: {
    		bars: {
    			show: true
    		},
    		points: { show: true }
    	},
    	bars: {
    		align: "center",
    		barWidth: 0.5
    	},
    	xaxis: {
    		axisLabel: "威客品级",
    		axisLabelUseCanvas: true,
    		axisLabelFontSizePixels: 12,
    		axisLabelFontFamily: 'Verdana, Arial',
    		axisLabelPadding: 10,
    		ticks: wticks
    	},
    	yaxis: {
    		axisLabel: "个数",
    		axisLabelUseCanvas: true,
    		axisLabelFontSizePixels: 12,
    		axisLabelFontFamily: 'Verdana, Arial',
    		axisLabelPadding: 3,
    		tickFormatter: function (v, axis) {
    			return v;
    		}
    	},
    	grid: {
    		hoverable: true,
    		borderWidth: 2,
    		backgroundColor: { colors: ["#ffffff", "#EDF5FF"] }
    	}
    };
    //商铺等级统计
	var sbar = new Array(); //数据  
    var sticks = new Array(); //横坐标值  
    <#list slevel as list>
    sbar.push([${list_index},'${list.count?c}']);
    sticks.push([${list_index},'${list.name}']);
    </#list>
    var sdataset = [{label: "", data: sbar, color: "#2E363F" }];
    
    var soptions = {
    	series: {
    		bars: {
    			show: true
    		},
    		points: { show: true }
    	},
    	bars: {
    		align: "center",
    		barWidth: 0.5
    	},
    	xaxis: {
    		axisLabel: "商铺等级",
    		axisLabelUseCanvas: true,
    		axisLabelFontSizePixels: 12,
    		axisLabelFontFamily: 'Verdana, Arial',
    		axisLabelPadding: 10,
    		ticks: sticks
    	},
    	yaxis: {
    		axisLabel: "个数",
    		axisLabelUseCanvas: true,
    		axisLabelFontSizePixels: 12,
    		axisLabelFontFamily: 'Verdana, Arial',
    		axisLabelPadding: 3,
    		tickFormatter: function (v, axis) {
    			return v;
    		}
    	},
    	grid: {
    		hoverable: true,
    		borderWidth: 2,
    		backgroundColor: { colors: ["#ffffff", "#EDF5FF"] }
    	}
    };
    
    $(document).ready(function () {
	    //Display graph
	    $.plot($("#wbar"), wdataset, woptions, {
	    	legend: true
	    });
	    $.plot($("#sbar"), sdataset, soptions, {
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
    				
    				showTooltip(item.pageX,
    					item.pageY,
    					color,
    					"<strong>" + item.series.label + "</strong><br>" + item.series.xaxis.ticks[x].label + " : <strong>" + y + "</strong> 个");
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