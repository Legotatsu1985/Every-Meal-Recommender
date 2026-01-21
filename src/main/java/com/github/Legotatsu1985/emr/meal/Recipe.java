package com.github.Legotatsu1985.emr.meal;

import com.github.Legotatsu1985.emr.ai.Gemini;
import dev.langchain4j.model.chat.request.*;
import dev.langchain4j.model.chat.request.json.*;
import org.jetbrains.annotations.*;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.*;

public class Recipe {
    // AI model
    private Gemini gemini;
    private ResponseFormat responseFormat;
    private String responseRaw;

    // Recipe data
    private ArrayList<String> requestedIngredients;
    private JsonNode responseRoot;
    private String title;
    private int cookingTimeMinutes;
    private Map<String, Integer> ingredients;
    private LinkedList<String> steps;
    private int calories;

    private static final String QUESTION_TEMPLATE = "Suggest a recipe using the following ingredients: %s. " +
            "Provide the recipe title, cooking time in minutes, list of ingredients, steps, and total calories in JSON format.";

    public Recipe() {
        this.requestedIngredients = new ArrayList<>();
        this.ingredients = new HashMap<>();
        this.steps = new LinkedList<>();
    }

    public Recipe setIngredients(@NotNull String... ingredients) {
        this.requestedIngredients.addAll(Arrays.asList(ingredients));
        return this;
    }

    public void suggest() {
        this.setResponseFormat();
        this.gemini = new Gemini(this.responseFormat);
        String question = String.format(QUESTION_TEMPLATE, String.join(", ", this.requestedIngredients));
        this.responseRaw = this.gemini.ask(question);
        ObjectMapper mapper = new ObjectMapper();
        this.responseRoot = mapper.readTree(this.responseRaw);
    }

    public void printResponseRaw() {
        if (this.responseRaw != null) {
            System.out.println(this.responseRaw);
        } else {
            System.err.println("Response data is null.");
        }
    }

    public void setRecipeInfo() {
        if (this.responseRoot != null) {
            // Set Nodes
            JsonNode ingredients = this.responseRoot.path("ingredients");
            JsonNode steps = this.responseRoot.path("steps");

            // Set data
            this.title = this.responseRoot.get("recipe").asString();
            this.calories = this.responseRoot.get("calories").asInt();
            this.cookingTimeMinutes = this.responseRoot.get("cooking_time_minutes").asInt();

            for (JsonNode ing : ingredients) {
                String ingName = ing.get("ingredient").asString();
                int ingCount = ing.get("count").asInt();
                this.ingredients.put(ingName, ingCount);
            }

            for (JsonNode step : steps) {
                this.steps.add(step.asString());
            }
        } else {
            System.err.println("Response data is not available. Please call suggest() first.");
        }
    }

    private void setResponseFormat() {
        this.responseFormat = ResponseFormat.builder()
                .type(ResponseFormatType.JSON)
                .jsonSchema(JsonSchema.builder()
                        .rootElement(JsonObjectSchema.builder()
                                .addStringProperty("recipe")
                                .addIntegerProperty("cooking_time_minutes")
                                .addProperty("ingredients", JsonArraySchema.builder()
                                        .items(JsonObjectSchema.builder()
                                                .addStringProperty("ingredient")
                                                .addIntegerProperty("count")
                                                .build())
                                        .build())
                                .addProperty("steps", JsonArraySchema.builder()
                                        .items(new JsonStringSchema())
                                        .build())
                                .addIntegerProperty("calories")
                                .build())
                        .build())
                .build();
    }
}
