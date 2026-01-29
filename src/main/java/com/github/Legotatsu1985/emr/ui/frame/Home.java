package com.github.Legotatsu1985.emr.ui.frame;

import com.github.Legotatsu1985.emr.meal.Recipe;
import com.github.Legotatsu1985.emr.ui.Actions;
import com.github.Legotatsu1985.emr.ui.component.menubar.HomeMenuBar;
import com.github.Legotatsu1985.emr.ui.component.panel.ResultPanel;
import com.github.Legotatsu1985.emr.ui.component.panel.SuggestionPanel;

import javax.swing.*;

public class Home extends JFrame implements Actions {
    private SuggestionPanel suggestionPanel;
    private ResultPanel resultPanel;
    private JPanel recipeRequestPanel;
    private JPanel recipeManagingPanel;
    private HomeMenuBar menuBar;
    private JTabbedPane mainTabbedPane;

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
        this.recipeRequestPanel = new JPanel();
        this.recipeRequestPanel.setLayout(null);

        // Suggestion Panel
        this.suggestionPanel = new SuggestionPanel(this);
        this.suggestionPanel.setBounds(0, 0, 400, 600);

        // Result Panel

        // Menu Bar
        this.menuBar = new HomeMenuBar();

        // Set layout and add components
        this.setJMenuBar(this.menuBar);
        this.recipeRequestPanel.add(this.suggestionPanel);
        this.add(this.recipeRequestPanel);

        this.mainTabbedPane = new JTabbedPane();
        this.mainTabbedPane.add("Requesting", this.recipeRequestPanel);
        this.mainTabbedPane.add("Managing", new JPanel());
        this.add(this.mainTabbedPane);
    }

    public void setActionListeners() {}

    public void setSuggestedRecipe(Recipe recipe) {this.suggestedRecipe = recipe;}

    public void showResultPanel() {
        if (this.resultPanel != null) {
            this.resultPanel.setVisible(false);
            this.recipeRequestPanel.remove(this.resultPanel);
            this.resultPanel = null;
            this.recipeRequestPanel.revalidate();
            this.recipeRequestPanel.repaint();
        }
        this.resultPanel = new ResultPanel(this.suggestedRecipe, this);
        this.resultPanel.setBounds(400, 0, 400, 600);
        this.recipeRequestPanel.add(this.resultPanel);
        this.recipeRequestPanel.revalidate();
        this.recipeRequestPanel.repaint();
    }

    public void disposeResultPanel() {
        if (this.resultPanel != null) {
            this.resultPanel.setVisible(false);
            this.recipeRequestPanel.remove(this.resultPanel);
            this.resultPanel = null;
            this.recipeRequestPanel.revalidate();
            this.recipeRequestPanel.repaint();
        }
    }
}
