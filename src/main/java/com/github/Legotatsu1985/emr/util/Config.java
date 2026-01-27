package com.github.Legotatsu1985.emr.util;

import com.formdev.flatlaf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {
    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);
    public static final String CFG_STR = "config.properties";
    public static final Path CFG_PATH = Paths.get(CFG_STR);

    // Properties
    private String langCode;
    private String apiKey;
    private int windowStyle;

    public Config() {
        if (!Files.exists(CFG_PATH)) {
            try {
                Files.createFile(CFG_PATH);
                PropertyManager.save("lang", "en");
                PropertyManager.save("apiKey", "");
                PropertyManager.save("windowStyle", 0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        this.langCode = PropertyManager.getString("lang"); LOGGER.info("Lang Code = {}", this.langCode);
        this.apiKey = PropertyManager.getString("apiKey"); LOGGER.info("API Key = {}", this.apiKey);
        this.windowStyle = PropertyManager.getInt("windowStyle"); LOGGER.info("Window Style = {}", this.windowStyle);
    }

    public String getLangCode() {return this.langCode;}

    public LookAndFeel getWindowStyle() {
        return switch (getWindowStyleInt()) {
            case 1 -> new FlatDarkLaf();
            case 2 -> new FlatIntelliJLaf();
            case 3 -> new FlatDarculaLaf();
            default -> new FlatLightLaf();
        };
    }

    public int getWindowStyleInt() {return this.windowStyle;}

    public String getApiKey() {return this.apiKey;}

    public void saveLangCode(String langCode) {
        this.langCode = langCode;
        PropertyManager.save("lang", this.langCode);
    }

    public void saveApiKey(String apiKey) {
        this.apiKey = apiKey;
        PropertyManager.save("apiKey", this.apiKey);
    }

    public void saveWindowStyle(int index) {
        this.windowStyle = index;
        PropertyManager.save("windowStyle", this.windowStyle);
    }
}
