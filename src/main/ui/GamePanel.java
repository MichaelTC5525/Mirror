package ui;

import models.environment.Wall;
import ui.levels.GameMode;
import ui.levels.OriginalGame;
import ui.levels.TargetPracticeGame;
import models.Shell;
import models.animate.Tank;
import models.environment.PracticeTarget;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GamePanel extends JPanel {

    private static final int TANK_SIZE = 40;
    private static final int SHELL_SIZE = 10;
    private static final int TARGET_SIZE = 30;

    //The majority of this class is heavily referenced from the similar class in the SpaceInvaders project, in terms
    // of the GamePanel constructor, paintComponent method, and usage of the Graphics abstract class

    private GameMode game;

    //EFFECTS: creates the GamePanel for the GameMode to be drawn onto for GUI
    public GamePanel(GameMode game) {
        setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT));
        setBackground(Color.WHITE);
        this.game = game;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPlayerTank(g);
        drawActiveShells(g);
        if (game instanceof OriginalGame) {
            drawEnemyTank(g);
            drawWalls(g);
        } else {
            drawPracticeTargets(g);
        }
    }

    private void drawPlayerTank(Graphics g) {
        Tank t = game.getPlayerTank();
        if (!(t == null)) {
            g.setColor(new Color(0, 25, 220));
            g.fillRect((int) t.getTankX() - 20, (int) t.getTankY() - 20, TANK_SIZE, TANK_SIZE);
            g.drawLine((int) t.getTankX(), (int) t.getTankY(),
                    (int) (t.getTankX() + 55 * Math.cos(t.getBarrelDirection())),
                    (int) (t.getTankY() + 55 * Math.sin(-t.getBarrelDirection())));
        }
    }

    private void drawActiveShells(Graphics g) {
        List<Shell> activeShells = game.getShells();
        g.setColor(new Color(0, 0, 0));
        for (Shell shell : activeShells) {
            if (shell.isActive()) {
                g.fillOval((int) shell.getShellPosX(), (int) shell.getShellPosY(), SHELL_SIZE, SHELL_SIZE);
            }
        }
    }

    private void drawEnemyTank(Graphics g) {
        OriginalGame originalGame = (OriginalGame) game;
        Tank t = originalGame.getEnemyTank();
        if (!(t == null)) {
            g.setColor(new Color(220, 25, 0));
            g.fillRect((int) t.getTankX() - 20, (int) t.getTankY() - 20, TANK_SIZE, TANK_SIZE);
            g.drawLine((int) t.getTankX(), (int) t.getTankY(),
                    (int) (t.getTankX() + 55 * Math.cos(t.getBarrelDirection())),
                    (int) (t.getTankY() + 55 * Math.sin(-t.getBarrelDirection())));
        }

    }

    private void drawWalls(Graphics g) {
        OriginalGame originalGame = (OriginalGame) game;
        List<Wall> wallList = originalGame.getLevelWalls();
        g.setColor(new Color(0, 0, 0));
        for (Wall wall : wallList) {
            if (wall.getOrientation() == Wall.Orientation.VERTICAL) {
                g.drawLine((int) wall.getPositionX(), (int) (wall.getPositionY() - wall.getLength()),
                        (int) wall.getPositionX(), (int) (wall.getPositionY() + wall.getLength()));
            } else {
                g.drawLine((int) (wall.getPositionX() - wall.getLength()), (int) wall.getPositionY(),
                        (int) (wall.getPositionX() + wall.getLength()), (int) wall.getPositionY());
            }
        }
    }

    private void drawPracticeTargets(Graphics g) {
        TargetPracticeGame targetPracticeGame = (TargetPracticeGame) game;
        List<PracticeTarget> targetList = targetPracticeGame.getTargets();
        g.setColor(new Color(125, 20, 30));
        for (PracticeTarget t : targetList) {
            if (!t.isDestroyed()) {
                g.fillOval((int) t.getPositionX(), (int) t.getPositionY(), TARGET_SIZE, TARGET_SIZE);
            }
        }
    }

    //EFFECTS: returns the size of the tank square on the screen (length of one side)
    public int getTankSize() {
        return TANK_SIZE;
    }

    //EFFECTS: returns the size of the shells in the game (diameter)
    public int getShellSize() {
        return SHELL_SIZE;
    }

    //EFFECTS: returns the size of the Practice Targets in the game (diameter)
    public int getTargetSize() {
        return TARGET_SIZE;
    }
}
