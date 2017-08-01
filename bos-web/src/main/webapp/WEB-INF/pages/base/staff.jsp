<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
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
        function clearFormData() {
            //$("#addStandardForm").form("clear");
            $("#addStaffForm")[0].reset();
            $("#id").val("");
            return true;
        }
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


        function doAdd() {
            //alert("增加...");
            $('#addStaffWindow').window("open");
        }

        function doView() {
            //alert("查看...");
            $("#searchStaffWindow").window("open");
        }

        function doDelete() {
            //alert("删除...");

            //alert('作废');
            var arr = $("#grid").datagrid("getSelections");
            if (arr == null || arr == "") {
                $.messager.alert("警告!", "至少选中一行", "warning");
            } else {
                var ids = new Array();
                for (var i = 0; i < arr.length; i++) {
                    ids.push(arr[i].id);
                }
                var idsString = ids.join(",");
                // ajax发送后台
                $.post("${pageContext.request.contextPath}/staffAction_delBatch", {"ids": idsString}, function (data) {
                    if (data) {
                        $.messager.alert("恭喜!", "作废成功!", "info");
                        //  页面及时更新最新的数据库数据   调用 datagrid  reload方法即可
                        $("#grid").datagrid("clearChecked");//  清除之前选择项的√
                        $("#grid").datagrid("reload");//  再次向url地址发送请求  重新分页查询

                    } else {
                        $.messager.alert("可惜!", "系统维护....", "warning");
                    }
                });

            }

        }

        function doRestore() {
            //alert("将取派员还原...");
            var arr = $("#grid").datagrid("getSelections");
            if (arr == null || arr == "") {
                $.messager.alert("警告!", "至少选中一行", "warning");
            } else {
                var ids = new Array();
                for (var i = 0; i < arr.length; i++) {
                    ids.push(arr[i].id);
                }
                var idsString = ids.join(",");
                // ajax发送后台
                $.post("${pageContext.request.contextPath}/staffAction_restoreBatch", {"ids": idsString}, function (data) {
                    if (data) {
                        $.messager.alert("恭喜!", "还原成功!", "info");
                        //  页面及时更新最新的数据库数据   调用 datagrid  reload方法即可
                        $("#grid").datagrid("clearChecked");//  清除之前选择项的√
                        $("#grid").datagrid("reload");//  再次向url地址发送请求  重新分页查询

                    } else {
                        $.messager.alert("可惜!", "系统维护....", "warning");
                    }
                });

            }
        }
        //工具栏
        var toolbar = [{
            id: 'button-view',
            text: '查询',
            iconCls: 'icon-search',
            handler: doView
        }, {
            id: 'button-add',
            text: '增加',
            iconCls: 'icon-add',
            handler: doAdd
        }, {
            id: 'button-delete',
            text: '作废',
            iconCls: 'icon-cancel',
            handler: doDelete
        }, {
            id: 'button-save',
            text: '还原',
            iconCls: 'icon-save',
            handler: doRestore
        }];
        // 定义列
        var columns = [[{
            field: 'id',
            checkbox: true,
        }, {
            field: 'name',
            title: '姓名',
            width: 120,
            align: 'center'
        }, {
            field: 'telephone',
            title: '手机号',
            width: 120,
            align: 'center'
        }, {
            field: 'haspda',
            title: '是否有PDA',
            width: 120,
            align: 'center',
            formatter: function (data, row, index) {
                if (data == "1") {
                    return "有";
                } else {
                    return "无";
                }
            }
        }, {
            field: 'deltag',
            title: '是否作废',
            width: 120,
            align: 'center',
            formatter: function (data, row, index) {
                if (data == "1") {
                    return "<font color=green>启用</font>";
                } else {
                    return "<font color=red>作废</font>";
                }
            }
        }, {
            field: 'standard',
            title: '取派标准',
            width: 120,
            align: 'center'
        }, {
            field: 'station',
            title: '所在单位',
            width: 200,
            align: 'center'
        }]];

        $(function () {
            // 先将body隐藏，再显示，不会出现页面刷新效果
            $("body").css({visibility: "visible"});

            //页面加载完成,点击保存,提交表单,判断表单,数据合法才可提交

            $("#save").click(function () {
                var flag = $("#addStaffForm").form("validate");


                if (flag || $("#id").val() != null) {
                    $("#addStaffForm").submit();
                    $("#addStaffWindow").window("close");
                }
            });

            $("#search").click(function () {
                var params = {
                    "name": $("#sname").val(),
                    "telephone": $("#stelephone").val(),
                    "station": $("#sstation").val()
                };
                $("#grid").datagrid("load", params);
                $("#searchStaffWindow").window("close");
            })

            // 取派员信息表格
            $('#grid').datagrid({
                iconCls: 'icon-forward',
                fit: true,
                border: false,
                rownumbers: true,
                striped: true,
                pageList: [5, 10, 15],
                pagination: true,
                toolbar: toolbar,
                url: "${pageContext.request.contextPath}/staffAction_pageQuery",
                idField: 'id',
                columns: columns,
                onDblClickRow: function (rowIndex, rowData) {
                    $('#addStaffWindow').window("open");
                    $('#addStaffForm').form("load", rowData);

                }
            });

            // 添加取派员窗口
            $('#addStaffWindow').window({
                title: '添加取派员',
                width: 400,
                modal: true,
                shadow: true,
                closed: true,
                height: 400,
                resizable: false
            });

            //查询取派员窗口
            $('#searchStaffWindow').window({
                title: '条件查询',
                width: 400,
                modal: true,
                shadow: true,
                closed: true,
                height: 400,
                resizable: false
            });


        });

        function doDblClickRow() {
            alert("双击表格数据...");
        }

        $.extend($.fn.validatebox.defaults.rules, {
            telephone: {
                validator: function (value, param) {
                    var reg = /^1[3|4|5|7|8]\d{9}$/;
                    return reg.test(value);
                },
                message: '手机号必须以1|3|4|5|7|8开头的11位数字'
            },
            uniquePhone: {
                validator: function (value) {
                    var flag;
                    $.ajax({   //  必须采用 原始的$.ajax语法完成ajax的发送
                        url: '${pageContext.request.contextPath}/staffAction_validTelephone',
                        type: 'POST',
                        timeout: 60000,
                        data: {"telephone": value},
                        async: false,
                        success: function (data, textStatus, jqXHR) {
                            if (data) {
                                flag = true;
                            } else {
                                flag = false;
                            }
                        }
                    });
                    if (flag) {
                        // 样式效果
                        $("#tel").removeClass('validatebox-invalid');
                    }
                    return flag;
                },
                message: '手机号已经存在'
            }

        })
    </script>
