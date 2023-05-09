package ui;

import javax.swing.*;
import java.awt.*;

public class IngredientPanel {
    JFrame frame;
    JPanel panel;

    // Text Field
    JTextField ingredientName;
    JTextField mass;
    JTextField carbohydrate;
    JTextField protein;
    JTextField fat;

    // Label
    JLabel nameLabel;
    JLabel massLabel;
    JLabel carbohydrateLabel;
    JLabel proteinLabel;
    JLabel fatLabel;

    // EFFECTS: creates new meal creation window
    public IngredientPanel() {
        initializeFields();

        panel.add(nameLabel);
        panel.add(ingredientName);

        panel.add(massLabel);
        panel.add(mass);

        panel.add(carbohydrateLabel);
        panel.add(carbohydrate);

        panel.add(proteinLabel);
        panel.add(protein);

        panel.add(fatLabel);
        panel.add(fat);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setMinimumSize(new Dimension(250,250));
        panel.setPreferredSize(new Dimension(250,250));
    }

    // EFFECTS: initializes creation fields of a new ingredient
    private void initializeFields() {
        frame = new JFrame("New Ingredient");
        panel = new JPanel();

        nameLabel = new JLabel("Enter Name: ");
        massLabel = new JLabel("Enter Mass: ");
        carbohydrateLabel = new JLabel("Enter Carbohydrate: ");
        proteinLabel = new JLabel("Enter Protein: ");
        fatLabel = new JLabel("Enter Fat: ");

        ingredientName = new JTextField();
        ingredientName.setMaximumSize(new Dimension(300, 10));

        mass = new JTextField();
        mass.setMaximumSize(new Dimension(300, 20));

        carbohydrate = new JTextField();
        mass.setMaximumSize(new Dimension(300, 20));

        protein = new JTextField();
        mass.setMaximumSize(new Dimension(300, 20));

        fat = new JTextField();
        mass.setMaximumSize(new Dimension(300, 20));


    }

    public JPanel returnJPanel() {
        return panel;
    }

    public String getIngredientName() {
        return ingredientName.getText();
    }

    public int getMass() {
        return Integer.parseInt(mass.getText());
    }

    public double getCarbohydrate() {
        return Double.parseDouble(carbohydrate.getText());
    }

    public double getProtein() {
        return Double.parseDouble(protein.getText());
    }

    public double getFat() {
        return Double.parseDouble(fat.getText());
    }







}
