package models.environment;

import models.Shell;
import models.animate.PlayerTank;

public class PracticeTarget implements Obstacle, Destructible {

    private double positionX;
    private double positionY;
    private boolean destroyed;

    //EFFECTS: instantiates a new PracticeTarget, with given position coordinates and a non-destroyed condition
    public PracticeTarget(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        destroyed = false;
    }

    //REQUIRES: in contact with an object
    //MODIFIES: this, obj (if Shell)
    //EFFECTS: performs specific actions depending on the class of the object
    public void contactObject(Object obj) {
        if (obj instanceof Shell) {
            ((Shell) obj).changeActive(false);
            this.breakDown();
        } else if (obj instanceof PlayerTank) {
            System.out.println("The tank rammed right through us.");
            System.out.println("I guess he just felt lazy.");
            this.breakDown();
        }
    }


    //MODIFIES: this
    //EFFECTS: signifies the breaking down of a target when hit by a Shell or run over by a Tank
    @Override
    public void breakDown() {
        this.destroyed = true;
    }

    //EFFECTS: returns whether the PracticeTarget has been destroyed
    public boolean isDestroyed() {
        return destroyed;
    }

    //EFFECTS: returns the x-position of the practice target
    public double getPositionX() {
        return positionX;
    }

    //EFFECTS: returns the y-position of the practice target
    public double getPositionY() {
        return positionY;
    }
}
