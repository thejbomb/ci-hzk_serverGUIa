package main;

import data.HzkException;
import data.UserData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.round1.Round1Controller;
import main.round2.Round2Controller;
import network.ClientHandler;
import network.ClientHandlerInterface;
import network.ClientInteractionInterface;
import tool.Constants;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements ClientInteractionInterface, Initializable, ClientHandlerInterface {
    @FXML
    private Button bt_start;
    @FXML
    private TableView<UserData> tb_usersList;
    @FXML
    private AnchorPane ap_round1Interface;
    @FXML
    private AnchorPane ap_round2Interface;
    @FXML
    private AnchorPane ap_root;
    @FXML
    private Label lb_cnTitle;
    @FXML
    private Label lb_enTitle;
    @FXML
    private Label lb_name;
    @FXML
    private Label lb_level;
    @FXML
    private Label lb_status;
    @FXML
    private Round1Controller ap_round1InterfaceController;
    @FXML
    private Round2Controller ap_round2InterfaceController;

    private ClientInteractionInterface clientHandlerNotify;

    private List<ClientHandler> clients = null;

    private long activeThreadId = -1;

    public MainController() {
        //initUsersData();
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
        levelColumn.setCellValueFactory(new PropertyValueFactory<UserData, String>("userLevel"));
        idColumn.setCellValueFactory(new PropertyValueFactory<UserData, String>("USER_ID"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<UserData, String>("user_status"));

        bt_start.setId(Constants.FONT_BUTTON);
        lb_name.setId(Constants.FONT_TABLE_HEADER);
        lb_level.setId(Constants.FONT_TABLE_HEADER);
        lb_level.setId(Constants.FONT_TABLE_HEADER);
        lb_status.setId(Constants.FONT_TABLE_HEADER);
        lb_cnTitle.setId(Constants.CN_FONT_TITLE);
        lb_enTitle.setId(Constants.EN_FONT_TITLE);

        tb_usersList.setItems(Main.USERS_LIST);
    }

    private void updateTable(int ID) throws HzkException {
        boolean found = false;
        for (int i = 0; i < Main.USERS_LIST.size(); i++) {
            if (Main.USERS_LIST.get(i).getUSER_ID() == ID) {
                tb_usersList.getItems().get(i).setUserStatus(true);
                tb_usersList.getItems().set(i, tb_usersList.getItems().get(i));
                clientHandlerNotify.writeToClient(Constants.LOGIN_SUCCESS);
                found = true;
            }
        }
        if (!found)
            throw new HzkException("User ID Not Found");
    }

    @FXML
    private void handleMouseClick(MouseEvent e) throws Exception {
        if (e.getSource() == bt_start) {
            sendDataToAllClients(Constants.BEGIN_COMP);
            ap_root.setVisible(false);
            ap_round2Interface.setVisible(true);
            ap_round2InterfaceController.init();
            ap_round2InterfaceController.setStyle();
        }
    }

    private void sendDataToAllClients(int command) {
        for (ClientHandler ch : clients) {
            ch.writeToClient(command);
        }
    }

    @Override
    public void writeToClient(int command) {
        switch (command) {
            case Constants.BEGIN_R1L1:
                sendDataToAllClients(command);
                break;
            case Constants.BEGIN_R1L2:
                sendDataToAllClients(command);
                break;
            case Constants.BEGIN_R1L3:
                sendDataToAllClients(command);
                break;
            case Constants.DIS_R1L1_EX:
                sendDataToAllClients(command);
                break;
            default:
                break;
        }
    }

    @Override
    public void writeToClient(int command, LinkedList<String> data) {

    }

    @Override
    public void writeToClient(int command, LinkedList<String> data, int source) {

    }

    @Override
    public void addClient(ClientHandler client) {
        if (clients == null)
            clients = new LinkedList<>();
        clients.add(client);
    }

    @Override
    public void setClientInteractionInterface(ClientInteractionInterface client) {
        clientHandlerNotify = client;
    }

    @Override
    public void connectUser(int userID) {
        try {
            updateTable(userID);
        } catch (HzkException ex) {
            for (ClientHandler client : clients) {
                if (client.getThreadID() == activeThreadId)
                    client.writeToClient(Constants.ERROR_ID_NOT_FOUND);
            }
        }
    }

    @Override
    public void setActiveThread(long threadID) {
        activeThreadId = threadID;
    }

    @Override
    public void handleClientData(int command, LinkedList<String> data) {

    }

    @Override
    public void handleClientData(int command, LinkedList<String> data, int source) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AnchorPane.setBottomAnchor(ap_round2Interface, 0.0);
        AnchorPane.setTopAnchor(ap_round2Interface, 0.0);
        AnchorPane.setLeftAnchor(ap_round2Interface, 0.0);
        AnchorPane.setRightAnchor(ap_round2Interface, 0.0);
        ap_round2Interface.setVisible(false);

        ap_round2InterfaceController.setNotify(this);
    }
}
