<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
    <%--<%@ include file="/WEB-INF/jsp/common/include.jsp"%>--%>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui-1.5/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui-1.5/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/plugins/toastr/toastr.min.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/common.css">
	<script type="text/javascript" src="${ctx}/static/jquery-easyui-1.5/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/plugins/toastr/toastr.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/jquery-easyui-1.5/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/jquery-easyui-1.5/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${ctx}/static/jquery-easyui-1.5/easyui.validate.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/common.js"></script>
    <link href="${ctx}/static/plugins/H/img/favicon.ico" rel="shortcut icon" />
    <link href="${ctx}/static/css/index.css" rel="stylesheet" type="text/css">
    <script src="${ctx}/static/js/index.js" type="text/javascript"></script>
    <title>债权匹配系统</title>
</head>
<script type="text/javascript">
	jQuery.prototype.serializeObject=function(){
		var obj=new Object();
		$.each(this.serializeArray(),function(index,param){
			if(!(param.name in obj)){
				obj[param.name]=param.value;
			}
		});
		return obj;
	};
</script>
<body class="easyui-layout">
    <div data-options="region:'north',split:true" style="height:106px;">
    	<div style="height: 98px;
		    background-image: url(./static/plugins/H/img/index_navigate11.jpg);
		    background-repeat: no-repeat;">
		     <div style="float: right;margin-right: 29px;">
		     	<img class="indexIcon" src="./static/plugins/H/img/logout.png">
	    	  	<a href="javascript:void(0)" style="color: white;" onclick="logout()">退出</a>
		     </div>
		     <div style="float: right;margin-right: 29px;">
		     	<img class="indexIcon" src="./static/plugins/H/img/user.png">
	    	  	<a href="javascript:void(0)" style="color: white;" onclick="showUpdatePwd()">修改密码</a>
		     </div>
		     <div style="float: right;margin-right: 29px;">
		     	<img class="indexIcon" src="./static/plugins/H/img/user.png">
	    	  	<span style="color:white">${realname} 您好！</span>
		     </div>		     
		</div>
    </div>
    <div id="menul" data-options="region:'west',title:'菜单列表',split:true" class="easyui-accordion" style="width:200px;">
        <%--<div id="tree"></div>--%>
    </div>
    <div id="tt" class="easyui-tabs" data-options="region:'center'" >
    	<div title="首页" style="padding:10px">
			<p style="font-size:14px">债权匹配</p>
		</div>
    </div>
    
	<div id="mm" class="easyui-menu" data-options="onClick:menuHandler" style="width:150px;">
		<div id="mm-reload" name="6">刷新</div>
		<div id="mm-tabclose" name="1">关闭</div>
		<div id="mm-tabcloseall" name="2">全部关闭</div>
		<div id="mm-tabcloseother" name="3">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright" name="4">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft" name="5">当前页左侧全部关闭</div>
	</div>
    
<div id="dlg_index_pwd" class="easyui-dialog" style="width:400px" iconCls="icon-add" closed="true" buttons="#dlg_index_pwd_buttons">
<form id="fm_index_pwd" method="post" novalidate style="margin:0;padding:20px 50px">
    <div style="margin-bottom:10px">
        <input id="oldPassword" name="oldPassword" class="easyui-textbox" type="password" data-options="required:true" label="旧密码：" style="width:100%">
    </div>
    <div style="margin-bottom:10px" id="password_div">
        <input id="password" name="password" class="easyui-textbox" type="password" data-options="required:true,validType:['safepass']" label="新密码：" style="width:100%">
    </div>
    <div style="margin-bottom:10px" id="confirmPassword_div">
        <input id="confirmPassword" name="confirmPassword" class="easyui-textbox" type="password" required="required" validType="equalTo['#password']" invalidMessage="两次输入密码不匹配" label="确认新密码：" style="width:100%">
    </div>     
</form>
</div>
<div id="dlg_index_pwd_buttons">
<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="updatePwd()" style="width:90px">提交</a>
<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_index_pwd').dialog('close')" style="width:90px">取消</a>
</div>    
    
</body>
</html>
