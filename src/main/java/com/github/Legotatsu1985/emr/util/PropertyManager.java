package com.github.Legotatsu1985.emr.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

public class PropertyManager {
    private static final Properties PP = new Properties();
    private static final boolean IS_CFG_EXISTS = Files.exists(Config.CFG_PATH);

    public static void save(@NotNull String key, @NotNull String value) {
        if (IS_CFG_EXISTS) {
            try (FileInputStream in = new FileInputStream(Config.CFG_STR)) {
                PP.load(in);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            PP.setProperty(key, value);
            try (FileOutputStream out = new FileOutputStream(Config.CFG_STR)) {
                PP.store(out, "Updated property: " + key);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Properties file does not exist: " + Config.CFG_STR);
        }
    }

    public static void save(@NotNull String key, int value) {save(key, String.valueOf(value));}

    public static void remove(@NotNull String key) {
        if (IS_CFG_EXISTS) {
            try (FileInputStream in = new FileInputStream(Config.CFG_STR)) {
                PP.load(in);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            PP.remove(key);
            try (FileOutputStream out = new FileOutputStream(Config.CFG_STR)) {
                PP.store(out, "Removed property: " + key);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Properties file does not exist: " + Config.CFG_STR);
        }
    }

    public static @Nullable String getString(@NotNull String key) {
        if (IS_CFG_EXISTS) {
            try (FileInputStream in = new FileInputStream(Config.CFG_STR)) {
                PP.load(in);
                return PP.getProperty(key);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Properties file does not exist: " + Config.CFG_STR);
        }
    }

    public static int getInt(@NotNull String key) {
        String value = getString(key);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Failed to parse int property: " + key, e);
            }
        }
        return 0;
    }
}
