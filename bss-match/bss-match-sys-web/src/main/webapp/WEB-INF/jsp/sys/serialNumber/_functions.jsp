<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<script type="text/javascript">

	var snBasePath = '${ctx}${basePath}'; //基础路径
	var url;
	
	
	var snTypeStore = ${fx:findSubsByPath("/SYS/SN_TYPE")};
	
	function dictSNFormatter(value,row,index){	//序列类型
		return snTypeStore[value];
	}

	function newSn(){
        $('#snType').combobox('reload');
	    $('#dlg_sn').dialog({title:'新增流水号',iconCls:'icon-add'}).dialog('open').dialog('center');
	    $('#fm_sn').form('clear');
	    $('#snType').combobox('setValue','3');
	    $('#nextNum').textbox('setValue','1');
	    $('#poolSize').textbox('setValue','200');

	    url = snBasePath+'/add';
    }
	
	function editSn(){
	    var row = $('#dg_sn').datagrid('getSelected');
	    $('#snType').combobox('reload');
	    if (row){
		   $('#fm_sn').form('load',row);
	       $('#dlg_sn').dialog({title:'修改流水号',iconCls:'icon-edit'}).dialog('open').dialog('center');
	       url = snBasePath+'/update';
	    }
	}
	
    function saveSn(){
        $('#fm_sn').form('submit',{
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
                    $('#dlg_sn').dialog('close');
                    $('#dg_sn').datagrid('reload');
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
    
    function deleteSn(){
        var row = $('#dg_sn').datagrid('getSelected');
        if (row){
            $.messager.confirm('确认','你确定要删除此流水号吗？',function(r){
                if (r){
                    $.post(snBasePath+'/delete',{id:row.id},function(result){
                    	if (result.code == 1){
                            $('#dg_sn').datagrid('reload');    // reload the user data
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
    
</script>