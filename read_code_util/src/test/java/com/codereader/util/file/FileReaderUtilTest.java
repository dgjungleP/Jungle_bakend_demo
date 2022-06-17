package com.codereader.util.file;


import com.codereader.clazz.ClazzFileInfo;
import com.codereader.clazz.ClazzInfo;
import com.codereader.clazz.ClazzTreeNode;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileReaderUtilTest {


    @Test
    public void testFileReader() {
        Map<FileReaderUtil.FileType, List<FilePath>> fileRoot = FileReaderUtil.getFileRoot("C:\\Users\\jd53\\Documents\\GitHub\\startMybatis\\mybatis-3\\src" + "\\main");

        List<ClazzFileInfo> clazzFileInfoList = fileRoot.values()
                .parallelStream()
                .flatMap(Collection::stream)
                .map(FileReaderUtil::readJavaFile)
                .filter(data -> StringUtils.isNotBlank(data.getFileName()))
                .collect(Collectors.toList());
        long sumFileLine = clazzFileInfoList.stream()
                .mapToLong(ClazzFileInfo::getFileSize)
                .sum();

        List<ClazzInfo> clazzInfoList = clazzFileInfoList.stream()
                .map(ClazzFileInfo::getAllClazz)
                .filter(data -> !data.isEmpty())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        for (ClazzInfo info : clazzInfoList) {
            System.out.println(info);
        }
        ClazzTreeNode treeNode = ClazzTreeNode.buildTree(clazzInfoList);
        System.out.println("Total count: " + sumFileLine);
        System.out.println(treeNode);
    }

    @Test
    public void testReadJavaFile() {
        String path = "C:\\Users\\jd53\\Documents\\GitHub\\Jungle_bakend_demo\\schedule_manager\\src\\main\\java\\com\\jungle\\demo\\scheduled\\MyScheduledAutoConfiguration.java";
        FileReaderUtil.readJavaFile(FilePath.of(path, "C:\\Users\\jd53\\Documents\\GitHub\\Jungle_bakend_demo\\schedule_manager\\src\\main"));
    }
}
