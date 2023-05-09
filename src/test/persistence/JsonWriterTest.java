package persistence;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Some methods follow the ideas from JsonWriterTest in:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

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
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyRecipe() {
        try {
            Recipe emptyRecipe = new Recipe("Empty",null, "Empty");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyRecipe.json");
            writer.open();
            writer.write(emptyRecipe);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyRecipe.json");
            emptyRecipe = reader.read();
            assertEquals("Empty", emptyRecipe.getName());
            assertEquals(0, emptyRecipe.getIngredients().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralRecipe() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralRecipe.json");
            writer.open();
            writer.write(recipe);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralRecipe.json");
            recipe = reader.read();
            assertEquals("beefAndLamb", recipe.getName());
            List<Ingredient> ingredients = recipe.getIngredients();
            assertEquals(2, ingredients.size());
            checkIngredient("beef", ingredients.get(0));
            checkIngredient("lamb", ingredients.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}