<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="dlg_sn" class="easyui-dialog" style="width:400px" iconCls="icon-add" closed="true" buttons="#dlg_sn_buttons">
<form id="fm_sn" method="post" novalidate style="margin:0;padding:20px 50px">
    <div style="margin-bottom:10px;display:none">
    	<input name="id" class="easyui-textbox" label="id" style="width:100%">
    </div>
    <div style="margin-bottom:10px">
        <input id="snName" name="snName" class="easyui-textbox" data-options="required:true" label="名称：" style="width:100%" >
    </div>
    <div style="margin-bottom:10px">
        <input id="snKey" name="snKey" class="easyui-textbox" data-options="required:true" label="关键字：" style="width:100%" >
    </div>
    <div style="margin-bottom:10px">
        <input class="easyui-combobox" 
        		id="snType"
				name="snType"
				label="类型："
				style="width:100%"
				required="true"
				readonly="true"
				data-options="
						url:'${ctx}/sys/dict/findSubsByPathNormal.do?path=/SYS/SN_TYPE',
						method:'get',
						valueField:'code',
						textField:'name',
						panelHeight:'auto'
			    ">
    </div>    
    <div style="margin-bottom:10px">
        <input id="currRange" name="currRange" class="easyui-textbox" data-options="prompt:'不用填写...'" label="作用范围：" style="width:100%" >
    </div>
    <div style="margin-bottom:10px">
        <input id="nextNum" name="nextNum" class="easyui-textbox" data-options="required:true" label="当前流水：" style="width:100%">
    </div>
    <div style="margin-bottom:10px">
        <input id="poolSize" name="poolSize" class="easyui-textbox" data-options="required:true" label="池大小：" style="width:100%">
    </div>
</form>
</div>
<div id="dlg_sn_buttons">
<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveSn()" style="width:90px">保存</a>
<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_sn').dialog('close')" style="width:90px">取消</a>
</div>


<div id="dlg_sn_pwd" class="easyui-dialog" style="width:400px" iconCls="icon-add" closed="true" buttons="#dlg_sn_buttons_pwd">
<form id="fm_sn_pwd" method="post" novalidate style="margin:0;padding:20px 50px">
	<div style="margin-bottom:10px;display:none">
    	<input name="id" class="easyui-textbox" label="id" style="width:100%">
    </div>
	<div style="margin-bottom:10px" id="password_div">
        <input id="password2" name="password" class="easyui-textbox" type="password" data-options="required:true,validType:['safepass']" label="密码：" style="width:100%">
    </div>
    <div style="margin-bottom:10px" id="confirmPassword_div">
        <input id="confirmPassword2" name="confirmPassword" class="easyui-textbox" type="password" required="required" validType="equalTo['#password2']" invalidMessage="两次输入密码不匹配" label="确认密码：" style="width:100%">
    </div>     
</form>
</div>
<div id="dlg_sn_buttons_pwd">
<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="resetPwdPost()" style="width:90px">保存</a>
<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_sn_pwd').dialog('close')" style="width:90px">取消</a>
</div>
