package com.github.Legotatsu1985.emr.meal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

public class RecipeManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeManager.class);
    private static final String RECIPES_JSON_PATH = "saved_recipes.jsonl";

    private Recipe recipe;

    private LinkedList<Recipe> loadedRecipes;

    public RecipeManager() {this.loadedRecipes = new LinkedList<>();}

    public RecipeManager(@NotNull Recipe recipe) {this.recipe = recipe;}

    public void saveRecipe(boolean append) {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Recipe.class, new RecipeSerializer());
        mapper.registerModule(module);

        // mapper.enable(SerializationFeature.INDENT_OUTPUT);

        /*try (FileWriter fw = new FileWriter(RECIPES_JSON_PATH, append)) {
            String recipeJson = mapper.writeValueAsString(this.recipe);
            fw.write(recipeJson + "\n");
        } catch (IOException e) {
            LOGGER.error("Failed to save recipe: {}", e.getMessage());
        }*/
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RECIPES_JSON_PATH, append))) {
            String recipeJson = mapper.writeValueAsString(this.recipe);
            bw.write(recipeJson + "\n");
        } catch (IOException e) {
            LOGGER.error("Failed to save recipe: {}", e.getMessage());
        }
    }

    public RecipeManager loadAll() {
        if (Files.exists(Paths.get(RECIPES_JSON_PATH))) {
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(Recipe.class, new RecipeDeserializer());
            mapper.registerModule(module);

            File recipesFile = new File(RECIPES_JSON_PATH);
            if (!this.loadedRecipes.isEmpty()) {
                this.loadedRecipes.clear();
            }
            try (BufferedReader br = new BufferedReader(new FileReader(recipesFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    Recipe loadedRecipe = mapper.readValue(line, Recipe.class);
                    this.loadedRecipes.add(loadedRecipe);
                }
            } catch (IOException e) {
                LOGGER.error("Failed to load recipes: {}", e.getMessage());
            }
        } else {
            LOGGER.warn("No recipes file found.");
        }
        return this;
    }

    public LinkedList<Recipe> getLoadedRecipes() {return this.loadedRecipes;}
}
