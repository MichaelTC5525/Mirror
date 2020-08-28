package models.animate;

import exception.OutOfBattlefieldException;
import models.Shell;
import observer.Subject;
import ui.Main;

import java.util.ArrayList;
import java.util.List;

public abstract class Tank extends Subject {

    //list of active shells this tank has fired
    private List<Shell> shellList;

    double tankX;
    double tankY;
    int moveSpeed;
    private boolean alive;
    double barrelDirection;

    //EFFECTS: instantiates a new Tank object
    public Tank(double tankX, double tankY, int moveSpeed) {
        this.tankX = tankX;
        this.tankY = tankY;
        this.moveSpeed = moveSpeed;
        this.alive = true;
        this.barrelDirection = 0;
        this.shellList = new ArrayList<>();
    }

    //REQUIRES: Tank object spawned and alive
    //MODIFIES: this, shot
    //EFFECTS: initializes a shell being shot from the tank, and notifies Observers
    public void fireShot() {
        Shell shot = new Shell(this);
        addShell(shot);
        notifyObservers(shot);
    }

    //REQUIRES: Tank is alive
    //MODIFIES: this
    //EFFECTS: determines whether or not the Tank is outside the boundaries of the game
    public void getNextTank(int keyCode) throws OutOfBattlefieldException {
        if (tankX < 0 || tankX > Main.WIDTH || tankY < 0 || tankY > Main.HEIGHT) {
            throw new OutOfBattlefieldException();
        }
    }

    //REQUIRES: Tank fired a shot
    //MODIFIES: this
    //EFFECTS: adds a fired shot to a list of Shells fired from this tank
    public void addShell(Shell shot) {
        if (!this.shellList.contains(shot)) {
            shellList.add(shot);
            shot.addSourceTank(this);
        }
    }

    //REQUIRES: Shell fired from this tank has become inactive
    //MODIFIES: this
    //EFFECTS: removes a previously active shell fired from this tank from its list of fired shells
    public void removeShell(Shell shot) {
        if (this.shellList.contains(shot)) {
            shellList.remove(shot);
            shot.removeSourceTank(this);
        }
    }

    //REQUIRES: there is a shell in contact with this Tank object
    //MODIFIES: this
    //EFFECTS: this Tank dies (becomes inactive) if it is contacted by a shell they did not fire
    public void checkDeath(Shell shot) {
        if (!this.shellList.contains(shot)) {
            setAlive(false);
            shot.changeActive(false);
            shot.removeSourceTank(shot.getSourceTank());
        }
    }

    //EFFECTS: returns the speed of the tank at a current point in time
    public int getTankSpeed() {
        return moveSpeed;
    }

    //EFFECTS: sets the current speed of the tank
    public void setTankSpeed(int speed) {
        moveSpeed = speed;
    }

    //EFFECTS: returns the current x-position of the tank
    public double getTankX() {
        return tankX;
    }

    //EFFECTS: sets the current x-position of the tank
    public void setTankX(double x) {
        tankX = x;
    }

    //EFFECTS: returns the current y-position of the tank
    public double getTankY() {
        return tankY;
    }

    //EFFECTS: sets the current y-position of the tank
    public void setTankY(double y) {
        tankY = y;
    }

    //EFFECTS: returns whether or not the current tank is still alive; i.e. not yet destroyed
    public boolean isAlive() {
        return alive;
    }

    //EFFECTS: sets whether the current tank is still alive
    public void setAlive(boolean status) {
        this.alive = status;
    }

    //EFFECTS: returns the aiming direction of the tank barrel in radians
    public double getBarrelDirection() {
        return barrelDirection;
    }

    //EFFECTS: returns the current list of active shells fired from this tank
    public List<Shell> getShellList() {
        return shellList;
    }
}
