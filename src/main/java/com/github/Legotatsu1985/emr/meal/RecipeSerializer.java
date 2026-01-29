package com.github.Legotatsu1985.emr.meal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

public class RecipeSerializer extends StdSerializer<Recipe> {
    public RecipeSerializer() {super(Recipe.class);}

    @Override
    public void serialize(Recipe recipe, JsonGenerator gen, SerializerProvider provider) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        gen.writeStartObject();
        gen.writeStringField("date", now.toString()); // To avoid duplicate recipe entries
        gen.writeNumberField("calories", recipe.getCalories()); // Total calories
        gen.writeNumberField("cooking_time_minutes", recipe.getCookingTimeMinutes()); // Cooking time in minutes
        gen.writeArrayFieldStart("ingredients"); // Ingredients list
        for (Map.Entry<String, Integer> entry : recipe.getIngredients().entrySet()) {
            gen.writeStartObject();
            gen.writeNumberField("count", entry.getValue());
            gen.writeStringField("ingredient", entry.getKey());
            gen.writeEndObject();
        }
        gen.writeEndArray();
        gen.writeStringField("recipe", recipe.getTitle());
        gen.writeArrayFieldStart("steps");
        for (String step : recipe.getSteps()) {
            gen.writeString(step);
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }
}
