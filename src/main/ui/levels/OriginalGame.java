package ui.levels;

import models.Shell;
import models.animate.PlayerTank;
import models.animate.EnemyTank;
import models.animate.Tank;
import models.environment.Wall;
import ui.Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class OriginalGame extends GameMode {

    private List<Wall> levelWalls;

    //EFFECTS: constructs the game, instantiates tank objects, and other necessary elements
    public OriginalGame() {
        super();
        playerTank = new PlayerTank(0, 0, 15);
        enemyTank = new EnemyTank(Main.WIDTH, Main.HEIGHT, 15);
        levelWalls = new ArrayList<>();
        levelWalls.add(new Wall(Main.WIDTH / 2, Main.HEIGHT / 2,
                300, Wall.Orientation.VERTICAL));
        levelWalls.add(new Wall(Main.WIDTH / 2, Main.HEIGHT / 2,
                300, Wall.Orientation.HORIZONTAL));
        levelWalls.add(new Wall(Main.WIDTH * 0.75, Main.HEIGHT * 0.75,
                150, Wall.Orientation.HORIZONTAL));
        levelWalls.add(new Wall(Main.WIDTH * 0.25, Main.HEIGHT * 0.25,
                100, Wall.Orientation.VERTICAL));
        shells = new ArrayList<>();
    }

    //REQUIRES: this game mode was selected through Main
    //MODIFIES: playerTank, enemyTank
    //EFFECTS: sets this game mode as an observer to both player and enemy tanks
    @Override
    public void setUp() {
        playerTank.addObserver(this);
        enemyTank.addObserver(this);
        System.out.println("Setting up the game with player and enemy tanks.");
        super.setUp();
    }

    //MODIFIES: playerTank, enemyTank, shells
    //EFFECTS: calls various methods to update the current state of the game; is called every timer tick
    @Override
    public void updateScene() {
        if (!(enemyTank == null)) {
            enemyTank.getNextBarrel(playerTank);
        }

        super.updateScene();
        dequeueShellCollisions();
        checkWallBoundsAgainst(playerTank);
        checkWallBoundsAgainst(enemyTank);

        if (!(playerTank == null) && !(enemyTank == null)) {
            finishTheKills();
        }
    }

    private void checkWallBoundsAgainst(Tank tank) {
        if (tank != null) {
            Point tankTopLeft = new Point((int) (tank.getTankX() - gp.getTankSize() / 2.0),
                    (int) (tank.getTankY() - gp.getTankSize() / 2.0));
            Rectangle tankBound = new Rectangle(tankTopLeft.x, tankTopLeft.y,
                    gp.getTankSize(), gp.getTankSize());
            for (Wall wall : levelWalls) {
                if (wall.getOrientation() == Wall.Orientation.VERTICAL) {
                    adjustTankXToWall(tank, tankBound, wall);
                } else {
                    adjustTankYToWall(tank, tankBound, wall);
                }
            }
        }
    }

    private void adjustTankXToWall(Tank tank, Rectangle tankBound, Wall wall) {
        Point wallTopLeft = new Point((int) (wall.getPositionX() - 1),
                (int) (wall.getPositionY() - wall.getLength()));
        Rectangle wallBound = new Rectangle(wallTopLeft.x, wallTopLeft.y, 2, (int) (2 * wall.getLength()));
        if (wallBound.intersects(tankBound) && tank.getTankX() <= wall.getPositionX()) {
            tank.setTankX(wall.getPositionX() - gp.getTankSize() / 2.0);
        } else if (wallBound.intersects(tankBound) && tank.getTankX() >= wall.getPositionX()) {
            tank.setTankX(wall.getPositionX() + gp.getTankSize() / 2.0);
        }
    }

    private void adjustTankYToWall(Tank tank, Rectangle tankBound, Wall wall) {
        Point wallTopLeft = new Point((int) (wall.getPositionX() - wall.getLength()),
                (int) (wall.getPositionY() - 1));
        Rectangle wallBound = new Rectangle(wallTopLeft.x, wallTopLeft.y, (int) (2 * wall.getLength()), 2);
        if (wallBound.intersects(tankBound) && tank.getTankY() <= wall.getPositionY()) {
            tank.setTankY(wall.getPositionY() - gp.getTankSize() / 2.0);
        } else if (wallBound.intersects(tankBound) && tank.getTankY() >= wall.getPositionY()) {
            tank.setTankY(wall.getPositionY() + gp.getTankSize() / 2.0);
        }
    }

    private void dequeueShellCollisions() {
        List<Shell> inactiveShells = new ArrayList<>();
        for (Shell shot : shells) {
            for (Wall wall : levelWalls) {
                checkShotToWallCollision(shot, wall);
                if (!shot.isActive()) {
                    inactiveShells.add(shot);
                }
            }
            if (!(playerTank == null) && !(enemyTank == null)) {
                checkShellCollidedWithTank(shot, playerTank);
                checkShellCollidedWithTank(shot, enemyTank);
            }
        }

        shells.removeAll(inactiveShells);
    }

    private void checkShotToWallCollision(Shell shot, Wall wall) {
        if (wall.getOrientation() == Wall.Orientation.VERTICAL) {
            if (shot.getShellPosX() >= wall.getPositionX() - 5 && shot.getShellPosX() <= wall.getPositionX() + 5
                    && wall.getPositionY() - wall.getLength() <= shot.getShellPosY()
                    && shot.getShellPosY() <= wall.getPositionY() + wall.getLength()) {
                wall.contactObject(shot);
            }
        } else {
            if (shot.getShellPosX() >= wall.getPositionX() - wall.getLength()
                    && shot.getShellPosX() <= wall.getPositionX() + wall.getLength()
                    && shot.getShellPosY() >= wall.getPositionY() - 5
                    && shot.getShellPosY() <= wall.getPositionY() + 5) {
                wall.contactObject(shot);
            }
        }
    }

    private void checkShellCollidedWithTank(Shell shot, Tank tank) {
        boolean withinTankX = shot.getShellPosX() >= tank.getTankX() - gp.getTankSize() / 2
                && shot.getShellPosX() <= tank.getTankX() + gp.getTankSize() / 2;
        boolean withinTankY = shot.getShellPosY() >= tank.getTankY() - gp.getTankSize() / 2
                && shot.getShellPosY() <= tank.getTankY() + gp.getTankSize() / 2;
        if (withinTankX && withinTankY) {
            tank.checkDeath(shot);
        }
    }

    private void finishTheKills() {
        if (!playerTank.isAlive()) {
            playerTank = null;
        }

        if (!enemyTank.isAlive()) {
            enemyTank = null;
        }
    }

    //EFFECTS: returns the enemyTank object in this current game
    public EnemyTank getEnemyTank() {
        return enemyTank;
    }

    //EFFECTS: returns the list of walls in the current map
    public List<Wall> getLevelWalls() {
        return levelWalls;
    }
}
