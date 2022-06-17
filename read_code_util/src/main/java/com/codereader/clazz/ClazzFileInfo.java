package com.codereader.clazz;

import cn.hutool.core.collection.CollectionUtil;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ClazzFileInfo {
    @Setter(AccessLevel.NONE)
    private List<ClazzInfo> allClazz;
    private Long fileSize;
    private String fileName;
    @Setter(AccessLevel.NONE)
    private List<String> allImportList;
    @Setter(AccessLevel.NONE)
    private String basePackage;

    public ClazzFileInfo() {
        this.allClazz = new ArrayList<>();
        this.fileSize = 0L;
        this.allImportList = new ArrayList<>();
    }

    public ClazzFileInfo(String fileName) {
        this();
        this.fileName = fileName;
    }

    public void setAllClazz(List<ClazzInfo> allClazz) {
        if (allClazz != null) {
            allClazz.stream()
                    .filter(data -> !ClazzInfo.ClassType.IMPORT_CLAZZ.equals(data.getType()))
                    .forEach(data -> data.setBasePackage(this.basePackage));
        }
        this.allClazz = allClazz;
    }

    public void setAllImportList(List<String> allImportList) {

        this.allImportList = allImportList;
        if (this.allClazz != null) {
            allClazz.forEach(data -> data.findCurrentClazz(this.allImportList));
            List<ClazzInfo> importClazz = allImportList.stream().map(ClazzInfo::buildFromImport)
                    .distinct()
                    .collect(Collectors.toList());
            this.allClazz = new ArrayList<>(CollectionUtil.unionDistinct(this.allClazz, importClazz));
        }
    }


    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
        if (this.allClazz != null) {
            allClazz.stream()
                    .filter(data -> !ClazzInfo.ClassType.IMPORT_CLAZZ.equals(data.getType()))
                    .forEach(data -> data.setBasePackage(this.basePackage));
        }
    }
}
