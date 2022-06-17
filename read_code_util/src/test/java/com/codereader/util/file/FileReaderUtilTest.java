package com.codereader.util.file;


import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FileReaderUtilTest {


    @Test
    public void testFileReader() {
        Map<FileReaderUtil.FileType, List<String>> fileRoot = FileReaderUtil.getFileRoot("C:\\Users\\jd53\\Documents\\GitHub\\startMybatis\\mybatis-3\\src" + "\\main");

        long sum = fileRoot.values()
                .parallelStream()
                .flatMap(Collection::stream)
                .mapToLong(FileReaderUtil::readJavaFile)
                .sum();
        System.out.println("Total count: " + sum);
    }

    @Test
    public void testReadJavaFile() {
        String path = "C:\\Users\\jd53\\Documents\\GitHub\\Jungle_bakend_demo\\schedule_manager\\src\\main\\java\\com\\jungle\\demo\\scheduled\\MyScheduledAutoConfiguration.java";

        FileReaderUtil.readJavaFile(path);
    }
}
