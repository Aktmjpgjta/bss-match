<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>


<div class="easyui-dialog" id="prm_dlg" title="查看参数" style="width:80%;height:550px;max-width:800px;padding:10px" closed="true" data-options="iconCls:'icon-save'">
	<div style="margin-bottom:20px">
		<input class="easyui-textbox" readonly="true" id="reqmsg" label="请求:" labelPosition="top" multiline="true" style="width:100%;height:220px">
	</div>
	<div style="margin-bottom:20px">
		<input class="easyui-textbox" readonly="true" id="resmsg" label="响应:" labelPosition="top" multiline="true" style="width:100%;height:215px">
	</div>
</div>