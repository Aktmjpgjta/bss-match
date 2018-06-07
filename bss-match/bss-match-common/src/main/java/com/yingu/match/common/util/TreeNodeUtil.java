package com.yingu.match.common.util;

import com.yingu.match.common.model.JsonTreeData;

import java.util.ArrayList;
import java.util.List;

public class TreeNodeUtil {

    public final static List<JsonTreeData> getfatherNode(List<JsonTreeData> treeDataList) {
        List<JsonTreeData> newTreeDataList = new ArrayList<JsonTreeData>();
        for (JsonTreeData jsonTreeData : treeDataList) {
            if(jsonTreeData.getPid() .equals("")) {
                //获取父节点下的子节点
                jsonTreeData.setChildren(getChildrenNode(jsonTreeData.getId(),treeDataList));
                jsonTreeData.setState("open");
                newTreeDataList.add(jsonTreeData);
            }
        }
        return newTreeDataList;
    }

    private final static List<JsonTreeData> getChildrenNode(String pid , List<JsonTreeData> treeDataList) {
        List<JsonTreeData> newTreeDataList = new ArrayList<JsonTreeData>();
        for (JsonTreeData jsonTreeData : treeDataList) {
            if(jsonTreeData.getPid() == null)  continue;
            //这是一个子节点
            if(jsonTreeData.getPid().equals(pid)){
                //递归获取子节点下的子节点
                List<JsonTreeData> tempList=getChildrenNode(jsonTreeData.getId() , treeDataList);
                if(tempList.size()>0){
                     jsonTreeData.setState("closed");
                }else{
                    jsonTreeData.setState("open");
                }
                jsonTreeData.setChildren(tempList);

                newTreeDataList.add(jsonTreeData);
            }
        }
        return newTreeDataList;
    }
}