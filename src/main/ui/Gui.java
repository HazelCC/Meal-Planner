package ui;

import model.*;

import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

// The class represent the graphic user interface of the recipe application
public class Gui extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/Recipe.json";
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private DefaultListModel<Ingredient> ingredientDefaultListModel;
    private DefaultListModel<String> ingredientNameDefaultListModel;
    private JList<String> ingredientNameList;
    private JLabel procedure;
    private JLabel totalProtein;
    private JLabel totalCarbohydrate;
    private JLabel totalFat;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private Recipe recipe;

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: Create and set up the main application window.
    public Gui() throws IOException {
        super("My Recipe");
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initializeData();
        initializeHeader();
        initializeScreen();
        initializeButtons();
        pack();
        setVisible(true);

        exit();
    }

    // MODIFIES: this
    // EFFECTS: add image to the selected panel
    private static void addImageToPanel(JPanel procedurePanel) throws IOException {
        BufferedImage myPicture = ImageIO.read(new File("./data/cookingImage.jpeg"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        picLabel.setMinimumSize(new Dimension(30, 30));
        procedurePanel.add(picLabel);
    }

    // EFFECTS: perform actions that are shown after exiting the app
    private void exit() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                printLog(EventLog.getInstance());
                dispose();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initializes IO, data, and list models, initialize the default recipe with empty procedure
    private void initializeData() {
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        recipe = new Recipe("MyRecipe", ingredients, "Empty");
        ingredientDefaultListModel = new DefaultListModel<>();
        ingredientNameDefaultListModel = new DefaultListModel<>();

    }

    // MODIFIES: this
    // EFFECTS: initializes header pane for date and calories
    private void initializeHeader() {
        JPanel headerPane = new JPanel();
        headerPane.setLayout(new FlowLayout());

        JPanel proteinPanel = initializeProteinPane();
        JPanel carbohydratePanel = initializeCarbohydratePane();
        JPanel fatPanel = initializeFatPane();

        headerPane.add(carbohydratePanel);
        headerPane.add(Box.createHorizontalStrut(20));
        headerPane.add(proteinPanel);
        headerPane.add(Box.createHorizontalStrut(20));
        headerPane.add(fatPanel);

        headerPane.setBackground(Color.CYAN);
        add(headerPane, BorderLayout.PAGE_START);
    }

    // MODIFIES: this
    // EFFECTS: initializes carbohydrate panel in header
    private JPanel initializeCarbohydratePane() {
        totalCarbohydrate = new JLabel("Carbohydrate: " + recipe.getRecipeNutrition()[0]);
        JPanel carbohydratePane = new JPanel();
        carbohydratePane.add(totalCarbohydrate);
        carbohydratePane.setPreferredSize(new Dimension(150, 30));
        carbohydratePane.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        return carbohydratePane;
    }

    // MODIFIES: this
    // EFFECTS: initializes protein panel in header
    private JPanel initializeProteinPane() {
        totalProtein = new JLabel("Protein: " + recipe.getRecipeNutrition()[1]);
        JPanel proteinPane = new JPanel();
        proteinPane.add(totalProtein);
        proteinPane.setPreferredSize(new Dimension(150, 30));
        proteinPane.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        return proteinPane;
    }

    // MODIFIES: this
    // EFFECTS: initializes fat panel in header
    private JPanel initializeFatPane() {
        totalFat = new JLabel("Fat: " + recipe.getRecipeNutrition()[2]);
        JPanel fatPane = new JPanel();
        fatPane.add(totalFat);
        fatPane.setPreferredSize(new Dimension(150, 30));
        fatPane.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        return fatPane;
    }

    // MODIFIES: this
    // EFFECTS: initializes middle split pane for ingredients and procedure
    private void initializeScreen() throws IOException {
        JSplitPane recipePane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        recipePane.setOneTouchExpandable(true);
        recipePane.setDividerLocation(250);
        JScrollPane setupIngredientPane = setupIngredientPane();
        JPanel procedurePanel = setupProcedurePane();
        addImageToPanel(procedurePanel);
        recipePane.add(setupIngredientPane);
        recipePane.add(procedurePanel);
        recipePane.setMinimumSize(new Dimension(300, 300));
        recipePane.setPreferredSize(new Dimension(500, 300));
        recipePane.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        add(recipePane, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: setup configurations for procedure pane
    private JPanel setupProcedurePane() {
        procedure = new JLabel("Procedure: " + recipe.getProcedure());
        JPanel procedurePane = new JPanel();
        procedurePane.add(procedure);
        procedurePane.setPreferredSize(new Dimension(150, 300));
        procedurePane.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        return procedurePane;
    }

    // MODIFIES: this
    // EFFECTS: setup configurations for ingredient pane
    private JScrollPane setupIngredientPane() {
        ingredientNameList = new JList<>(ingredientNameDefaultListModel);
        JScrollPane ingredientScrollPane = new JScrollPane(ingredientNameList);
        ingredientScrollPane.createVerticalScrollBar();
        ingredientScrollPane.setHorizontalScrollBar(null);
        return ingredientScrollPane;
    }

    // MODIFIES: this
    // EFFECTS: initializes buttons
    private void initializeButtons() {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout());

        JButton addIngredient = new JButton("Add Ingredient");
        addIngredient.setActionCommand("Add");
        addIngredient.addActionListener(this);

        JButton addProcedure = new JButton("Add Procedure");
        addProcedure.setActionCommand("Procedure");
        addProcedure.addActionListener(this);

        JButton saveData = new JButton("Save Data");
        saveData.setActionCommand("Save");
        saveData.addActionListener(this);

        JButton loadData = new JButton("Load Data");
        loadData.setActionCommand("Load");
        loadData.addActionListener(this);

        buttonPane.add(addIngredient);
        buttonPane.add(addProcedure);
        buttonPane.add(saveData);
        buttonPane.add(loadData);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    // MODIFIES: this
    // EFFECTS: updates ingredients on screen
    private void updateIngredient() {
        ingredientDefaultListModel.clear();
        ingredientNameDefaultListModel.clear();
        List<Ingredient> ingredients = recipe.getIngredients();
        for (Ingredient ingredient : ingredients) {
            ingredientDefaultListModel.addElement(ingredient);
            ingredientNameDefaultListModel.addElement(ingredient.getName());
        }
        updateNutrition();

    }

    // MODIFIES: this
    // EFFECTS: updates nutrition value, display the change on the screen
    private void updateNutrition() {
        double[] newNutrition = recipe.getRecipeNutrition();
        totalCarbohydrate.setText("Carbohydrate: " + newNutrition[0]);
        totalProtein.setText("Protein: " + newNutrition[1]);
        totalFat.setText("Fat: " + newNutrition[2]);
    }

    // MODIFIES: this
    // EFFECTS: update procedure, display the change on the screen
    private void updateProcedure() {
        String newProcedure = recipe.getProcedure();
        procedure.setText("Procedure: " + newProcedure);
    }

    // EFFECTS: make the following action command cause the corresponding operations
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Add":
                addIngredientAction();
                break;
            case "Procedure":
                addProcedureAction();
                break;
            case "Save":
                saveRecipeData();
            case "Load":
                loadRecipeData();
        }
    }

    // MODIFIES: this
    // EFFECTS: load the data into the recipe
    private void loadRecipeData() {
        try {
            recipe = jsonReader.read();
            updateIngredient();
        } catch (IOException e) {
            System.out.println("Unable to read from file " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: save the recipe data
    private void saveRecipeData() {
        try {
            jsonWriter.open();
            jsonWriter.write(recipe);
            jsonWriter.close();
            System.out.println("Recipe is saved!");
        } catch (IOException e) {
            System.out.println("IOException!");
        } catch (Exception e) {
            System.out.println("General exception!");
        }
    }


    // MODIFIES: this
    // EFFECTS: adds ingredient to the recipe is one is in the input end
    private void addIngredientAction() {
        IngredientPanel ingredientPanel = new IngredientPanel();
        JPanel panel = ingredientPanel.returnJPanel();

        int optionPaneValue = JOptionPane.showConfirmDialog(
                null, panel,
                "Create Ingredient",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);

        if (optionPaneValue == JOptionPane.OK_OPTION) {
            try {
                String ingredientName = ingredientPanel.getIngredientName();
                double mass = ingredientPanel.getMass();
                double carbohydrate = ingredientPanel.getCarbohydrate();
                double protein = ingredientPanel.getProtein();
                double fat = ingredientPanel.getFat();
                Ingredient ingredient = new Ingredient(ingredientName, mass, carbohydrate, protein, fat);
                recipe.addIngredient(ingredient);
                updateIngredient();
            } catch (Exception e) {
                System.out.println("Invalid!");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds procedure to the recipe is one is in the input end
    private void addProcedureAction() {
        ProcedurePanel procedurePanel = new ProcedurePanel();
        JPanel panel = procedurePanel.returnJPanel();

        int optionPaneValue = JOptionPane.showConfirmDialog(
                null, panel,
                "Add procedure",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);

        if (optionPaneValue == JOptionPane.OK_OPTION) {
            try {
                String procedure = procedurePanel.getProcedure();
                recipe.setProcedure(procedure);
                updateProcedure();
            } catch (Exception e) {
                System.out.println("Invalid!");
            }
        }

    }

    //EFFECTS: print the event log to the console
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString() + "\n\n");
        }
    }
}
