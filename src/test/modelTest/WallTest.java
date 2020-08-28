package modelTest;

import models.*;

import models.animate.PlayerTank;
import models.animate.Tank;
import models.environment.Obstacle;
import models.environment.Wall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WallTest {

    Obstacle obst;
    Wall wall;
    Shell shot;
    @BeforeEach
    public void runBefore() {

        obst = new Wall(30, 30, 10, Wall.Orientation.HORIZONTAL);
        wall = new Wall(25, 35, 5, Wall.Orientation.HORIZONTAL);
        shot = new Shell(new PlayerTank(0, 0, 0));

    }

    @Test
    public void testInit() {
        assertEquals(25, wall.getPositionX());
        assertEquals(35, wall.getPositionY());
        assertEquals(5, wall.getLength());
        assertEquals(Wall.Orientation.HORIZONTAL, wall.getOrientation());

    }

    @Test
    public void testBreakShell() {
        assertTrue(shot.isActive());
        wall.contactObject(shot);
        assertFalse(shot.isActive());
    }

    @Test
    public void testContactTankNoDeaths() {
        Tank tank = new PlayerTank(0, 0, 15);
        wall.contactObject(tank);
        assertTrue(tank.isAlive());  //should not kill the tank
    }

    @Test
    public void testContactOtherObject() {
        Obstacle otherWall = new Wall(15, 15, 15, Wall.Orientation.HORIZONTAL);
        wall.contactObject(otherWall);
    }

    @Test
    public void testObstacleImplementedUsingWall() {

        Shell shot1 = new Shell(new PlayerTank(0, 0, 0));
        assertTrue(shot1.isActive());

        obst.contactObject(shot1);
        assertFalse(shot1.isActive());

    }
}
