package models.environment;

import models.Shell;
import models.animate.Tank;

public class Wall implements Obstacle {

    private double positionX;
    private double positionY;
    private double length;
    private Orientation orientation;

    public enum Orientation { HORIZONTAL, VERTICAL }

    //EFFECTS: instantiates a new Wall object, with a given position, length, and Horizontal or Vertical Orientation
    public Wall(double positionX, double positionY, double length, Orientation orientation) {

        //represents the center point of the wall
        this.positionX = positionX;
        this.positionY = positionY;


        //length represents the distance from the center of the wall to an endpoint of the wall
        this.length = length;

        //represents up-down or left-right extensions
        this.orientation = orientation;
    }

    //REQUIRES: this Wall is in contact with something
    //MODIFIES: obj, if it is a Shell
    //EFFECTS: performs set behaviours depending on what type of object has contacted this wall
    @Override
    public void contactObject(Object obj) {
        if (obj instanceof Shell) {
            Shell shot = (Shell) obj;
            shot.changeActive(false);
            System.out.println("The shell broke against the wall.");
        } else if (obj instanceof Tank) {
            System.out.println("The tank couldn't get through the wall");

            //Stops the tank from moving through the Wall by not allowing incrementation
            ((Tank) obj).setTankX(((Tank) obj).getTankX());
            ((Tank) obj).setTankY(((Tank) obj).getTankY());
        }
    }

    //EFFECTS: returns the x-position of the Wall
    public double getPositionX() {
        return positionX;
    }

    //EFFECTS: returns the y-position of the Wall
    public double getPositionY() {
        return positionY;
    }

    //EFFECTS: returns the length of this Wall
    public double getLength() {
        return length;
    }

    //EFFECTS: returns the current orientation of the Wall
    public Orientation getOrientation() {
        return orientation;
    }
}
