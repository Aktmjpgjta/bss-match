<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
<script type="text/javascript">
    var pc;
    //不要放在$(function(){});中
    $.parser.onComplete = function () {
        if (pc) clearTimeout(pc);
        pc = setTimeout(closes, 1);
    }

    function closes() {
        $('#loading').fadeOut(10, function () {
            $(this).remove();
        });
    }
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
<div id="loading" style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#ffffff;text-align :center;padding-top:20%;">
</div>