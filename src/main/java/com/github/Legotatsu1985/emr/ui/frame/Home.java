package com.github.Legotatsu1985.emr.ui.frame;

import com.github.Legotatsu1985.emr.meal.Recipe;
import com.github.Legotatsu1985.emr.ui.Actions;
import com.github.Legotatsu1985.emr.ui.component.menubar.HomeMenuBar;
import com.github.Legotatsu1985.emr.ui.component.panel.SuggestionPanel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class Home extends JFrame implements Actions {
    private SuggestionPanel suggestionPanel;
    private JPanel basePanel;
    private JPanel resultPanel;
    private HomeMenuBar menuBar;

    private JLabel resultTempLabel;

    private Recipe suggestedRecipe;

    public Home() {
        super("Every Meal Recommender");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        build();
        setActionListeners();
    }

    private void build() {
        // Base Panel
        this.basePanel = new JPanel();
        this.basePanel.setLayout(null);

        // Suggestion Panel
        this.suggestionPanel = new SuggestionPanel(this);
        this.suggestionPanel.setBounds(0, 0, 400, 600);

        // Result Panel
        this.resultPanel = new JPanel();
        this.resultPanel.setLayout(null);
        this.resultPanel.setBounds(390, 10, 390, 540);
        this.resultPanel.setBackground(Color.ORANGE);
        this.resultPanel.setVisible(false);

        this.resultTempLabel = new JLabel("RESULT");
        this.resultTempLabel.setBounds(140, 260, 100, 30);
        this.resultTempLabel.setForeground(Color.BLACK);
        this.resultTempLabel.setFont(new Font("Arial", Font.BOLD, 24));
        this.resultTempLabel.setVisible(false);

        // Menu Bar
        this.menuBar = new HomeMenuBar();

        // Set layout and add components
        this.setJMenuBar(this.menuBar);
        this.basePanel.add(this.suggestionPanel);
        this.basePanel.add(this.resultPanel);
        this.resultPanel.add(this.resultTempLabel);
        this.add(this.basePanel);
    }

    public void setActionListeners() {}

    public void setSuggestedRecipe(Recipe recipe) {this.suggestedRecipe = recipe;}
}
