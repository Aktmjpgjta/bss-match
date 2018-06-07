<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<div id="dlg_dict" class="easyui-dialog" modal="true" style="width:500px" iconCls="icon-add" closed="true" buttons="#dlg_dict_buttons">
	<form id="fm_dict" method="post" novalidate style="margin:0;padding:20px 50px">
	    <div style="margin-bottom:10px;display:none">
	    	<input name="id" class="easyui-textbox" label="id" style="width:100%">
	    </div>
		<div style="margin-bottom:10px;display:none">
	    	<input id="pid" name="pid" class="easyui-textbox" label="pid" style="width:100%">
	    </div>
	    <div style="margin-bottom:10px">
	        <input id="name" name="name" class="easyui-textbox" data-options="required:true" label="名称：" style="width:100%" >
	    </div>
	    <div style="margin-bottom:10px">
	        <input id="code" name="code" class="easyui-textbox" data-options="required:true" label="关键字：" style="width:100%">
	    </div>
	    <div style="margin-bottom:10px">
	        <input id="value1" name="value1" class="easyui-textbox" label="值1：" style="width:100%">
	    </div>
	    <div style="margin-bottom:10px">
	        <input id="value2" name="value2" class="easyui-textbox" label="值2：" style="width:100%">
	    </div>
	    <div style="margin-bottom:10px">
	        <input id="value3" name="value3" class="easyui-textbox" label="值3：" style="width:100%">
	    </div>
	    <div style="margin-bottom:10px">
	        <input id="value4" name="value4" class="easyui-textbox" label="值4：" style="width:100%">
	    </div>
	    <div style="margin-bottom:10px">
	        <input id="value5" name="value5" class="easyui-textbox" label="值5：" style="width:100%">
	    </div>
		<div style="margin-bottom:10px">
	        <input id="remark" name="remark" class="easyui-textbox" multiline="true" label="备注：" style="width:100%;height:50px">
	    </div>
	    <div style="margin-bottom:10px">
	        <input id="path" name="path" class="easyui-textbox" data-options="readonly:true,disabled:true" label="全路径：" style="width:100%">
	    </div>
	    
		<div style="margin-bottom:10px">
			<select class="easyui-combobox" id="status" name="status" label="状态：" style="width:100%;" data-options="required:true,panelHeight:'auto'">
                <option value="1">启用</option>
                <option value="0">禁用</option>
            </select>
	    </div>
	</form>
</div>
<div id="dlg_dict_buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="save()" style="width:90px">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_dict').dialog('close')" style="width:90px">取消</a>
</div>