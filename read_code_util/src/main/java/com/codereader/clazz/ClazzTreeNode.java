package com.codereader.clazz;

import cn.hutool.core.collection.CollectionUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ClazzTreeNode {
    private ClazzTreeNode() {
    }

    private List<ClazzTreeNode> children;

    private ClazzInfo current;

    //TODO both extends and implements
    public static ClazzTreeNode buildTree(List<ClazzInfo> clazzInfoList) {
        List<CheckedClazzInfoHolder> clazzInfoHolders = clazzInfoList.stream()
                .map(CheckedClazzInfoHolder::new)
                .collect(Collectors.toList());
        ClazzTreeNode node = new ClazzTreeNode().buildNode(clazzInfoHolders, null);
        node.children = node.children.stream().filter(data -> {
            ClazzInfo current = data.current;
            return !data.children.isEmpty() || !ClazzInfo.ClassType.IMPORT_CLAZZ.equals(current.getType());
        }).collect(Collectors.toList());
        return node;
    }

    private ClazzTreeNode buildNode(List<CheckedClazzInfoHolder> clazzInfoList, ClazzInfo data) {
        ClazzTreeNode node = new ClazzTreeNode();
        node.current = data;
        node.children = node.buildTree(clazzInfoList, node.current);
        return node;
    }

    private List<ClazzTreeNode> buildTree(List<CheckedClazzInfoHolder> clazzInfoList, ClazzInfo current) {
        List<CheckedClazzInfoHolder> currentClazz;
        if (checkValidateClassInfo(clazzInfoList)) {
            return new ArrayList<>();
        }

        if (current == null) {
            currentClazz = clazzInfoList.stream()
                    .filter(CheckedClazzInfoHolder::isParentClass)
                    .collect(Collectors.toList());
        } else {
            currentClazz = clazzInfoList.stream()
                    .filter(data -> !data.isParentClass())
                    .filter(data -> data.haveThisParent(current))
                    .collect(Collectors.toList());
        }
        return currentClazz.stream()
                .map(data -> this.buildNode(clazzInfoList, data.getBaseClassInfo()))
                .collect(Collectors.toList());
    }

    private boolean checkValidateClassInfo(List<CheckedClazzInfoHolder> clazzInfoList) {
        if (clazzInfoList == null || clazzInfoList.isEmpty()) {
            return true;
        }
        long count = clazzInfoList.stream()
                .filter(data -> !data.checkDone())
                .filter(data -> !ClazzInfo.ClassType.IMPORT_CLAZZ.equals(data.getBaseClassInfo().getType()))
                .count();
        return count == 0;
    }


}
