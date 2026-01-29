package com.github.Legotatsu1985.emr.ui.component.panel;

import com.github.Legotatsu1985.emr.meal.Recipe;
import com.github.Legotatsu1985.emr.meal.RecipeManager;
import com.github.Legotatsu1985.emr.ui.Actions;
import com.github.Legotatsu1985.emr.ui.frame.Home;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedList;

public class RecipesPanel extends JPanel implements Actions {
    // Components
    private JScrollPane recipesTableScrollPane;
    private JTable recipesTable;
    private DefaultTableModel recipesTableModel;
    private ResultPanel resultPanel;

    // Recipe Management
    private RecipeManager recipeManager;
    private LinkedList<Recipe> loadedRecipes;
    private static final String[] RECIPES_TABLE_COLUMNS = {"Date", "Title"};
    private Home home;
    public RecipesPanel(@NotNull Object home) {
        super();
        this.setPreferredSize(new Dimension(800, 600));
        this.setLayout(null);

        this.home = (Home) home;
        this.recipeManager = new RecipeManager();
        this.loadedRecipes = new LinkedList<>();
        refreshRecipes();
        build();
        setActionListeners();
    }

    private void build() {
        // this.setBackground(Color.GREEN);
        // Recipes Table
        setRecipesTableModel();
        setRecipesTable();
    }

    public void setActionListeners() {
        /*this.recipesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = recipesTable.getSelectedRow();
                if (selectedRow >= 0 && selectedRow < recipesTable.getRowCount()) {
                    Recipe selectedRecipe = loadedRecipes.get(selectedRow);
                    if (resultPanel != null) {
                        remove(resultPanel);
                        resultPanel = null;
                        revalidate();
                        repaint();
                    }
                    resultPanel = new ResultPanel(selectedRecipe, home, false);
                    resultPanel.setBounds(400, 0, 400, 600);
                    add(resultPanel);
                    resultPanel.revalidate();
                    resultPanel.repaint();
                } else {
                    if (resultPanel != null) {
                        remove(resultPanel);
                        resultPanel = null;
                        revalidate();
                        repaint();
                    }
                }
            }
        });*/
        this.recipesTable.getSelectionModel().addListSelectionListener(_ -> {
            int selectedRow = this.recipesTable.getSelectedRow();
            if (selectedRow >= 0 && selectedRow < this.recipesTable.getRowCount()) {
                Recipe selectedRecipe = this.loadedRecipes.get(selectedRow);
                if (this.resultPanel != null) {
                    this.remove(this.resultPanel);
                    this.resultPanel = null;
                    this.revalidate();
                    this.repaint();
                }
                this.resultPanel = new ResultPanel(selectedRecipe, this.home, false);
                this.resultPanel.setBounds(400, 0, 400, 600);
                this.add(this.resultPanel);
                this.resultPanel.revalidate();
                this.resultPanel.repaint();
            } else {
                if (this.resultPanel != null) {
                    this.remove(this.resultPanel);
                    this.resultPanel = null;
                    this.revalidate();
                    this.repaint();
                }
            }
        });
    }

    public void refreshRecipes() {
        if (!this.loadedRecipes.isEmpty()) {
            this.loadedRecipes.clear();
        }
        this.loadedRecipes = this.recipeManager.loadAll().getLoadedRecipes();
        setRecipesTableModel();
        setRecipesTable();
        setActionListeners();
    }

    private void setRecipesTableModel() {
        if (this.recipesTableModel != null) {
            this.recipesTableModel = null;
        }
        this.recipesTableModel = new DefaultTableModel(RECIPES_TABLE_COLUMNS, 0);
        if (this.loadedRecipes != null) {
            for (Recipe recipe : this.loadedRecipes) {
                this.recipesTableModel.addRow(new Object[]{recipe.getDate(), recipe.getTitle()});
            }
        }
    }

    public void setRecipesTable() {
        if (this.recipesTable != null) {
            this.remove(this.recipesTableScrollPane);
            this.recipesTable = null;
            this.recipesTableScrollPane = null;
        }
        this.recipesTable = new JTable(this.recipesTableModel);
        this.recipesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.recipesTableScrollPane = new JScrollPane(this.recipesTable);
        this.recipesTableScrollPane.setBounds(0, 0, 390, 600);
        this.add(this.recipesTableScrollPane);
        this.revalidate();
        this.repaint();
    }
}
