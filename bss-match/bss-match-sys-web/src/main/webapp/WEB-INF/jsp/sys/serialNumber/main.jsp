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
       
	<table class="easyui-datagrid" id="dg_sn"
            data-options="rownumbers:true,singleSelect:true,pageSize:30,collapsible:true,url:'${ctx}/sys/serialNumber/list.do',method:'get',fit:true,pagination:true,toolbar:'#topBtn'">
        <thead>
            <tr>
                <th data-options="field:'snName',width:200">名称</th>
                <th data-options="field:'snKey',width:200">关键字</th>
                <th data-options="field:'snType',width:150" formatter="dictSNFormatter">类型</th>
                <th data-options="field:'currRange',width:150">当前范围</th>
                <th data-options="field:'nextNum',width:150">当前流水</th>
                <th data-options="field:'poolSize',width:150">池大小</th>
                <th field="createTime" width="180" formatter="DateFormatter">创建时间</th>
            </tr>
        </thead>
    </table>
     
    <div id="topBtn" style="padding:2px 5px;">
    <shiro:hasPermission name="/sys/serialNumber/add">
		<div class="easyui-linkbutton" onclick="newSn()" iconCls="icon-add" plain="true">新增</div>
	</shiro:hasPermission>
	<shiro:hasPermission name="/sys/serialNumber/update">
		<div class="easyui-linkbutton" onclick="editSn()" iconCls="icon-edit" plain="true">修改</div>
	</shiro:hasPermission>
	<shiro:hasPermission name="/sys/serialNumber/delete">
		<div class="easyui-linkbutton" onclick="deleteSn();" iconCls="icon-remove" plain="true">删除</div>
	</shiro:hasPermission>
    </div>

</body>
</html>