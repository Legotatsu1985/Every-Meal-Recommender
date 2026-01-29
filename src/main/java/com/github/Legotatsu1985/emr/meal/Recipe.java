package com.github.Legotatsu1985.emr.meal;

import com.github.Legotatsu1985.emr.ai.Gemini;
import com.github.Legotatsu1985.emr.util.FileEditor;
import dev.langchain4j.model.chat.request.*;
import dev.langchain4j.model.chat.request.json.*;
import org.jetbrains.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.*;

public class Recipe {
    // Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(Recipe.class);

    // AI model
    private Gemini gemini;
    private ResponseFormat responseFormat;
    private String responseRaw;

    // Recipe data
    private ArrayList<String> requestedIngredients;
    private JsonNode responseRoot;
    private String createdDate;
    private String title;
    private int cookingTimeMinutes;
    private Map<String, Integer> ingredients;
    private List<String> steps;
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

    public Recipe setIngredients(@NotNull List<String> ingredients) {
        this.requestedIngredients.addAll(ingredients);
        return this;
    }

    public void suggest() {
        this.setResponseFormat();
        this.gemini = new Gemini(this.responseFormat);
        String question = String.format(QUESTION_TEMPLATE, String.join(", ", this.requestedIngredients));
        this.responseRaw = this.gemini.ask(question);
        /*FileEditor fe = new FileEditor("src/main/resources/sample_recipe.json");
        this.responseRaw = fe.load().getString();*/
        if (this.responseRaw != null) {
            LOGGER.info("\n{}", this.responseRaw);
            ObjectMapper mapper = new ObjectMapper();
            this.responseRoot = mapper.readTree(this.responseRaw);
            this.setRecipeInfo();
        } else {
            LOGGER.error("Response data is null.");
        }
    }

    public void printResponseRaw() {
        if (this.responseRaw != null) {
            LOGGER.info("\n{}", this.responseRaw);
        } else {
            LOGGER.error("Response data is null.");
        }
    }

    private void setRecipeInfo() {
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
            LOGGER.error("Response data is null.");
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

    public @Nullable String getDate() {return this.createdDate;}
    public String getTitle() {return this.title;}
    public int getCookingTimeMinutes() {return this.cookingTimeMinutes;}
    public Map<String, Integer> getIngredients() {return this.ingredients;}
    public List<String> getSteps() {return this.steps;}
    public int getCalories() {return this.calories;}

    public void setDate(@NotNull String date) {this.createdDate = date;}
    public void setTitle(@NotNull String title) {this.title = title;}
    public void setCookingTimeMinutes(int minutes) {this.cookingTimeMinutes = minutes;}
    public void setIngredients(@NotNull Map<String, Integer> ingredients) {this.ingredients = ingredients;}
    public void setSteps(@NotNull List<String> steps) {this.steps = steps;}
    public void setCalories(int calories) {this.calories = calories;}
}
