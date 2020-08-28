package ui.levels;

import exception.OutOfBattlefieldException;
import models.Shell;
import models.animate.EnemyTank;
import models.animate.PlayerTank;
import models.animate.Tank;
import observer.Observer;
import ui.GamePanel;
import ui.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GameMode extends JFrame implements Observer {

    PlayerTank playerTank;
    EnemyTank enemyTank;
    List<Shell> shells;

    GamePanel gp;

    //EFFECTS: instantiates a new GameMode, adding necessary underlying components for GUI and interactivity
    public GameMode() {
        //Setup reference from SpaceInvaders
        super("Mirror");
        setUndecorated(true);
        addKeyListener(new KeyHandler());
        addMouseListener(new MouseHandler());
        addTimer();
    }


    //Timer sourced from SpaceInvaders
    // Set up timer
    // EFFECTS:  initializes a timer that updates game each
    //           20 milliseconds
    private void addTimer() {
        Timer t = new Timer(10, ae -> updateScene());

        t.start();
    }

    //REQUIRES: this has been instantiated
    //EFFECTS: sets the stage for the GUI to become visible, calling necessary methods
    public void setUp() {

        //GUI setup sourced from SpaceInvaders
        setUndecorated(true);
        gp = new GamePanel(this);
        add(gp);
        pack();
        centreOnScreen();
        setVisible(true);
    }

    //EFFECTS: returns the player's Tank object in the game
    public PlayerTank getPlayerTank() {
        return playerTank;
    }

    //EFFECTS: returns the enemy Tank object in the game
    public EnemyTank getEnemyTank() {
        return enemyTank;
    }

    //REQUIRES: called by clock tick
    //MODIFIES: playerTank, shot in shells, gp
    //EFFECTS: calls methods to update game Object properties as time goes on, and calls for drawing updates
    public void updateScene() {
        if (!(playerTank == null)) {
            playerTank.getNextBarrel(MouseInfo.getPointerInfo().getLocation());
        }
        for (Shell shot : shells) {
            if (shot.isActive()) {
                shot.getNextShell();
            }
        }
        gp.repaint();
    }

    //REQUIRES: Keyboard keys have been pressed
    //MODIFIES: selected
    //EFFECTS: tries to move the tank to its next location based on key press; keeps tank within boundaries of game
    private void tryGetNextTank(Tank selected, int keyCode) {
        try {
            selected.getNextTank(keyCode);
        } catch (OutOfBattlefieldException e) {
            if (selected.getTankX() < 0) {
                selected.setTankX(0);
            } else if (selected.getTankX() > Main.WIDTH) {
                selected.setTankX(Main.WIDTH);
            }

            if (selected.getTankY() < 0) {
                selected.setTankY(0);
            } else if (selected.getTankY() > Main.HEIGHT) {
                selected.setTankY(Main.HEIGHT);
            }
        } catch (NullPointerException ignored) {
            //
        }
    }

    //REQUIRES: at least one Tank has fired a shell
    //MODIFIES: shells
    //EFFECTS: adds any newly fired shells to the list of shells, and removes any that have become inactive
    @Override
    public void update(Shell shot) {
        System.out.println("SHOTS FIRED!");
        shells.add(shot);
        List<Shell> toRemove = new ArrayList<>();
        for (Shell shell : shells) {
            if (!shell.isActive()) {
                toRemove.add(shell);
            }
        }
        shells.removeAll(toRemove);
    }

    // Copied from SpaceInvaders
    // MODIFIES: this
    // EFFECTS: location of frame is set so frame is centered on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    //EFFECTS: returns the list of currently active shells in the game
    public List<Shell> getShells() {
        return shells;
    }


    //KeyHandler setup using SpaceInvaders reference; custom-built from there
    public class KeyHandler extends KeyAdapter {

        //REQUIRES: invoked; a key has been pressed
        //MODIFIES: playerTank, enemyTank
        //EFFECTS: responds to key presses and moves tank objects accordingly
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            } else if (e.getKeyCode() == KeyEvent.VK_X) {
                setVisible(false);
                Main.trySave();
            } else {
                tryGetNextTank(playerTank, e.getKeyCode());
                tryGetNextTank(enemyTank, e.getKeyCode());
            }
        }
    }

    //Based off the setup of KeyHandler, I tried to make something for MouseEvents, NOT present in SpaceInvaders
    public class MouseHandler extends MouseAdapter {

        //REQUIRES: mouse has been clicked
        //MODIFIES: playerTank, enemyTank
        //EFFECTS: fires a shot from playerTank and enemyTank; if the corresponding tank is not null
        @Override
        public void mouseClicked(MouseEvent e)  {
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (!(playerTank == null)) {
                    playerTank.fireShot();
                }
                if (!(enemyTank == null)) {
                    enemyTank.fireShot();
                }
            }
        }
    }
}
