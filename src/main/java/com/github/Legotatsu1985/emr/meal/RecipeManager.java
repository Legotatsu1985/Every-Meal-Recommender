package com.github.Legotatsu1985.emr.meal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RecipeManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeManager.class);
    private static final String RECIPES_JSON_PATH = "saved_recipes.jsonl";

    private Recipe recipe;

    public RecipeManager(@NotNull Recipe recipe) {
        this.recipe = recipe;
    }

    public void saveRecipe(boolean append) {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Recipe.class, new RecipeSerializer());
        mapper.registerModule(module);

        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RECIPES_JSON_PATH, append))) {
            String jsonLine = mapper.writeValueAsString(this.recipe);
            bw.write(jsonLine + "\n");
            LOGGER.info("Recipe saved successfully.");
        } catch (IOException e) {
            LOGGER.error("Failed to save recipe: {}", e.getMessage());
        }
    }
}
