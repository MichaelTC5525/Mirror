package modelTest;

import exception.OutOfBattlefieldException;
import ui.levels.OriginalGame;
import models.animate.PlayerTank;
import models.Shell;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Main;

import java.awt.*;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTankTest {

    PlayerTank playerTank;
    Shell shot;

    Point point1;
    Point point2;
    Point point3;
    Point point4;

    @BeforeEach
    public void runBefore() {

        playerTank = new PlayerTank(Main.WIDTH / 2, Main.HEIGHT / 2, 15);
        shot = new Shell(playerTank);
        point1 = new Point(Main.WIDTH - 5, 5);
        point2 = new Point(5, 5);
        point3 = new Point(5, Main.HEIGHT - 5);
        point4 = new Point(Main.WIDTH - 5, Main.HEIGHT - 5);

    }

    @Test
    public void testTankSpawn() {

        assertEquals(Main.WIDTH / 2, playerTank.getTankX());
        assertEquals(Main.HEIGHT / 2, playerTank.getTankY());
        assertEquals(15, playerTank.getTankSpeed());
        assertTrue(playerTank.isAlive());

    }

    @Test
    public void testGetNextTankMoveUp() {

        double previousTankY = playerTank.getTankY();

        assertEquals(Main.WIDTH / 2, playerTank.getTankX());
        assertEquals(Main.HEIGHT / 2, playerTank.getTankY());

        try {
            playerTank.getNextTank(KeyEvent.VK_W);
        } catch (OutOfBattlefieldException e) {
            fail();
        }
        assertEquals(previousTankY - playerTank.getTankSpeed(), playerTank.getTankY());

    }

    @Test
    public void testGetNextTankMoveDown() {

        double previousTankY = playerTank.getTankY();

        assertEquals(Main.WIDTH / 2, playerTank.getTankX());
        assertEquals(Main.HEIGHT / 2, playerTank.getTankY());

        try {
            playerTank.getNextTank(KeyEvent.VK_S);
        } catch (OutOfBattlefieldException e) {
            fail();
        }
        assertEquals(previousTankY + playerTank.getTankSpeed(), playerTank.getTankY());

    }

    @Test
    public void testGetNextTankMoveLeft() {

        double previousTankX = playerTank.getTankX();

        assertEquals(Main.WIDTH / 2, playerTank.getTankX());
        assertEquals(Main.HEIGHT / 2, playerTank.getTankY());

        try {
            playerTank.getNextTank(KeyEvent.VK_A);
        } catch (OutOfBattlefieldException e) {
            fail();
        }
        assertEquals(previousTankX - playerTank.getTankSpeed(), playerTank.getTankX());

    }

    @Test
    public void testGetNextTankMoveRight() {

        double previousTankX = playerTank.getTankX();

        assertEquals(Main.WIDTH / 2, playerTank.getTankX());
        assertEquals(Main.HEIGHT / 2, playerTank.getTankY());

        try {
            playerTank.getNextTank(KeyEvent.VK_D);
        } catch (OutOfBattlefieldException e) {
            fail();
        }
        assertEquals(previousTankX + playerTank.getTankSpeed(), playerTank.getTankX());

    }

    @Test
    public void testShotFired() {
        playerTank.fireShot();
        assertTrue(shot.isActive());
    }

    @Test
    public void testSetTankX() {
        assertEquals(Main.WIDTH / 2, playerTank.getTankX());
        playerTank.setTankX(30);
        assertEquals(30, playerTank.getTankX());
    }

    @Test
    public void testSetTankY() {
        assertEquals(Main.HEIGHT / 2, playerTank.getTankY());
        playerTank.setTankY(30);
        assertEquals(30, playerTank.getTankY());
    }

    @Test
    public void testSetMoveSpeed() {
        assertEquals(15, playerTank.getTankSpeed());
        playerTank.setTankSpeed(30);
        assertEquals(30, playerTank.getTankSpeed());
    }

    @Test
    public void testKillTank() {
        assertTrue(playerTank.isAlive());
        playerTank.setAlive(false);
        assertFalse(playerTank.isAlive());
    }

    @Test
    public void testGetNextBarrel1stQuadrant() {
        double offsetX = point1.x - playerTank.getTankX();
        double offsetY = point1.y - playerTank.getTankY();
        double hypotenuse = Math.sqrt(offsetX * offsetX + offsetY * offsetY);
        playerTank.getNextBarrel(point1);
        assertEquals(Math.asin(-offsetY / hypotenuse), playerTank.getBarrelDirection());
    }

    @Test
    public void testGetNextBarrel2ndQuadrant() {
        double offsetX = point2.x - playerTank.getTankX();
        double offsetY = point2.y - playerTank.getTankY();
        double hypotenuse = Math.sqrt(offsetX * offsetX + offsetY * offsetY);
        playerTank.getNextBarrel(point2);
        assertEquals(Math.PI - (Math.asin(-offsetY / hypotenuse)), playerTank.getBarrelDirection());
    }

    @Test
    public void testGetNextBarrel3rdQuadrant() {
        double offsetX = point3.x - playerTank.getTankX();
        double offsetY = point3.y - playerTank.getTankY();
        double hypotenuse = Math.sqrt(offsetX * offsetX + offsetY * offsetY);
        playerTank.getNextBarrel(point3);
        assertEquals(Math.PI + (Math.asin(offsetY / hypotenuse)), playerTank.getBarrelDirection());
    }

    @Test
    public void testGetNextBarrel4thQuadrant() {
        double offsetX = point4.x - playerTank.getTankX();
        double offsetY = point4.y - playerTank.getTankY();
        double hypotenuse = Math.sqrt(offsetX * offsetX + offsetY * offsetY);
        playerTank.getNextBarrel(point4);
        assertEquals(-Math.asin(offsetY / hypotenuse), playerTank.getBarrelDirection());
    }

    @Test
    public void testGetNextTankNoException() {
        try {
            playerTank.getNextTank(KeyEvent.VK_A);
        } catch (OutOfBattlefieldException e) {
            fail();
        }
    }

    @Test
    public void testGetNextTankExpectOutOfBattlefieldException() {
        PlayerTank outOfBoundsTank = new PlayerTank(Main.WIDTH, Main.HEIGHT, 15);
        try {
            outOfBoundsTank.getNextTank(KeyEvent.VK_S);
            fail();
        } catch (OutOfBattlefieldException e) {
            System.out.println("Good");
        }
    }

    @Test
    public void testGetNextTankNotAMoveKey() {
        try {
            double previousTankX = playerTank.getTankX();
            double previousTankY = playerTank.getTankY();
            playerTank.getNextTank(KeyEvent.VK_F);
            assertEquals(previousTankX, playerTank.getTankX());
            assertEquals(previousTankY, playerTank.getTankY());
        } catch (OutOfBattlefieldException e) {
            fail();
        }
    }

    @Test
    public void testAddAndRemoveShells() {
        playerTank.addShell(shot);
        assertEquals(1, playerTank.getShellList().size());
        assertEquals(playerTank, shot.getSourceTank());
        playerTank.removeShell(shot);
        assertEquals(0, playerTank.getShellList().size());
        assertEquals(null, shot.getSourceTank());
    }

    @Test
    public void testDeathByShellNotInFiredList() {
        Shell notFromUsShot = new Shell(playerTank);
        assertEquals(0, playerTank.getShellList().size());
        assertTrue(playerTank.isAlive());

        //Assume notFromUsShot is in contact with playerTank
        playerTank.checkDeath(notFromUsShot);
        assertFalse(playerTank.isAlive());
        assertFalse(notFromUsShot.isActive());
    }

    @Test
    public void testNoDeathShellFiredFromHere() {
        Shell fromUsShot = new Shell(playerTank);
        playerTank.addShell(fromUsShot);
        assertEquals(1, playerTank.getShellList().size());
        assertTrue(playerTank.isAlive());

        //Assume fromUsShot is in contact with playerTank
        playerTank.checkDeath(fromUsShot);
        assertEquals(1, playerTank.getShellList().size());
        assertTrue(playerTank.isAlive());
    }

    @Test
    public void testDuplicateObserver() {
        OriginalGame originalGame = new OriginalGame();
        assertEquals(0, playerTank.getObservers().size());
        playerTank.addObserver(originalGame);
        assertEquals(1, playerTank.getObservers().size());
        playerTank.addObserver(originalGame);
        assertEquals(1, playerTank.getObservers().size());
    }

    @Test
    public void testNotifyObservers() {
        OriginalGame originalGame = new OriginalGame();
        originalGame.setUp();
        originalGame.getPlayerTank().notifyObservers(new Shell(originalGame.getPlayerTank()));
        assertEquals(1, originalGame.getShells().size());
    }

    @Test
    public void testNotifyObserversWithInactiveShell() {
        OriginalGame originalGame = new OriginalGame();
        originalGame.setUp();
        assertEquals(0, originalGame.getShells().size());

        Shell shot1 = new Shell(originalGame.getPlayerTank());
        originalGame.getPlayerTank().notifyObservers(shot1);
        assertEquals(1, originalGame.getShells().size());

        Shell shot2 = new Shell(originalGame.getPlayerTank());
        shot1.changeActive(false);

        originalGame.getPlayerTank().notifyObservers(shot2);
        assertEquals(1, originalGame.getShells().size());
    }
}
