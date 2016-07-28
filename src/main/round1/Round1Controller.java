package main.round1;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import tool.Constants;

import java.net.URL;
import java.util.ResourceBundle;

public class Round1Controller implements Initializable, Runnable {
    @FXML
    private AnchorPane ap_root;
    @FXML
    private TabPane tp_mainTab;
    @FXML
    private Label lb_cnRoundTitle;
    @FXML
    private Label lb_enRoundTitle;
    @FXML
    private Label lb_cnRoundDescription;
    @FXML
    private Label lb_cnInstructionHeader;
    @FXML
    private Label lb_cnInstruction;
    @FXML
    private Label lb_enInstruction;
    @FXML
    private Label lb_enTimeLimit;
    @FXML
    private Label lb_cnTimeLimit;
    @FXML
    private Label lb_enExampleHeader;
    @FXML
    private Label lb_cnExample;
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


    private Thread thread;

    public Round1Controller() {

    }

    public void init() {
        thread = new Thread(this);
        thread.start();

    }

    public void setStyle() {
        lb_cnRoundTitle.setId(Constants.CN_FONT_ROUND_TITLE);
        lb_enRoundTitle.setId(Constants.EN_FONT_ROUND_TITLE);
        lb_cnRoundDescription.setId(Constants.CN_FONT_ROUND_DESCRIPTION);

        lb_cnInstructionHeader.setId(Constants.CN_FONT_HEADER);
        lb_cnInstruction.setId(Constants.CN_FONT_TEXT);
        lb_cnExample.setId(Constants.CN_FONT_EXAMPLE);
        lb_enExampleHeader.setId(Constants.EN_FONT_HEADER);
        lb_enInstruction.setId(Constants.EN_FONT_TEXT);
        lb_cnTimeLimit.setId(Constants.CN_FONT_TIME_LIMIT);
        lb_enTimeLimit.setId(Constants.EN_FONT_TIME_LIMIT);

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

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
            lb_cnRoundTitle.setVisible(false);
            lb_enRoundTitle.setVisible(false);
            lb_cnRoundDescription.setVisible(false);
            tp_mainTab.setVisible(true);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage() + " " + getClass());
        }
        thread.interrupt();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
