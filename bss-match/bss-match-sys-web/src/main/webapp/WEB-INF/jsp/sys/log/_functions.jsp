<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<script type="text/javascript">

	function usernameFormatter(value,row,index){
		if(row.user==null){
			return "";
		}else{
			return row.user.username;
		}
		 
	}
	//删除
	function del(){
		var row = $("#dg_log").datagrid('getSelections');
		if(row.length!=0) {
			 $.messager.confirm("确认", "确认删除吗？", function (r) {
			        if (r) {
			        	var ids = "";;
			        	for(var i=0;i<row.length;i++){
			        		ids += row[i].id + ",";
			        	}
			        	$.ajax({
						     url:'${ctx}/sys/log/delete.do',
						     type:'post',
						     data:"ids="+ids,
						     success:function(result){
				                if (result.code == 1){
				                    $('#dg_log').datagrid('reload');
				                } else {
				                	$.messager.show({
				                        title: 'Error',
				                        msg: result.msg
				                    });
				                }
				                $.messager.progress('close');
						     }
						});
			        }
			 });
		}else{
			$.messager.alert('提示','请选择一条');
		}
	}
</script>