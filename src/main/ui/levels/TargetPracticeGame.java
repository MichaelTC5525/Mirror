package ui.levels;

import models.Shell;
import models.environment.PracticeTarget;
import models.animate.PlayerTank;
import ui.Main;

import java.util.ArrayList;
import java.util.List;

public class TargetPracticeGame extends GameMode {

    private List<PracticeTarget> practiceTargets;

    //EFFECTS: constructs the game, instantiates tank objects, and other necessary elements
    public TargetPracticeGame() {
        super();
        playerTank = new PlayerTank(Main.WIDTH / 2, Main.HEIGHT * 0.75, 15);
        practiceTargets = new ArrayList<>();
        shells = new ArrayList<>();
        for (double d = 1; d < 19; d++) {
            if (d % 2 == 1) {
                practiceTargets.add(new PracticeTarget(d * 100, 100));
            } else {
                practiceTargets.add(new PracticeTarget(d * 100, 200));
            }
        }
    }

    //REQUIRES: this game mode was selected through Main
    //MODIFIES: playerTank
    //EFFECTS: instantiates necessary game elements, setting itself as an observer to the active tank
    @Override
    public void setUp() {
        playerTank.addObserver(this);
        System.out.println("Creating a player tank in a simulated environment.");
        super.setUp();
    }

    //MODIFIES: playerTank, shells
    @Override
    public void updateScene() {
        super.updateScene();
        dequeueShellCollisions();
    }

    private void dequeueShellCollisions() {
        List<Shell> inactiveShells = new ArrayList<>();
        List<PracticeTarget> destroyedTargets = new ArrayList<>();
        for (Shell shot : shells) {
            for (PracticeTarget pt : practiceTargets) {
                checkShotToTargetCollision(shot, pt);
                if (!shot.isActive()) {
                    inactiveShells.add(shot);
                }
                if (pt.isDestroyed()) {
                    destroyedTargets.add(pt);
                }
            }
        }

        shells.removeAll(inactiveShells);
        practiceTargets.removeAll(destroyedTargets);
    }

    private void checkShotToTargetCollision(Shell shot, PracticeTarget pt) {
        if (shot.getShellPosX() >= pt.getPositionX() && shot.getShellPosX() <= pt.getPositionX() + gp.getTargetSize()
                && pt.getPositionY() <= shot.getShellPosY()
                && shot.getShellPosY() <= pt.getPositionY() + gp.getTargetSize()) {
            pt.contactObject(shot);
        }
    }

    //EFFECTS: returns the list of active practice targets in the game
    public List<PracticeTarget> getTargets() {
        return practiceTargets;
    }
}
