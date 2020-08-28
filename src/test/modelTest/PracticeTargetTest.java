package modelTest;

import models.animate.PlayerTank;
import models.environment.Destructible;
import models.environment.PracticeTarget;
import models.Shell;
import models.environment.Wall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PracticeTargetTest {

    Destructible destructible;
    PracticeTarget pt;

    @BeforeEach
    public void runBefore() {

        destructible = new PracticeTarget(30, 30);
        pt = new PracticeTarget(50, 55);

    }

    @Test
    public void testInit() {
        assertFalse(pt.isDestroyed());
        assertEquals(50, pt.getPositionX());
        assertEquals(55, pt.getPositionY());
    }

    @Test
    public void testBreak() {
        assertFalse(pt.isDestroyed());
        pt.breakDown();
        assertTrue(pt.isDestroyed());
    }

    @Test
    public void testContactAndBreakShell() {
        Shell shot = new Shell(new PlayerTank(10, 10, 10));
        assertTrue(shot.isActive());
        assertFalse(pt.isDestroyed());
        pt.contactObject(shot);
        assertFalse(shot.isActive());
        assertTrue(pt.isDestroyed());
    }

    @Test
    public void testContactPlayerTankWithoutShellFiring() {

        assertFalse(pt.isDestroyed());

        pt.contactObject(new PlayerTank(10, 10, 10));

        assertTrue(pt.isDestroyed());
    }

    @Test
    public void testContactObjectNotShellOrPlayerTank() {
        Wall noContact = new Wall(15, 15, 10, Wall.Orientation.HORIZONTAL);
        assertFalse(pt.isDestroyed());
        pt.contactObject(noContact);
        assertFalse(pt.isDestroyed());
    }

    @Test
    public void testDestructibleBreakDownImplementedByPracticeTarget() {

        assertFalse(destructible.isDestroyed());
        destructible.breakDown();
        assertTrue(destructible.isDestroyed());

    }

}
