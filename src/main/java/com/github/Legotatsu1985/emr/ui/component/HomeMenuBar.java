package com.github.Legotatsu1985.emr.ui.component;

import com.github.Legotatsu1985.emr.App;
import com.github.Legotatsu1985.emr.ui.Actions;

import javax.swing.*;

public class HomeMenuBar extends JMenuBar implements Actions {
    // Menus
    private JMenu file;

    // Menu Items
    private JMenuItem settingsItem;
    private JMenuItem exitItem;

    public HomeMenuBar() {
        this.file = new JMenu("File");
        this.settingsItem = new JMenuItem("Settings");
        this.exitItem = new JMenuItem("Exit");
        build();
        setActionListeners();
    }

    private void build() {
        this.add(this.file);
        this.file.add(this.settingsItem);
        this.file.add(this.exitItem);
    }

    public void setActionListeners() {
        this.settingsItem.addActionListener(_ -> App.openSetting());
        this.exitItem.addActionListener(_ -> System.exit(0));
    }
}
