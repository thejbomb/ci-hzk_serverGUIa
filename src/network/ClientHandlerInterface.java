package network;

import java.util.LinkedList;

public interface ClientHandlerInterface {
    void connectUser(int userID);

    void setActiveThread(long threadID);

    void handleClientData(int command, LinkedList<String> data);
}
