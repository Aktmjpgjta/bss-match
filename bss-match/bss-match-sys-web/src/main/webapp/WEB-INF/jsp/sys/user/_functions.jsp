<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<script type="text/javascript">

	var userBasePath = '${ctx}${basePath}'; //基础路径
	var url;

	function newUser(){
        $('#roleId').combobox('reload');
	    $('#dlg_user').dialog({title:'新增用户',iconCls:'icon-add'}).dialog('open').dialog('center');
	    $('#fm_user').form('clear');
	    $('#status').combobox('setValue','1');
	    $("#password_div").show();
    	$("#confirmPassword_div").show();
	    url = userBasePath+'/add';
    }
	
	function editUser(){
	    var row = $('#dg_user').datagrid('getSelected');
	    if (row){
			$('#fm_user').form('load',row);
			
	    	$("#password_div").hide();
	    	$("#confirmPassword_div").hide();
	    	$('#password').textbox('setValue','只是为了验证能通过1a');
	    	$('#confirmPassword').textbox('setValue','只是为了验证能通过1a');
	    	
	       $('#dlg_user').dialog({title:'修改用户',iconCls:'icon-edit'}).dialog('open').dialog('center');
	       $.ajax({   
			     url:userBasePath+'/findUserRoleByUser.do',   
			     type:'post',   
			     data:'id='+row.id,
			     success:function(data){
			    	 var values = [];
			    	 for(var i=0; i<data.length; i++){
			    		 values.push(data[i].roleId);
			    	 }
			    	 $('#roleId').combobox('setValues', values);
			     }
		   });
	       url = userBasePath+'/update';
	    }
	}
	
    function saveUser(){
        $('#fm_user').form('submit',{
            url: url,
            onSubmit: function(){
            	var isValid = $(this).form('validate');
        		if (isValid){
        			$.messager.progress({
                        msg:'Loading ...'
                    });
        		}
        		return isValid;
            },
            success: function(result){
                var result = eval('('+result+')');
                if (result.code == 1){
                    $('#dlg_user').dialog('close');
                    $('#dg_user').datagrid('reload');
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
    
    function deleteUser(){

        var row = $('#dg_user').datagrid('getSelected');
        if (row){
            $.messager.confirm('确认','你确定要删除此用户吗？',function(r){
                if (r){
                    $.post(userBasePath+'/delete',{id:row.id},function(result){
                    	if (result.code == 1){
                            $('#dg_user').datagrid('reload');    // reload the user data
                        } else {
                            $.messager.show({    // show error message
                                title: 'Error',
                                msg: result.msg
                            });
                        }
                    },'json');
                }
            });
        }
    }
    
    function search(){
    	
    	alert(1111);
    	
    }
    
    function statusFormatter(value){
    	if(value==1 || value=="1"){
    		return "正常";
    	}else if(value==0 || value=="0"){
    		return "<span style='color:red'>禁用</span>"
    	}else{
    		return "未知";
    	}
    }
    
    
	function resetPwd(){
	    var row = $('#dg_user').datagrid('getSelected');
	    if (row){
		   $('#fm_user_pwd').form('load',row);
		   $('#password2').textbox('setValue','');
	    	$('#confirmPassword2').textbox('setValue','');
	       $('#dlg_user_pwd').dialog({title:'重置密码',iconCls:'icon-edit'}).dialog('open').dialog('center');
	       url = userBasePath+'/resetPwd';
	    }
	}
	
	function resetPwdPost(){
        $('#fm_user_pwd').form('submit',{
            url: url,
            onSubmit: function(){
            	var isValid = $(this).form('validate');
        		if (isValid){
        			$.messager.progress({
                        msg:'Loading ...'
                    });
        		}
        		return isValid;
            },
            success: function(result){
                var result = eval('('+result+')');
                if (result.code == 1){
                    $('#dlg_user_pwd').dialog('close');
                    $.messager.show({
                        title: '提示',
                        msg: '重置成功！'
                    });
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
	
	function disableUser(){
	    var row = $('#dg_user').datagrid('getSelected');
	    if (row){
	    	$.messager.progress({
                msg:'Loading ...'
            });
	       $.ajax({
			     url:userBasePath+'/disableUser.do',   
			     type:'post',   
			     data:'id='+row.id,
			     success:function(result){
			    	$.messager.progress('close');
	                if (result.code == 1){
	                    $.messager.show({
	                        title: '提示',
	                        msg: '处理成功！'
	                    });
	                    $('#dg_user').datagrid('reload');
	                } else {
	                	$.messager.show({
	                        title: 'Error',
	                        msg: result.msg
	                    });
	                }
			     }
		   });
	    }
	}
    
</script>