package com.github.Legotatsu1985.emr.meal;

import com.github.Legotatsu1985.emr.ai.Gemini;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.chat.request.ResponseFormatType;
import dev.langchain4j.model.chat.request.json.JsonArraySchema;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import dev.langchain4j.model.chat.request.json.JsonSchema;
import org.jetbrains.annotations.NotNull;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Recipe {
    // AI model
    private Gemini gemini;
    private ResponseFormat responseFormat;

    // Recipe data
    private ArrayList<String> requestedIngredients;
    private JsonNode responseRoot;
    private String title;
    private int cookingTimeMinutes;
    private ArrayList<String> ingredients;
    private LinkedList<String> steps;
    private int calories;



    public Recipe(String... requestedIngredients) {
        this.requestedIngredients = new ArrayList<>();
        this.requestedIngredients.addAll(Arrays.asList(requestedIngredients));
    }

    public Recipe setIngredients(@NotNull String... ingredients) {
        this.requestedIngredients.addAll(Arrays.asList(ingredients));
        return this;
    }

    public void suggest() {
        this.setResponseFormat();
        this.gemini = new Gemini(this.responseFormat);
        String question = String.format("Suggest a recipe using the following ingredients: %s. " +
                "Provide the recipe title, cooking time in minutes, list of ingredients, steps, and total calories in JSON format.",
                String.join(", ", this.requestedIngredients));
        String response = this.gemini.ask(question);
        System.out.println(response);
        ObjectMapper mapper = new ObjectMapper();
        this.responseRoot = mapper.readTree(response);

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
                                        .items(JsonObjectSchema.builder()
                                                .addStringProperty("step")
                                                .build())
                                        .build())
                                .addIntegerProperty("calories")
                                .build())
                        .build())
                .build();
    }
}
