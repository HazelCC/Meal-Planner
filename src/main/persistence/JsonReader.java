package persistence;

import model.Ingredient;
import model.Recipe;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;
// Some methods follow the ideas from JsonReader in:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a reader that reads recipe from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads recipe from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Recipe read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseRecipe(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses recipe from JSON object and returns it
    private Recipe parseRecipe(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String procedure = jsonObject.getString("procedure");
        Recipe recipe = new Recipe(name, new ArrayList<>(), procedure);
        addIngredients(recipe, jsonObject);
        recipe.calculateRecipeNutrition();
        return recipe;
    }

    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addIngredients(Recipe recipe, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Ingredients");
        for (Object json : jsonArray) {
            JSONObject nextIngredient = (JSONObject) json;
            addIngredient(recipe, nextIngredient);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses ingredient from JSON object and adds it to workroom
    private void addIngredient(Recipe recipe, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double mass = jsonObject.getDouble("mass");
        double carbohydrateLevel = jsonObject.getDouble("carbohydrate level");
        double proteinLevel = jsonObject.getDouble("protein level");
        double fatLevel = jsonObject.getDouble("fat level");
        Ingredient ingredient = new Ingredient(name, mass, carbohydrateLevel, proteinLevel, fatLevel);
        recipe.addIngredient(ingredient);
    }
}
