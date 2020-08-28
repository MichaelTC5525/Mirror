package models.animate;

import exception.OutOfBattlefieldException;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayerTank extends Tank {

    //EFFECTS: instantiates a new PlayerTank object, with appropriate position and speed
    public PlayerTank(double tankX, double tankY, int moveSpeed) {
        super(tankX, tankY, moveSpeed);
    }

    //REQUIRES: mouse has been clicked to initiate firing; this Tank is alive
    //MODIFIES: this
    //EFFECTS: prints a statement to the console, and delegates to the superclass' definition
    @Override
    public void fireShot() {

        System.out.println("Firing a shot at the enemy!");

        super.fireShot();

    }

    //REQUIRES: this Tank is alive
    //MODIFIES: this
    //EFFECTS: determines and increments the appropriate coordinate of the Tank for update based on keyboard inputs
    @Override
    public void getNextTank(int keyCode) throws OutOfBattlefieldException {
        switch (keyCode) {
            case KeyEvent.VK_W:
                tankY -= moveSpeed;
                break;
            case KeyEvent.VK_A:
                tankX -= moveSpeed;
                break;
            case KeyEvent.VK_D:
                tankX += moveSpeed;
                break;
            case KeyEvent.VK_S:
                tankY += moveSpeed;
                break;
            default:
                break;
        }
        super.getNextTank(keyCode);
    }

    //REQUIRES: Tank is alive
    //MODIFIES: this
    //EFFECTS: sets and returns the next aim direction angle in radians of the tank barrel
    public void getNextBarrel(Point currentMousePos) {
        double offsetX = currentMousePos.x - tankX;
        double offsetY = currentMousePos.y - tankY;
        double hypotenuse = Math.sqrt(offsetX * offsetX + offsetY * offsetY);
        if (offsetX < 0) {
            if (offsetY < 0) {
                this.barrelDirection = Math.PI - (Math.asin(-offsetY / hypotenuse));
                return;
            }
            this.barrelDirection = Math.PI + (Math.asin(offsetY / hypotenuse));
            return;
        }

        if (offsetY < 0) {
            this.barrelDirection = (Math.asin(-offsetY / hypotenuse));
            return;
        }
        this.barrelDirection = -(Math.asin(offsetY / hypotenuse));
    }

}
