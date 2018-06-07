<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<script type="text/javascript">

	var basePath = '${ctx}${basePath}'; //基础路径
	var url;
	
	
	function search() {
		var queryJson = $('#search_form').serializeObject();
		$('#dg_table').datagrid('load', queryJson);
	}
    
	function formatLogTime(value,row,index){
		return new Date(value).Format("yyyy-MM-dd hh:mm:ss.S");
	}
	
	function operateFmt(value,row,index){
		var str = '<a href="#" class="easyui-linkbutton" onclick="viewParams('+value+')">查看参数</a>';  
        return str; 
	}
	
	function viewParams(id){
	 //   var row = $('#dg_table').datagrid('getSelected');
	 //   if (row){
	       $.ajax({   
			     url:basePath+'/viewParams.do',   
			     type:'post',   
			     data:'id='+id,
			     success:function(data){
			    	 $('#reqmsg').textbox('setValue', data.reqmsg);
			    	 $('#resmsg').textbox('setValue', data.resmsg);
			     }
		   });
	       $('#prm_dlg').dialog('open');
	 //   }
	}
	
	Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
	
</script>