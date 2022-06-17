package com.codereader.util.file;


import com.codereader.clazz.ClazzInfo;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.codereader.clazz.ClassConstants.*;

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
            Stream<String> lineStream = buffer.lines();
            List<String> lines = lineStream.collect(Collectors.toList());
            List<String> importLines = getAllImport(lines);
            lines = cleanJavaFile(lines);
            fileSize = lines.size();
//            System.out.println("File: " + file.getName() + " fileLine=" + fileSize);
            readJavaClazz(lines);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileSize;
    }


    private static void readJavaClazz(List<String> lines) {
        StringBuilder clazzLineBuilder = new StringBuilder();
        long classCount = 0;
        boolean startClazzLine = false;

        ClazzInfo.ClassType type = ClazzInfo.ClassType.SIMPLE;
        for (String line : lines) {
            if (line.matches("[^\"(]+" + CLAZZ_TAG + "[^\");]+")) {
                startClazzLine = true;
                type = ClazzInfo.ClassType.SIMPLE;
            } else if (line.matches("[^\"(]+" + INTERFACE_TEG + "[^\");]+")) {
                startClazzLine = true;
                type = ClazzInfo.ClassType.INTERFACE;
            }
            if (startClazzLine) {
                clazzLineBuilder.append(line);
                if (line.trim().endsWith("{")) {
                    startClazzLine = false;
                    String clazzLine = clazzLineBuilder.toString().trim();
                    clazzLine = clazzLine.substring(0, clazzLine.length() - 1);
                    clazzLineBuilder = new StringBuilder();
                    classCount++;
                    readClazzLine(clazzLine, classCount > 1 ? ClazzInfo.ClassType.INNER_CLASS : type);
                }
            }
        }
    }

    private static void readClazzLine(String clazzLine, ClazzInfo.ClassType type) {
        ClazzInfo clazzInfo = ClazzInfo.getClazzInfo(clazzLine,type);

    }


    private static List<String> cleanJavaFile(List<String> lines) {
        return lines.stream()
                .filter(StringUtils::isNotBlank)
                .filter(data -> !data.startsWith("package"))
                .filter(data -> !data.startsWith("import"))
                .filter(data -> !data.trim().startsWith("//"))
                .filter(data -> !data.trim().startsWith("/*"))
                .filter(data -> !data.trim().startsWith("*"))
                .filter(data -> !data.trim().startsWith("*/"))
                .collect(Collectors.toList());
    }

    private static List<String> getAllImport(List<String> lines) {
        return lines.stream()
                .filter(StringUtils::isNotBlank)
                .filter(data -> data.startsWith("import")).collect(Collectors.toList());
    }

    enum FileType {
        JAVA, DICTIONARY, OTHER
    }


}
