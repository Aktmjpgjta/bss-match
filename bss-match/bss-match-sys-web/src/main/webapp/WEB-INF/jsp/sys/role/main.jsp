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
       
	<table class="easyui-datagrid" id="dg_role"
            data-options="rownumbers:true,singleSelect:true,pageSize:30,collapsible:true,url:'${ctx}/sys/role/list.do',method:'get',fit:true,pagination:true,toolbar:'#topBtn'">
        <thead>
            <tr>
                <th data-options="field:'name',width:300">角色名</th>
            </tr>
        </thead>
    </table>
     
    <div id="topBtn" style="padding:2px 5px;">
    <shiro:hasPermission name="/sys/role/add">
		<div class="easyui-linkbutton" onclick="add()" iconCls="icon-add" plain="true">新增</div>
	</shiro:hasPermission>
	<shiro:hasPermission name="/sys/role/update">	
		<div class="easyui-linkbutton" onclick="edit()" iconCls="icon-edit" plain="true">修改</div>
	</shiro:hasPermission>
	<shiro:hasPermission name="/sys/role/delete">
		<div class="easyui-linkbutton" onclick="del();" iconCls="icon-remove" plain="true">删除</div>
	</shiro:hasPermission>
    </div>

</body>
</html>