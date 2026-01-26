package com.github.Legotatsu1985.emr.ui.component.panel;

import com.github.Legotatsu1985.emr.meal.Recipe;
import com.github.Legotatsu1985.emr.ui.Actions;
import com.github.Legotatsu1985.emr.ui.frame.Home;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SuggestionPanel extends JPanel implements Actions {
    private JLabel ingredientInputLabel;
    private JTextField ingredientInputField;
    private JList<String> ingredientList;
    private DefaultListModel<String> ingredientListModel;
    private JButton addIngredientButton;
    private JButton removeIngredientButton;
    private JButton clearIngredientsButton;
    private JButton suggestRecipeButton;

    private Home home;

    private Recipe suggestedRecipe;

    public SuggestionPanel(@NotNull Home home) {
        super();
        this.setPreferredSize(new Dimension(400, 600));
        this.setLayout(null);
        this.home = home;
        build();
        setActionListeners();
    }

    private void build() {
        this.ingredientInputLabel = new JLabel("Ingredient you wish to use:");
        this.ingredientInputLabel.setBounds(10, 10, 200, 30);

        this.ingredientInputField = new JTextField();
        this.ingredientInputField.setBounds(160, 10, 150, 30);
        this.ingredientInputField.setToolTipText("Enter an ingredient you want to request.");

        this.addIngredientButton = new JButton("Add");
        this.addIngredientButton.setBounds(320, 10, 60, 30);

        this.removeIngredientButton = new JButton("Remove Selected");
        this.removeIngredientButton.setBounds(10, 460, 180, 30);

        this.clearIngredientsButton = new JButton("Clear All");
        this.clearIngredientsButton.setBounds(200, 460, 180, 30);

        this.ingredientListModel = new DefaultListModel<>();
        this.ingredientList = new JList<>(ingredientListModel);
        this.ingredientList.setBounds(10, 50, 370, 400);

        this.suggestRecipeButton = new JButton("Suggest Recipe");
        this.suggestRecipeButton.setBounds(10, 500, 370, 50);

        this.add(ingredientInputLabel);
        this.add(ingredientInputField);
        this.add(addIngredientButton);
        this.add(removeIngredientButton);
        this.add(clearIngredientsButton);
        this.add(suggestRecipeButton);
        this.add(ingredientList);
        updateAllButtonStates();
    }

    public void setActionListeners() {
        this.ingredientList.addListSelectionListener(_ -> updateAllButtonStates());
        this.addIngredientButton.addActionListener(_ -> addIngredient());
        this.ingredientInputField.addActionListener(_ -> addIngredient());
        this.removeIngredientButton.addActionListener(_ -> removeIngredient());
        this.clearIngredientsButton.addActionListener(_ -> clearIngredients());
        this.suggestRecipeButton.addActionListener(_ -> suggestRecipe());
    }

    private void addIngredient() {
        String ingredient = this.ingredientInputField.getText().trim();
        if (!ingredient.isEmpty()) {
            this.ingredientListModel.addElement(ingredient);
            this.ingredientInputField.setText(null); // Clear input field
            updateAllButtonStates();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid ingredient.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void removeIngredient() {
        int[] selectedIndices = this.ingredientList.getSelectedIndices();
        for (int i = selectedIndices.length - 1; i >= 0; i--) {
            this.ingredientListModel.removeElementAt(selectedIndices[i]);
        }
        updateAllButtonStates();
    }

    private void clearIngredients() {
        this.ingredientListModel.clear();
        updateAllButtonStates();
    }

    private void suggestRecipe() {
        ArrayList<String> ingredients = new ArrayList<>();
        for (int i = 0; i < this.ingredientListModel.getSize(); i++) {
            ingredients.add(this.ingredientListModel.getElementAt(i));
        }
        this.suggestedRecipe = new Recipe();
        this.suggestedRecipe.setIngredients(ingredients).suggest();
        setSuggestedRecipe();
    }

    private void updateAllButtonStates() {
        this.removeIngredientButton.setEnabled(!this.ingredientList.isSelectionEmpty());
        this.clearIngredientsButton.setEnabled(!this.ingredientListModel.isEmpty());
        this.suggestRecipeButton.setEnabled(!this.ingredientListModel.isEmpty());
    }

    public Recipe getSuggestedRecipe() {return this.suggestedRecipe;}

    private void setSuggestedRecipe() {this.home.setSuggestedRecipe(this.suggestedRecipe);}
}
