package network;

import data.*;
import javafx.application.Platform;
import tool.Constants;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;

public class ClientHandler extends Thread implements Notify {

    private BufferedReader in = null;
    private PrintWriter out = null;
    private final Socket CLIENT;
    private boolean initialized = false;
    private ObjectOutputStream os = null;
    private ObjectInputStream is = null;
    private Notify notify = null;
    private InetAddress address = null;
    //private final int ID;

    public ClientHandler(Socket clientSocket) {
        CLIENT = clientSocket;
    }

    public void setNotifyObject(Notify notify) {
        this.notify = notify;
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
        notify.takeNotice(Constants.ADD_CLIENT, this); // add new client to clients list
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

    public boolean isInitialized() {
        return initialized;
    }

    public void writeToClient(String text) {
        out.println(text);
    }

    public void writeToClient(int command) {
        out.println(command);

    }

    private void writeToClient(Object data) {
        try {
            os.writeObject(data);
        } catch (IOException ex) {
            System.out.println(ex.getMessage() + " " + getClass());
        }
    }

    public void writeToClient(int command, Object data) {
        switch (command) {
            case 1:
                break;
            case Constants.ERROR: // error message
                out.println(command);
                out.println((String) data);
                break;
            default:
                break;
        }
    }


    private String readMessageFromClient() {
        try {
            return in.readLine();
        } catch (IOException e) {
            System.out.println(e);
            return "-1";
        }


    }

    private int readIntFromClient() {
        try {
            return Integer.parseInt(in.readLine());
        } catch (IOException e) {
            System.out.println(e);
            return -1;
        }
    }

    @Override
    public void run() {
        init();
        try {
            while (true) {
                String response = in.readLine();
                int command = Integer.parseInt(response);
                System.out.println("Thread " + getId() + " is working...");
                if (command > 999) {
                    switch (command) {
                        case Constants.USER_CONNECT:
                            String data = in.readLine();
                            Platform.runLater(() -> {
                                notify.takeNotice(Constants.SET_NOTIFY, this);
                                notify.takeNotice(Constants.SET_ACTIVE_THREAD, getId());
                                notify.takeNotice(command, data);


                            });
                            break;
                        case 2:
                            String sData = (String) is.readObject();
                            Platform.runLater(() -> {
                                notify.takeNotice(command, sData);
                            });
                            break;
                        default:
                            break;
                    }
                } else {
                    Platform.runLater(() -> {
                        notify.takeNotice(command);
                    });
                }


            }
        } catch (IOException | ClassNotFoundException ex) {
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

    @Override
    public void takeNotice() {

    }

    @Override
    public void takeNotice(int command) {
        writeToClient(command);
    }

    @Override
    public void takeNotice(int command, Object data) {
        switch (command) {
            case 1:
                break;
            case Constants.ERROR:
                writeToClient(command, data);
                break;
            default:
                break;
        }
    }
}
