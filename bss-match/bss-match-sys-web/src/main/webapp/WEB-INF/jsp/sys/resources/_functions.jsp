<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<script type="text/javascript">

var userBasePath = '${ctx}${basePath}'; //基础路径
var url;

$(function(){
	initDictJsonStore('${ctx}','/COMP/STATUS');
});

function typeFormatter(value,row,index){
    return dictFormatter('${ctx}','/SYS/MENU_TYPE',value);
}
var statusJsonStore = ${fx:findSubsByPath("/COMP/STATUS")};


var isUpdate = false;
function add(){
	isUpdate = false;
	var row = $('#res_tree').treegrid('getSelected');
    if (row){
        $('#dlg_res').dialog({title:'新增菜单',iconCls:'icon-add'}).dialog('open').dialog('center');
        $('#fm_res').form('clear');
//	    $('#dlg_dict').dialog({title:'新增菜单',iconCls:'icon-add'}).dialog('open').dialog('center');
//	    $('#fm_dict').form('clear');
	 //   $('#path').textbox('textbox').attr('readonly',false);
//	 	$('#status').combobox('setValue','1');
	 	$('#pid').textbox('setValue', row.id);
	    url = userBasePath+'/add';
    }
}

function edit(){
	isUpdate = true;
    var row = $('#res_tree').treegrid('getSelected');
    if (row){
       $('#dlg_res').dialog({title:'修改菜单',iconCls:'icon-edit'}).dialog('open').dialog('center');
       $('#fm_res').form('load',row);
       url = userBasePath+'/update';
    }
}

function save(){
	var row = $('#res_tree').treegrid('getSelected');
    $('#fm_res').form('submit',{
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
                $('#dlg_res').dialog('close');
                if(isUpdate){
                	//$('#dg_dict').treegrid('reload', row.id);
                	$('#res_tree').treegrid('update',{
                		id: row.id,
                		row: result.obj,
                		state: result.state
                	});
                	$('#res_tree').treegrid('reload', row.id);
                }else{
	               	$('#res_tree').treegrid('append',{
	               		parent: row.id,
	               		data: [result.obj]
	               	});
                }
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
	var row = $('#res_tree').treegrid('getSelected');
    if (row){
        $.messager.confirm('确认','你确定要级联删除菜单吗？',function(r){
            if (r){
                $.post(userBasePath+'/delete',{id:row.id},function(result){
                	if (result.code == 1){
                		$('#res_tree').treegrid('remove', row.id);
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