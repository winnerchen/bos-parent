<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>宅急送BOS主界面</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css">
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>



<SCRIPT type="text/javascript">
		
		var setting = {
			data: {
				simpleData: {
					enable: true
				}
			}
		};

		var zNodes =[
			{ id:1, pId:0, name:"父节点1 "},
			{ id:11, pId:1, name:"父节点11",page:"http://www.baidu.com"},
			{ id:12, pId:1, name:"叶子节点111",page:"http://www.baidu.com"},
			{ id:2, pId:0, name:"父节点2 "},
			{ id:21, pId:2, name:"父节点11",page:"http://www.baidu.com"},
			{ id:22, pId:2, name:"叶子节点111",page:"http://www.baidu.com"}
		];

		$(function(){
			$.post("${pageContext.request.contextPath}/demo/menu.json",function(data){
				$.fn.zTree.init($("#treeDemo"), setting, data);
			},"json");
		
		});
		
	</SCRIPT>
</head>
<body class="easyui-layout">

	<div data-options="region:'north',title:'北部',split:false"
		style="height:100px;"></div> 
		
		<div data-options="region:'south',title:'南部',split:false"
		style="height:100px;"></div> 
		
		
		
		<div data-options="region:'west',title:'西部',split:false"
		style="width:180px;">
		<div  class="easyui-accordion" data-options="region:'west',title:'菜单导航',split:false"
		style="width: 180px;">
		<!-- 折叠菜单 -->
		<div title="基本功能">
				<ul id="treeDemo" class="ztree"></ul>
			</div>
			<div title="系统管理">系统菜单</div>
			<div title="联系我们">联系我们</div>
		</div>
		
		
		
		</div> 
		
		<div id = "tt" class="easyui-tabs" data-options="region:'center',title:'中部'"
		style="padding:5px;background:#eee;">
			
			
			
			
		
		</div> 



</body>

<script type="text/javascript">
	 /* function zTreeOnClick(event, treeId, treeNode) {
	    alert(treeNode.tId + ", " + treeNode.name);
	}; */
	var setting = {
		callback: {
			onClick: function(event, treeId, treeNode, clickFlag){
				//  treeNode 每一个菜单json 对象
				if(treeNode.page==null||treeNode.page==undefined){
					return;
				}
				 var flag =  $("#tt").tabs("exists",treeNode.name);
				 
				 if(!flag){
					 $("#tt").tabs("add",{
						  title:treeNode.name,
						  //  content 内容 添加嵌套页面<iframe>
						  content:'<div style="width:100%;height:100%;overflow:hidden;">'
								+ '<iframe src="'
								+ treeNode.page
								+ '" scrolling="auto" style="width:100%;height:100%;border:0;" ></iframe></div>',
						  closable:true
					  });

				 }else{
					 $("#tt").tabs("select",treeNode.name);
				 }
			}
		},
		data: {
			simpleData: {
				enable: true
			}
		}
	}; 
	
	
	
</script>

</html>