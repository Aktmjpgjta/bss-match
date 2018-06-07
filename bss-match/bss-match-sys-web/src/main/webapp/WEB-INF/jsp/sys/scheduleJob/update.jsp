<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="dlg_update" class="easyui-dialog" style="width:400px" iconCls="icon-add" closed="true" buttons="#dlg-edit-buttons">
	<form id="ff2" action="${ctx}/sys/scheduleJob/update.do" method="POST">
		<input type="hidden" id="id" name="id" value="">
		<table>
			<tr>
				<td>任务标识:</td>
				<td><input type="text"  value="${scheduleJob.name}" readOnly="true" id="editName" name="name" class="easyui-textbox" required="true" validType="length[1,25]"></td>
			</tr>
			<tr>
				<td>任务名称:</td>
				<td><input type="text"  value="${scheduleJob.group}" readOnly="true" id="editGroup" name="group" class="easyui-textbox" required="true" validType="length[1,25]"></td>
			</tr>
			<tr>
				<td>表达式:</td>
				<td><input type="text"  value="${scheduleJob.cronExpression}"  id="editCronExpression" name="cronExpression" class="easyui-textbox" required="true" validType="length[1,25]"></td>
			</tr>
			<tr>
				<td>调用定时类:</td>
				<td><input type="text"  value="${scheduleJob.className}" disabled="true" id="editClassName" name="className"  class="easyui-textbox" required="true" validType="length[1,50]"></td>
			</tr>
			
		</table>
	</form>
</div>	
<div id="dlg-edit-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok"  onclick="submitForm2()">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_update').dialog('close')" style="width:90px">取消</a>
</div>
