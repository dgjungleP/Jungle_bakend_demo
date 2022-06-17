package com.codereader.util.file;


import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.*;

public class FileReaderUtil {
    public static final String JAVA_TAIL = ".java";

    private FileReaderUtil() {
    }

    public static Map<FileType, List<String>> getFileRoot(String filePath) {
        Map<FileType, List<String>> fileRootPath = new HashMap<>();
        File file = new File(filePath);
        Queue<File> fileDictionary = new ArrayDeque<>();
        fileDictionary.add(file);
        while (!fileDictionary.isEmpty()) {
            File firstFile = fileDictionary.poll();
            File[] listFiles = firstFile.listFiles();
            if (listFiles != null) {
                Arrays.stream(listFiles).forEachOrdered(nextFile -> {
                    FileType type = checkFileType(nextFile);
                    fileRootPath.computeIfAbsent(type, (key) -> new ArrayList<>())
                            .add(nextFile.getPath());
                    if (type.equals(FileType.DICTIONARY)) {
                        fileDictionary.add(nextFile);
                    }
                });
            }
        }
        return fileRootPath;
    }

    private static Boolean checkJavaFile(File file) {
        if (file == null) {
            return false;
        }
        String name = file.getName();
        return name.endsWith(JAVA_TAIL);
    }

    public static FileType checkFileType(File file) {
        if (file.isDirectory()) {
            return FileType.DICTIONARY;
        } else if (checkJavaFile(file)) {
            return FileType.JAVA;
        }
        return FileType.OTHER;
    }

    public static long readJavaFile(String filePath) {
        long fileSize = 0;
        if (filePath == null || !filePath.endsWith(JAVA_TAIL)) {
            return fileSize;
        }
        File file = new File(filePath);
        if (!file.isFile()) {
            return fileSize;
        }
        try (Reader fileReader = new FileReader(file);
             BufferedReader buffer = new BufferedReader(fileReader);) {
            fileSize = buffer.lines()
                    .filter(StringUtils::isNotBlank)
                    .filter(data -> !data.startsWith("package"))
                    .filter(data -> !data.startsWith("import"))
                    .count();
            System.out.println("File: " + file.getName() + " fileLine=" + fileSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileSize;
    }

    enum FileType {
        JAVA, DICTIONARY, OTHER
    }
}
