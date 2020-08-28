package modelTest;

import exception.OutOfBattlefieldException;
import models.animate.PlayerTank;
import models.animate.EnemyTank;
import models.Shell;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Main;

import java.awt.*;
import java.awt.event.KeyEvent;

public class EnemyTankTest {

    PlayerTank playerTank;
    EnemyTank enemyTank;
    Shell shot;

    Point point1;
    Point point2;
    Point point3;
    Point point4;

    @BeforeEach
    public void runBefore() {
        playerTank = new PlayerTank(Main.WIDTH / 2, Main.HEIGHT / 2, 15);
        enemyTank = new EnemyTank(300, 350, 15);
        shot = new Shell(enemyTank);
        point1 = new Point(Main.WIDTH - 5, 5);
        point2 = new Point(5, 5);
        point3 = new Point(5, Main.HEIGHT - 5);
        point4 = new Point(Main.WIDTH - 5, Main.HEIGHT - 5);
    }

    @Test
    public void testEnemyTankSpawn() {

        assertEquals(300, enemyTank.getTankX());
        assertEquals(350, enemyTank.getTankY());
        assertEquals(15, enemyTank.getTankSpeed());
        assertTrue(enemyTank.isAlive());

    }

    @Test
    public void testGetNextTankOppositeDirectionToPlayer() {

        double previousTankX = enemyTank.getTankX();

        assertEquals(300, enemyTank.getTankX());
        assertEquals(350, enemyTank.getTankY());

        try {
            enemyTank.getNextTank(KeyEvent.VK_D);
        } catch (OutOfBattlefieldException e) {
            fail();
        }
        assertEquals(previousTankX - enemyTank.getTankSpeed(), enemyTank.getTankX());

    }

    @Test
    public void testShotGetsFired() {
        enemyTank.fireShot();
        assertTrue(shot.isActive());
    }

    @Test
    public void testKillTank() {

        assertTrue(enemyTank.isAlive());
        enemyTank.setAlive(false);
        assertFalse(enemyTank.isAlive());

    }

    @Test
    public void testGetNextBarrelInverseDirectionOfPlayer() {
        enemyTank.getNextBarrel(playerTank);
        assertEquals(playerTank.getBarrelDirection() + Math.PI, enemyTank.getBarrelDirection());
    }

    @Test
    public void testGetNextTankNoExceptions() {
        try {
            enemyTank.getNextTank(KeyEvent.VK_W);
        } catch (OutOfBattlefieldException e) {
            fail();
        }
    }

    @Test
    public void testGetNextTankExpectOutOfBattlefieldExceptionXLessThan0() {
        EnemyTank outOfBoundsTank = new EnemyTank(-10, 500, 15);
        try {
            outOfBoundsTank.getNextTank(KeyEvent.VK_D);
            fail();
        } catch (OutOfBattlefieldException e) {
            System.out.println("Good");
        }
    }

    @Test
    public void testGetNextTankExpectOutOfBattlefieldExceptionXGreaterThanWidth() {
        EnemyTank outOfBoundsTank = new EnemyTank(Main.WIDTH + 100, 500, 15);
        try {
            outOfBoundsTank.getNextTank(KeyEvent.VK_A);
            fail();
        } catch (OutOfBattlefieldException e) {
            System.out.println("Good");
        }
    }

    @Test
    public void testGetNextTankExpectOutOfBattlefieldExceptionYLessThan0() {
        EnemyTank outOfBoundsTank = new EnemyTank(500, -50, 15);
        try {
            outOfBoundsTank.getNextTank(KeyEvent.VK_S);
            fail();
        } catch (OutOfBattlefieldException e) {
            System.out.println("Good");
        }
    }

    @Test
    public void testGetNextTankExpectOutOfBattlefieldExceptionYGreaterThanHeight() {
        EnemyTank outOfBoundsTank = new EnemyTank(500, Main.HEIGHT + 100, 15);
        try {
            outOfBoundsTank.getNextTank(KeyEvent.VK_W);
            fail();
        } catch (OutOfBattlefieldException e) {
            System.out.println("Good");
        }
    }

    @Test
    public void testGetNextTankNotAMoveKey() {
        try {
            double previousTankX = enemyTank.getTankX();
            double previousTankY = enemyTank.getTankY();
            enemyTank.getNextTank(KeyEvent.VK_F);
            assertEquals(previousTankX, enemyTank.getTankX());
            assertEquals(previousTankY, enemyTank.getTankY());
        } catch (OutOfBattlefieldException e) {
            fail();
        }
    }
}
