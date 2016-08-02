package network;

import javafx.application.Platform;
import tool.Constants;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

public class ClientHandler extends Thread implements ClientInteractionInterface {

    private BufferedReader in = null;
    private PrintWriter out = null;
    private final Socket CLIENT;
    private ClientHandlerInterface mainController = null;
    private LinkedList<String> inputQueue;
    private LinkedList<String> outputQueue;

    public ClientHandler(Socket clientSocket) {
        CLIENT = clientSocket;
    }

    public void setNotify(ClientHandlerInterface notify) {
        mainController = notify;
    }

    private void init() {
        try {
            out = new PrintWriter(CLIENT.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(CLIENT.getInputStream()));
        } catch (IOException e) {
            System.out.println(e);
        }
        int address = CLIENT.getPort();
        System.out.println("Remote address = " + address);
        mainController.addClient(this); // add new client to clients list
    }

    public long getThreadID() {
        return getId();
    }

    @Override
    public void run() {
        init();
        boolean listening = true;
        try {
            while (listening) {
                String response = readFromClient();
                System.out.println(response);
                System.out.println("Thread " + getId() + " is working...");
                if (response.compareTo(Constants.TRANSMISSION_BEGIN) == 0) {
                    out.println(Constants.CLIENT_SEND_NEXT);
                    response = readFromClient();
                    while (response.compareTo(Constants.TRANSMISSION_END) != 0) {
                        System.out.println("From client: " + response);
                        if (inputQueue == null)
                            inputQueue = new LinkedList<>();
                        inputQueue.add(response);
                        response = readFromClient();
                    }
                    int command = Integer.parseInt(inputQueue.getFirst());
                    System.out.println("Client command: " + Integer.toHexString(command));
                    inputQueue.removeFirst();

                    switch (command) {
                        case Constants.USER_CONNECT:
                            Platform.runLater(() -> {
                                mainController.setClientInteractionInterface(this);
                                System.out.println("Thread " + getId() + " is working..1.");
                                mainController.setActiveThread(getId());
                                mainController.connectUser(Integer.parseInt(inputQueue.getFirst()));
                                inputQueue = null;
                            });
                            break;
                        default:
                            Platform.runLater(() -> {
                                mainController.setActiveThread(getId());
                                mainController.handleClientData(command, inputQueue);
                                inputQueue = null;
                            });
                            break;
                    }
                } else if (response.compareTo(Constants.SERVER_SEND_NEXT) == 0) {
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

    @Override
    public void writeToClient(int command) {
        writeToClient(command, new LinkedList<>());
    }

    @Override
    public void writeToClient(int command, LinkedList<String> data) {
        data.addFirst(Integer.toString(command));
        outputQueue = data;
        System.out.println("SERVER -> CLIENT BEGIN TRANSMISSION");
        out.println(Constants.TRANSMISSION_BEGIN);
    }

}
