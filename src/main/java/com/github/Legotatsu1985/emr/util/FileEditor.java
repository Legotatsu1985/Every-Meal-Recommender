package com.github.Legotatsu1985.emr.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileEditor {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileEditor.class);

    private String filePath;
    private String rawString;

    public FileEditor(@NotNull String filePath) {
        this.filePath = filePath;
        this.rawString = null;
    }

    public FileEditor load() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(this.filePath), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            this.rawString = sb.toString();
            return this;
        } catch (IOException e) {
            LOGGER.error("Failed to load JSON file: {}", e.getMessage());
        }
        return this;
    }

    public void write(@NotNull String str, boolean append) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.filePath, append), StandardCharsets.UTF_8))) {
            bw.write(str);
        } catch (IOException e) {
            LOGGER.error("Failed to write to file: {}", e.getMessage());
        }
    }

    public @Nullable String getString() {
        if (this.rawString != null) {
            return this.rawString;
        } else {
            LOGGER.error("Raw string is null. Make sure to call load() before getString().");
            return null;
        }
    }
}
