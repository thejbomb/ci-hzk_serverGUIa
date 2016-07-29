package network;

import java.io.IOException;
import java.net.ServerSocket;

public class Server implements Runnable {
    private final int PORT_NUMBER;
    private boolean listening = false;
    private Thread serverThread = null;
    private ClientHandlerInterface mainControllerNotify = null;

    public Server(int portNumber, boolean listening) {
        PORT_NUMBER = portNumber;
        this.listening = listening;
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
                newClient.setNotify(mainControllerNotify);
                newClient.start();
                System.out.println("Active Thread: " + Thread.activeCount());

                System.out.println(server.getInetAddress() + " connected on thread " + newClient.getId());

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
