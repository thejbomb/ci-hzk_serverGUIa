package main.round2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.Main;
import tool.Constants;

import java.net.URL;
import java.util.ResourceBundle;

public class Level1Controller implements Initializable {
    public Tab tb_tools;
    @FXML
    private AnchorPane ap_root;
    @FXML
    private TabPane tp_mainTab;
    @FXML
    private Label lb_cnInstructionHeader;
    @FXML
    private Label lb_cnInstruction;
    @FXML
    private Label lb_enInstruction;
    @FXML
    private Label lb_cnTimeLimit;
    @FXML
    private Label lb_enTimeLimit;
    @FXML
    private Label lb_enExampleHeader;
    @FXML
    private Label lb_cnExample;
    @FXML
    private Label lb_enWarning;
    @FXML
    private Label lb_question1;
    @FXML
    private Label lb_question2;
    @FXML
    private Label lb_question3;
    @FXML
    private Label lb_question4;
    @FXML
    private Label lb_question5;
    @FXML
    private Button bt_startTimer;
    @FXML
    private Label lb_timer;

    private void setStyleId() {
        lb_cnInstructionHeader.setId(Constants.CN_FONT_HEADER);
        lb_cnInstruction.setId(Constants.CN_FONT_TEXT);
        lb_enInstruction.setId(Constants.EN_FONT_TEXT);
        lb_cnTimeLimit.setId(Constants.CN_FONT_TIME_LIMIT);
        lb_enTimeLimit.setId(Constants.EN_FONT_TIME_LIMIT);

        lb_enExampleHeader.setId(Constants.EN_FONT_EXAMPLE_HEADER);
        lb_cnExample.setId(Constants.CN_FONT_EXAMPLE);
        lb_enWarning.setId(Constants.EN_FONT_EXAMPLE_WARNING);

        lb_timer.setId(Constants.FONT_TIMER);

        lb_question1.setId(Constants.CN_FONT_QUESTION);
        lb_question2.setId(Constants.CN_FONT_QUESTION);
        lb_question3.setId(Constants.CN_FONT_QUESTION);
        lb_question4.setId(Constants.CN_FONT_QUESTION);
        lb_question5.setId(Constants.CN_FONT_QUESTION);

        bt_startTimer.setId(Constants.EN_FONT_START_TIMER);
    }

    private void setData() {
        lb_cnInstruction.setText(Main.R2L1_DATA.CN_INSTRUCTION);
        lb_enInstruction.setText(Main.R2L1_DATA.EN_INSTRUCTION);
        String cnTimeLimit = "限时" + Main.R2L1_DATA.TIME_LIMIT + "分钟";
        String enTimeLimit = "Time Limit: " + Main.R2L1_DATA.TIME_LIMIT + ((Main.R2L1_DATA.TIME_LIMIT > 1) ? " minutes" : " minute");
        lb_cnTimeLimit.setText(cnTimeLimit);
        lb_enTimeLimit.setText(enTimeLimit);

        lb_cnExample.setText(Main.R2L1_DATA.EXAMPLES.get(0));

        lb_question1.setText(Main.R2L1_DATA.QUESTIONS.get(0));
        lb_question2.setText(Main.R2L1_DATA.QUESTIONS.get(1));
        lb_question3.setText(Main.R2L1_DATA.QUESTIONS.get(2));
        lb_question4.setText(Main.R2L1_DATA.QUESTIONS.get(3));
        lb_question5.setText(Main.R2L1_DATA.QUESTIONS.get(4));
    }

    @FXML
    private void handleMouseClick(MouseEvent e) {
        if (e.getSource() == bt_startTimer) {
            bt_startTimer.setVisible(false);
            lb_timer.setVisible(true);
            lb_question1.setVisible(true);
            lb_question2.setVisible(true);
            lb_question3.setVisible(true);
            lb_question4.setVisible(true);
            lb_question5.setVisible(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleId();
        //setData();
        lb_timer.setVisible(false);
        lb_question1.setVisible(false);
        lb_question2.setVisible(false);
        lb_question3.setVisible(false);
        lb_question4.setVisible(false);
        lb_question5.setVisible(false);
        bt_startTimer.setVisible(true);
    }
}
