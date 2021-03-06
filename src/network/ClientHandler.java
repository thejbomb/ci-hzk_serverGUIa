package network;

import javafx.application.Platform;
import tool.Constants;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class ClientHandler extends Thread {

    private BufferedReader in = null;
    private PrintWriter out = null;
    private final Socket CLIENT;
    private ClientHandlerInterface mainController = null;
    private LinkedList<String> inputQueue;
    private LinkedList<String> outputQueue;
    private LinkedList<LinkedList<String>> dataQueue;

    private boolean tranmissionOver = true;

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

                System.out.println("RESPONSE FROM CLIENT: " + response + " on thread " + getThreadID());
                if (response.compareTo(Constants.TRANSMISSION_BEGIN) == 0) {
                    out.println(Constants.CLIENT_SEND_NEXT);
                    response = readFromClient();
                    while (response.compareTo(Constants.TRANSMISSION_END) != 0) {
                        System.out.println("From client on thread " + getThreadID() + ": " + response);
                        if (inputQueue == null)
                            inputQueue = new LinkedList<>();
                        inputQueue.add(response);
                        response = readFromClient();
                    }
                    int command = 0;
                    try {
                        command = Integer.parseInt(inputQueue.getFirst());
                    } catch (NumberFormatException ex) {

                    }
                    System.out.println("Client command: " + Integer.toHexString(command) + " on thread " + getThreadID());
                    inputQueue.removeFirst();

                    switch (command) {
                        case Constants.USER_CONNECT:
                            Platform.runLater(() -> {
                                mainController.setActiveThread(getId());
                                mainController.connectUser(Integer.parseInt(inputQueue.getFirst()));
                                inputQueue = null;
                            });
                            break;
                        default:
                            int finalCommand = command;
                            Platform.runLater(() -> {
                                mainController.setActiveThread(getId());
                                mainController.handleClientData(finalCommand, inputQueue);
                                inputQueue = null;
                            });
                            break;
                    }

                } else if (outputQueue != null && response.compareTo(Constants.SERVER_SEND_NEXT) == 0) {

                    while (outputQueue != null && !outputQueue.isEmpty()) {
                        System.out.println("Thread " + getThreadID() + " is trying to transmit...");
                        try {
                            System.out.println("To client on thread " + getThreadID() + ": " + outputQueue.getFirst());
                            out.println(outputQueue.getFirst());
                        } catch (NoSuchElementException ex) {
                            ex.printStackTrace();
                        }
                        outputQueue.removeFirst();
                        if (outputQueue.isEmpty()) {
                            out.println(Constants.TRANSMISSION_END);
                            System.out.println("TRANSMISSION_END ON THREAD" + getThreadID());
                            if (dataQueue != null && dataQueue.size() != 0)
                                sendDataInQueue();
                            else
                                tranmissionOver = true;

                        }
                    }
                    outputQueue = null;
                }


            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage() + " " + getClass());
        } finally {
            try {
                System.out.println("Closing network " + CLIENT.getInetAddress() + " with thread " + getThreadID());
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

    public void writeToClient(int command) {
        writeToClient(command, new LinkedList<>());
    }

    public void writeToClient(int command, LinkedList<String> data) {
        if (tranmissionOver) {
            System.out.println("SERVER IS FREE ON THREAD " + getThreadID());
            tranmissionOver = false;
            outputQueue = new LinkedList<>();
            outputQueue.addFirst(Integer.toString(command));
            outputQueue.addAll(data);
            System.out.println("CONTENT IN OUTPUT QUEUE ON THREAD " + getThreadID() + ": " + outputQueue);
            System.out.println("SERVER -> CLIENT BEGIN TRANSMISSION" + " on thread " + getThreadID());
            System.out.println("CH To Client: command = " + Integer.toHexString(command) + " | levelData = " + data + " on thread " + getThreadID());
            out.println(Constants.TRANSMISSION_BEGIN);
        } else {
            System.out.println("SERVER IS BUSY. ADDING DATA TO DATA QUEUE ON THREAD" + getThreadID());
            if (dataQueue == null)
                dataQueue = new LinkedList<>();
            LinkedList<String> ll = new LinkedList<>();
            ll.addFirst(Integer.toString(command));
            ll.addAll(data);
            dataQueue.add(ll);
        }
    }

    private void sendDataInQueue() {
        if (dataQueue != null && dataQueue.size() != 0) {
            outputQueue = new LinkedList<>();
            LinkedList<String> data = dataQueue.removeFirst();
            outputQueue.addAll(data);
            System.out.println("CONTENT IN OUTPUT QUEUE ON THREAD " + getThreadID() + ": " + outputQueue);
            System.out.println("SERVER -> CLIENT BEGIN TRANSMISSION (IN DATA QUEUE)" + " on thread " + getThreadID());
            System.out.println("CH To Client: command = " + Integer.toHexString(Integer.parseInt(data.getFirst())) + " | levelData = " + data.getLast() + " on thread " + getThreadID());
            out.println(Constants.TRANSMISSION_BEGIN);
        }
    }
}