</head>
<body class="easyui-layout" style="visibility:hidden;">
<div region="center" border="false">
    <table id="grid"></table>
</div>
<div class="easyui-window" title="对收派员进行添加或者修改" id="addStaffWindow" collapsible="false" minimizable="false"
     maximizable="false" style="top:20px;left:200px"
     data-options="onBeforeClose:clearFormData">
    <div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
        <div class="datagrid-toolbar">
            <a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">保存</a>
        </div>
    </div>

    <div region="center" style="overflow:auto;padding:5px;" border="false">
        <form id="addStaffForm" action="${pageContext.request.contextPath}/staffAction_save" method="post">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">收派员信息</td>
                </tr>
                <!-- TODO 这里完善收派员添加 table -->

                <tr>
                    <td>姓名</td>
                    <td>
                        <input type="text" name="name" class="easyui-validatebox" required="true"/>
                        <input type="hidden" name="id" id="id">
                    </td>
                </tr>
                <tr>
                    <td>手机</td>
                    <td><input type="text" name="telephone" class="easyui-validatebox" required="true"
                               data-options="validType:['telephone','uniquePhone']"
                    /></td>
                </tr>
                <tr>
                    <td>单位</td>
                    <td><input type="text" name="station" class="easyui-validatebox" required="true"/></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="checkbox" name="haspda" value="1"/>
                        是否有PDA
                    </td>
                </tr>
                <tr>
                    <td>取派标准</td>
                    <td>
                        <input type="text" name="standard" class="easyui-combobox"
                               data-options="editable:false,valueField:'name',textField:'name',url:'${pageContext.request.contextPath}/standardAction_ajaxList'"
                        />
                    </td>
                </tr>
            </table>
        </form>
    </div>

</div>

<!-- 条件查询窗体 -->
<div class="easyui-window" title="取派员条件查询" id="searchStaffWindow" closed="true" collapsible="false" minimizable="false"
     maximizable="false" style="top:20px;left:200px">
    <div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
        <div class="datagrid-toolbar">
            <a id="search" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">查询</a>
        </div>
    </div>

    <div region="center" style="overflow:auto;padding:5px;" border="false">
        <form id="searchStaffForm">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="2">收派员信息</td>
                </tr>
                <tr>
                    <td>姓名</td>
                    <td><input type="text" name="name" id="sname"/></td>
                </tr>
                <tr>
                    <td>手机</td>
                    <td><input type="text" name="telephone" id="stelephone"/>
                    </td>
                </tr>
                <tr>
                    <td>单位</td>
                    <td><input type="text" name="station" id="sstation"/></td>
                </tr>
            </table>
        </form>
    </div>
</div>


</body>
</html>	