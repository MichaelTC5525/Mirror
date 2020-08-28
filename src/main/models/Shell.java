package models;

import models.animate.Tank;
import models.environment.Wall;
import ui.Main;

public class Shell {

    //tank that this particular shell was fired from
    private Tank sourceTank;

    private double shellPosX;
    private double shellPosY;
    private int shellSpeed;
    private double shellDirection;
    private boolean active;

    //REQUIRES: Tank must fireShot()
    //MODIFIES: this
    //EFFECTS: instantiates a new Shell object at sourceTank position with direction, and keeps note of the sourceTank
    public Shell(Tank sourceTank) {

        shellSpeed = 5;
        shellPosX = sourceTank.getTankX();
        shellPosY = sourceTank.getTankY();
        shellDirection = sourceTank.getBarrelDirection();
        active = true;
        this.sourceTank = sourceTank;

    }

    //REQUIRES: current shell is active and has come into contact with a Wall
    //MODIFIES: this
    //EFFECTS: registers the collision between this and Wall
    public void hitWall(Wall wall) {
        wall.contactObject(this);
    }

    //REQUIRES: Shell has been fired, shell is currently active
    //MODIFIES: this
    //EFFECTS: calculates the next x and y position for a travelling tank shell if within boundaries of the game
    public void getNextShell() {
        if (shellPosX < 0 || shellPosY < 0 || shellPosX > Main.WIDTH || shellPosY > Main.HEIGHT) {
            this.changeActive(false);
            removeSourceTank(getSourceTank());
        }

        shellPosX += (shellSpeed * Math.cos(shellDirection));
        shellPosY -= (shellSpeed * Math.sin(shellDirection));
    }

    //REQUIRES: Shell was fired from a live Tank object
    //MODIFIES: this
    //EFFECTS: sets the tank from which this shell was fired
    public void addSourceTank(Tank tank) {
        sourceTank = tank;
        tank.addShell(this);
    }

    //REQUIRES: Shell has become inactive (collision or out of bounds)
    //MODIFIES: this
    //EFFECTS: sets the tank from which this shell was fired to null
    public void removeSourceTank(Tank tank) {
        if (sourceTank != null) {
            sourceTank = null;
            tank.removeShell(this);
        }
    }

    //EFFECTS: returns the Tank that this Shell was fired from
    public Tank getSourceTank() {
        return sourceTank;
    }

    //EFFECTS: returns the current x-position of a shell
    public double getShellPosX() {
        return shellPosX;
    }

    //EFFECTS: returns the current y-position of a shell
    public double getShellPosY() {
        return shellPosY;
    }

    //EFFECTS: returns the current speed of the shell
    public int getShellSpeed() {
        return shellSpeed;
    }

    //EFFECTS: returns the direction of the shell's trajectory
    public double getShellDirection() {
        return shellDirection;
    }

    //EFFECTS: returns whether the shell is still active, i.e. still flying through the air or destroyed
    public boolean isActive() {
        return active;
    }

    //EFFECTS: sets the shell x-position
    public void setShellPosX(double shellPosX) {
        this.shellPosX = shellPosX;
    }

    //EFFECTS: sets the shell y-position
    public void setShellPosY(double shellPosY) {
        this.shellPosY = shellPosY;
    }

    //EFFECTS: sets the speed of the shell
    public void setShellSpeed(int shellSpeed) {
        this.shellSpeed = shellSpeed;
    }

    //MODIFIES: this
    //EFFECTS: sets the active state of the Shell to that indicated by parameter
    public void changeActive(boolean newState) {
        this.active = newState;
    }
}
