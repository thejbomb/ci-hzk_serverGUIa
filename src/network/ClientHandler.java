package network;

import javafx.application.Platform;
import tool.Constants;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;

public class ClientHandler extends Thread implements ClientInteractionInterface {

    private BufferedReader in = null;
    private PrintWriter out = null;
    private final Socket CLIENT;
    private boolean initialized = false;
    private ObjectOutputStream os = null;
    private ObjectInputStream is = null;
    private ClientHandlerInterface mainControllerNotify = null;
    private InetAddress address = null;
    private int currentCommand = -1;
    //private final int ID;
    private LinkedList<String> inputQueue;
    private LinkedList<String> outputQueue;

    public ClientHandler(Socket clientSocket) {
        CLIENT = clientSocket;
    }

    public void setNotify(ClientHandlerInterface notify) {
        mainControllerNotify = notify;
    }

    private void init() {
        try {
            out = new PrintWriter(CLIENT.getOutputStream(), true);
            os = new ObjectOutputStream(CLIENT.getOutputStream());
            in = new BufferedReader(new InputStreamReader(CLIENT.getInputStream()));
            is = new ObjectInputStream(CLIENT.getInputStream());
            initialized = true;
        } catch (IOException e) {
            System.out.println(e);
        }
        address = CLIENT.getInetAddress();
        System.out.println("Remote address = " + address);
        mainControllerNotify.addClient(this); // add new client to clients list
    }

    public long getThreadID() {
        return getId();
    }

    public int compareTo(ClientHandler client) {
        if (client.address == address)
            return 0;
        else
            return -1;
    }

    @Override
    public void run() {
        init();
        boolean listening = true;
        try {
            while (listening) {
                String response = readFromClient();
                System.out.println("Thread " + getId() + " is working...");
                if (Integer.parseInt(response) == Constants.TRANMISSION_BEGIN) {
                    out.println(Constants.CLIENT_SEND_NEXT);
                    response = readFromClient();
                    while (Integer.parseInt(response) != Constants.TRANSMISSION_END) {
                        System.out.println("From client: " + response);
                        if (inputQueue == null)
                            inputQueue = new LinkedList<>();
                        inputQueue.add(response);
                        response = readFromClient();
                    }
                    int command = Integer.parseInt(inputQueue.getFirst());

                    inputQueue.removeFirst();

                    switch (command) {
                        case Constants.USER_CONNECT:
                            Platform.runLater(() -> {
                                mainControllerNotify.setClientInteractionInterface(this);
                                mainControllerNotify.setActiveThread(getId());
                                mainControllerNotify.connectUser(Integer.parseInt(inputQueue.getFirst()));
                                inputQueue = null;
                            });
                            break;
                        default:
                            Platform.runLater(() -> {
                                mainControllerNotify.handleClientData(command, inputQueue);
                                inputQueue = null;
                            });
                            break;
                    }
                } else if (Integer.parseInt(response) == Constants.SERVER_SEND_NEXT) {
                    while (!outputQueue.isEmpty()) {
                        System.out.println("To client: " + outputQueue.getFirst());
                        out.println(outputQueue.getFirst());
                        outputQueue.removeFirst();
                        if (outputQueue.isEmpty())
                            out.println(Constants.TRANSMISSION_END);
                    }
                    outputQueue = null;
                }

            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage() + " " + getClass());
        } finally {
            try {
                System.out.println("Closing network " + CLIENT.getInetAddress());
                CLIENT.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println(ex.getMessage() + " " + getClass());
            }
        }
    }

    private String readFromClient() throws IOException {
        return in.readLine();
    }

    private void writeToClient(String data) {
        out.println(data);
    }


    @Override
    public void writeToClient(int command) {
        writeToClient(command, new LinkedList<>());
    }

    @Override
    public void writeToClient(int command, LinkedList<String> data) {
        data.addFirst(Integer.toString(command));
        outputQueue = data;
        out.println(Constants.TRANMISSION_BEGIN);
    }

    @Override
    public void writeToClient(int command, LinkedList<String> data, int source) {

    }
}
