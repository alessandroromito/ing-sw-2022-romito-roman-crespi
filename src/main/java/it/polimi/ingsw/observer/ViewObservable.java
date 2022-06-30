package it.polimi.ingsw.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Class that can be observed from another class implementing the Observer interface.
 * This class is related to the graphic interface (CLI/GUI).
 */
public abstract class ViewObservable {
    protected final List<ViewObserver> observers = new ArrayList<>();

    /**
     * Adds an observer.
     * @param observer observer to be added to the list.
     */
    public void addObserver(ViewObserver observer) {
        observers.add(observer);
    }

    /**
     * Add a list of observer.
     * @param observers list of observer to be added to the list.
     */
    public void addAllObservers(List<ViewObserver> observers) { this.observers.addAll(observers); }

    /**
     * Removes an observer.
     * @param observer observer to be removed from the list.
     */
    public void removeObserver(ViewObserver observer) {
        observers.remove(observer);
    }

    /**
     * Removes all the observer of a list.
     * @param observerList list of observer to be removed from the local list.
     */
    public void removeAllObservers(List<ViewObserver> observerList) {
        observers.removeAll(observerList);
    }

    /**
     * Notifies all the observers contained in the local list using lambda.
     * @param lambda lambda to be called on the observer contained in the local observer list.
     */
    protected void notifyObserver(Consumer<ViewObserver> lambda) {
        for(ViewObserver observer : observers) {
            lambda.accept(observer);
        }
    }
}
