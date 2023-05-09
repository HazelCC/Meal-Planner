package model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Some methods follow the ideas from WorkRoom in:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Recipe collects all the ingredients needed for a specific dish, and record the procedure to cook it. It also
//calculates the nutritional value in this specific dish

public class Recipe implements Writable {
    private String name;
    private ArrayList<Ingredient> ingredients;
    private String procedure = "Empty";
    private double[] recipeNutrition = new double[3];
    private double carbohydrateMass = 0;
    private double proteinMass = 0;
    private double fatMass = 0;



    public Recipe(String name, ArrayList<Ingredient> ingredients, String procedure) {
        this.name = name;
        this.ingredients = ingredients;
        this.procedure = procedure;
        this.calculateRecipeNutrition();
    }

    /*
     * MODIFIES: this
     * EFFECTS: add the ingredient to the recipe, log the change to the eventLog
     */
    public void addIngredient(Ingredient i) {
        ingredients.add(i);
        EventLog.getInstance().logEvent(new Event("Add ingredient to the recipe"));
    }

    /*
     * REQUIRES: ingredients is not empty
     * MODIFIES: this
     * EFFECTS: calculate the rounded nutritional value of the recipe. Store the result into recipeNutrition
     */
    public void calculateRecipeNutrition() {
        carbohydrateMass = 0;
        proteinMass = 0;
        fatMass = 0;
        try {
            for (int i = 0; i < ingredients.size(); i++) {
                double[] current = ingredients.get(i).getIngredientNutrition();
                carbohydrateMass += current[0];
                proteinMass += current[1];
                fatMass += current[2];
            }
        } catch (Exception e) {
            System.out.println("Cannot Calculate");
        }
        recipeNutrition[0] = Math.round(carbohydrateMass);
        recipeNutrition[1] = Math.round(proteinMass);
        recipeNutrition[2] = Math.round(fatMass);


    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }


    public String getProcedure() {
        return procedure;
    }

    // MODIFIES: this
    // EFFECTS: set the procedure to the string, log the update to the eventLog
    public void setProcedure(String procedure) {
        this.procedure = procedure;
        EventLog.getInstance().logEvent(new Event("Update the procedure"));
    }



    public double[] getRecipeNutrition() {
        calculateRecipeNutrition();
        return recipeNutrition;
    }


    public String getName() {
        return name;
    }

/*
     * EFFECTS: create a JSONObject based on the given value
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("name", name);
            json.put("Ingredients", ingredientsToJson());
            json.put("procedure", procedure);
            json.put("Recipe Nutrition", recipeNutrition);
            json.put("carbohydrate mass", carbohydrateMass);
            json.put("protein mass", proteinMass);
            json.put("fat mass", fatMass);
        } catch (JSONException e) {
            System.out.println("Incomplete object");
        }


        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray ingredientsToJson() {
        JSONArray jsonArray = new JSONArray();
        try {
            for (Ingredient ingredient : ingredients) {
                jsonArray.put(ingredient.toJson());
            }
        } catch (Exception e) {
            System.out.println("Cannot create array");
        }

        return jsonArray;
    }


}



