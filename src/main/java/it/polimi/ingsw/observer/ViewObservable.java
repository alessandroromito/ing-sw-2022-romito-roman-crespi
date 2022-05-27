package it.polimi.ingsw.observer;

import it.polimi.ingsw.network.message.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class ViewObservable {
    protected final List<ViewObserver> observers = new ArrayList<>();

    public void addObserver(ViewObserver observer) {
        observers.add(observer);
    }

    public void addAllObservers(List<ViewObserver> observers) { observers.addAll(observers); }

    public void removeObserver(ViewObserver observer) {
        observers.remove(observer);
    }

    public void removeAllObservers(List<ViewObserver> observerList) {
        observers.removeAll(observerList);
    }

    protected void notifyObserver(Consumer<ViewObserver> lambda) {
        for(ViewObserver observer : observers) {
            lambda.accept(observer);
        }
    }
}
