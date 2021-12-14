package com.demo.notes.processing;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathUtils {
    private static final String separator = FileSystems.getDefault().getSeparator();

    public static String getRootPathFor(FileType fileType) {
        Path homePath = Paths.get(
                System.getenv("HOMEPATH") +
                        separator +
                        "demo" +
                        separator +
                        "app" +
                        separator +
                        "output" +
                        separator +
                        fileType.getValue()
        );
        if (Files.notExists(homePath)) {
            try {
                Files.createDirectories(homePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return homePath + separator;
    }

    public enum FileType {
        JSON("json", ".json"),
        PARQUET("parquet", ".parquet");

        private final String value;
        private final String extension;

        FileType(String value, String extension) {
            this.value = value;
            this.extension = extension;
        }

        public String getValue() {
            return value;
        }

        public String getExtension() {
            return extension;
        }
    }
}

