package it.polimi.ingsw.observer;

import it.polimi.ingsw.network.message.Message;

/**
 * Interface implemented from class "listeners" to update the observer list of the Observable class.
 */
public interface Observer {
    void update(Message message);
}
