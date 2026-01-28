package com.github.Legotatsu1985.emr.ui.component.panel;

import com.github.Legotatsu1985.emr.meal.Recipe;
import com.github.Legotatsu1985.emr.ui.frame.Home;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ResultPanel extends JPanel {
    // Components
    private JLabel titleLabel;
    private JLabel caloriesLabel;
    private JLabel cookingTimeLabel;
    private JLabel ingredientTableLabel;
    private JTable ingredientTable;
    private JTable stepsTable;
    private DefaultTableModel ingredientTableModel;
    private DefaultTableModel stepsTableModel;

    // Recipe data
    private String title;
    private int cookingTimeMinutes;
    private Map<String, Integer> ingredientsMap; // <name, count>
    private List<String> stepsList; // Ordered list of steps
    private int calories;
    private static final String[] INGREDIENT_TABLE_COLUMNS = {"Ingredient", "Quantity"};
    private static final String[] STEPS_TABLE_COLUMNS = {"Step Number", "Instruction"};
    private String[][] ingredientTableData;
    private String[][] stepsTableData;

    private Home home;

    public ResultPanel(@NotNull Recipe recipe, @NotNull Object home) {
        super();
        this.setPreferredSize(new Dimension(400, 600));
        this.setLayout(null);
        this.title = recipe.getTitle();
        this.cookingTimeMinutes = recipe.getCookingTimeMinutes();
        this.ingredientsMap = recipe.getIngredients();
        this.stepsList = recipe.getSteps();
        this.calories = recipe.getCalories();
        this.home = (Home) home;
        build();
    }

    private void build() {
        // Recipe Title
        this.titleLabel = new JLabel(this.title);
        this.titleLabel.setBounds(10, 10, 400, 50);
        this.titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Cooking Time and Calories (same line)
        this.cookingTimeLabel = new JLabel(String.format("Cooking Time: %d minutes", this.cookingTimeMinutes));
        this.cookingTimeLabel.setBounds(10, 70, 200, 30);
        this.cookingTimeLabel.setFont(new Font("Arial", Font.BOLD, 14));

        this.caloriesLabel = new JLabel(String.format("Calories: %d kcal", this.calories));
        this.caloriesLabel.setBounds(220, 70, 170, 30);
        this.caloriesLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Ingredient Table
        this.ingredientTableLabel = new JLabel("Ingredients");
        this.ingredientTableLabel.setBounds(10, 110, 200, 30);
        this.ingredientTableLabel.setFont(new Font("Arial", Font.BOLD, 14));

        this.ingredientTableModel = new DefaultTableModel(INGREDIENT_TABLE_COLUMNS, 0);


        this.add(this.titleLabel);
        this.add(this.cookingTimeLabel);
        this.add(this.caloriesLabel);
        this.add(this.ingredientTableLabel);
    }

    private void setTableData() {
        if (this.ingredientsMap != null && this.stepsList != null) {

        }
    }
}
