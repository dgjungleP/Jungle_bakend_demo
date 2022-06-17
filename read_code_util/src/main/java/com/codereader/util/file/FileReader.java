package com.codereader.util.file;

import java.io.File;
import java.util.*;

public class FileReader {
    public static final String JAVA_TAIL = ".java";

    public static final List<String> JAVA_FILE_PATH = new ArrayList<>();

    private FileReader() {
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


    enum FileType {
        JAVA, DICTIONARY, OTHER
    }
}
