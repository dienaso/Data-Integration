<!DOCTYPE html>
<html>
<head>
	<title>菜单管理</title>
	<link rel="stylesheet" href="/common/jstree/themes/default/style.min.css" />
</head>
<body>
	<div id="content-header">
		<div id="breadcrumb">
			<a href="#" class="tip-bottom"> <i class="icon-home"></i>
				Home
			</a>
			<a href="#" class="current">菜单管理</a>
		</div>
		<h1>菜单管理</h1>
	</div>
	<hr>
	<div class="container-fluid">
		<div>
			<button onclick="menu_create();" class="btn btn-success btn-sm" type="button"> <i class="glyphicon glyphicon-asterisk"></i>
				添加
			</button>
			<button onclick="menu_rename();" class="btn btn-primary btn-sm" type="button">
				<i class="glyphicon glyphicon-pencil"></i>
				修改
			</button>
			<button onclick="menu_delete();" class="btn btn-danger btn-sm" type="button">
				<i class="glyphicon glyphicon-remove"></i>
				删除
			</button>
			<input type="text" placeholder="Search" id="menu_q" style="box-shadow:inset 0 0 4px #eee; width:120px; margin:0; padding:6px 12px; border-radius:4px; border:1px solid silver; font-size:1.1em;" value=""></div>

		<div id="jstree_menu" style="margin-top:1em; min-height:200px;"></div>
	</div>

	<script src="/common/jstree/jstree.js"></script>
	<script type="text/javascript">
	function menu_create() {
		var ref = $('#jstree_menu').jstree(true),
			sel = ref.get_selected();
		if(!sel.length) { return false; }
		sel = sel[0];
		sel = ref.create_node(sel, {"type":"file"});
		if(sel) {
			ref.edit(sel);
		}
	};
	function menu_rename() {
		var ref = $('#jstree_menu').jstree(true),
			sel = ref.get_selected();
		if(!sel.length) { return false; }
		sel = sel[0];
		ref.edit(sel);
	};
	function menu_delete() {
		var ref = $('#jstree_menu').jstree(true),
			sel = ref.get_selected();
		if(!sel.length) { return false; }
		ref.delete_node(sel);
	};
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
						"url" : "/menus/get",
						"dataType" : "json" 
					}
					
				},
				"types" : {
					"#" : { "max_children" : 1, "max_depth" : 3, "valid_children" : ["root"] },
					"default" : { "valid_children" : ["default","file"] }
				},
				"plugins" : [ "contextmenu", "dnd", "search", "state", "types", "wholerow" ]
			})
			.on('delete_node.jstree', function (e, data) {
				if(data.node.id == 0){
					$.gritter.add({
						title:	'操作提示！',
						text:	'根节点不允许删除！',
						sticky: false
					});	
					data.instance.refresh();
					return null;
				}
				$.get('/menus/operation?method=delete_node', { 'id' : data.node.id })
					.done(function (d) {
						if(d.flag == false){
							$.gritter.add({
								title:	'操作提示！',
								text:	d.msg,
								sticky: false
							});	
							data.instance.refresh();
							return;
						}
					})
					.fail(function () {
						data.instance.refresh();
					});
			})
			.on('create_node.jstree', function (e, data) {
				$.get('/menus/operation?method=create_node', { 'id' : data.node.parent, 'position' : data.position, 'text' : data.node.text })
					.done(function (d) {
						if(d.flag == false){
							$.gritter.add({
								title:	'操作提示！',
								text:	d.msg,
								sticky: false
							});	
							return;
						}
						data.instance.set_id(data.node, d.obj.id);
					})
					.fail(function () {
						data.instance.refresh();
					});
			})
			.on('rename_node.jstree', function (e, data) {
				$.get('/menus/operation?method=rename_node', { 'id' : data.node.id, 'text' : data.text })
					.done(function (d) {
						if(d.flag == false){
							$.gritter.add({
								title:	'操作提示！',
								text:	d.msg,
								sticky: false
							});	
							data.instance.refresh();
							return;
						}
					})
					.fail(function () {
						data.instance.refresh();
					});
			})
			.on('move_node.jstree', function (e, data) {
				$.get('/menus/operation?method=move_node', { 'id' : data.node.id, 'parent' : data.parent, 'position' : data.position })
					.done(function (d) {
						if(d.flag == false){
							$.gritter.add({
								title:	'操作提示！',
								text:	d.msg,
								sticky: false
							});	
							data.instance.refresh();
							return;
						}
					})
					.fail(function () {
						data.instance.refresh();
					});
			})
			.on('copy_node.jstree', function (e, data) {
				$.get('/menus/operation?method=copy_node', { 'id' : data.original.id, 'parent' : data.parent, 'position' : data.position })
					.done(function (d) {
						if(d.flag == false){
							$.gritter.add({
								title:	'操作提示！',
								text:	d.msg,
								sticky: false
							});	
							data.instance.refresh();
							return;
						}
					})
					.always(function () {
						data.instance.refresh();
					});
			});
	});
</script>
</body>
</html>