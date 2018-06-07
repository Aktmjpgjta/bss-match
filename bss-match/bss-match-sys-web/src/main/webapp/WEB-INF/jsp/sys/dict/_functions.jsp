<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<script type="text/javascript">

var userBasePath = '${ctx}${basePath}'; //基础路径
var url;

$(function(){
	initDictJsonStore('${ctx}','/COMP/STATUS');
});

var statusJsonStore = ${fx:findSubsByPath("/COMP/STATUS")};


var isUpdate = false;
function add(){
	isUpdate = false;
	var row = $('#dg_dict').treegrid('getSelected');
    if (row){
	    $('#dlg_dict').dialog({title:'新增字典',iconCls:'icon-add'}).dialog('open').dialog('center');
	    $('#fm_dict').form('clear');
	 //   $('#path').textbox('textbox').attr('readonly',false);
	 	$('#status').combobox('setValue','1');
	 	$('#pid').textbox('setValue', row.id);
	    url = userBasePath+'/add';
    }
}

function edit(){
	isUpdate = true;
    var row = $('#dg_dict').treegrid('getSelected');
    if (row){
       $('#dlg_dict').dialog({title:'修改字典',iconCls:'icon-edit'}).dialog('open').dialog('center');
       $('#fm_dict').form('load',row);
       url = userBasePath+'/update';
    }
}

function save(){
	var row = $('#dg_dict').treegrid('getSelected');
    $('#fm_dict').form('submit',{
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
                $('#dlg_dict').dialog('close');
                if(isUpdate){
                	//$('#dg_dict').treegrid('reload', row.id);
                	$('#dg_dict').treegrid('update',{
                		id: row.id,
                		row: result.obj,
                		state: result.state
                	});
                	$('#dg_dict').treegrid('reload', row.id);
                }else{
	               	$('#dg_dict').treegrid('append',{
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
	var row = $('#dg_dict').treegrid('getSelected');
    if (row){
        $.messager.confirm('确认','你确定要级联删除字典吗？',function(r){
            if (r){
                $.post(userBasePath+'/delete',{id:row.id},function(result){
                	if (result.code == 1){
                		$('#dg_dict').treegrid('remove', row.id);
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
function statusFormatter(value,row,index){
	if(statusJsonStore[value]=="停用"){
		return "<span style='color:red'>停用</span>"
	}
	return statusJsonStore[value];
}
</script>