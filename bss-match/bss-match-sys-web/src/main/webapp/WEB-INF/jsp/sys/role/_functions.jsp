<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<script type="text/javascript">

	var userBasePath = '${ctx}${basePath}'; //基础路径
	var url;

	function add(){
        $('#roleId').combobox('reload');
	    $('#dlg_role').dialog({title:'新增角色',iconCls:'icon-add'}).dialog('open').dialog('center');
	    $('#fm_role').form('clear');
	    url = userBasePath+'/add';
    }
	
	function edit(){
	    var row = $('#dg_role').datagrid('getSelected');
	    if (row){
	       $('#resourcesId').combotree('clear');
	       $('#resourcesId').combotree('reload');
	       $('#dlg_role').dialog({title:'修改角色',iconCls:'icon-edit'}).dialog('open').dialog('center');
	       $('#fm_role').form('load',row);
	       
	       $.ajax({   
		     	url:'${ctx}/sys/role/findRoleResourcesByRole.do',   
		     	type:'post',   
		     	data:'id='+row.id,
		     	success:function(data){   
					var s= new Array();
			     	for(var i=0;i<data.length;i++){
			    	  	s.push(data[i].resourcesId);
			      	}
			     	$("#resourcesId").combotree('setValues',s);
		     	}
		   });
	       
	       url = userBasePath+'/update';
	    }
	}
	
    function save(){
        $('#fm_role').form('submit',{
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
                    $('#dlg_role').dialog('close');
                    $('#dg_role').datagrid('reload');
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
    
    function del(){

        var row = $('#dg_role').datagrid('getSelected');
        if (row){
            $.messager.confirm('确认','你确定要删除此角色吗？',function(r){
                if (r){
                    $.post(userBasePath+'/delete',{id:row.id},function(result){
                    	if (result.code == 1){
                            $('#dg_role').datagrid('reload');    // reload the user data
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
    
</script>