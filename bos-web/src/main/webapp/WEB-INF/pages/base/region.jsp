<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>区域设置</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">	
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath }/ocp/jquery.ocupload-1.1.2.js"
	type="text/javascript"></script>
<script type="text/javascript">
	function doAdd(){
		$('#addRegionWindow').window("open");
	}
	
	function doView(){
		alert("修改...");
	}
	
	function doDelete(){
		alert("删除...");
	}
	
	//工具栏
	var toolbar = [ {
		id : 'button-edit',	
		text : '修改',
		iconCls : 'icon-edit',
		handler : doView
	}, {
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	}, {
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	}, {
		id : 'button-import',
		text : '导入',
		iconCls : 'icon-redo'
	}];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	},{
		field : 'province',
		title : '省',
		width : 120,
		align : 'center'
	}, {
		field : 'city',
		title : '市',
		width : 120,
		align : 'center'
	}, {
		field : 'district',
		title : '区',
		width : 120,
		align : 'center'
	}, {
		field : 'postcode',
		title : '邮编',
		width : 120,
		align : 'center'
	}, {
		field : 'shortcode',
		title : '简码',
		width : 120,
		align : 'center'
	}, {
		field : 'citycode',
		title : '城市编码',
		width : 200,
		align : 'center'
	} ] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 收派标准数据表格
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			pageList: [30,50,100],
			pagination : true,
			toolbar : toolbar,
			url : "${pageContext.request.contextPath}/regionAction_pageQuery",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		// 添加、修改区域窗口
		$('#addRegionWindow').window({
	        title: '添加修改区域',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		$("#button-import").upload({
			name: 'upload',//   name  后台接受文件 name值!
			action: '${pageContext.request.contextPath}/regionAction_oneClickUpload',//  后台接受文件地址
			enctype: 'multipart/form-data',
			//  autoSubmit: true,//  选择文件立刻提交文件
			onSelect: function() {
				//  不要立刻上传文件  判断文件是否是一个 excel文件
				this.autoSubmit = false;
				//  判断是否是一个excel文件  正则表达式 ...
				var  re = /^(.+\.xls|.+\.xlsx)$/;  //  aa.xls    bb.xlsx
				if (re.test(this.filename())) {
					this.submit();  // 提交请求
				}
				else {
					$.messager.alert("警告!","请上传一个有效excel文件","warning");
				}

			},//  选择文件 该事件触发
			onComplete: function(data) {
				//  服务器接受上传文件 之后  回送数据 ajax发送请求完成上传功能
				//alert(typeof(data));
				var flag = eval("(" + data + ")");
				//alert(typeof (flag));
				if(flag) {
					$.messager.alert("恭喜!", "成功!", "info");
				}
				else {
					$.messager.alert("出错了!", "上传失败!", "error");
				}
			}
		});
		$("#save").click(function () {
			var flag = $("#addregionwindow").form("validate");
			if(flag) {
				$("#addRegionForm").submit();
				$("#addRegionWindow").window("close");
			}
		});



	});

	function doDblClickRow(){
		alert("双击表格数据...");
	}
	$.extend($.fn.validatebox.defaults.rules,{
		uniqueRegionCode:{
			validator: function (value) {
				var flag;
				$.ajax({   //  必须采用 原始的$.ajax语法完成ajax的发送
					url : '${pageContext.request.contextPath}/regionAction_validRegionCode',
					type : 'POST',
					timeout : 60000,
					data:{"id" : value},
					async: false,
					success : function(data, textStatus, jqXHR) {
						if (data) {
							flag = true;
						}else{
							flag = false;
						}
					}
				});
				if(flag){
					// 样式效果
					$("#tel").removeClass('validatebox-invalid');
				}
				return flag;
			},
			message: '区域编码已经存在'
		}


	});
	$.extend($.fn.validatebox.defaults.rules,{
		uniquePostcode:{
			validator: function (value) {
				var flag;
				$.ajax({   //  必须采用 原始的$.ajax语法完成ajax的发送
					url : '${pageContext.request.contextPath}/regionAction_validPostcode',
					type : 'POST',
					timeout : 60000,
					data:{"postcode" : value},
					async: false,
					success : function(data, textStatus, jqXHR) {
						if (data) {
							flag = true;
						}else{
							flag = false;
						}
					}
				});
				if(flag){
					// 样式效果
					$("#tel").removeClass('validatebox-invalid');
				}
				return flag;
			},
			message: '邮政编码已经存在'
		}


	});
	$.extend($.fn.validatebox.methods, {
		remove: function (jq, newposition) {
			return $(jq).removeClass("validatebox-text validatebox-invalid");
		},

		reduce: function (jq, newposition) {
			return jq.each(function () {
				var opt = $(this).data().validatebox.options;
				$(this).addClass("validatebox-text").validatebox(opt);
			});
		}
	});
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false">
    	<table id="grid"></table>
	</div>
	<div class="easyui-window" title="区域添加修改" id="addRegionWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="addRegionForm" action="${pageContext.request.contextPath}/regionAction_save" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">区域信息</td>
					</tr>
					<tr>
						<td>区域编码</td>
						<td><input type="text" name="id" class="easyui-validatebox" required="true"
								   data-options="validType:'uniqueRegionCode'"/></td>
					</tr>
					<tr>
						<td>省</td>
						<td><input type="text" name="province" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>市</td>
						<td><input type="text" name="city" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>区</td>
						<td><input type="text" name="district" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>邮编</td>
						<td><input type="text" name="postcode" class="easyui-validatebox" required="true"
								   data-options="validType:'uniquePostcode'"/></td>
					</tr>
					<tr>
						<td>简码</td>
						<td><input type="text" name="shortcode" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>城市编码</td>
						<td><input type="text" name="citycode" class="easyui-validatebox" required="true"/></td>
					</tr>
					</table>
			</form>
		</div>
	</div>
</body>
</html>