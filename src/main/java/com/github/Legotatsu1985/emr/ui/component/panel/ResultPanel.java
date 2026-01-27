package com.github.Legotatsu1985.emr.ui.component.panel;

import com.github.Legotatsu1985.emr.meal.Recipe;
import com.github.Legotatsu1985.emr.ui.frame.Home;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ResultPanel extends JPanel {
    // Components
    private JLabel titleLabel;
    private JLabel caloriesLabel;
    private JLabel cookingTimeLabel;
    private JTable ingredientTable;
    private JTable stepsTable;

    // Recipe data
    private String title;
    private int cookingTimeMinutes;
    private Map<String, Integer> ingredients; // <name, count>
    private List<String> steps; // Ordered list of steps
    private int calories;

    private Home home;

    public ResultPanel(@NotNull Recipe recipe, @NotNull Object home) {
        super();
        this.setPreferredSize(new Dimension(400, 600));
        this.setLayout(null);
        this.title = recipe.getTitle();
        this.cookingTimeMinutes = recipe.getCookingTimeMinutes();
        this.ingredients = recipe.getIngredients();
        this.steps = recipe.getSteps();
        this.calories = recipe.getCalories();
        this.home = (Home) home;
        build();
    }

    private void build() {
        this.setBackground(Color.ORANGE);
    }
}
