package network;

import java.util.LinkedList;

/**
 * Created by quang on 08/18/16.
 */
public interface ClientInteractionInterface {

    void sendCommandToClient(long threadId, int command);

    void sendCommandToAllClients(int command);

    void sendDataToClient(long threadId, int command, LinkedList<String> data);

    void sendDataToAllClients(int command, LinkedList<String> data);
}
