package com.codereader.clazz;

import cn.hutool.core.collection.CollectionUtil;
import lombok.Data;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
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
        ClazzTreeNode node = new ClazzTreeNode().buildNode(clazzInfoList, null);
        node.children = node.children.stream().filter(data -> {
            ClazzInfo current = data.current;
            return !data.children.isEmpty() || !ClazzInfo.ClassType.IMPORT_CLAZZ.equals(current.getType());
        }).collect(Collectors.toList());
        return node;
    }

    private ClazzTreeNode buildNode(List<ClazzInfo> clazzInfoList, ClazzInfo data) {
        ClazzTreeNode node = new ClazzTreeNode();
        node.current = data;
        node.children = node.buildTree(clazzInfoList, node.current);
        return node;
    }

    private List<ClazzTreeNode> buildTree(List<ClazzInfo> clazzInfoList, ClazzInfo current) {
        List<ClazzInfo> currentClazz;
        if (clazzInfoList == null || clazzInfoList.isEmpty()) {
            return new ArrayList<>();
        }

        if (current == null) {
            currentClazz = clazzInfoList.stream()
                    .filter(data -> data.getInterfaces().isEmpty())
                    .filter(data -> StringUtils.isEmpty(data.getParentClazz()))
                    .collect(Collectors.toList());
        } else {
            currentClazz = clazzInfoList.stream()
                    .filter(data -> data.getInterfaces().contains(current.getAbsoluteClazzName()) || StringUtils.equals(data.getParentClazz(), current.getAbsoluteClazzName()))
                    .collect(Collectors.toList());
        }
        return currentClazz.stream()
                .map(data -> this.buildNode(CollectionUtil.subtractToList(clazzInfoList, currentClazz), data))
                .collect(Collectors.toList());
    }


}
