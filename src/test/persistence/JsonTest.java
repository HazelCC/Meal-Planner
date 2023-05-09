package persistence;

import model.*;


import static org.junit.jupiter.api.Assertions.assertEquals;

// Some methods follow the ideas from JsonTest in:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonTest {
    protected void checkIngredient(String name, Ingredient ingredient) {
        assertEquals(name, ingredient.getName());
    }
}
