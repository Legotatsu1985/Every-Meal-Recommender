package com.github.Legotatsu1985.emr;

import com.github.Legotatsu1985.emr.ui.frame.*;
import com.github.Legotatsu1985.emr.util.Config;

import javax.swing.*;
import java.awt.*;

public class App {
    public static Config CFG;

    static void main() {
        CFG = new Config(); // Load app configuration

        try {
            UIManager.setLookAndFeel(CFG.getWindowStyle());
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("Failed to set LookAndFeel: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            Home home = new Home();
            home.setVisible(true);
        });
    }
    public static void openSetting() {
        SwingUtilities.invokeLater(() -> {
            Setting setting = new Setting();
            setting.setVisible(true);
        });
    }
    public static void updateLookAndFeel() {
        try {
            UIManager.setLookAndFeel(CFG.getWindowStyle());
            for (Window window : Window.getWindows()) {
                SwingUtilities.updateComponentTreeUI(window);
            }
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("Failed to update LookAndFeel: " + e.getMessage());
        }
    }
}
