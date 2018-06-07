<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="dlg_user" class="easyui-dialog" style="width:400px" iconCls="icon-add" closed="true" buttons="#dlg_user_buttons">
<form id="fm_user" method="post" novalidate style="margin:0;padding:20px 50px">
    <div style="margin-bottom:10px;display:none">
    	<input name="id" class="easyui-textbox" label="id" style="width:100%">
    </div>
    <div style="margin-bottom:10px">
        <input id="realname" name="realname" class="easyui-textbox" data-options="required:true" label="姓名：" style="width:100%" >
    </div>
    <div style="margin-bottom:10px">
        <input id="username" name="username" class="easyui-textbox" data-options="required:true,validType:['englishOrNum','minLength[6]']" label="用户名：" style="width:100%" >
    </div>
    <div style="margin-bottom:10px">
        <input id="email" name="email" class="easyui-textbox" data-options="required:true" label="邮箱：" style="width:100%" >
    </div>
    <div style="margin-bottom:10px" id="password_div">
        <input id="password" name="password" class="easyui-textbox" type="password" data-options="required:true,validType:['safepass']" label="密码：" style="width:100%">
    </div>
    <div style="margin-bottom:10px" id="confirmPassword_div">
        <input id="confirmPassword" name="confirmPassword" class="easyui-textbox" type="password" required="required" validType="equalTo['#password']" invalidMessage="两次输入密码不匹配" label="确认密码：" style="width:100%">
    </div> 
    <div style="margin-bottom:10px">
        <input class="easyui-combobox" 
        		id="roleId"
				name="roleId"
				label="角色："
				style="width:100%"
				multiple="multiple"
				required="true"
				data-options="
						url:'${ctx}/sys/role/listAll.do',
						method:'get',
						valueField:'id',
						textField:'name',
						panelHeight:'auto'
			    ">
    </div>
	<div style="margin-bottom:10px">
		<select class="easyui-combobox" id="status" name="status" label="状态：" style="width:100%;" data-options="required:true,panelHeight:'auto'">
               <option value="1">正常</option>
               <option value="0">禁用</option>
           </select>
    </div>    
</form>
</div>
<div id="dlg_user_buttons">
<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveUser()" style="width:90px">保存</a>
<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_user').dialog('close')" style="width:90px">取消</a>
</div>


<div id="dlg_user_pwd" class="easyui-dialog" style="width:400px" iconCls="icon-add" closed="true" buttons="#dlg_user_buttons_pwd">
<form id="fm_user_pwd" method="post" novalidate style="margin:0;padding:20px 50px">
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
<div id="dlg_user_buttons_pwd">
<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="resetPwdPost()" style="width:90px">保存</a>
<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_user_pwd').dialog('close')" style="width:90px">取消</a>
</div>
