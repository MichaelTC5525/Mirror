package observer;

import models.Shell;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {

    private List<Observer> observers = new ArrayList<>();

    //MODIFIES: this
    //EFFECTS: adds an Observer to this Subject's list of Observers
    public void addObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    //EFFECTS: calls update on each of the Observers in the Subject's list of Observers
    public void notifyObservers(Shell shot) {
        for (Observer observer : observers) {
            observer.update(shot);
        }
    }

    //EFFECTS: returns the list of observers
    public List<Observer> getObservers() {
        return observers;
    }

}
