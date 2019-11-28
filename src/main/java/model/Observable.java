package main.java.model;

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
     * @param o Observer to be attached.
     */
    public void attach(Observer o){
        this.observers.add(o);
    }

    /**
     * Detach an observer.
     * @param o Observer to be detached.
     */
    public void detach(Observer o){
        if(!observers.isEmpty()){
            observers.remove(o);
        }
    }

    /**
     * Notifies the observers.
     * @param o Obserable object to help update the observers.
     */
    public void notifyObservers(Observable o){
        for(Observer observer : observers){
            observer.update(o);
        }
    }

    /**
     * Notifies the observers.
     * @param message Message to be flashed on receiving the notifcation.
     */
    public void notifyObservers(String message){
        for(Observer observer : observers){
            observer.update(message);
        }
    }
}