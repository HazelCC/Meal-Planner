package ui;

import javax.swing.*;
import java.awt.*;

public class ProcedurePanel {
    JFrame frame;
    JPanel panel;
    JTextField procedure;
    JLabel procedureLabel;


    // EFFECTS: creates new procedure window
    public ProcedurePanel() {
        initializeFields();
        panel.add(procedureLabel);
        panel.add(procedure);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setMinimumSize(new Dimension(250,200));
        panel.setPreferredSize(new Dimension(250,200));
    }

    // EFFECTS: initializes procedure creation fields
    private void initializeFields() {
        frame = new JFrame("Add ingredient: ");
        panel = new JPanel();
        procedureLabel = new JLabel("Enter procedure:");

        procedure = new JTextField();
        procedure.setMaximumSize(new Dimension(300, 25));
    }

    public JPanel returnJPanel() {
        return panel;
    }

    // EFFECTS: retrieves meal name entered
    public String getProcedure() {
        return procedure.getText();
    }







}
