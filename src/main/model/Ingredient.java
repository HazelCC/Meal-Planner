package model;

import persistence.Writable;
import org.json.JSONObject;

// Some methods follow the ideas from Thingy in:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// represent an ingredient that can be added to a recipe, with its name, mass, and nutrition value in percentage.
// Then, the total nutritional value in the portion of the user's input will be calculated.
public class Ingredient implements Writable {
    private String ingredientName;
    private double mass;
    private double carbohydrateLevel;
    private double proteinLevel;
    private double fatLevel;
    private double[] ingredientNutrition = new double[3];


    /*/
     * REQUIRES: ingredientName is not null; mass, carbohydratePercentage, proteinPercentage, and
     * fatPercentage >= 0
     * EFFECTS: construct an Ingredient with name, mass, and nutrition value
     */
    public Ingredient(String ingredientName, double mass, double carbohydrateLevel, double proteinLevel,
                      double fatLevel) {
        this.ingredientName = ingredientName;
        this.mass = mass;
        this.carbohydrateLevel = carbohydrateLevel;
        this.proteinLevel = proteinLevel;
        this.fatLevel = fatLevel;
        this.calculateIngredientNutrition();

    }

    /*
     * REQUIRES: ingredientName has a non-zero length; mass, carbohydratePercentage, proteinPercentage, and
     * fatPercentage >= 0
     * MODIFIES: this
     * EFFECTS: calculate the nutrition value of the ingredient and assign the result to ingredientNutrition, in the
     * order of mass of carbohydrate, protein, and fat
     */
    public void calculateIngredientNutrition() {
        double ingredientCarbohydrateMass = (mass / 100.0 * carbohydrateLevel);
        double ingredientProteinMass = mass / 100.0 * proteinLevel;
        double ingredientFatMass = mass / 100.0 * fatLevel;
        ingredientNutrition[0] = ingredientCarbohydrateMass;
        ingredientNutrition[1] = ingredientProteinMass;
        ingredientNutrition[2] = ingredientFatMass;
    }

    public String getIngredientName() {
        return ingredientName;
    }


    public double[] getIngredientNutrition() {
        return ingredientNutrition;
    }


    public double getMass() {
        return mass;
    }


    public double getCarbohydrateLevel() {
        return carbohydrateLevel;
    }

    public double getProteinLevel() {
        return proteinLevel;
    }

    public double getFatLevel() {
        return fatLevel;
    }

    public String getName() {
        return ingredientName;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", ingredientName);
        json.put("mass", mass);
        json.put("carbohydrate level", carbohydrateLevel);
        json.put("protein level", proteinLevel);
        json.put("fat level", fatLevel);
        json.put("ingredient nutrition", ingredientNutrition);


        return json;
    }

}


