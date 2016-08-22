package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;

public class Server implements Runnable, ClientInteractionInterface {
    private final int PORT_NUMBER;
    private boolean listening = false;
    private Thread serverThread = null;
    private ClientHandlerInterface mainControllerNotify = null;
    private LinkedList<ClientHandler> clients;

    public Server(int portNumber, boolean listening) {
        PORT_NUMBER = portNumber;
        this.listening = listening;
        clients = new LinkedList<>();
        serverThread = new Thread(this);
    }

    public void start(ClientHandlerInterface notify) {
        mainControllerNotify = notify;
        serverThread.start();
    }


    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(PORT_NUMBER);
            while (listening) {

                ClientHandler newClient = new ClientHandler(server.accept());
                clients.add(newClient);
                newClient.setNotify(mainControllerNotify);
                newClient.start();
                System.out.println("Active Thread: " + Thread.activeCount());

                System.out.println(server.getInetAddress() + " connected on thread " + newClient.getId());

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void sendCommandToClient(long threadId, int command) {
        sendDataToClient(threadId, command, new LinkedList<>());
    }

    @Override
    public void sendCommandToAllClients(int command) {
        System.out.println("BEGINNING TO SEND COMMAND TO ALL CLIENTS");
        for (ClientHandler ch : clients)
            sendCommandToClient(ch.getThreadID(), command);
        System.out.println("COMMAND SENT TO ALL CLIENTS");
    }

    @Override
    public void sendDataToClient(long threadId, int command, LinkedList<String> data) {
        System.out.println("S DATA SET: " + data);
        for (ClientHandler ch : clients)
            if (ch.getThreadID() == threadId)
                ch.writeToClient(command, data);


    }

    @Override
    public void sendDataToAllClients(int command, LinkedList<String> data) {
        System.out.println("BEGINNING TO SEND DATA TO ALL CLIENTS");
        for (ClientHandler ch : clients)
            //sendDataToClient(ch.getThreadID(), command, data);
            ch.writeToClient(command, data);
        System.out.println("DATA SENT TO ALL CLIENTS");
    }

}
