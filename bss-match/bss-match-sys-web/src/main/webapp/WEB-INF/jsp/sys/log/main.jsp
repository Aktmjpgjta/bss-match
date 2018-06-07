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
       

   <table id="dg_log" class="easyui-datagrid" fit="true" 
            url="${ctx}/sys/log/list.do"
            toolbar="#toolbar" pagination="true" pageSize=50 
            rownumbers="true" fitColumns="true" singleSelect="false" pageList="[10,50,100,500,1000,5000]">
        <thead>
            <tr>
               <th field=""  data-options="checkbox:true"></th>
                <th field="url" width="120">url</th>
                <th field="parameter" width="50">parameter</th>
                <th field="remoteAddr" width="50">remoteAddr</th>
                <th field="agent" width="150">agent</th>
                <th field="user"  width="30"  formatter="usernameFormatter">username</th>
                <th field="beginTime" width="50" formatter="DateFormatter">begintime</th>
                <th field="endTime" width="50" formatter="DateFormatter">endtime</th>
            </tr>
        </thead>
    </table>
    <div id="toolbar">
    	<shiro:hasPermission name="/sys/log/delete">
        	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
        </shiro:hasPermission>
    </div>


</body>
</html>