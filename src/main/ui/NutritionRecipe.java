package ui;

import model.Ingredient;
import model.Recipe;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Some methods follow the ideas from WorkRoomApp in:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class NutritionRecipe {
    private static final String RECIPE = "./data/Recipe.json";
    ArrayList<Ingredient> ingredients = new ArrayList<>();
    private Recipe recipe1 = new Recipe("MyRecipe", ingredients, "Empty");
    private Scanner in;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the nutrition recipe application
    public NutritionRecipe() {
        in = new Scanner(System.in);;
        jsonWriter = new JsonWriter(RECIPE);
        jsonReader = new JsonReader(RECIPE);
        runRecipe();
    }

    // MODIFIES: this
    // EFFECTS: take and processes user input
    private void runRecipe() {
        boolean status = true;
        String input = null;
        init();
        while (status) {
            displayMenu();
            input = in.nextLine();
            input = input.toLowerCase();

            if (input.equals("q")) {
                status = false;
            } else {
                processCommand(input);
            }
        }
        System.out.println("\nThank you for using the app!");
    }

    // MODIFIES: this
    // EFFECTS: processes the input besides quit
    private void processCommand(String command) {
        if (command.equals("a")) {
            addIngredient();
        } else if (command.equals("v")) {
            viewRecipe();
        } else if (command.equals("p")) {
            addProcedure();
        } else if (command.equals("s")) {
            saveRecipe();
        } else if (command.equals("l")) {
            loadRecipe();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadRecipe() {
        try {
            recipe1 = jsonReader.read();
            System.out.println("Loaded " + recipe1.getName() + " from " + RECIPE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + RECIPE);
        }
    }

    // EFFECTS: saves the workroom to file
    private void saveRecipe() {
        try {
            jsonWriter.open();
            jsonWriter.write(recipe1);
            jsonWriter.close();
            System.out.println("Saved " + recipe1.getName() + " to " + RECIPE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + RECIPE);
        }
    }

    // MODIFIES: this
    // EFFECTS: add procedure to the recipe
    private void addProcedure() {
        System.out.println("Enter the procedure");
        String procedure = in.nextLine();
        recipe1.setProcedure(procedure);
        System.out.println("Procedure successfully set!");
    }

    // MODIFIES: this
    // EFFECTS: print the recipe to the console
    private void viewRecipe() {
        System.out.println("\nProcedure:" + recipe1.getProcedure());
        System.out.println("\nThe nutrition value in gram per portion (in order of carbohydrate, protein, fat)"
                + recipe1.getRecipeNutrition()[0] + " " + recipe1.getRecipeNutrition()[1] + " "
                + recipe1.getRecipeNutrition()[2]);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\nAttention: finish writing the recipe before saving!");
        System.out.println("\ta -> add ingredient");
        System.out.println("\tv -> view recipe");
        System.out.println("\tp -> add procedure");
        System.out.println("\ts -> save recipe to file");
        System.out.println("\tl -> load recipe from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: initializes recipe
    private void init() {
        in = new Scanner(System.in);
        in.useDelimiter("\n");
    }

    // MODIFIES: this
    // EFFECTS: add ingredient one by one to the recipe
    public void addIngredient() {
        boolean check = true;
        while (check) {
            System.out.println("Enter the name of ingredient:");
            String name = in.next();
            System.out.println("Enter the mass of ingredient:");
            double mass = in.nextDouble();
            System.out.println("Enter the nutrition in the format of carbohydrate mass, "
                    + "protein mass, " + "fat mass per 100 g, hit enter after entering each value");
            double carb = in.nextDouble();
            double protein = in.nextDouble();
            double fat = in.nextDouble();
            Ingredient i = new Ingredient(name, mass, carb, protein, fat);
            ingredients.add(i);
            recipe1.calculateRecipeNutrition();
            check = checkContinueAdd();
        }
    }

    // MODIFIES: this
    // EFFECTS: check whether the user want to add more ingredient
    private boolean checkContinueAdd() {
        boolean check;
        String input = "";
        while (!(input.equals("y") || input.equals("n"))) {
            System.out.println("Do you want to add more? Type y to add more, type n to stop adding.");
            input = in.next();
        }
        if (input.equals("y")) {
            check = true;
        } else {
            check = false;
        }
        return check;
    }


}
