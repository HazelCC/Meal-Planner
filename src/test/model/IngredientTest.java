package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IngredientTest {
    Ingredient ingredient;
    @BeforeEach
    void setup(){
        ingredient = new Ingredient("beef", 100, 2,
                20,4);
    }



    @Test
    void testIngredient() {
        assertEquals("beef",ingredient.getIngredientName());
        assertEquals(100, ingredient.getMass());
        assertEquals(2, ingredient.getCarbohydrateLevel());
        assertEquals(20, ingredient.getProteinLevel());
        assertEquals(4, ingredient.getFatLevel());

    }

    @Test
    void testCalculateIngredientNutrition() {
        ingredient.calculateIngredientNutrition();
        assertEquals(2, ingredient.getIngredientNutrition()[0]);
        assertEquals(20, ingredient.getIngredientNutrition()[1]);
        assertEquals(4, ingredient.getIngredientNutrition()[2]);
    }


}