package persistence;

import model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Some methods follow the ideas from JsonReaderTest in:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
class JsonReaderTest extends JsonTest {

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
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Recipe recipe = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyRecipe() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyRecipe.json");
        try {
            Recipe recipe = reader.read();
            assertEquals("Empty", recipe.getName());
            assertEquals("Empty", recipe.getProcedure());
            assertEquals(0, recipe.getIngredients().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralRecipe() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralRecipe.json");
        try {
            Recipe recipe = reader.read();
            assertEquals("beefAndLamb", recipe.getName());
            List<Ingredient> ingredients = recipe.getIngredients();
            assertEquals(2, ingredients.size());
            checkIngredient("beef", ingredients.get(0));
            checkIngredient("lamb", ingredients.get(1));
            assertEquals(6, recipe.getRecipeNutrition()[0]);
            assertEquals(60, recipe.getRecipeNutrition()[1]);
            assertEquals(12, recipe.getRecipeNutrition()[2]);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}