package com.github.Legotatsu1985.emr.ui.frame;

import com.github.Legotatsu1985.emr.App;
import com.github.Legotatsu1985.emr.ui.Actions;

import javax.swing.*;
import java.awt.*;

public class Setting extends JDialog implements Actions {
    private static final String[] LANGUAGES = {
            "English (US)"
    };
    private static final String[] LANG_CODES = {
            "en-US"
    };
    private static final String[] WINDOW_STYLES = {
            "Light",
            "Dark",
            "IntelliJ",
            "Darcula"
    };
    private JPanel mainPanel;
    private JComboBox<String> langComboBox;
    private JComboBox<String> styleComboBox;
    private JLabel langLabel;
    private JLabel styleLabel;
    private JButton saveButton;

    public Setting() {
        super((JFrame) null, "Settings", true);
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        build();
        setActionListeners();
    }

    private void build() {
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(null);

        this.langLabel = new JLabel("Language");
        this.langLabel.setBounds(10, 10, 100, 30);
        this.langComboBox = new JComboBox<>(LANGUAGES);
        this.langComboBox.setBounds(110, 10, 100, 30);
        this.langComboBox.setSelectedIndex(getLangIndex(App.CFG.getLangCode()));

        this.styleLabel = new JLabel("Window Style");
        this.styleLabel.setBounds(10, 50, 100, 30);
        this.styleComboBox = new JComboBox<>(WINDOW_STYLES);
        this.styleComboBox.setBounds(110, 50, 100, 30);
        this.styleComboBox.setSelectedIndex(App.CFG.getWindowStyleInt());

        this.saveButton = new JButton("Save & Close");
        this.saveButton.setBounds(150, 200, 100, 30);

        this.mainPanel.add(this.langLabel);
        this.mainPanel.add(this.langComboBox);
        this.mainPanel.add(this.styleLabel);
        this.mainPanel.add(this.styleComboBox);
        this.mainPanel.add(this.saveButton);
        this.add(this.mainPanel);
    }

    public void setActionListeners() {
        this.saveButton.addActionListener(_ -> {
            int selectedLangIndex = this.langComboBox.getSelectedIndex();
            int selectedStyleIndex = this.styleComboBox.getSelectedIndex();
            String selectedLangCode = LANG_CODES[selectedLangIndex];
            App.CFG.saveLangCode(selectedLangCode);
            App.CFG.saveWindowStyle(selectedStyleIndex);
            App.updateLookAndFeel();
            this.dispose();
        });
    }

    private static int getLangIndex(String langCode) {
        int index = 0;
        for (String code : LANG_CODES) {
            if (code.equals(langCode)) {
                return index;
            }
            index++;
        }
        return 0;
    }
}
