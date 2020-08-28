package ui;

import java.util.Scanner;
import java.util.ArrayList;

public class MainMenu {

    //B04-SimpleCalculator used for Scanner setup reference
    private Scanner scanner;
    private String playerInput;

    //Makes a new MainMenu
    public MainMenu() { }


    void initializeMenu() {

        printMainMenu();

        initializeScanner();

        if (playerInput.equals("Original") || playerInput.equals("Target Practice")) {

            System.out.println("You have chosen to play: " + playerInput);

        } else if (playerInput.equals("Extras")) {

            handleExtras();

            initializeMenu();

        } else if (playerInput.toUpperCase().equals("EXIT")) {
            System.exit(0);
        } else {
            System.out.println("That didn't match our options. Be sure to exactly match the words in your input.");
            initializeMenu();
        }

    }

    private void initializeScanner() {

        scanner = new Scanner(System.in);
        playerInput = scanner.nextLine();

    }

    private void printMainMenu() {

        System.out.println("Welcome to MIRROR.");
        System.out.println("Please select an available option by typing the name as it appears:");

        ArrayList<String> modes = new ArrayList<>();
        modes.add("Original");
        modes.add("Target Practice");
        modes.add("Extras");
        printOptions(modes);
        System.out.println("To exit the game, simply type: Exit");

    }

    private void handleExtras() {

        System.out.println("You have chosen: " + playerInput + ". Here you can learn more about the game.");

        printExtrasMenu();
        checkExtraOptions();

    }

    private void checkExtraOptions() {

        while (true) {
            initializeScanner();

            if (playerInput.toUpperCase().equals("ORIGINAL INFO")) {
                System.out.print("The Original mode is the way the game is meant to be played.");
                System.out.println(" You fight a moving tank in this scenario, with a special twist.");
            } else if (playerInput.toUpperCase().equals("TARGET PRACTICE INFO")) {
                System.out.print("The Target Practice mode is for you to train your aim. For newcomers too.");
                System.out.println(" You fight stationary targets in this scenario.");
            } else if (playerInput.toUpperCase().equals("WHO MADE THIS?")) {
                System.out.println("Oh you know, just some guy. He's alright, I guess.");
            } else if (playerInput.toUpperCase().equals("BACK")) {
                break;
            } else {
                System.out.println("Sorry, that wasn't one of the options available in Extras.");
            }

            printExtrasMenu();
        }
    }

    private void printExtrasMenu() {

        System.out.println("What would you like to learn more about?");

        ArrayList<String> info = new ArrayList<>();
        info.add("Tell me about the Original mode (type: Original Info)");
        info.add("Tell me about the Target Practice mode (type: Target Practice Info)");
        info.add("Tell me about the developers of this game (type: Who made this?)");
        printOptions(info);

        System.out.println("To return to the main menu type: back");

    }

    private void printOptions(ArrayList<String> list) {

        for (String s : list) {
            System.out.println(s);
        }

    }

    String getPlayerInput() {
        return playerInput;
    }

}


