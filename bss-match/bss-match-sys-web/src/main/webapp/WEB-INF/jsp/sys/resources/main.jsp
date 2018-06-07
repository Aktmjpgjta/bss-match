<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html >
<html>
<head>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资源列表</title>
</head>
<body>

	<jsp:include page="_functions.jsp"/>
	<jsp:include page="./_dialogs.jsp"/>
       
	<table id="res_tree" class="easyui-treegrid" style="width:700px;height:300px"
            data-options="
                url:'${ctx}/sys/resources/list.do?path=${path}',
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
                <th field="name" width="350">名称</th>
                <th field="url" width="500">地址</th>
                <th field="type" width="150" formatter="typeFormatter">类型</th>
                <th field="sort" width="150">排序号</th>
            </tr>
        </thead>
    </table>

    <div id="dict_button" class="easyui-menu" style="width:120px;">
    <shiro:hasPermission name="/sys/resources/add">
    	<div onclick="add()" data-options="iconCls:'icon-add'">添加</div>
    </shiro:hasPermission>
    <shiro:hasPermission name="/sys/resources/update">
    	<div onclick="edit()" data-options="iconCls:'icon-edit'">编辑</div>
    </shiro:hasPermission>
    <shiro:hasPermission name="/sys/resources/delete">
    	<div onclick="del()" data-options="iconCls:'icon-remove'">级联删除</div>
    </shiro:hasPermission>
	</div>
    
</body>
</html>