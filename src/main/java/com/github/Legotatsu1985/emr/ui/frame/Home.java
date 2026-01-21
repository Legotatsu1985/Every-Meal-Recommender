package com.github.Legotatsu1985.emr.ui.frame;

import com.github.Legotatsu1985.emr.ui.Actions;
import com.github.Legotatsu1985.emr.ui.component.HomeMenuBar;

import javax.swing.*;

public class Home extends JFrame implements Actions {
    private JPanel mainPanel;
    private HomeMenuBar menuBar;

    public Home() {
        super("Every Meal Recommender");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        build();
    }

    private void build() {
        // Create instances
        this.mainPanel = new JPanel();
        this.menuBar = new HomeMenuBar();

        // Set layout and add components
        this.setJMenuBar(this.menuBar);
        this.add(this.mainPanel);
    }

    public void setActionListeners() {}
}
