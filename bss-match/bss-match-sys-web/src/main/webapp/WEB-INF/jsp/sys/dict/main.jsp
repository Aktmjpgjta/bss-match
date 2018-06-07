<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html >
<html>
<head>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
</head>
<body>

	<jsp:include page="./_functions.jsp"/>
	<jsp:include page="./_dialogs.jsp"/>
       
	<table id="dg_dict" class="easyui-treegrid" style="width:700px;height:300px"
            data-options="
                url: '${ctx}/sys/dict/list.do?path=${path}',
                rownumbers: true,
                idField: 'id',
                treeField: 'name',
                fit:true,
				onContextMenu: function(e,row){
					e.preventDefault();
					$(this).treegrid('select', row.id);
					$('#dict_button').menu('show',{
						left: e.pageX,
						top: e.pageY
					});
				}
            ">
        <thead>
            <tr>
                <th field="name" width="250">名称</th>
                <th field="code" width="200">关键字</th>
                <th field="path" width="300">全路径</th>
                <th field="value1" width="150">值1</th>
                <th field="value2" width="150">值2</th>
                <th field="value3" width="150">值3</th>
                <th field="value4" width="150">值4</th>
                <th field="value5" width="150">值5</th>
                <th field="status" width="150" formatter="statusFormatter">状态</th>
            </tr>
        </thead>
    </table>

    <div id="dict_button" class="easyui-menu" style="width:120px;">
    <shiro:hasPermission name="/sys/dict/add">
    	<div onclick="add()" data-options="iconCls:'icon-add'">添加</div>
    </shiro:hasPermission>
    <shiro:hasPermission name="/sys/dict/update">
    	<div onclick="edit()" data-options="iconCls:'icon-edit'">编辑</div>
    </shiro:hasPermission>
    <shiro:hasPermission name="/sys/dict/delete">
    	<div onclick="del()" data-options="iconCls:'icon-remove'">级联删除</div>
    </shiro:hasPermission>
	</div>
    
</body>
</html>