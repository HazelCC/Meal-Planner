package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class RecipeTest {
    Recipe recipe;
    @BeforeEach
    public void setup(){
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        Ingredient beef = new Ingredient("beef", 200, 2, 20, 4);
        Ingredient lamb = new Ingredient("lamb", 100, 2, 20, 4);
        ingredients.add(beef);
        ingredients.add(lamb);
        recipe = new Recipe("beefAndLamb",ingredients, "cook procedure");}


    @Test
    public void testRecipe(){

        assertEquals("beefAndLamb", recipe.getName());
        assertEquals("cook procedure", recipe.getProcedure());
        assertEquals(2, recipe.getIngredients().size());

    }

    @Test
    public void testCalculateNutrition(){
        assertEquals(6, recipe.getRecipeNutrition()[0]);
        assertEquals(60, recipe.getRecipeNutrition()[1]);
        assertEquals(12, recipe.getRecipeNutrition()[2]);
    }

}
