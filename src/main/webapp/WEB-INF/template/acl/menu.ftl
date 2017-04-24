<!DOCTYPE html>
<html>
<head>
	<title>菜单权限</title>
	<link rel="stylesheet" href="/common/jstree/themes/default/style.min.css" />
</head>
<body>
	<div id="content-header">
		<div id="breadcrumb">
			<a href="#" class="tip-bottom"> <i class="icon-home"></i>
				Home
			</a>
			<a href="#" class="tip-bottom"> <i class="icon-bar-key"></i>
				权限管理
			</a>
			<a href="#" class="current">菜单权限</a>
		</div>
		<h1>菜单权限</h1>
	</div>
	<div class="container-fluid">
		<div>
			<div class="control-group">
              <div class="controls">
                <select id="role" placeholder="请先选择角色，再勾选所需菜单权限">
                  <#list roles as list>
                  	<option>${list.roleName}</option>
                  </#list>
                </select>
              </div>
            </div>
			<input type="text" placeholder="Search" id="menu_q" style="box-shadow:inset 0 0 4px #eee; width:120px; margin:0; padding:6px 12px; border-radius:4px; border:1px solid silver; font-size:1.1em;" value="">
		</div>

		<div id="jstree_menu" style="margin-top:1em; min-height:200px;"></div>
		
		<div>
			<button onclick="save();" class="btn btn-primary btn-sm" type="button"> <i class="glyphicon glyphicon-asterisk"></i>
				保存
			</button>
		</div>
	</div>

	<script src="/common/jstree/jstree.js"></script>
	<script type="text/javascript">
	$(function () {
		var to = false;
		$('#menu_q').keyup(function () {
			if(to) { clearTimeout(to); }
			to = setTimeout(function () {
				var v = $('#menu_q').val();
				$('#jstree_menu').jstree(true).search(v);
			}, 250);
		});
		
		$('#jstree_menu')
			.jstree({
				"core" : {
					"animation" : 0,
					"check_callback" : true,
					'force_text' : true,
					"themes" : { "stripes" : true,
								 "variant" : "large"
								 },
					'data' : {
						"url" : "/acl/getMenu",
						"dataType" : "json" 
					}
					
				},
				"types" : {
					"#" : { "max_children" : 1, "max_depth" : 3, "valid_children" : ["root"] },
					"default" : { "valid_children" : ["default","file"] }
				},
				"plugins" : ["search", "types", "wholerow", "checkbox" ]
			})
			
	});
	checkMenus($("#role option:selected").val());
	
	//角色下拉框事件监听
	$('#role').change(function(){
		checkMenus($(this).children('option:selected').val());
	}) 
	
	//回选指定角色菜单权限
	function checkMenus(role) {
		$('#jstree_menu').jstree('uncheck_all');
		
		$.get('/acl/getAceByRole', { 'role' : role})
			.done(function (d) {
				for(var i=0; i<d.length; i++){
					if(d[i].hasChild == 0){
						$('#jstree_menu').jstree('check_node',d[i].id);
					}
				}  
			});
	}
	
	//保存菜单权限
	function save() {
		//1、获取选中角色
		var role = $("#role option:selected").val();
		//2、获取选中菜单id
		var menu_ids = $('#jstree_menu').jstree(true).get_bottom_checked();
		var menu_parent_ids = $('#jstree_menu').jstree(true).get_bottom_checked('full');
		$.each(menu_parent_ids,function(n,value) {
			if(value.parent != '0' && menu_ids.indexOf(value.parent) == '-1'){
				menu_ids = menu_ids + ',' +value.parent
			} 
  		});  
  		console.log(menu_ids);
		//3、ajax保存菜单权限
		$.get('/acl/saveMenuAcl', { 'role' : role, 'menu_ids' : menu_ids})
			.done(function (d) {
				if(d.flag == false){
					$.gritter.add({
						title:	'操作提示！',
						text:	d.msg,
						sticky: false
					});	
					return;
				}else {
					$.gritter.add({
						title:	'操作提示！',
						text:	d.msg,
						sticky: false
					});	
				}
			})
			.fail(function () {
				$.gritter.add({
					title:	'操作提示！',
					text:	'保存失败！',
					sticky: false
				});	
			});
	};
</script>
</body>
</html>