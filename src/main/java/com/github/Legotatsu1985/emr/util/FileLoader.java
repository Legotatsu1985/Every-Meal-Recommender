package com.github.Legotatsu1985.emr.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileLoader.class);

    private String filePath;
    private String rawString;

    public FileLoader(@NotNull String filePath) {
        this.filePath = filePath;
        this.rawString = null;
    }

    @Nullable
    public String load() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(this.filePath), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            this.rawString = sb.toString();
            return this.rawString;
        } catch (IOException e) {
            LOGGER.error("Failed to load JSON file: {}", e.getMessage());
        }
        return null;
    }
}
