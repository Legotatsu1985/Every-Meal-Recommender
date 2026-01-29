package com.github.Legotatsu1985.emr.ui.component.panel;

import com.github.Legotatsu1985.emr.meal.Recipe;
import com.github.Legotatsu1985.emr.meal.RecipeManager;
import com.github.Legotatsu1985.emr.ui.Actions;
import com.github.Legotatsu1985.emr.ui.frame.Home;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ResultPanel extends JPanel implements Actions {
    // Components
    private JLabel titleLabel;
    private JLabel caloriesLabel;
    private JLabel cookingTimeLabel;
    private JLabel ingredientTableLabel;
    private JLabel stepsTableLabel;
    private JScrollPane ingredientTableScrollPane;
    private JScrollPane stepsTableScrollPane;
    private JTable ingredientTable;
    private JTable stepsTable;
    private DefaultTableColumnModel ingredientTableColumnModel;
    private DefaultTableColumnModel stepsTableColumnModel;
    private DefaultTableModel ingredientTableModel;
    private DefaultTableModel stepsTableModel;
    private JButton saveRecipeButton;

    // Recipe data
    private Recipe recipe;
    private String title;
    private int cookingTimeMinutes;
    private Map<String, Integer> ingredientsMap; // <name, count>
    private List<String> stepsList; // Ordered list of steps
    private int calories;
    private static final String[] INGREDIENT_TABLE_COLUMNS = {"Ingredient", "Quantity"};
    private static final String[] STEPS_TABLE_COLUMNS = {"Step", "Instruction"};

    private Home home;
    private boolean isSavable;

    public ResultPanel(@NotNull Recipe recipe, @NotNull Object home, boolean isSavable) {
        super();
        this.setPreferredSize(new Dimension(400, 600));
        this.setLayout(null);
        this.recipe = recipe;
        this.title = recipe.getTitle();
        this.cookingTimeMinutes = recipe.getCookingTimeMinutes();
        this.ingredientsMap = recipe.getIngredients();
        this.stepsList = recipe.getSteps();
        this.calories = recipe.getCalories();
        this.home = (Home) home;
        this.isSavable = isSavable;
        build();
        setActionListeners();
    }

    private void build() {
        // Recipe Title
        this.titleLabel = new JLabel(this.title);
        this.titleLabel.setBounds(0, 10, 400, 50);
        this.titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Cooking Time and Calories (same line)
        this.cookingTimeLabel = new JLabel(String.format("Cooking Time: %d minutes", this.cookingTimeMinutes));
        this.cookingTimeLabel.setBounds(0, 70, 200, 30);
        this.cookingTimeLabel.setFont(new Font("Arial", Font.BOLD, 14));

        this.caloriesLabel = new JLabel(String.format("Calories: %d kcal", this.calories));
        this.caloriesLabel.setBounds(210, 70, 170, 30);
        this.caloriesLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Ingredient Table
        this.ingredientTableLabel = new JLabel("Ingredients");
        this.ingredientTableLabel.setBounds(0, 110, 200, 30);
        this.ingredientTableLabel.setFont(new Font("Arial", Font.BOLD, 14));

        this.ingredientTableModel = new DefaultTableModel(INGREDIENT_TABLE_COLUMNS, 0);
        for (Map.Entry<String, Integer> entry : this.ingredientsMap.entrySet()) {
            this.ingredientTableModel.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }
        this.ingredientTable = new JTable(this.ingredientTableModel);
        this.ingredientTable.setEnabled(false);
        this.ingredientTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.ingredientTableColumnModel = (DefaultTableColumnModel) this.ingredientTable.getColumnModel();
        this.ingredientTableColumnModel.getColumn(0).setPreferredWidth(305);
        this.ingredientTableColumnModel.getColumn(1).setPreferredWidth(70);
        this.ingredientTableScrollPane = new JScrollPane(this.ingredientTable);
        this.ingredientTableScrollPane.setBounds(0, 145, 380, 150);

        // Steps Table
        this.stepsTableLabel = new JLabel("Steps");
        this.stepsTableLabel.setBounds(0, 300, 200, 30);
        this.stepsTableLabel.setFont(new Font("Arial", Font.BOLD, 14));

        this.stepsTableModel = new DefaultTableModel(STEPS_TABLE_COLUMNS, 0);
        int stepNum = 1;
        for (String step: this.stepsList) {
            this.stepsTableModel.addRow(new Object[]{stepNum, step});
            stepNum++;
        }
        this.stepsTable = new JTable(this.stepsTableModel);
        this.stepsTable.setEnabled(false);
        this.stepsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.stepsTableColumnModel = (DefaultTableColumnModel) this.stepsTable.getColumnModel();
        this.stepsTableColumnModel.getColumn(0).setPreferredWidth(40);
        this.stepsTableColumnModel.getColumn(1).setPreferredWidth(1040);
        this.stepsTableScrollPane = new JScrollPane(this.stepsTable);
        this.stepsTableScrollPane.setBounds(0, 335, 380, 150);

        // Save Recipe Button
        this.saveRecipeButton = new JButton("Save Recipe");
        this.saveRecipeButton.setBounds(140, 490, 100, 30);
        this.saveRecipeButton.setEnabled(this.isSavable);

        this.add(this.titleLabel);
        this.add(this.cookingTimeLabel);
        this.add(this.caloriesLabel);
        this.add(this.ingredientTableLabel);
        this.add(this.ingredientTableScrollPane);
        this.add(this.stepsTableLabel);
        this.add(this.stepsTableScrollPane);
        this.add(this.saveRecipeButton);
    }

    public void setActionListeners() {
        this.saveRecipeButton.addActionListener(_ -> {
            RecipeManager rm = new RecipeManager(this.recipe);
            rm.saveRecipe(true);
            this.saveRecipeButton.setEnabled(false);
            JOptionPane.showMessageDialog(this.home,
                    "Recipe Saved Successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            this.home.disposeResultPanel();
        });
    }
}
