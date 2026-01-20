package com.github.Legotatsu1985.emr;

import com.github.Legotatsu1985.emr.meal.Recipe;
import com.github.Legotatsu1985.emr.util.Config;

public class App {
    public static Config CFG;

    static void main() {
        CFG = new Config(); // Load app configuration

        Recipe recipe = new Recipe();
        recipe.setIngredients("egg", "bread", "mayonnaise", "ketchup").suggest();
        recipe.setRecipeInfo();
        recipe.printResponseRaw();
    }
}
