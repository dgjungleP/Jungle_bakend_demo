package com.codereader.clazz;

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

@Data
public class ClazzFileInfo {
    @Setter(AccessLevel.NONE)
    private List<ClazzInfo> allClazz;
    private Long fileSize;
    private String fileName;
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
            allClazz.forEach(data -> data.setBasePackage(this.basePackage));
        }
        this.allClazz = allClazz;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
        if (this.allClazz != null) {
            allClazz.forEach(data -> data.setBasePackage(this.basePackage));
        }
    }
}
