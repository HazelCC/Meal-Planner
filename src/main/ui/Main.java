package ui;

import java.io.FileNotFoundException;

// The main class where the application can be run
public class Main {
    public static void main(String[] args) {
        try {
            new Gui();
        } catch (Exception e) {
            System.out.println("Unable to run application");
        }
    }
}
