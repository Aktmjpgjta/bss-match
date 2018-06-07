<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<title>User list</title>
<script type="text/javascript">
//删除
function del(){
	var row = $("#dg").datagrid('getSelections');
	if(row.length!=0) {
		 $.messager.confirm("确认", "确认删除吗？", function (r) {
		        if (r) {
		        	var myArray=new Array();
		        	var myArray2=new Array();
		        	for(var i=0;i<row.length;i++){
		        		myArray.push(row[i].name);
		        		myArray2.push(row[i].group);
		        	}
		        	$.ajax({   
					     url:'${ctx}/sys/scheduleJob/delete.do',   
					     type:'post',   
					     data:"name="+myArray+"&group="+myArray2,
					     success:function(data){   
					        if(data.msg==null){
					        	window.location="${ctx}/sys/scheduleJob/toList.do";
					        }else{
					        	$.messager.alert('提示',data.msg);
					        }
					     }
					});
		        }
		 });
	}else{
		$.messager.alert('提示','请选择一条');
	}
}

//弹窗修改
function upd(){
	var row =  $("#dg").datagrid('getSelections');
	if(row.length==1){
		//window.location="${ctx}/sys/scheduleJob/toUpdate.do?name="+row[0].name+"&group="+row[0].group+"&cronExpression="+row[0].cronExpression+"&className="+row[0].className;
		$('#dlg_update').form('load',row);
		$('#dlg_update').dialog({title:'修改定时任务',iconCls:'icon-edit',onOpen:function(){
		    $("#editName").textbox("setValue",row[0].name);   
		    $("#editGroup").textbox("setValue",row[0].group);   
		    $("#editCronExpression").textbox("setValue",row[0].cronExpression);   
		    $("#editClassName").textbox("setValue",row[0].className);   
			//$("#editName").val(row[0].name);
		}}).dialog('open').dialog('center');
		
	}else{
		$.messager.alert('提示','请选择一条');
	}
}

function startNow(){
	var row = $("#dg").datagrid('getSelections');
	if(row.length!=0) {
		 $.messager.confirm("确认", "确认操作吗？", function (r) {
		        if (r) {
		        	var myArray=new Array();
		        	var myArray2=new Array();
		        	for(var i=0;i<row.length;i++){
		        		myArray.push(row[i].name);
		        		myArray2.push(row[i].group);
		        	}
		        	$.ajax({   
					     url:'${ctx}/sys/scheduleJob/startNow.do',   
					     type:'post',   
					     data:"name="+myArray+"&group="+myArray2,
					     success:function(data){   
					        if(data.msg==null){
					        	window.location="${ctx}/sys/scheduleJob/toList.do";
					        }else{
					        	$.messager.alert('提示',data.msg);
					        }
					     }
					});
		        }
		 });
	}else{
		$.messager.alert('提示','请选择一条');
	}
}

function resume(){
	var row = $("#dg").datagrid('getSelections');
	if(row.length!=0) {
		 $.messager.confirm("确认", "确认操作吗？", function (r) {
		        if (r) {
		        	var myArray=new Array();
		        	var myArray2=new Array();
		        	for(var i=0;i<row.length;i++){
		        		myArray.push(row[i].name);
		        		myArray2.push(row[i].group);
		        	}
		        	$.ajax({   
					     url:'${ctx}/sys/scheduleJob/resume.do',   
					     type:'post',   
					     data:"name="+myArray+"&group="+myArray2,
					     success:function(data){   
					        if(data.msg==null){
					        	window.location="${ctx}/sys/scheduleJob/toList.do";
					        }else{
					        	$.messager.alert('提示',data.msg);
					        }
					     }
					});
		        }
		 });
	}else{
		$.messager.alert('提示','请选择一条');
	}
}

