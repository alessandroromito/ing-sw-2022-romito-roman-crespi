package it.polimi.ingsw.observer;

import it.polimi.ingsw.network.message.Message;

import java.util.ArrayList;
import java.util.List;

public abstract class ViewObservable {
    protected final List<ViewObserver> observers = new ArrayList<>();

    public void addObserver(ViewObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ViewObserver observer) {
        observers.remove(observer);
    }

    public void removeAllObservers(List<ViewObserver> observerList) {
        observers.removeAll(observerList);
    }

    protected void notifyObserver(Message message) {
        for(ViewObserver observer : observers) {
            observer.update(message);
        }
    }
}
