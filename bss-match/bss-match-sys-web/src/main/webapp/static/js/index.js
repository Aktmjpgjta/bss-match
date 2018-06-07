$(function(){

    $.ajax({
        type : 'POST',
        url : ctx+'/sys/resources/findResourcesEMUByResources.do',
        cache : false,
        success : function(data) {
            $.each(data,function(i,item){
                if(item.pid==""){
                    if(item.children.length>0){
                        $.each(item.children,function(j,jtem){
                            if(j==0){
                                $('#menul').accordion('add',{
                                    title:jtem.text,
                                    selected:true,
                                    content:getChildManul(jtem.children)
                                });
                            }else{
                                $('#menul').accordion('add',{
                                    title:jtem.text,
                                    selected:false,
                                    content:getChildManul(jtem.children)
                                });

                            }

                        })
                    }
                }

            })
        }
    })

    /*
     $('#tree').tree({
     url: '${ctx}/sys/resources/findResourcesEMUByResources.do',
     onClick: function(node){
     if($('#tree').tree('isLeaf',node.target)){//判断是否是叶子节点
     addTab(node.text,'${pageContext.request.contextPath}'+node.attributes.url)
     }
     }
     });*/

    $('#tt').tabs({
        onContextMenu:function(e, title, index){
            e.preventDefault();
            if(index>0){
                $('#mm').menu('show', {
                    left: e.pageX,
                    top: e.pageY
                }).data("tabTitle", title);;
            }
        }
    });
});

/**
 * 根据母菜单遍历出子菜单
 * @param data
 * @returns {string}
 */
function  getChildManul(data){
    var str="<ul class='list'>";
    if(data.length>0){
        $.each(data,function(j,jtem){
            str+="<li ><a onclick='addTab(\""+jtem.text+"\",\""+ctx+jtem.attributes.url+"\")'>"+jtem.text+"</a></li>"
        })
    }
    str+="</ul>"
    return str;
}

/**
 * 根据点击的菜单，右侧弹出页面
 * @param title 页面的显示的标题
 * @param url 弹出的页面
 */
function addTab(title, url){
    if ($('#tt').tabs('exists', title)){
        $('#tt').tabs('select', title);
    } else {
        var content = createFrame(url);
        $('#tt').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}
/**
 * 嵌入的iframe
 * @param url
 * @returns {string}
 */
function createFrame(url){
    return '<iframe scrolling="auto" frameborder="0" src="'+url+'" style="width:100%;height:99.5%"></iframe>';
}

function logout(){
    $.messager.confirm("确认", "确认注销吗？", function (r) {
        if (r) {
            window.location=ctx+"/logout.do";
        }
    });
}

//菜单处理
function menuHandler(menu) {
    var type = parseInt(menu.name);
    var allTabs = $("#tt").tabs('tabs');
    var allTabtitle = [];
    $.each(allTabs, function (i, n) {
        var opt = $(n).panel('options');
        if (opt.closable)
            allTabtitle.push(opt.title);
    });
    var curTabTitle = $("#mm").data("tabTitle");
    var curTabIndex = $("#tt").tabs("getTabIndex", $("#tt").tabs("getTab", curTabTitle));

    switch (type) {
        case 1://关闭当前
            $("#tt").tabs("close", curTabIndex);
            return false;
            break;
        case 2://全部关闭
            for (var i = 0; i < allTabtitle.length; i++) {
                $('#tt').tabs('close', allTabtitle[i]);
            }
            break;
        case 3://除此之外全部关闭
            for (var i = 0; i < allTabtitle.length; i++) {
                if (curTabTitle != allTabtitle[i])
                    $('#tt').tabs('close', allTabtitle[i]);
            }
            $('#tt').tabs('select', curTabTitle);
            break;
        case 4://当前侧面右边
            for (var i = curTabIndex; i < allTabtitle.length; i++) {
                $('#tt').tabs('close', allTabtitle[i]);
            }
            $('#tt').tabs('select', curTabTitle);
            break;
        case 5: //当前侧面左边
            for (var i = 0; i < curTabIndex - 1; i++) {
                $('#tt').tabs('close', allTabtitle[i]);
            }
            $('#tt').tabs('select', curTabTitle);
            break;
        case 6: //刷新
            var currTab = $('#tt').tabs('getTab', curTabIndex);
            var iframe = $(currTab.panel('options').content);
            var src = iframe.attr('src');
            $('#tt').tabs('update', { tab: currTab, options: { content: createFrame(src)} });
            break;
    }
}


function showUpdatePwd(){
	$('#fm_index_pwd').form('clear');
	$('#dlg_index_pwd').dialog({title:'修改密码',iconCls:'icon-edit'}).dialog('open').dialog('center');
}

function updatePwd(){
    $('#fm_index_pwd').form('submit',{
        url: ctx+'/sys/user/updatePwd.do',
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
                $('#dlg_index_pwd').dialog('close');
                $.messager.show({
                    title: '提示',
                    msg: '密码修改成功！'
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