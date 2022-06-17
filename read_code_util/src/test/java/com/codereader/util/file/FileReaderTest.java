package com.codereader.util.file;


import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class FileReaderTest {


    @Test
    public void testFileReader() {
        Map<FileReader.FileType, List<String>> fileRoot = FileReader.getFileRoot("F:\\project\\Jungle_bakend_demo\\my_analysis\\src" + "\\main");

        fileRoot.forEach((key, value) -> {
            System.out.println(key + ": ");
            for (String filePath : value) {
                System.out.println(filePath);
            }
        });
    }
}
