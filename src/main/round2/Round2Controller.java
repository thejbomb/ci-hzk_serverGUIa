package main.round2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import tool.Constants;

import java.net.URL;
import java.util.ResourceBundle;

public class Round2Controller implements Initializable, Runnable {
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

    public Round2Controller() {

    }

    public void init() {
        thread = new Thread(this);
        thread.start();

    }

    public void setStyle() {
        lb_cnRoundTitle.setId(Constants.CN_FONT_ROUND_TITLE);
        lb_enRoundTitle.setId(Constants.EN_FONT_ROUND_TITLE);
        lb_cnRoundDescription.setId(Constants.CN_FONT_ROUND_DESCRIPTION);

        lb_cnRoundNumber1.setId(Constants.CN_FONT_ROUND_NUMBER);
        lb_enRoundNumber1.setId(Constants.EN_FONT_ROUND_NUMBER);
        lb_cnRoundBeginner.setId(Constants.CN_FONT_ROUND_LEVEL);
        lb_enRoundBeginner.setId(Constants.EN_FONT_ROUND_LEVEL);
        lb_cnRoundNumber2.setId(Constants.CN_FONT_ROUND_NUMBER);
        lb_enRoundNumber2.setId(Constants.EN_FONT_ROUND_NUMBER);
        lb_cnRoundIntermediate.setId(Constants.CN_FONT_ROUND_LEVEL);
        lb_enRoundIntermediate.setId(Constants.EN_FONT_ROUND_LEVEL);
        lb_cnRoundNumber3.setId(Constants.CN_FONT_ROUND_NUMBER);
        lb_enRoundNumber3.setId(Constants.EN_FONT_ROUND_NUMBER);
        lb_cnRoundAdvance.setId(Constants.CN_FONT_ROUND_LEVEL);
        lb_enRoundAdvance.setId(Constants.EN_FONT_ROUND_LEVEL);

        bt_beginnerStart.setId(Constants.FONT_BUTTON);
        bt_intermediateStart.setId(Constants.FONT_BUTTON);
        bt_advanceStart.setId(Constants.FONT_BUTTON);

    }

    @FXML
    private void handleMouseClick(MouseEvent e){
        if(e.getSource() == bt_beginnerStart){
            ap_root.setVisible(false);
            ap_level1Interface.setVisible(true);

        }
        else if(e.getSource() == bt_intermediateStart){
            ap_root.setVisible(false);
            ap_level2Interface.setVisible(true);
        }
        else if(e.getSource() == bt_advanceStart){
            ap_root.setVisible(false);
            ap_level3Interface.setVisible(true);
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(0);
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
        ap_root.setVisible(false);

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
