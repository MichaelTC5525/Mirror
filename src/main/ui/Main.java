package ui;

import exception.MismatchedSaveFileException;
import network.ReadWebPageEx;
import ui.levels.GameMode;
import ui.levels.OriginalGame;
import ui.levels.TargetPracticeGame;

import java.io.IOException;
import java.util.List;

public class Main {

    public static final int HEIGHT = 1080;
    public static final int WIDTH = 1920;
    static MainMenu menu;

    private static GameMode gameMode;

    private static int gameModeNumber;

    //EFFECTS: starts the program
    public static void main(String[] args) throws IOException {

        ReadWebPageEx.printStartMessage();

        menu = new MainMenu();
        menu.initializeMenu();

        tryLoad();

        startGameMode();
    }

    private static void tryLoad() {
        SavesManager.tryLoad();
    }

    //MODIFIES: gameMode, gameMode.playerTank, gameMode.enemyTank, gameModeNumber
    //EFFECTS: starts the game based on which mode was picked, setting appropriate fields
    private static void startGameMode() {
        if (menu.getPlayerInput().equals("Original")) {
            gameMode = new OriginalGame();
            gameModeNumber = 1;

        } else if (menu.getPlayerInput().equals("Target Practice")) {
            gameMode = new TargetPracticeGame();
            gameModeNumber = 2;
        }

        try {
            SavesManager.assignFields(gameMode.getPlayerTank(), gameMode.getEnemyTank());
        } catch (MismatchedSaveFileException e) {
            System.out.println("The save file you tried to load is not appropriate for your selected gamemode.");
        }

        gameMode.setUp();
    }


    //EFFECTS: begins the save process
    public static void trySave() {
        SavesManager.trySave();
    }

    //EFFECTS: updates a given ArrayList with the Strings to be written to the save file
    static void addFromSource(List<String> addTo) {
        SavesManager.addFromSource(addTo, gameModeNumber, gameMode.getPlayerTank(), gameMode.getEnemyTank());
    }
}
