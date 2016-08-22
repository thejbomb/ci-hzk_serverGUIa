package main.round4;

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
import main.Main;
import main.MainController;
import tool.Constants;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class Round4Controller extends MainController implements Initializable, Runnable {
    @FXML
    private AnchorPane ap_root;
    @FXML
    private AnchorPane ap_level1Interface;
    @FXML
    private Level1Controller ap_level1InterfaceController;
    /* @FXML
     private AnchorPane ap_level2Interface;
    /@FXML
     private Level2Controller ap_level2InterfaceController;
     @FXML
     private AnchorPane ap_level3Interface;
     @FXML
     private Level3Controller ap_level3InterfaceController;*/
    @FXML
    private TabPane tp_mainTab;
    @FXML
    private Label lb_cnRoundDescription;
    @FXML
    private Label lb_cnRoundTitle;
    @FXML
    private Label lb_enRoundTitle;
    @FXML
    private Button bt_beginnerStart;
    @FXML
    private Button bt_intermediateStart;
    @FXML
    private Button bt_advanceStart;
    @FXML
    private Label lb_cnRoundNumber1;
    @FXML
    private Label lb_cnRoundNumber2;
    @FXML
    private Label lb_cnRoundNumber3;
    @FXML
    private Label lb_enRoundNumber1;
    @FXML
    private Label lb_enRoundNumber2;
    @FXML
    private Label lb_enRoundNumber3;
    @FXML
    private Label lb_cnRoundBeginner;
    @FXML
    private Label lb_cnRoundIntermediate;
    @FXML
    private Label lb_cnRoundAdvance;
    @FXML
    private Label lb_enRoundBeginner;
    @FXML
    private Label lb_enRoundIntermediate;
    @FXML
    private Label lb_enRoundAdvance;
    @FXML
    private AnchorPane ap_parent;

    private Thread thread;

    private int currentLevel = 0;

    public void init() {
        thread = new Thread(this);
        thread.start();

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

    public void setActiveThread(long threadId) {
        super.setActiveThread(threadId);
        if (ap_level1InterfaceController != null)
            ap_level1InterfaceController.setActiveThread(threadId);
    }

    private LinkedList<String> packageData(Object data) {
        LinkedList<String> result = new LinkedList<>();

        if (data.getClass() == Integer.class) {
            result.add(Integer.toString((int) data));
        } else if (data.getClass() == Long.class)
            result.add(Long.toString((long) data));

        return result;
    }

    @FXML
    private void handleMouseClick(MouseEvent e) {
        if (e.getSource() == bt_beginnerStart) {
            currentLevel = Constants.LEVEL1;
            hide();
            ap_level1InterfaceController.init(level1Users, this);
            ap_level1InterfaceController.show();
            writeToClient(Constants.BEGIN_R4L1);
            writeToClient(Constants.S2C_R4L1_SEED, packageData(Main.R4L1_DATA.getSeed()));
        } else if (e.getSource() == bt_intermediateStart) {
            currentLevel = Constants.LEVEL2;
            hide();
            //ap_level2InterfaceController.init(level2Users,this);
            //ap_level2InterfaceController.show();
            writeToClient(Constants.BEGIN_R4L2);
        } else if (e.getSource() == bt_advanceStart) {
            currentLevel = Constants.LEVEL3;
            hide();
            //ap_level3InterfaceController.init(level3Users,this);
            //ap_level3InterfaceController.show();
            writeToClient(Constants.BEGIN_R4L3);
        }
    }

    @Override
    public void writeToClient(int command) {
        System.out.println("R4 TO: command = " + Integer.toHexString(command) + " | data = ");
        super.writeToClient(command);

    }

    @Override
    public void writeToClient(int command, LinkedList<String> data) {
        System.out.println("R4 To Client: command = " + Integer.toHexString(command) + " | data = " + data);
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
                        // ap_level2InterfaceController.handleClientData(command, data);
                        break;
                    case Constants.LEVEL3:
                        //ap_level3InterfaceController.handleClientData(command, data);
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
            lb_cnRoundTitle.setVisible(false);
            lb_enRoundTitle.setVisible(false);
            lb_cnRoundDescription.setVisible(false);
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

    /*    AnchorPane.setBottomAnchor(ap_level2Interface, 0.0);
        AnchorPane.setTopAnchor(ap_level2Interface, 0.0);
        AnchorPane.setLeftAnchor(ap_level2Interface, 0.0);
        AnchorPane.setRightAnchor(ap_level2Interface, 0.0);
        ap_level2Interface.setVisible(false);*/
/*
        AnchorPane.setBottomAnchor(ap_level3Interface, 0.0);
        AnchorPane.setTopAnchor(ap_level3Interface, 0.0);
        AnchorPane.setLeftAnchor(ap_level3Interface, 0.0);
        AnchorPane.setRightAnchor(ap_level3Interface, 0.0);
        ap_level3Interface.setVisible(false);*/
    }


}
