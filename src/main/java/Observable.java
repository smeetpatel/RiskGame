package main.java;

import java.util.ArrayList;

/**
 * Class to assist attaching, detaching, and notifying observers.
 */
public class Observable {
    /**
     * Maintains list of observers.
     */
    private ArrayList<Observer> observers  = new ArrayList<Observer>();

    /**
     * Attaches an observer.
     */
    public void attach(Observer o){
        this.observers.add(o);
    }

    /**
     * Detach an observer.
     */
    public void detach(Observer o){
        if(!observers.isEmpty()){
            observers.remove(o);
        }
    }

    /**
     * Notifies the observers.
     */
    public void notifyObservers(Observable observable){
        for(Observer observer : observers){
            observer.update(observable);
        }
    }
}