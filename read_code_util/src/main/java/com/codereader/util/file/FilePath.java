package com.codereader.util.file;

import lombok.Data;

@Data
public class FilePath {

    private String absolutePath;
    private String relativePath;

    public static FilePath of(String path, String rootPath) {
        FilePath result = new FilePath();
        result.absolutePath = path;
        result.relativePath = path.replace(rootPath, "");
        return result;

    }
}
