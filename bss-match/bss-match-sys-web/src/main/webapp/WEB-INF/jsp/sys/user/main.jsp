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
       
	<table class="easyui-datagrid" id="dg_user"
            data-options="rownumbers:true,singleSelect:true,pageSize:30,collapsible:true,url:'${ctx}/sys/user/list.do',method:'get',fit:true,pagination:true,toolbar:'#topBtn'">
        <thead>
            <tr>
                <th data-options="field:'realname',width:150">姓名</th>
                <th data-options="field:'username',width:150">用户名</th>
                <th data-options="field:'email',width:250">邮箱</th>
                <th data-options="field:'status',width:150" formatter="statusFormatter">状态</th>
                <th field="createTime" width="180" formatter="DateFormatter">创建时间</th>
            </tr>
        </thead>
    </table>
     
    <div id="topBtn" style="padding:2px 5px;">
    <shiro:hasPermission name="/sys/user/add">
		<div class="easyui-linkbutton" onclick="newUser()" iconCls="icon-add" plain="true">新增</div>
	</shiro:hasPermission>
	<shiro:hasPermission name="/sys/user/update">
		<div class="easyui-linkbutton" onclick="editUser()" iconCls="icon-edit" plain="true">修改</div>
	</shiro:hasPermission>
	<shiro:hasPermission name="/sys/user/resetPwd">
		<div class="easyui-linkbutton" onclick="resetPwd();" iconCls="icon-edit" plain="true">重置密码</div>
	</shiro:hasPermission>
	<shiro:hasPermission name="/sys/user/disableUser">
		<div class="easyui-linkbutton" onclick="disableUser();" iconCls="icon-edit" plain="true">禁用/启用</div>
	</shiro:hasPermission>
	<shiro:hasPermission name="/sys/user/delete">	
		<div class="easyui-linkbutton" onclick="deleteUser();" iconCls="icon-remove" plain="true">删除用户</div>
	</shiro:hasPermission>
    </div>

</body>
</html>