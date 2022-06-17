package com.codereader.util.file;


import com.codereader.clazz.ClazzFileInfo;
import com.codereader.clazz.ClazzInfo;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.codereader.clazz.ClazzConstants.*;

public class FileReaderUtil {
    public static final String JAVA_TAIL = ".java";


    private FileReaderUtil() {
    }

    public static Map<FileType, List<FilePath>> getFileRoot(String filePath) {
        Map<FileType, List<FilePath>> fileRootPath = new HashMap<>();
        File file = new File(filePath);
        System.out.println(file.getPath());
        Queue<File> fileDictionary = new ArrayDeque<>();
        fileDictionary.add(file);
        while (!fileDictionary.isEmpty()) {
            File firstFile = fileDictionary.poll();
            File[] listFiles = firstFile.listFiles();
            if (listFiles != null) {
                Arrays.stream(listFiles).forEachOrdered(nextFile -> {
                    FileType type = checkFileType(nextFile);
                    fileRootPath.computeIfAbsent(type, (key) -> new ArrayList<>())
                            .add(FilePath.of(nextFile.getPath(), filePath));
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

    public static ClazzFileInfo readJavaFile(FilePath filePath) {
        String absolutePath = filePath.getAbsolutePath();
        ClazzFileInfo info = new ClazzFileInfo();
        long fileSize = 0;
        if (absolutePath == null || !absolutePath.endsWith(JAVA_TAIL)) {
            return info;
        }
        File file = new File(absolutePath);
        if (!file.isFile()) {
            return info;
        }
        try (Reader fileReader = new FileReader(file);
             BufferedReader buffer = new BufferedReader(fileReader);) {
            Stream<String> lineStream = buffer.lines();
            List<String> lines = lineStream.collect(Collectors.toList());
            List<String> importLines = getAllImport(lines);
            String packageLine = getPackage(lines);
            lines = cleanJavaFile(lines);
            fileSize = lines.size();
            List<ClazzInfo> allClazz = readJavaClazz(lines);
            info.setAllClazz(allClazz);
            info.setAllImportList(importLines);
            info.setBasePackage(packageLine);
        } catch (Exception e) {
            e.printStackTrace();
        }
        info.setFileSize(fileSize);
        info.setFileName(file.getName());
        return info;
    }

    private static String getPackage(List<String> lines) {
        return lines.stream()
                .filter(StringUtils::isNotBlank)
                .filter(data -> data.startsWith("package"))
                .map(FileReaderUtil::readPackageLine)
                .collect(Collectors.joining(""));

    }

    private static String readPackageLine(String line) {
        return line.replace("package", "")
                .replace(";", "")
                .trim();
    }

    private static List<ClazzInfo> readJavaClazz(List<String> lines) {
        List<ClazzInfo> result = new ArrayList<>();
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
                    ClazzInfo clazzInfo = readClazzLine(clazzLine, classCount > 1 ? ClazzInfo.ClassType.INNER_CLASS : type);
                    result.add(clazzInfo);
                }
            }
        }
        return result;
    }

    private static ClazzInfo readClazzLine(String clazzLine, ClazzInfo.ClassType type) {
        return ClazzInfo.getClazzInfo(clazzLine, type);
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
                .filter(data -> data.startsWith("import"))
                .map(FileReaderUtil::readImportLine)
                .collect(Collectors.toList());
    }

    private static String readImportLine(String importLine) {
        return importLine.replace("import", "")
                .replace(";", "")
                .trim();
    }

    enum FileType {
        JAVA, DICTIONARY, OTHER
    }


}
