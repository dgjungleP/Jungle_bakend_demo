package com.codereader.clazz;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClazzFileInfo {
    private List<ClazzInfo> allClazz;
    private Long fileSize;
    private String fileName;
    private List<String> allImportList;
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
}
