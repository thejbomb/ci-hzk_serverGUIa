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
import main.round3.Round3Controller;
import main.round4.Round4Controller;
import main.round5.Round5Controller;
import network.ClientHandlerInterface;
import network.ClientInteractionInterface;
import tool.Constants;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class MainController implements Initializable, ClientHandlerInterface {
    @FXML
    private Button bt_start;
    @FXML
    private TableView<UserData> tb_usersList;
    @FXML
    private AnchorPane ap_round1Interface;
    @FXML
    private Round1Controller ap_round1InterfaceController;
    @FXML
    private AnchorPane ap_round2Interface;
    @FXML
    private Round2Controller ap_round2InterfaceController;
    @FXML
    private AnchorPane ap_round3Interface;
    @FXML
    private Round3Controller ap_round3InterfaceController;
    @FXML
    private AnchorPane ap_round4Interface;
    @FXML
    private Round4Controller ap_round4InterfaceController;
    @FXML
    private AnchorPane ap_round5Interface;
    @FXML
    private Round5Controller ap_round5InterfaceController;
    @FXML
    private AnchorPane ap_scoreboardInterface;
    @FXML
    private ScoreboardController ap_scoreboardInterfaceController;
    @FXML
    private AnchorPane ap_root;
    @FXML
    private Label lb_title_zh;
    @FXML
    private Label lb_title_en;
    @FXML
    private Label lb_name;
    @FXML
    private Label lb_level;
    @FXML
    private Label lb_status;

    private static ClientInteractionInterface clientHandler;

    protected LinkedList<UserDataLevel1> level1Users = null;
    protected LinkedList<UserDataLevel2> level2Users = null;
    protected LinkedList<UserDataLevel3> level3Users = null;

    protected long activeThreadId = -1;

    private static int currentRound = Constants.ROUND2;

    private int userLevel = 0;

    public static int getNextRound() {
        if(currentRound == Constants.ROUND1)
            currentRound = Constants.ROUND2;
        else if (currentRound == Constants.ROUND2)
            currentRound = Constants.ROUND3;
        else if (currentRound == Constants.ROUND3)
            currentRound = Constants.ROUND4;
        else if (currentRound == Constants.ROUND4)
            currentRound = Constants.ROUND5;
        return currentRound;
    }

    public void init(ClientInteractionInterface handler) {
        clientHandler = handler;
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
        tb_usersList.setItems(Main.USERS_LIST);
    }

    private void updateTable(int ID) throws HzkException {
        boolean found = false;
        for (int i = 0; i < Main.USERS_LIST.size(); i++) {
            if (Main.USERS_LIST.get(i).getUSER_ID() == ID) {
                TableColumn statusColumn = (TableColumn) tb_usersList.getColumns().get(3);
                tb_usersList.getItems().get(i).setUserStatus(true);
                tb_usersList.getItems().set(i, tb_usersList.getItems().get(i));
                clientHandler.sendDataToClient(activeThreadId, Constants.LOGIN_SUCCESS, tb_usersList.getItems().get(i).getBasicInfo());
                found = true;
            }
        }
        if (!found)
            throw new HzkException("User ID Not Found");
    }

    public void startRound(int roundNumber) {
        switch (roundNumber) {
            case Constants.ROUND1:
                writeToClient(Constants.BEGIN_R1L1, Constants.LEVEL1);
                writeToClient(Constants.BEGIN_R1L2, Constants.LEVEL2);
                writeToClient(Constants.BEGIN_R1L3, Constants.LEVEL3);
                ap_round1Interface.setVisible(true);
                ap_round1InterfaceController.init(ap_scoreboardInterfaceController);
                ap_round1InterfaceController.setUsers(level1Users, level2Users, level3Users);
                ap_round1InterfaceController.show();
                break;
            case Constants.ROUND2:
                writeToClient(Constants.BEGIN_RND2);
                writeToClient(Constants.BEGIN_R2L1, Constants.LEVEL1);
                writeToClient(Constants.BEGIN_R2L2, Constants.LEVEL2);
                writeToClient(Constants.BEGIN_R2L3, Constants.LEVEL3);
                ap_round2Interface.setVisible(true);
                ap_round2InterfaceController.init(ap_scoreboardInterfaceController);
                ap_round2InterfaceController.setUsers(level1Users, level2Users, level3Users);
                ap_round2InterfaceController.show();
                break;
            case Constants.ROUND3:
                writeToClient(Constants.BEGIN_R3L1, Constants.LEVEL1);
                writeToClient(Constants.BEGIN_R3L2, Constants.LEVEL2);
                writeToClient(Constants.BEGIN_R3L3, Constants.LEVEL3);
                ap_round3Interface.setVisible(true);
                ap_round3InterfaceController.init(ap_scoreboardInterfaceController);
                ap_round3InterfaceController.setUsers(level1Users, level2Users, level3Users);
                ap_round3InterfaceController.show();
                break;
            case Constants.ROUND4:
                writeToClient(Constants.BEGIN_RND4);
                ap_round4Interface.setVisible(true);
                ap_round4InterfaceController.init(ap_scoreboardInterfaceController);
                //ap_round4InterfaceController.show();
                ap_round4InterfaceController.setUsers(level1Users, level2Users, level3Users);
                break;
            case Constants.ROUND5:
                writeToClient(Constants.BEGIN_RND5);
                writeToClient(Constants.BEGIN_R5L1, Constants.LEVEL1);
                writeToClient(Constants.BEGIN_R5L2, Constants.LEVEL2);
                writeToClient(Constants.BEGIN_R5L3, Constants.LEVEL3);
                ap_round5Interface.setVisible(true);
                ap_round5InterfaceController.init(ap_scoreboardInterfaceController);
                ap_round5InterfaceController.setUsers(level1Users, level2Users, level3Users);
                ap_round5InterfaceController.show();
                break;
        }
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
    private void handleMouseClick(MouseEvent e) {
        if (e.getSource() == bt_start) {
            sendCommandToAllClients(Constants.BEGIN_COMP);
            ap_root.setVisible(false);
            ap_scoreboardInterface.setVisible(true);
            ap_scoreboardInterfaceController.init(this);
            ap_scoreboardInterfaceController.setCurrentRound(currentRound);
            ap_scoreboardInterfaceController.initUserData(level1Users, level2Users, level3Users);
            ap_scoreboardInterfaceController.display();
        }
    }

    protected LinkedList<String> packageData(Object data) {
        LinkedList<String> result = new LinkedList<>();
        if (data.getClass() == Integer.class)
            result.add(Integer.toString((int) data));
        else if (data.getClass() == String.class)
            result.add((String) data);
        else if (data.getClass() == Long.class)
            result.add(Long.toString((long) data));
        return result;
    }

    protected UserDataLevel1 getLevel1User(long threadId) {
        UserDataLevel1 result = null;
        for (UserDataLevel1 ud : level1Users)
            if (ud.getThreadId() == threadId)
                result = ud;

        return result;
    }

    protected UserDataLevel2 getLevel2User(long threadId) {
        UserDataLevel2 result = null;
        for (UserDataLevel2 ud : level2Users)
            if (ud.getThreadId() == threadId)
                result = ud;

        return result;
    }

    protected UserDataLevel3 getLevel3User(long threadId) {
        UserDataLevel3 result = null;
        for (UserDataLevel3 ud : level3Users)
            if (ud.getThreadId() == threadId)
                result = ud;

        return result;
    }

    private void sendCommandToClient(int command, long threadId) {
        MainController.clientHandler.sendCommandToClient(threadId, command);
    }

    private void sendCommandToAllClients(int command) {
        MainController.clientHandler.sendCommandToAllClients(command);
    }

    private void sendDataToClient(int command, LinkedList<String> data, UserData user) {
        sendDataToClient(command, data, user.getThreadId());
    }

    private void sendDataToClient(int command, LinkedList<String> data, long threadId) {
        MainController.clientHandler.sendDataToClient(threadId, command, data);
    }

    private void sendDataToAllClient(int command, LinkedList<String> data) {
        System.out.println("M DATA SET: " + data);
        MainController.clientHandler.sendDataToAllClients(command, data);
    }


    public void writeToClient(int command) {
        System.out.println("M TO: command = " + Integer.toHexString(command) + " | levelData = ");
        switch (command) {
            default:
                sendCommandToAllClients(command);
                break;
        }
    }

    // write command and data to all clients
    public void writeToClient(int command, LinkedList<String> data) {
        System.out.println("M To Client: command = " + Integer.toHexString(command) + " | data = " + data);
        sendDataToAllClient(command, data);
    }

    // write command and data to client on threadId
    public void writeToClient(int command, LinkedList<String> data, long threadId) {
        System.out.println("M To Client: command = " + Integer.toHexString(command) + " | data = " + data);
        sendDataToClient(command, data, threadId);
    }

    // write command to client on threadId
    public void writeToClient(int command, long threadId) {
        System.out.println("M To Client: command = " + Integer.toHexString(command) + " | data = ");
        sendCommandToClient(command, threadId);
    }

    // write command to all clients except for client on threadId
    public void writeToAllClientsExcept(int command, long threadId) {
        System.out.println("M To Client: command = " + Integer.toHexString(command) + " | data = ");
        for (UserDataLevel1 ud : level1Users)
            if (ud.getThreadId() != threadId)
                sendCommandToClient(command, ud.getThreadId());
    }

    // write command to a group of user
    public void writeToClient(int command, int level) {
        switch (level) {
            case Constants.LEVEL1:
                for (UserDataLevel1 ud : level1Users)
                    writeToClient(command, ud.getThreadId());
                break;
            case Constants.LEVEL2:
                for (UserDataLevel2 ud : level2Users)
                    writeToClient(command, ud.getThreadId());
                break;
            case Constants.LEVEL3:
                for (UserDataLevel3 ud : level3Users)
                    writeToClient(command, ud.getThreadId());
                break;
        }

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
            for (UserDataLevel3 ud : level3Users) {
                if (ud.getUSER_ID() == userID)
                    ud.setThreadId(activeThreadId);

            }
        } catch (HzkException ex) {
            clientHandler.sendCommandToClient(activeThreadId, Constants.ERROR_ID_NOT_FOUND);

        }
    }

    @Override
    public void setActiveThread(long threadID) {
        activeThreadId = threadID;
        if (ap_round1InterfaceController != null)
            ap_round1InterfaceController.setActiveThread(threadID);
        if (ap_round2InterfaceController != null)
            ap_round2InterfaceController.setActiveThread(threadID);
        if (ap_round3InterfaceController != null)
            ap_round3InterfaceController.setActiveThread(threadID);
        if (ap_round4InterfaceController != null)
            ap_round4InterfaceController.setActiveThread(threadID);
        if (ap_round5InterfaceController != null)
            ap_round5InterfaceController.setActiveThread(threadID);
    }

    @Override
    public void handleClientData(int command, LinkedList<String> data) {
        System.out.println("M FROM: command = " + command + " | data = " + data);
        System.out.println("Current round = " + currentRound);
        switch (command) {
            case Constants.C2S_R2L3_SEED:
                for (UserDataLevel3 ud : level3Users) {
                    if (ud.getThreadId() == activeThreadId)
                        ud.setSeed(data);
                }
                ap_round5InterfaceController.handleClientData(command, null);
                break;
            default:
                switch (currentRound) {
                    case Constants.ROUND1:
                        ap_round1InterfaceController.handleClientData(command, data);
                        break;
                    case Constants.ROUND2:
                        ap_round2InterfaceController.handleClientData(command, data);
                        break;
                    case Constants.ROUND3:
                        ap_round3InterfaceController.handleClientData(command, data);
                        break;
                    case Constants.ROUND4:
                        ap_round4InterfaceController.handleClientData(command, data);
                        break;
                    case Constants.ROUND5:
                        ap_round5InterfaceController.handleClientData(command, data);
                        break;
                    default:
                        break;
                }
                break;
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AnchorPane.setBottomAnchor(ap_round1Interface, 0.0);
        AnchorPane.setTopAnchor(ap_round1Interface, 0.0);
        AnchorPane.setLeftAnchor(ap_round1Interface, 0.0);
        AnchorPane.setRightAnchor(ap_round1Interface, 0.0);
        ap_round1Interface.setVisible(false);

        AnchorPane.setBottomAnchor(ap_round2Interface, 0.0);
        AnchorPane.setTopAnchor(ap_round2Interface, 0.0);
        AnchorPane.setLeftAnchor(ap_round2Interface, 0.0);
        AnchorPane.setRightAnchor(ap_round2Interface, 0.0);
        ap_round2Interface.setVisible(false);

        AnchorPane.setBottomAnchor(ap_round3Interface, 0.0);
        AnchorPane.setTopAnchor(ap_round3Interface, 0.0);
        AnchorPane.setLeftAnchor(ap_round3Interface, 0.0);
        AnchorPane.setRightAnchor(ap_round3Interface, 0.0);
        ap_round3Interface.setVisible(false);

        AnchorPane.setBottomAnchor(ap_round4Interface, 0.0);
        AnchorPane.setTopAnchor(ap_round4Interface, 0.0);
        AnchorPane.setLeftAnchor(ap_round4Interface, 0.0);
        AnchorPane.setRightAnchor(ap_round4Interface, 0.0);
        ap_round4Interface.setVisible(false);

        AnchorPane.setBottomAnchor(ap_round5Interface, 0.0);
        AnchorPane.setTopAnchor(ap_round5Interface, 0.0);
        AnchorPane.setLeftAnchor(ap_round5Interface, 0.0);
        AnchorPane.setRightAnchor(ap_round5Interface, 0.0);
        ap_round5Interface.setVisible(false);

        AnchorPane.setBottomAnchor(ap_scoreboardInterface, 0.0);
        AnchorPane.setTopAnchor(ap_scoreboardInterface, 0.0);
        AnchorPane.setLeftAnchor(ap_scoreboardInterface, 0.0);
        AnchorPane.setRightAnchor(ap_scoreboardInterface, 0.0);
        ap_scoreboardInterface.setVisible(false);

        initUserData();
    }
}
