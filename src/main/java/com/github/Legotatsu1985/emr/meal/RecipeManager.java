package com.github.Legotatsu1985.emr.meal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.Legotatsu1985.emr.util.JsonManager;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecipeManager extends JsonManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeManager.class);
    private static final String RECIPES_JSON_PATH = "src/main/resources/saved_recipes.json";

    private Recipe recipe;

    public RecipeManager(@NotNull Recipe recipe) {
        super(RECIPES_JSON_PATH, null);
    }

    private void parseRecipe() {
        // Set the recipe data to memorizable JSON string.

    }
}
