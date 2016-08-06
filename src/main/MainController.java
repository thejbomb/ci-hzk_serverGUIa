package main;

import data.*;
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

public class MainController implements Initializable, ClientHandlerInterface {
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

    private static List<ClientHandler> clients = null;

    protected LinkedList<UserDataLevel1> level1Users = null;
    protected LinkedList<UserDataLevel2> level2Users = null;
    protected LinkedList<UserDataLevel3> level3Users = null;

    private long activeThreadId = -1;

    private int currentRound = 0;


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
        idColumn.setCellValueFactory(new PropertyValueFactory<UserData, Integer>("USER_ID"));
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
                clientHandlerNotify.writeToClient(Constants.LOGIN_SUCCESS, tb_usersList.getItems().get(i).getBasicInfo());
                found = true;
            }
        }
        if (!found)
            throw new HzkException("User ID Not Found");
    }

    private void initUserData() {
        if (level1Users == null)
            level1Users = new LinkedList<>();
        if (level2Users == null)
            level2Users = new LinkedList<>();
        if (level3Users == null)
            level3Users = new LinkedList<>();
        for (UserData ud : Main.USERS_LIST) {
            if (ud.getUSER_LEVEL() == 1)
                level1Users.add(new UserDataLevel1(ud.getUSER_NAME(), ud.getUSER_LEVEL(), ud.getUSER_ID()));
            else if (ud.getUSER_LEVEL() == 2)
                level2Users.add(new UserDataLevel2(ud.getUSER_NAME(), ud.getUSER_LEVEL(), ud.getUSER_ID()));
            else
                level3Users.add(new UserDataLevel3(ud.getUSER_NAME(), ud.getUSER_LEVEL(), ud.getUSER_ID()));
        }
    }

    @FXML
    private void handleMouseClick(MouseEvent e) throws Exception {
        if (e.getSource() == bt_start) {
            sendCommandToAllClients(Constants.BEGIN_COMP);
            ap_root.setVisible(false);
            ap_round2Interface.setVisible(true);
            ap_round2InterfaceController.init();
            ap_round2InterfaceController.setStyle();
            ap_round2InterfaceController.setUsers(level1Users, level2Users, level3Users);
            System.out.println(clients);
        }
    }

    private LinkedList<String> packageData(Object data) {
        LinkedList<String> result = new LinkedList<>();
        if (data.getClass() == Integer.class)
            result.add(Integer.toString((int) data));
        else if (data.getClass() == String.class)
            result.add((String) data);
        else
            result.add(null);
        return result;
    }

    private void sendCommandToAllClients(int command) {
        for (ClientHandler ch : MainController.clients) {
            ch.writeToClient(command);
        }
    }

    private void sendDataToClient(int command, LinkedList<String> data, UserData user) {
        for (ClientHandler ch : MainController.clients) {
            if (ch.getThreadID() == user.getThreadId())
                ch.writeToClient(command, data);
        }
    }

    public void writeToClient(int command) {
        switch (command) {
            case Constants.S2C_R2L1_SCR:
                for (UserDataLevel1 ud : level1Users) {
                    if (ud.getPoints() != null)
                        sendDataToClient(command, packageData(ud.getPoints().getLast()), ud);
                }
                break;
            case Constants.S2C_R2L2_SCR:
                for (UserDataLevel2 ud : level2Users) {
                    if (ud.getPoints() != null)
                        sendDataToClient(command, packageData(ud.getPoints().getLast()), ud);
                }
                break;
            default:
                sendCommandToAllClients(command);
                break;
        }
    }

    public void writeToClient(int command, LinkedList<String> data) {

    }

    @Override
    public void addClient(ClientHandler client) {
        if (MainController.clients == null)
            MainController.clients = new LinkedList<>();
        MainController.clients.add(client);
    }

    @Override
    public void setClientInteractionInterface(ClientInteractionInterface client) {
        clientHandlerNotify = client;
    }

    @Override
    public void connectUser(int userID) {
        try {
            updateTable(userID);
            for (UserDataLevel1 ud : level1Users) {
                if (ud.getUSER_ID() == userID)
                    ud.setThreadId(activeThreadId);

            }
            for (UserDataLevel2 ud : level2Users) {
                if (ud.getUSER_ID() == userID)
                    ud.setThreadId(activeThreadId);

            }
        } catch (HzkException ex) {
            for (ClientHandler client : MainController.clients) {
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
        switch (command) {
            case Constants.C2S_R2L1_ANS:
                for (UserDataLevel1 ud : level1Users) {
                    if (ud.getThreadId() == activeThreadId)
                        ud.setRound2Answers(data);
                }
                ap_round2InterfaceController.handleClientData(command, null);
                break;
            case Constants.C2S_R2L2_ANS:
                for (UserDataLevel2 ud : level2Users) {
                    if (ud.getThreadId() == activeThreadId)
                        ud.setRound2Answers(data);
                }
                ap_round2InterfaceController.handleClientData(command, null);
                break;
            default:
                switch (currentRound) {
                    case Constants.ROUND1:
                        //ap_round2InterfaceController.handleClientData(command, data);
                        break;
                    case Constants.ROUND2:
                        ap_round2InterfaceController.handleClientData(command, data);
                        break;
                    case Constants.ROUND3:
                        //ap_round2InterfaceController.handleClientData(command, data);
                        break;
                    case Constants.ROUND4:
                        // ap_round2InterfaceController.handleClientData(command, data);
                        break;
                    case Constants.ROUND5:
                        // ap_round2InterfaceController.handleClientData(command, data);
                        break;
                    default:
                        break;
                }
                break;
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AnchorPane.setBottomAnchor(ap_round2Interface, 0.0);
        AnchorPane.setTopAnchor(ap_round2Interface, 0.0);
        AnchorPane.setLeftAnchor(ap_round2Interface, 0.0);
        AnchorPane.setRightAnchor(ap_round2Interface, 0.0);
        ap_round2Interface.setVisible(false);

        currentRound = Constants.ROUND2;

        initUserData();
    }
}
