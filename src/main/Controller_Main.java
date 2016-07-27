package main;

import data.DataException;
import data.UserData;
import data.UserDataCollection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import network.ClientHandler;
import network.Notify;
import tool.Constants;
import tool.FileInput;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller_Main implements Notify, Initializable {

    @FXML
    private Button bt_start;
    @FXML
    private VBox vb_usersList;
    @FXML
    private ScrollPane sp_usersList;
    @FXML
    private TableView<UserData> tb_usersList;

    private UserDataCollection userDataCollection;

    private ObservableList<UserData> usersData = FXCollections.observableArrayList();

    private Notify clientNotify;

    private List<ClientHandler> clients = null;

    private long activeThreadId = -1;


    public Controller_Main() {
        initUsersData();
    }

    private void setClientNotify(Notify notify) {
        this.clientNotify = notify;
    }

    public void init() {
        Pane header = (Pane) tb_usersList.lookup("TableHeaderRow");
        if (header.isVisible()) {
            header.setMaxHeight(0);
            header.setMinHeight(0);
            header.setPrefHeight(0);
            header.setVisible(false);
        }

        TableColumn nameColumn = (TableColumn) tb_usersList.getColumns().get(0);
        TableColumn levelColumn = (TableColumn) tb_usersList.getColumns().get(1);
        TableColumn idColumn = (TableColumn) tb_usersList.getColumns().get(2);
        TableColumn statusColumn = (TableColumn) tb_usersList.getColumns().get(3);
        nameColumn.setCellValueFactory(new PropertyValueFactory<UserData, String>("USER_NAME"));
        levelColumn.setCellValueFactory(new PropertyValueFactory<UserData, String>("USER_LEVEL"));
        idColumn.setCellValueFactory(new PropertyValueFactory<UserData, String>("USER_ID"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<UserData, String>("user_status"));

        tb_usersList.setItems(usersData);

    }

    private void updateTable(int ID) throws DataException {
        boolean found = false;
        for (int i = 0; i < usersData.size(); i++) {
            if (usersData.get(i).getUSER_ID() == ID) {
                tb_usersList.getItems().get(i).setUserStatus(true);
                tb_usersList.getItems().set(i, tb_usersList.getItems().get(i));
                clientNotify.takeNotice(Constants.LOGIN_SUCCESS);
                found = true;
            }
        }
        if (!found)
            throw new DataException("Error 999");
    }


    private void initUsersData() {
        userDataCollection = new UserDataCollection();
        FileInput fs = new FileInput();
        String[] users;
        try {
            users = fs.readFile("src/asset/Users");
        } catch (IOException e) {
            e.printStackTrace();
            users = new String[1];
            users[0] = "N/A#N/A#N/A#";
        }
        String[][] _users = fs.parseUsersData(users);
        for (String[] _user : _users) {
            int index = 0;
            //userDataCollection.add(new UserData(_user[index++], Integer.parseInt(_user[index++]), Integer.parseInt(_user[index])));
            usersData.add(new UserData(_user[index++], Integer.parseInt(_user[index++]), Integer.parseInt(_user[index]), false));
        }

    }

    @FXML
    private void handleMouseClick(MouseEvent e) {
        if (e.getSource() == bt_start) {
            sendDataToAllClients(Constants.BEGIN_COMP);
        }
    }

    private void sendDataToAllClients(int command) {
        for (ClientHandler ch : clients) {
            ch.takeNotice(command);
        }
    }

    @Override
    public void takeNotice() {

    }

    @Override
    public void takeNotice(int command) {

    }


    @Override
    public void takeNotice(int command, Object data) {
        switch (command) {
            case Constants.USER_CONNECT:
                try {
                    updateTable(Integer.parseInt((String) data));
                } catch (DataException ex) {
                    for (ClientHandler ch : clients) {
                        if (ch.getThreadID() == activeThreadId)
                            ch.takeNotice(Constants.ERROR, ex.getMessage());
                    }
                }
                break;
            case 2:
                break;
            case Constants.SET_ACTIVE_THREAD:
                activeThreadId = (long) data;
                break;
            case Constants.SET_NOTIFY:
                setClientNotify((Notify) data);
                break;
            case Constants.ADD_CLIENT:
                if (clients == null)
                    clients = new LinkedList<>();
                clients.add((ClientHandler) data);
                break;
            default:
                break;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
