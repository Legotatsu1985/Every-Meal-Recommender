package com.github.Legotatsu1985.emr.meal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RecipeDeserializer extends StdDeserializer<Recipe> {
    public RecipeDeserializer() {super(Recipe.class);}

    @Override
    public Recipe deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        Recipe recipe = new Recipe();
        recipe.setDate(node.get("date").asText());
        recipe.setTitle(node.get("recipe").asText());
        recipe.setCalories(node.get("calories").asInt());
        recipe.setCookingTimeMinutes(node.get("cooking_time_minutes").asInt());
        Map<String, Integer> ingredients = new HashMap<>();
        for (JsonNode ingredientNode : node.get("ingredients")) {
            String ingredient = ingredientNode.get("ingredient").asText();
            int count = ingredientNode.get("count").asInt();
            ingredients.put(ingredient, count);
        }
        recipe.setIngredients(ingredients);
        List<String> steps = new LinkedList<>();
        for (JsonNode stepNode : node.get("steps")) {
            steps.add(stepNode.asText());
        }
        recipe.setSteps(steps);
        return recipe;
    }
}
