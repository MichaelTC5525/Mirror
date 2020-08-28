package models.animate;

import exception.OutOfBattlefieldException;

import java.awt.event.KeyEvent;

public class EnemyTank extends Tank {

    //EFFECTS: instantiates a new EnemyTank, with coordinates and a movement speed
    public EnemyTank(double tankX, double tankY, int moveSpeed) {
        super(tankX, tankY, moveSpeed);
    }

    //REQUIRES: mouse has been clicked to initiate firing; this Tank is alive
    //MODIFIES: this
    //EFFECTS: prints a statement informing console of shot, and delegates to the superclass' definition
    @Override
    public void fireShot() {

        System.out.println("Firing a shot at the player!");

        super.fireShot();

    }

    //REQUIRES: a keyboard key has been pressed, this Tank is alive
    //MODIFIES: this
    //EFFECTS: determines and increments appropriate coordinate of the Tank for update based on keyboard inputs
    @Override
    public void getNextTank(int keyCode) throws OutOfBattlefieldException {
        switch (keyCode) {
            case KeyEvent.VK_W:
                tankY += moveSpeed;
                break;
            case KeyEvent.VK_A:
                tankX += moveSpeed;
                break;
            case KeyEvent.VK_D:
                tankX -= moveSpeed;
                break;
            case KeyEvent.VK_S:
                tankY -= moveSpeed;
                break;
            default:
                break;
        }
        super.getNextTank(keyCode);
    }

    //REQUIRES: PlayerTank is alive, clock tick for update
    //MODIFIES: this
    //EFFECTS: calculates the value of the enemy tank barrel direction to be opposite to the playerTank barrel direction
    public void getNextBarrel(PlayerTank playerTank) {
        this.barrelDirection = playerTank.barrelDirection + Math.PI;
    }
}
