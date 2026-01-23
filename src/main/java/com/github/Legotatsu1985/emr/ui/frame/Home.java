package com.github.Legotatsu1985.emr.ui.frame;

import com.github.Legotatsu1985.emr.ui.Actions;
import com.github.Legotatsu1985.emr.ui.component.HomeMenuBar;

import javax.swing.*;
import java.awt.*;

public class Home extends JFrame implements Actions {
    private JPanel basePanel;
    private JPanel resultPanel;
    private HomeMenuBar menuBar;
    private JLabel ingredientInputLabel;
    private JTextField ingredientInputField;
    private JList<String> ingredientList;
    private DefaultListModel<String> ingredientListModel;
    private JButton addIngredientButton;
    private JButton removeIngredientButton; // Only enabled when at least one ingredient is selected.
    private JButton clearIngredientsButton; // Only enabled when at least one ingredient is in the list.
    private JButton suggestRecipeButton; // Only enabled when at least one ingredient is in the list.

    private JLabel resultTempLabel;

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

        // Result Panel
        this.resultPanel = new JPanel();
        this.resultPanel.setLayout(null);
        this.resultPanel.setBounds(390, 10, 390, 540);
        this.resultPanel.setBackground(Color.ORANGE);
        this.resultPanel.setEnabled(false);

        this.resultTempLabel = new JLabel("RESULT");
        this.resultTempLabel.setBounds(140, 260, 100, 30);
        this.resultTempLabel.setForeground(Color.BLACK);
        this.resultTempLabel.setFont(new Font("Arial", Font.BOLD, 24));
        this.resultTempLabel.setEnabled(false);

        // Menu Bar
        this.menuBar = new HomeMenuBar();

        // Ingredient Input Label
        this.ingredientInputLabel = new JLabel("Ingredient you wish to use:");
        this.ingredientInputLabel.setBounds(10, 10, 200, 30);

        // Ingredient Input Field
        this.ingredientInputField = new JTextField();
        this.ingredientInputField.setBounds(160, 10, 150, 30);
        this.ingredientInputField.setToolTipText("Enter an ingredient you want to request.");

        // Ingredient Add Button
        this.addIngredientButton = new JButton("Add");
        this.addIngredientButton.setBounds(320, 10, 60, 30);

        // Ingredient Remove Button
        this.removeIngredientButton = new JButton("Remove Selected");
        this.removeIngredientButton.setBounds(10, 460, 180, 30);

        // Ingredient Clear Button
        this.clearIngredientsButton = new JButton("Clear All");
        this.clearIngredientsButton.setBounds(200, 460, 180, 30);

        // Ingredient List
        this.ingredientListModel = new DefaultListModel<>();
        this.ingredientList = new JList<>(ingredientListModel);
        this.ingredientList.setBounds(10, 50, 370, 400);

        // Suggest Recipe Button
        this.suggestRecipeButton = new JButton("Suggest Recipe");
        this.suggestRecipeButton.setBounds(10, 500, 370, 50);

        // Set layout and add components
        updateAllButtonStates();
        this.setJMenuBar(this.menuBar);
        this.basePanel.add(this.ingredientInputLabel);
        this.basePanel.add(this.ingredientInputField);
        this.basePanel.add(this.addIngredientButton);
        this.basePanel.add(this.removeIngredientButton);
        this.basePanel.add(this.clearIngredientsButton);
        this.basePanel.add(this.suggestRecipeButton);
        this.basePanel.add(this.ingredientList);
        this.basePanel.add(this.resultPanel);
        this.resultPanel.add(this.resultTempLabel);
        this.add(this.basePanel);
    }

    // Setting action listeners for all components
    public void setActionListeners() {
        this.ingredientList.addListSelectionListener(_ -> updateAllButtonStates());
        this.addIngredientButton.addActionListener(_ -> addIngredient());
        this.ingredientInputField.addActionListener(_ -> addIngredient());
        this.removeIngredientButton.addActionListener(_ -> removeIngredient());
        this.clearIngredientsButton.addActionListener(_ -> clearIngredients());
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
        int[] selectedIndexes = this.ingredientList.getSelectedIndices();
        for (int i = selectedIndexes.length - 1; i >= 0; i--) {
            this.ingredientListModel.removeElementAt(selectedIndexes[i]);
        }
        updateAllButtonStates();
    }

    private void clearIngredients() {
        this.ingredientListModel.clear();
        updateAllButtonStates();
    }

    private void setRemoveIngredientButtonState() {this.removeIngredientButton.setEnabled(!this.ingredientList.isSelectionEmpty());}

    private void setClearIngredientsButtonState() {this.clearIngredientsButton.setEnabled(!this.ingredientListModel.isEmpty());}

    private void setSuggestRecipeButtonState() {this.suggestRecipeButton.setEnabled(!this.ingredientListModel.isEmpty());}

    private void updateAllButtonStates() {
        setRemoveIngredientButtonState();
        setClearIngredientsButtonState();
        setSuggestRecipeButtonState();
    }
}
