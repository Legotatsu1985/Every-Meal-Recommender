package com.github.Legotatsu1985.emr.ui.frame;

import com.github.Legotatsu1985.emr.meal.Recipe;
import com.github.Legotatsu1985.emr.ui.Actions;
import com.github.Legotatsu1985.emr.ui.component.menubar.HomeMenuBar;
import com.github.Legotatsu1985.emr.ui.component.panel.*;

import javax.swing.*;

public class Home extends JFrame implements Actions {
    private SuggestionPanel suggestionPanel;
    private RecipesPanel recipesPanel;
    private ResultPanel resultPanel;
    private JPanel recipeRequestPanel;
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
        // Recipe Request Panel
        this.recipeRequestPanel = new JPanel();
        this.recipeRequestPanel.setLayout(null);

        // Suggestion Panel
        this.suggestionPanel = new SuggestionPanel(this);
        this.suggestionPanel.setBounds(0, 0, 400, 600);

        // Recipes Panel
        this.recipesPanel = new RecipesPanel(this);
        this.recipesPanel.setBounds(0, 0, 800, 600);

        // Menu Bar
        this.menuBar = new HomeMenuBar();
        this.setJMenuBar(this.menuBar);

        // Panels in Tabbed Pane
        this.recipeRequestPanel.add(this.suggestionPanel);

        // Tabbed Pane
        this.mainTabbedPane = new JTabbedPane();
        this.mainTabbedPane.add("Requesting", this.recipeRequestPanel);
        this.mainTabbedPane.add("Managing", this.recipesPanel);
        this.add(this.mainTabbedPane);
    }

    public void setActionListeners() {
        this.mainTabbedPane.addChangeListener(_ -> {
            if (this.mainTabbedPane.getSelectedIndex() == 1) { // If Managing Tab is selected
                this.recipesPanel.refreshRecipes();
                this.recipesPanel.revalidate();
                this.recipesPanel.repaint();
            }
        });
    }

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
