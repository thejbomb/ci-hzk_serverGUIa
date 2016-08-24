package main;

import data.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.round2.Round2Controller;
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
    private AnchorPane ap_round2Interface;
    @FXML
    private Round2Controller ap_round2InterfaceController;
    @FXML
    private AnchorPane ap_round4Interface;
    @FXML
    private Round4Controller ap_round4InterfaceController;
    @FXML
    private AnchorPane ap_round5Interface;
    @FXML
    private Round5Controller ap_round5InterfaceController;
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

    private static ClientInteractionInterface clientHandler;

    protected LinkedList<UserDataLevel1> level1Users = null;
    protected LinkedList<UserDataLevel2> level2Users = null;
    protected LinkedList<UserDataLevel3> level3Users = null;

    protected long activeThreadId = -1;

    private int currentRound = 0;

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
            ap_round4Interface.setVisible(true);
            ap_round4InterfaceController.init();
            ap_round4InterfaceController.setUsers(level1Users, level2Users, level3Users);
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
            case Constants.S2C_R2L1_SCR:
                for (UserDataLevel1 ud : level1Users) {
                    if (ud.getRound2Points() != null)
                        sendDataToClient(command, packageData(ud.getRound2Points().getLast()), ud);
                }
                break;
            case Constants.S2C_R2L2_SCR:
                for (UserDataLevel2 ud : level2Users) {
                    if (ud.getRound2Points() != null)
                        sendDataToClient(command, packageData(ud.getRound2Points().getLast()), ud);
                }
                break;
            case Constants.S2C_R2L3_SCR:
                for (UserDataLevel3 ud : level3Users) {
                    if (ud.getRound2Points() != null)
                        sendDataToClient(command, packageData(ud.getRound2Points().getLast()), ud);
                }
                break;
            case Constants.S2C_R5L1_SCR:
                for (UserDataLevel1 ud : level1Users) {
                    sendDataToClient(command, packageData(ud.getRound5Points()), ud);
                }
                break;
            case Constants.S2C_R5L2_SCR:
                for (UserDataLevel2 ud : level2Users) {
                    sendDataToClient(command, packageData(ud.getRound5Points()), ud);
                }
                break;
            case Constants.S2C_R5L3_SCR:
                for (UserDataLevel3 ud : level3Users) {
                    sendDataToClient(command, packageData(ud.getRound5Points()), ud);
                }
                break;
            default:
                sendCommandToAllClients(command);
                break;
        }
    }

    public void writeToClient(int command, LinkedList<String> data) {
        System.out.println("M To Client: command = " + Integer.toHexString(command) + " | data = " + data);
        sendDataToAllClient(command, data);
    }

    public void writeToClient(int command, LinkedList<String> data, long threadId) {
        System.out.println("M To Client: command = " + Integer.toHexString(command) + " | data = " + data);
        sendDataToClient(command, data, threadId);
    }

    public void writeToClient(int command, long threadId) {
        System.out.println("M To Client: command = " + Integer.toHexString(command) + " | data = ");
        sendCommandToClient(command, threadId);
    }

    public void writeToAllClientsExcept(int command, long threadId) {
        System.out.println("M To Client: command = " + Integer.toHexString(command) + " | data = ");
        for (UserDataLevel1 ud : level1Users)
            if (ud.getThreadId() != threadId)
                sendCommandToClient(command, ud.getThreadId());
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
        if (ap_round4InterfaceController != null)
            ap_round4InterfaceController.setActiveThread(threadID);
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
            case Constants.C2S_R2L3_ANS:
                for (UserDataLevel3 ud : level3Users) {
                    if (ud.getThreadId() == activeThreadId)
                        ud.setRound2Answers(data);
                }
                ap_round2InterfaceController.handleClientData(command, null);
                break;
            case Constants.C2S_R5L1_ANS:
                for (UserDataLevel1 ud : level1Users) {
                    if (ud.getThreadId() == activeThreadId)
                        ud.setRound5Answers(data);
                }
                ap_round5InterfaceController.handleClientData(command, null);
                break;
            case Constants.C2S_R5L2_ANS:
                for (UserDataLevel2 ud : level2Users) {
                    if (ud.getThreadId() == activeThreadId)
                        ud.setRound5Answers(data);
                }
                ap_round5InterfaceController.handleClientData(command, null);
                break;
            case Constants.C2S_R5L3_ANS:
                for (UserDataLevel3 ud : level3Users) {
                    if (ud.getThreadId() == activeThreadId)
                        ud.setRound5Answers(data);
                }
                ap_round5InterfaceController.handleClientData(command, null);
                break;
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
                        //ap_round2InterfaceController.handleClientData(command, levelData);
                        break;
                    case Constants.ROUND2:
                        ap_round2InterfaceController.handleClientData(command, data);
                        break;
                    case Constants.ROUND3:
                        //ap_round2InterfaceController.handleClientData(command, levelData);
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
        AnchorPane.setBottomAnchor(ap_round2Interface, 0.0);
        AnchorPane.setTopAnchor(ap_round2Interface, 0.0);
        AnchorPane.setLeftAnchor(ap_round2Interface, 0.0);
        AnchorPane.setRightAnchor(ap_round2Interface, 0.0);
        ap_round2Interface.setVisible(false);

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

        currentRound = Constants.ROUND4;

        initUserData();
    }
}
