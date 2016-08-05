package main.round2;

import data.UserDataLevel1;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import main.Main;
import tool.Constants;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class Level1Controller extends Round2Controller implements Initializable, Runnable {

    @FXML
    private AnchorPane ap_root;
    @FXML
    private TabPane tp_mainTab;
    @FXML
    private Label lb_cnInstructionHeader;
    @FXML
    private Label lb_instructionBody_zh;
    @FXML
    private Label lb_instructionBody_en;
    @FXML
    private Label lb_instructionTime_zh;
    @FXML
    private Label lb_instructionTime_en;
    @FXML
    private Label lb_enExampleHeader;
    @FXML
    private Label lb_exampleBody;
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
    @FXML
    private ComboBox cb_users;
    @FXML
    private GridPane gp_score;
    @FXML
    private Label lb_question1_st;
    @FXML
    private Label lb_question2_st;
    @FXML
    private Label lb_question3_st;
    @FXML
    private Label lb_question4_st;
    @FXML
    private Label lb_question5_st;
    @FXML
    private Label lb_answer1;
    @FXML
    private Label lb_answer2;
    @FXML
    private Label lb_answer3;
    @FXML
    private Label lb_answer4;
    @FXML
    private Label lb_answer5;
    @FXML
    private Label lb_total;
    @FXML
    private TextField tf_point1;
    @FXML
    private TextField tf_point2;
    @FXML
    private TextField tf_point3;
    @FXML
    private TextField tf_point4;
    @FXML
    private TextField tf_point5;
    @FXML
    private Label lb_pointTotal;

    private Thread thread;

    public void init(LinkedList<UserDataLevel1> level1Users) {
        this.level1Users = level1Users;
        thread = new Thread(this);
        thread.start();
        initComboBox();
    }

    private void initComboBox() {
        LinkedList<String> studentName = new LinkedList<>();
        for (UserDataLevel1 ud : level1Users) {
            studentName.add(ud.getUSER_NAME());
        }
        ObservableList<String> name = FXCollections.observableArrayList(studentName);
        cb_users.setItems(name);
        cb_users.setValue(cb_users.getItems().get(0));
    }

    private void setStyleId() {
        lb_cnInstructionHeader.setId(Constants.CN_FONT_HEADER);
        lb_instructionBody_zh.setId(Constants.CN_FONT_TEXT);
        lb_instructionBody_en.setId(Constants.EN_FONT_TEXT);
        lb_instructionTime_zh.setId(Constants.CN_FONT_TIME_LIMIT);
        lb_instructionTime_en.setId(Constants.EN_FONT_TIME_LIMIT);

        lb_enExampleHeader.setId(Constants.EN_FONT_EXAMPLE_HEADER);
        lb_exampleBody.setId(Constants.CN_FONT_EXAMPLE);
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
        lb_instructionBody_zh.setText(Main.R2L1_DATA.INSTRUCTION_ZH);
        lb_instructionBody_en.setText(Main.R2L1_DATA.INSTRUCTION_EN);
        String time_zh = "限时" + Main.R2L1_DATA.TIME_LIMIT + "分钟";
        String time_en = "Time Limit: " + Main.R2L1_DATA.TIME_LIMIT + ((Main.R2L1_DATA.TIME_LIMIT > 1) ? " minutes" : " minute");
        lb_instructionTime_zh.setText(time_zh);
        lb_instructionTime_en.setText(time_en);

        lb_exampleBody.setText(Main.R2L1_DATA.EXAMPLES.get(0));

        lb_question1.setText(Main.R2L1_DATA.QUESTIONS.get(0));
        lb_question2.setText(Main.R2L1_DATA.QUESTIONS.get(1));
        lb_question3.setText(Main.R2L1_DATA.QUESTIONS.get(2));
        lb_question4.setText(Main.R2L1_DATA.QUESTIONS.get(3));
        lb_question5.setText(Main.R2L1_DATA.QUESTIONS.get(4));

        lb_question1_st.setText(lb_question1.getText());
        lb_question2_st.setText(lb_question2.getText());
        lb_question3_st.setText(lb_question3.getText());
        lb_question4_st.setText(lb_question4.getText());
        lb_question5_st.setText(lb_question5.getText());

        lb_pointTotal.setText("0"); // the initial total point is 0
    }

    @FXML
    private void handleMouseClick(MouseEvent e) {
        if (e.getSource() == bt_startTimer) {
            bt_startTimer.setVisible(false);
            super.writeToClient(Constants.DIS_R1L1_QS);
            lb_timer.setVisible(true);
            lb_question1.setVisible(true);
            lb_question2.setVisible(true);
            lb_question3.setVisible(true);
            lb_question4.setVisible(true);
            lb_question5.setVisible(true);
        } else if (e.getSource() == gp_score && cb_users.getValue() != null) {
            for (UserDataLevel1 ud : level1Users) {
                if (ud.getUSER_NAME() != null && ud.getUSER_NAME().compareTo((String) cb_users.getValue()) == 0) {
                    lb_answer1.setText(ud.getRound2Answers().get(0));
                    lb_answer2.setText(ud.getRound2Answers().get(1));
                    lb_answer3.setText(ud.getRound2Answers().get(2));
                    lb_answer4.setText(ud.getRound2Answers().get(3));
                    lb_answer5.setText(ud.getRound2Answers().get(4));

                    tf_point1.setText((ud.getPoints() == null) ? "0" : Integer.toString(ud.getPoints().get(0)));
                    tf_point2.setText((ud.getPoints() == null) ? "0" : Integer.toString(ud.getPoints().get(1)));
                    tf_point3.setText((ud.getPoints() == null) ? "0" : Integer.toString(ud.getPoints().get(2)));
                    tf_point4.setText((ud.getPoints() == null) ? "0" : Integer.toString(ud.getPoints().get(3)));
                    tf_point5.setText((ud.getPoints() == null) ? "0" : Integer.toString(ud.getPoints().get(4)));
                    lb_pointTotal.setText((ud.getPoints() == null) ? "0" : Integer.toString(ud.getPoints().getLast()));
                }
            }
        }
    }

    @FXML
    private void handleKeyboard(KeyEvent e) {
        if (cb_users != null)
            for (UserDataLevel1 ud : level1Users) {
                if (ud.getUSER_NAME() != null && ud.getUSER_NAME().compareTo((String) cb_users.getValue()) == 0) {
                    ud.setPointRound2((tf_point1.getText().compareTo("") == 0) ? 0 : Integer.parseInt(tf_point1.getText()),0);
                    ud.setPointRound2((tf_point2.getText().compareTo("") == 0) ? 0 : Integer.parseInt(tf_point2.getText()),1);
                    ud.setPointRound2((tf_point3.getText().compareTo("") == 0) ? 0 : Integer.parseInt(tf_point3.getText()),2);
                    ud.setPointRound2((tf_point4.getText().compareTo("") == 0) ? 0 : Integer.parseInt(tf_point4.getText()),3);
                    ud.setPointRound2((tf_point5.getText().compareTo("") == 0) ? 0 : Integer.parseInt(tf_point5.getText()),4);
                    lb_pointTotal.setText(Integer.toString(ud.getPoints().getLast()));
                }
            }
    }

    @Override
    public void writeToClient(int command) {

    }

    @Override
    public void writeToClient(int command, LinkedList<String> data) {

    }

    @Override
    public void handleClientData(int command, LinkedList<String> data) {
        switch (command) {
            case Constants.SND_R1L1_RS:
                lb_answer1.setText(level1Users.getFirst().getRound2Answers().get(0));
                lb_answer2.setText(level1Users.getFirst().getRound2Answers().get(1));
                lb_answer3.setText(level1Users.getFirst().getRound2Answers().get(2));
                lb_answer4.setText(level1Users.getFirst().getRound2Answers().get(3));
                lb_answer5.setText(level1Users.getFirst().getRound2Answers().get(4));
                System.out.println(level1Users.getFirst().getRound2Answers());
                break;
            default:
                break;
        }
    }

    @Override
    public void run() {
        while (true) {
            if (tp_mainTab.getTabs().get(1).isSelected()) {
                super.writeToClient(Constants.DIS_R1L1_EX);
                break;
            }
        }
        thread.interrupt();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleId();
        setData();
        lb_timer.setVisible(false);
        lb_question1.setVisible(false);
        lb_question2.setVisible(false);
        lb_question3.setVisible(false);
        lb_question4.setVisible(false);
        lb_question5.setVisible(false);
        bt_startTimer.setVisible(true);
    }

}


