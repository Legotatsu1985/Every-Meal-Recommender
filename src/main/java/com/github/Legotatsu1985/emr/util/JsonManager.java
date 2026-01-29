package com.github.Legotatsu1985.emr.util;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class JsonManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonManager.class);

    private FileEditor fe;
    private String filePath;
    private String loadedString;
    private String jsonString;

    public JsonManager(String jsonFilePath, String str) {
        // Check the jsonFilePath is JSON file type.
        if (jsonFilePath.endsWith(".json")) {
            if (Files.exists(Paths.get(jsonFilePath))) {
                this.filePath = jsonFilePath;
                this.jsonString = str;
                this.read();
            } else {
                LOGGER.error("The provided file path does not exist: {}", jsonFilePath);
            }
        } else {
            LOGGER.error("The provided file path is not a JSON file: {}", jsonFilePath);
        }
    }

    private void read() {
        this.fe = new FileEditor(this.filePath);
        this.loadedString = this.fe.load().getString();
    }

    private void write(boolean append) {}

    public void setJsonString(@NotNull String jsonString) {}
}
