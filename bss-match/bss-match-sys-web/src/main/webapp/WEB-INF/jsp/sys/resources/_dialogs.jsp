<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="dlg_res" class="easyui-dialog" style="width:400px" iconCls="icon-add" closed="true" buttons="#dlg_res_buttons">
<form id="fm_res" method="post" novalidate style="margin:0;padding:20px 50px">
    <div style="margin-bottom:10px;display:none">
    	<input name="id" id="id" class="easyui-textbox" label="id" style="width:100%">
    	<input name="pid" id="pid" class="easyui-textbox" label="pid" style="width:100%">
    </div>
    <div style="margin-bottom:10px">
        <input id="name" name="name" class="easyui-textbox" data-options="required:true" label="名称：" style="width:100%" >
    </div>
    <div style="margin-bottom:10px">
        <input id="url" name="url" class="easyui-textbox" data-options="required:true" label="地址：" style="width:100%" >
    </div>
    <div style="margin-bottom:10px">
        <input class="easyui-combobox" 
        		id="type"
				name="type"
				label="类型："
				style="width:100%"
				required="true"
				data-options="
						url:'${ctx}/sys/dict/findSubsByPathNormal.do?path=/SYS/MENU_TYPE',
						method:'get',
						valueField:'code',
						textField:'name',
						panelHeight:'auto'
			    ">
    </div>
    <div style="margin-bottom:10px">
        <input id="sort" name="sort" class="easyui-textbox" data-options="required:true" label="排序：" style="width:100%" >
    </div>
</form>
</div>
<div id="dlg_res_buttons">
<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="save()" style="width:90px">保存</a>
<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_res').dialog('close')" style="width:90px">取消</a>
</div>