function stop(){
	var row = $("#dg").datagrid('getSelections');
	if(row.length!=0) {
		 $.messager.confirm("确认", "确认操作吗？", function (r) {
		        if (r) {
		        	var myArray=new Array();
		        	var myArray2=new Array();
		        	for(var i=0;i<row.length;i++){
		        		myArray.push(row[i].name);
		        		myArray2.push(row[i].group);
		        	}
		        	$.ajax({   
					     url:'${ctx}/sys/scheduleJob/stop.do',   
					     type:'post',   
					     data:"name="+myArray+"&group="+myArray2,
					     success:function(data){   
					        if(data.msg==null){
					        	window.location="${ctx}/sys/scheduleJob/toList.do";
					        }else{
					        	$.messager.alert('提示',data.msg);
					        }
					     }
					});
		        }
		 });
	}else{
		$.messager.alert('提示','请选择一条');
	}
}
function cron(){
	window.location="${ctx}/sys/scheduleJob/cron.do";
}
var userBasePath = '${ctx}${basePath}'; //基础路径


/**弹出添加页面
*/
function add(){
	$('#dlg_add').dialog({title:'新增定时任务',iconCls:'icon-add'}).dialog('open').dialog('center');
	//$('#ff').form('clear');
	//url =userBasePath+'/sys/scheduleJob/toAdd.do';
}

function submitForm(){
	if($("#ff").form('validate')){
		$.ajax({   
		     url:$("#ff").attr("action"),   
		     type:$("#ff").attr("method"),   
		     data:$("#ff").serializeArray(),
		     success:function(data){   
		        if(data.msg==null){
		        	window.location="${ctx}/sys/scheduleJob/toList.do";
		        }else{
		        	$.messager.alert('提示',data.msg);
		        }
		     }
		});
	}
}
function submitForm2(){
	if($("#ff2").form('validate')){
		$.ajax({   
		     url:$("#ff2").attr("action"),   
		     type:$("#ff2").attr("method"),   
		     data:$("#ff2").serializeArray(),
		     success:function(data){   
		        if(data.msg==null){
		        	window.location="${ctx}/sys/scheduleJob/toList.do";
		        }else{
		        	$.messager.alert('提示',data.msg);
		        }
		     }
		});
	}
}
function dictSTFormatter(value,row,index){
	if(value=="NORMAL"){
		return "正常"
	}else if(value=="ERROR"){
		return "出错"
	}else if(value=="PAUSED"){
		return "暂停"
	}else{
		return value;
	}
}

</script>
</head>
<body>
  	<jsp:include page="./add.jsp"/>
  	<jsp:include page="./update.jsp"/>
   <table id="dg"  class="easyui-datagrid" 
    fit="true" 
            url="${ctx}/sys/scheduleJob/list.do"
            toolbar="#topBtn" 
            rownumbers="true" fitColumns="true" singleSelect="false">
        <thead>
            <tr>
            	<th field=""  data-options="checkbox:true"></th>
                <th field="name"  width="50">任务标识</th>
                <th field="group"  width="50">任务名称</th>
                <th field="cronExpression"  width="50">表达式</th>
                <th field="status"  width="50" formatter="dictSTFormatter">状态</th>
                <th field="className"  width="50">调用定时类</th>
            </tr>
        </thead>
    </table>
    <div id="topBtn" style="padding:2px 5px;">
		<shiro:hasPermission name="/sys/scheduleJob/add">
		<a class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="add()">新增</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="/sys/scheduleJob/update">
       	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">编辑</a>
       	</shiro:hasPermission>
       	<shiro:hasPermission name="/sys/scheduleJob/delete">
       	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
       	</shiro:hasPermission>
       	<shiro:hasPermission name="/sys/scheduleJob/stop">
       	<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-edit" plain="true" onclick="stop()">暂停</a>
       	</shiro:hasPermission>
       	<shiro:hasPermission name="/sys/scheduleJob/resume">
       	<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-edit" plain="true" onclick="resume()">恢复</a>
       	</shiro:hasPermission>
       	<shiro:hasPermission name="/sys/scheduleJob/startNow">
       	<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-edit" plain="true" onclick="startNow()">执行一次</a>
       	</shiro:hasPermission>
<!--         	<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-edit" plain="true" onclick="cron()">表达器生成</a> -->
    </div>
</body>
</html>