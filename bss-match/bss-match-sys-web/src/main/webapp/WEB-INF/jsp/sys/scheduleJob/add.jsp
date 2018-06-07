<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="dlg_add" class="easyui-dialog" style="width:400px" iconCls="icon-add" closed="true" buttons="#dlg-buttons">
<form id="ff" action="${ctx}/sys/scheduleJob/add.do" method="POST">
	<table>
		<tr>
			<td>任务标识:</td>
			<td><input type="text" name="name" class="easyui-textbox" required="true" validType="length[1,25]"></td>
		</tr>
		<tr>
			<td>任务名称:</td>
			<td><input type="text" name="group" class="easyui-textbox" required="true" validType="length[1,25]"></td>
		</tr>
		<tr>
			<td>表达式:</td>
			<td><input type="text" value="0/30 * * * * ?" name="cronExpression" class="easyui-textbox" required="true" validType="length[1,25]"></td>
		</tr>
		<tr>
			<td>调用定时类:</td>
			<td><input type="text" name="className" value="com.yingu.match.job.TaskA"  class="easyui-textbox" required="true" validType="length[1,50]"></td>
		</tr>
	</table>
</form>
</div>
<div id="dlg-buttons">
	<a href="javascript:void(0)"  class="easyui-linkbutton c6" iconCls="icon-ok"  onclick="submitForm()">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_add').dialog('close')" style="width:90px">取消</a>
</div>
