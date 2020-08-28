package ui;

import exception.EmptyFileException;
import exception.MismatchedSaveFileException;
import models.animate.EnemyTank;
import models.animate.PlayerTank;
import models.animate.Tank;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;

public class SavesManager {

    private static HashMap<String, String> tankMap;
    private static String fileToLoad;

    static void tryLoad() {
        //Extract fields for tank object
        try {
            System.out.println("Please enter the name of the file you wish to load (file name is case-sensitive):");
            Scanner loadScanner = new Scanner(System.in);
            fileToLoad = loadScanner.nextLine();
            load(fileToLoad);
        } catch (NoSuchFileException e) {
            System.out.println("We couldn't find the file you wanted to load.");
            System.out.println("Your game begins anew...");
        } catch (IOException e) {
            System.out.println("Something else went wrong, so we can't load that file.");
            System.out.println("Your game begins anew...");
        } catch (EmptyFileException e) {
            System.out.println("The file requested contains no data. It cannot be loaded.");
        } finally {
            System.out.println("START");
        }
    }

    //File I/O procedure (File reading + PrintWriter) based on source FileReaderWriter
    //Ideas for what to do with read lines based on JSON save files from West of Loathing PC game
    private static void load(String desiredLoadFile) throws IOException, EmptyFileException {

        List<String> tankSave;
        tankMap = new HashMap<>();
        if (fileToLoad.toUpperCase().contains(".TXT")) {
            tankSave = Files.readAllLines(Paths.get("./data/" + desiredLoadFile));
        } else {
            tankSave = Files.readAllLines(Paths.get("./data/" + desiredLoadFile + ".TXT"));
        }

        for (String s : tankSave) {
            //splitOnSpace method used for extracting values code based from FileReaderWriter
            ArrayList<String> dataLine = splitOnSpace(s);
            tankMap.put(dataLine.get(0), dataLine.get(1));
        }

        if (tankSave.size() == 0) {
            throw new EmptyFileException();
        }
    }

    //splitOnSpace method directly sourced from FileReaderWriter
    //MODIFIES: line, ArrayList<String> dataLine
    //EFFECTS: splits each line in the file into two parts
    private static ArrayList<String> splitOnSpace(String line) {
        String[] splits = line.split(" ");
        return new ArrayList<>(Arrays.asList(splits));
    }

    static void assignFields(PlayerTank playerTank, EnemyTank enemyTank) throws MismatchedSaveFileException {
        if (!tankMap.isEmpty()) {
            boolean mismatch1 = tankMap.get("GameMode:").equals("Original")
                    && Main.menu.getPlayerInput().equals("Target Practice");
            boolean mismatch2 = tankMap.get("GameMode:").equals("TargetPractice")
                    && Main.menu.getPlayerInput().equals("Original");
            if (mismatch1 || mismatch2) {
                throw new MismatchedSaveFileException();
            } else if (tankMap.get("GameMode:").equals("Original")
                    && Main.menu.getPlayerInput().equals("Original")) {
                setTankFields(playerTank);
                setTankFields(enemyTank);
            } else {
                setTankFields(playerTank);
            }
        }
    }

    private static void setTankFields(Tank tank) {
        double savedTankX;
        double savedTankY;
        int savedTankSpeed;
        if (tank instanceof PlayerTank) {
            savedTankX = Double.parseDouble(tankMap.get("TankX:"));
            savedTankY = Double.parseDouble(tankMap.get("TankY:"));
            savedTankSpeed = Integer.parseInt(tankMap.get("TankSpeed:"));
        } else {
            if (!tankMap.containsKey("EnemyTankX:")) {
                System.exit(99);
            }
            savedTankX = Double.parseDouble(tankMap.get("EnemyTankX:"));
            savedTankY = Double.parseDouble(tankMap.get("EnemyTankY:"));
            savedTankSpeed = Integer.parseInt(tankMap.get("EnemyTankSpeed:"));
        }
        tank.setTankX(savedTankX);
        tank.setTankY(savedTankY);
        tank.setTankSpeed(savedTankSpeed);
    }

    static void trySave() {
        try {
            System.out.print("Please enter the name of the file you wish to save to.");
            System.out.println(" You may also create a new save file by typing a new name:");
            Scanner saveScanner = new Scanner(System.in);

            String fileToSave = saveScanner.nextLine();
            save(fileToSave);
        } catch (IOException e) {
            System.out.println("You don't appear to have an appropriate .TXT file to save the game.");
        }
    }

    //File I/O PrintWriting procedure based on source FileReaderWriter
    //MODIFIES: saveToTank
    //EFFECTS: prints lines containing tank attributes to a desired named text file
    private static void save(String fileToSave) throws IOException {

        List<String> toSave = new ArrayList<>();
        //Grab values of playerTank from the GameMode that was run
        Main.addFromSource(toSave);

        PrintWriter tankWriter;
        if (fileToSave.toUpperCase().contains(".TXT")) {
            tankWriter = new PrintWriter("./data/" + fileToSave, "UTF-8");
        } else {
            tankWriter = new PrintWriter("./data/" + fileToSave + ".TXT", "UTF-8");
        }

        //Write lines to file
        for (String s : toSave) {
            tankWriter.println(s);
        }
        tankWriter.close();

        System.exit(0);
    }

    static void addFromSource(List<String> addTo, int gameModeNumber, PlayerTank player, EnemyTank enemy) {

        if ((player == null || enemy == null) && gameModeNumber == 1) {
            return;
        }

        if (gameModeNumber == 1) {
            addTo.add("GameMode: Original");
        } else {
            addTo.add("GameMode: TargetPractice");
        }

        addTo.add("TankX: " + player.getTankX());
        addTo.add("TankY: " + player.getTankY());
        addTo.add("TankSpeed: " + player.getTankSpeed());
        if (gameModeNumber == 1) {
            addTo.add("EnemyTankX: " + enemy.getTankX());
            addTo.add("EnemyTankY: " + enemy.getTankY());
            addTo.add("EnemyTankSpeed: " + enemy.getTankSpeed());
        }

    }
}
