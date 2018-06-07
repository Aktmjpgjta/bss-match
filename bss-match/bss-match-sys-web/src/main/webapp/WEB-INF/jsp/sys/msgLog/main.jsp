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
       
	<table class="easyui-datagrid" id="dg_table"
            data-options="rownumbers:true,singleSelect:true,pageSize:30,collapsible:true,url:'${ctx}/sys/msgLog/list.do',method:'get',fit:true,pagination:true,toolbar:'#tb'">
        <thead>
            <tr>
                <th data-options="field:'id',width:60" formatter="operateFmt">操作</th>
                <th data-options="field:'bizId',width:260">业务流水号</th>
                <th data-options="field:'bizSystem',width:90">业务系统</th>
                <th data-options="field:'providerName',width:150">接口名称</th>
                <th data-options="field:'invokeTimeMillis',width:160" formatter="formatLogTime">请求时间</th>
                <th data-options="field:'reqTime',width:160" formatter="formatLogTime">接收时间</th>
                <th data-options="field:'resTime',width:160" formatter="formatLogTime">处理完成时间</th>
                <th data-options="field:'mseconds',width:80">处理耗时</th>
                <th data-options="field:'retCode',width:80">响应码</th>
                <th data-options="field:'retInfo',width:300">响应信息</th>
                <th field="createTime" width="160" formatter="DateFormatter">创建时间</th>
            </tr>
        </thead>
    </table>
     
    <div id="tb" style="padding:5px 10px;">
    <form  id="search_form">
                    &nbsp;业务流水号: <input class="easyui-textbox" name="bizId" id="bizId" style="width:220px">
                    &nbsp;接口名称: <input class="easyui-textbox" name="providerName" id="providerName" style="width:220px">
        &nbsp;<a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-search',onClick:search">查询</a>
		<a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-no'" onclick="javascript:$('#search_form').form('reset');">重置</a>
    </form>    
    </div>

</body>
</html>