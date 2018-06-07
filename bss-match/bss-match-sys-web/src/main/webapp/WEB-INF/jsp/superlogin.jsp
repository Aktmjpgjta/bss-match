<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html class="no-js">
   <head>
<style type="text/css">
.page-container .error {
	height: 14px;
	line-height: 14px;
	font-size: 12px;
	color: #fe3e3e;
	margin: 12px 0 28px;
	font-family: SimSun;
}
</style>
        <meta charset="utf-8">
        <title>债权匹配系统</title>
        <link rel="shortcut icon" href="${ctx}/static/plugins/H/img/favicon.ico" type="/image/x-icon">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <!-- CSS -->
        <link rel="stylesheet" href="${ctx}/static/plugins/H/login/css/reset.css">
        <link rel="stylesheet" href="${ctx}/static/plugins/H/login/css/supersized.css">
        <link rel="stylesheet" href="${ctx}/static/plugins/H/login/css/style.css">
        <link rel="stylesheet" href="${ctx}/static/plugins/H/login/css/login.css">
		<!-- Javascript -->
        <script src="${ctx}/static/plugins/H/login/js/supersized.3.2.7.min.js" ></script>
        <script src="${ctx}/static/plugins/H/login/js/supersized-init.js" ></script>
        <script src="${ctx}/static/plugins/H/login/js/scripts.js" ></script>
<script type="text/javascript">
$(function(){
	toastr.options = {  
	        //closeButton: true,  
	        debug: false,  
	        progressBar: true,  
	        positionClass: " toast-top-right",  
	        onclick: null,  
	        showDuration: "300",  
	        hideDuration: "1000",  
	        timeOut: "2000",  
	        extendedTimeOut: "1000",  
	        showEasing: "swing",  
	        hideEasing: "linear",  
	        showMethod: "fadeIn",  
	        hideMethod: "fadeOut"  
	    };
	//登陆回车事件
	
	document.onkeydown = function(e){ 
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
	    	submitForm();
	     }
	}
}); 
	function submitForm() {
		
		if(Trim($("#user_name").val())==""){
			toastr.error("请输入用户名");
			return ;
		}
		if(Trim($("#user_password").val())==""){
			toastr.error("请输入密码");
			return ;
		}
		if(Trim($("#code").val())==""){
			toastr.error("请输入验证码");
			return ;
		}
		
		if ($("#ff").form('validate') == true) {
			$.ajax({
				url:ctx+"/login.do",
				type:"post",
				data:{username:$("#user_name").val(),password:$("#user_password").val(),code:$("#code").val()},
				success : function(data) {
					if (data.msg == null) {
						window.location = "${pageContext.request.contextPath}/";
					} else {
						changeImg();
						$("#code").val('').focus();
						if("code"==data.msg){
							toastr.error("验证码错误");
						   // alert("请输入正确验证码");
						}else{
							toastr.error("用户名或者密码错误");
						   // alert(data.msg);
						}
					}
				}
			});
		}
	}
	function Trim(str)
	{
		return str.replace(/(^\s*)|(\s*$)/g, "");
	}
	function clearForm() {
		$('#ff').form('clear');
	}
	
	  // 刷新图片  
    function changeImg() {  
        var imgSrc = $("#imgObj");  
        var src = imgSrc.attr("src");  
        imgSrc.attr("src", changeUrl(src));  
    }  
    //为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳  
    function changeUrl(url) {  
        var timestamp = (new Date()).valueOf();  
        var index = url.indexOf("?",url);  
        if (index > 0) {  
            url = url.substring(0, url.indexOf("?"));  
        }  
        if ((url.indexOf("&") >= 0)) {  
            url = url + "×tamp=" + timestamp;  
        } else {  
            url = url + "?timestamp=" + timestamp;  
        }  
        return url;  
    }  
</script>
    </head>

    <body>

        <div class="page-container">
            <div id="ff" >
            <h1 style="color:#45bda6;font-size: 19px;    margin-bottom: 10px;">债权匹配系统登录</h1>
            	<div class="">
	            	<span>用户名:</span>&nbsp;<input type="text"  name="username" class="username"  id="user_name" placeholder="请输入您的用户名！" required="true">
	            </div>
	            <div>
	            	<label>密&nbsp;码:</label>&nbsp;&nbsp;&nbsp;<input type="password"  name="password" class="password"  id="user_password"  placeholder="请输入您的用户密码！" required="true">
	            </div>
	            <p id="error"  style="height: 14px;color: #fe3e3e;margin: 12px 0 28px;display: none;"></p>
			    <div class="valcode">
				    <label for="id"  >  
				        验证码:  
				    </label>  
			        <input type="text" id="code" name="code" placeholder="请输入验证码！" />  
			        <div class="img_div">
				        <img id="imgObj" alt="验证码" src="${ctx}/validateCode" onclick="changeImg()"/>  
<%--				        <a href="#" onclick="changeImg()">换一张</a> --%>
			        </div> 
	            </div>
	            <button href="javascript:void(0)" id="sub" class="submit_button" onclick="submitForm()">登录</button>
<!-- 	         <button class="submit_button" onclick="doSuperLogin();">登录</button>  -->
	            <div class="tishi"></div>
            </div>
        </div>
		
    </body>
</html>
