<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="dlg_role" class="easyui-dialog" style="width:400px" iconCls="icon-add" closed="true" buttons="#dlg_role_buttons">
<form id="fm_role" method="post" novalidate style="margin:0;padding:20px 50px">
    <div style="margin-bottom:10px;display:none">
    	<input name="id" class="easyui-textbox" label="id" style="width:100%">
    </div>
    <div style="margin-bottom:10px">
        <input id="name" name="name" class="easyui-textbox" data-options="required:true" label="角色名：" style="width:100%" >
    </div>

    <div style="margin-bottom:10px">
    	<input id="resourcesId" name="resourcesId" class="easyui-combotree" cascadeCheck ="false" label="资　源：" style="width:100%"
    		data-options="required:true, multiple:true, url:'${ctx}/sys/resources/list.do'">
    </div>

</form>
</div>
<div id="dlg_role_buttons">
<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="save()" style="width:90px">保存</a>
<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_role').dialog('close')" style="width:90px">取消</a>
</div>