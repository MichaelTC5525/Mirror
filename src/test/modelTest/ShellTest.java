package modelTest;

import models.*;

import models.animate.PlayerTank;
import models.environment.Wall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Main;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class ShellTest {

    private PlayerTank playerTank;
    private Shell shot;
    private Wall wall;

    @BeforeEach
    public void runBefore() {

        playerTank =  new PlayerTank(Main.WIDTH / 2.0, Main.HEIGHT / 2.0 , 15);
        shot = new Shell(playerTank);
        wall = new Wall(20, 20, 150, Wall.Orientation.HORIZONTAL);

    }

    @Test
    public void testShellSpawn() {

        assertEquals(5, shot.getShellSpeed());
        assertEquals(playerTank.getTankX(), shot.getShellPosX());
        assertEquals(playerTank.getTankY(), shot.getShellPosY());
        assertEquals(playerTank.getBarrelDirection(), shot.getShellDirection());
        assertTrue(shot.isActive());

    }

    @Test
    public void testGetNextShell() {

        double previousShellX = shot.getShellPosX();
        double previousShellY = shot.getShellPosY();

        assertEquals(playerTank.getTankX(), shot.getShellPosX());
        assertEquals(playerTank.getTankY(), shot.getShellPosY());
        assertTrue(shot.isActive());

        shot.getNextShell();
        double expectedXChange = (shot.getShellSpeed() * Math.cos(playerTank.getBarrelDirection()));
        double expectedYChange = (shot.getShellSpeed() * Math.sin(playerTank.getBarrelDirection()));
        assertEquals(previousShellX + expectedXChange, shot.getShellPosX());
        assertEquals(previousShellY - expectedYChange, shot.getShellPosY());
    }

    @Test
    public void testFireShellandDespawnXLessThan0() {
        playerTank.getNextBarrel(new Point((int) playerTank.getTankX() - 1, (int) playerTank.getTankY()));
        Shell newShot = new Shell(playerTank);
        while (newShot.isActive()) {
            newShot.getNextShell();
        }
        assertFalse(newShot.isActive());
    }

    @Test
    public void testFireShellAndDespawnExceedXBeforeY() {
        while (shot.isActive()) {
            shot.getNextShell();
        }
        assertFalse(shot.isActive());
    }

    @Test
    public void testFireShellAndDespawnExceedYBeforeX() {
        playerTank.getNextBarrel(new Point((int) playerTank.getTankX(), (int) playerTank.getTankY() + 1));
        Shell newShot = new Shell(playerTank);
        while (newShot.isActive()) {
            newShot.getNextShell();
        }
        assertFalse(newShot.isActive());
    }

    @Test
    public void testFireShellAndDespawnYLessThan0() {
        playerTank.getNextBarrel(new Point((int) playerTank.getTankX(), (int) playerTank.getTankY() - 1));
        Shell newShot = new Shell(playerTank);
        while (newShot.isActive()) {
            newShot.getNextShell();
        }
        assertFalse(newShot.isActive());
    }

    @Test
    public void testFireShellAndDespawnXandYLessThan0() {
        playerTank.getNextBarrel(new Point((int) playerTank.getTankX() - 1, (int) playerTank.getTankY() - 1));
        Shell newShot = new Shell(playerTank);
        while (newShot.isActive()) {
            newShot.getNextShell();
        }
        assertFalse(newShot.isActive());
    }

    @Test
    public void testFireShellAndDespawnXLessThan0YExceedsHeight() {
        playerTank.getNextBarrel(new Point((int) playerTank.getTankX() - 1, (int) playerTank.getTankY() + 1));
        Shell newShot = new Shell(playerTank);
        while (newShot.isActive()) {
            newShot.getNextShell();
        }
        assertFalse(newShot.isActive());
    }

    @Test
    public void testFireShellAndDespawnXExceedWidthAndYLessThan0() {
        playerTank.getNextBarrel(new Point((int) playerTank.getTankX() + 1, (int) playerTank.getTankY() - 1));
        Shell newShot = new Shell(playerTank);
        while (newShot.isActive()) {
            newShot.getNextShell();
        }
        assertFalse(newShot.isActive());
    }

    @Test
    public void testFireShellAndDespawnXandYExceed() {
        playerTank.getNextBarrel(new Point((int) playerTank.getTankX() + 1, (int) playerTank.getTankY() + 1));
        Shell newShot = new Shell(playerTank);
        while (newShot.isActive()) {
            newShot.getNextShell();
        }
        assertFalse(newShot.isActive());
    }

    @Test
    public void testChangeActive() {

        shot.changeActive(false);
        assertFalse(shot.isActive());
        shot.changeActive(true);
        assertTrue(shot.isActive());

    }

    @Test
    public void testHitWall() {
        assertTrue(shot.isActive());
        shot.hitWall(wall);
        assertFalse(shot.isActive());
    }

    @Test
    public void testAddSourceTank() {
        shot.addSourceTank(playerTank);
        assertEquals(playerTank, shot.getSourceTank());
        assertEquals(1, playerTank.getShellList().size());
        shot.addSourceTank(playerTank);
        assertEquals(1, playerTank.getShellList().size());
    }

    @Test
    public void testRemoveSourceTank() {
        shot.addSourceTank(playerTank);
        assertEquals(playerTank, shot.getSourceTank());
        assertEquals(1, playerTank.getShellList().size());
        shot.removeSourceTank(playerTank);
        assertNull(shot.getSourceTank());
        assertEquals(0, playerTank.getShellList().size());
    }

    @Test
    public void testAllSetters() {
        assertEquals(playerTank.getTankX(), shot.getShellPosX());
        assertEquals(playerTank.getTankY(), shot.getShellPosY());
        assertEquals(5, shot.getShellSpeed());
        shot.setShellPosX(100);
        shot.setShellPosY(100);
        shot.setShellSpeed(10);
        assertEquals(100, shot.getShellPosX());
        assertEquals(100, shot.getShellPosY());
        assertEquals(10, shot.getShellSpeed());
    }
}
