package main.round2;

import data.UserDataLevel1;
import data.UserDataLevel2;
import data.UserDataLevel3;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.MainController;
import tool.Constants;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class Round2Controller extends MainController implements Initializable, Runnable {
    @FXML
    private AnchorPane ap_root;
    @FXML
    private AnchorPane ap_level1Interface;
    @FXML
    private Level1Controller ap_level1InterfaceController;
    @FXML
    private AnchorPane ap_level2Interface;
    @FXML
    private Level2Controller ap_level2InterfaceController;
    @FXML
    private AnchorPane ap_level3Interface;
    @FXML
    private Level3Controller ap_level3InterfaceController;
    @FXML
    private TabPane tp_mainTab;
    @FXML
    private Label lb_roundDescription;
    @FXML
    private Label lb_roundNumber_zh;
    @FXML
    private Label lb_roundNumber_en;
    @FXML
    private Button bt_level1Instruction;
    @FXML
    private Button bt_level1Example;
    @FXML
    private Button bt_level2Instruction;
    @FXML
    private Button bt_level2Example;
    @FXML
    private Button bt_level3Instruction;
    @FXML
    private Button bt_level3Example;
    @FXML
    private Label lb_roundNumberS1_zh;
    @FXML
    private Label lb_roundNumberS2_zh;
    @FXML
    private Label lb_roundNumberS3_zh;
    @FXML
    private Label lb_roundNumberS1_en;
    @FXML
    private Label lb_roundNumberS2_en;
    @FXML
    private Label lb_roundNumberS3_en;
    @FXML
    private Label lb_roundLevel1_zh;
    @FXML
    private Label lb_roundLevel2_zh;
    @FXML
    private Label lb_roundLevel3_zh;
    @FXML
    private Label lb_roundLevel1_en;
    @FXML
    private Label lb_roundLevel2_en;
    @FXML
    private Label lb_roundLevel3_en;
    @FXML
    private AnchorPane ap_parent;

    private Thread thread;

    private int currentLevel = 0;

    public void init() {
        thread = new Thread(this);
        thread.start();

    }

    public void setActiveThread(long threadId) {
        activeThreadId = threadId;
        if (ap_level1InterfaceController != null)
            ap_level1InterfaceController.setActiveThread(threadId);
        if(ap_level2InterfaceController != null)
            ap_level2InterfaceController.setActiveThread(threadId);
        if(ap_level3InterfaceController != null)
            ap_level3InterfaceController.setActiveThread(threadId);
    }

    public void show() {
        ap_root.setVisible(true);
    }

    private void hide() {
        ap_root.setVisible(false);
    }

    public void setUsers(LinkedList<UserDataLevel1> level1, LinkedList<UserDataLevel2> level2, LinkedList<UserDataLevel3> level3) {
        level1Users = level1;
        level2Users = level2;
        level3Users = level3;
    }

    @FXML
    private void handleMouseClick(MouseEvent e) {
        if (e.getSource() == bt_level1Instruction) {
            currentLevel = Constants.LEVEL1;
            hide();
            ap_level1InterfaceController.init(level1Users, this);
            ap_level1InterfaceController.show();
            writeToClient(Constants.BEGIN_R2L1);
        } else if (e.getSource() == bt_level2Instruction) {
            currentLevel = Constants.LEVEL2;
            hide();
            ap_level2InterfaceController.init(level2Users, this);
            ap_level2InterfaceController.show();
            writeToClient(Constants.BEGIN_R2L2);
        } else if (e.getSource() == bt_level3Instruction) {
            currentLevel = Constants.LEVEL3;
            hide();
            ap_level3InterfaceController.init(level3Users, this);
            ap_level3InterfaceController.show();
            writeToClient(Constants.BEGIN_R2L3);
        }
    }

    @Override
    public void writeToClient(int command) {

        super.writeToClient(command);

    }

    @Override
    public void writeToClient(int command, LinkedList<String> data) {
        super.writeToClient(command, data);
    }

    @Override
    public void handleClientData(int command, LinkedList<String> data) {
        switch (command) {
            default:
                switch (currentLevel) {
                    case Constants.LEVEL1:
                        ap_level1InterfaceController.handleClientData(command, data);
                        break;
                    case Constants.LEVEL2:
                        ap_level2InterfaceController.handleClientData(command, data);
                        break;
                    case Constants.LEVEL3:
                        ap_level3InterfaceController.handleClientData(command, data);
                        break;
                    default:
                        break;
                }
                break;
        }

    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            lb_roundNumber_zh.setVisible(false);
            lb_roundNumber_en.setVisible(false);
            lb_roundDescription.setVisible(false);
            ap_root.setVisible(true);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage() + " " + getClass());
        }
        thread.interrupt();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hide();

        AnchorPane.setBottomAnchor(ap_level1Interface, 0.0);
        AnchorPane.setTopAnchor(ap_level1Interface, 0.0);
        AnchorPane.setLeftAnchor(ap_level1Interface, 0.0);
        AnchorPane.setRightAnchor(ap_level1Interface, 0.0);
        ap_level1Interface.setVisible(false);

        AnchorPane.setBottomAnchor(ap_level2Interface, 0.0);
        AnchorPane.setTopAnchor(ap_level2Interface, 0.0);
        AnchorPane.setLeftAnchor(ap_level2Interface, 0.0);
        AnchorPane.setRightAnchor(ap_level2Interface, 0.0);
        ap_level2Interface.setVisible(false);

        AnchorPane.setBottomAnchor(ap_level3Interface, 0.0);
        AnchorPane.setTopAnchor(ap_level3Interface, 0.0);
        AnchorPane.setLeftAnchor(ap_level3Interface, 0.0);
        AnchorPane.setRightAnchor(ap_level3Interface, 0.0);
        ap_level3Interface.setVisible(false);
    }
}
