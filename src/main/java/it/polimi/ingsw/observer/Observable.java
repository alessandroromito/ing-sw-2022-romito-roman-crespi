package it.polimi.ingsw.observer;

import it.polimi.ingsw.network.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that can be observed from another class implementing the Observer interface.
 */
public class Observable {
    private final List<Observer> observers = new ArrayList<>();

    /**
     * Adds an observer.
     * @param obs observer to be added to the list.
     */
    public void addObserver(Observer obs) {
        observers.add(obs);
    }

    /**
     * Removes an observer.
     * @param obs observer to be removed from the list.
     */
    public void removeObserver(Observer obs) {
        observers.remove(obs);
    }

    /**
     * Notifies all the observers of the list.
     * @param message message sent to the observers of the observer list.
     */
    protected void notifyObserver(Message message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}
