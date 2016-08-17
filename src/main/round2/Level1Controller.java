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
import javafx.scene.layout.BorderPane;
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
    private Label lb_instructionHeader;
    @FXML
    private Label lb_instructionBody_zh;
    @FXML
    private Label lb_instructionBody_en;
    @FXML
    private Label lb_instructionTime_zh;
    @FXML
    private Label lb_instructionTime_en;
    @FXML
    private Label lb_exampleHeader;
    @FXML
    private Label lb_exampleBody;
    @FXML
    private Label lb_exampleWarning;
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
    private BorderPane bp_start;
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
    @FXML
    private Button bt_home;

    private Round2Controller round2Controller;

    private LinkedList<Label> questions;
    private LinkedList<Label> questions_st;
    private LinkedList<Label> answers;
    private LinkedList<TextField> points;

    private Thread thread;

    public void init(LinkedList<UserDataLevel1> level1Users, Round2Controller controller) {
        this.level1Users = level1Users;
        round2Controller = controller;
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

    private void setData() {
        lb_instructionBody_zh.setText(Main.R2L1_DATA.INSTRUCTION_ZH);
        lb_instructionBody_en.setText(Main.R2L1_DATA.INSTRUCTION_EN);
        String time_zh = "限时" + Main.R2L1_DATA.TIME_LIMIT + "分钟";
        String time_en = "Time Limit: " + Main.R2L1_DATA.TIME_LIMIT + ((Main.R2L1_DATA.TIME_LIMIT > 1) ? " minutes" : " minute");
        lb_instructionTime_zh.setText(time_zh);
        lb_instructionTime_en.setText(time_en);

        lb_exampleBody.setText(Main.R2L1_DATA.EXAMPLES.get(0));

        for (int i = 0; i < questions.size(); i++) {
            questions.get(i).setText(Main.R2L1_DATA.QUESTIONS.get(i));

            questions_st.get(i).setText(questions.get(i).getText());
        }

        lb_pointTotal.setText("0"); // set the text of the initial score to 0
    }

    @FXML
    private void handleMouseClick(MouseEvent e) {
        if (e.getSource() == bt_startTimer) {
            bt_startTimer.setVisible(false);
            bp_start.setVisible(false);
            super.writeToClient(Constants.DIS_R2L1_QST);
            lb_timer.setVisible(true);
            for (Label question : questions) question.setVisible(true);
        } else if (e.getSource() == gp_score && cb_users.getValue() != null) {
            for (UserDataLevel1 ud : level1Users) {
                if (ud.getUSER_NAME() != null && ud.getUSER_NAME().compareTo((String) cb_users.getValue()) == 0) {
                    for (int i = 0; i < answers.size(); i++) {
                        answers.get(i).setText(ud.getRound2Answers().get(i));
                        points.get(i).setText((ud.getRound2Points() == null) ? "0" : Integer.toString(ud.getRound2Points().get(i)));
                    }
                    lb_pointTotal.setText((ud.getRound2Points() == null) ? "0" : Integer.toString(ud.getRound2Points().getLast()));
                }
            }
        } else if (e.getSource() == bt_home) {
            hide();
            round2Controller.show();
        }
    }

    public void show() {
        ap_root.setVisible(true);
    }

    private void hide() {
        ap_root.setVisible(false);
    }

    @FXML
    private void handleKeyboard(KeyEvent e) {
        if (cb_users != null)
            for (UserDataLevel1 ud : level1Users) {
                if (ud.getUSER_NAME() != null && ud.getUSER_NAME().compareTo((String) cb_users.getValue()) == 0) {
                    for (int i = 0; i < points.size(); i++)
                        ud.setPointRound2((points.get(i).getText().compareTo("") == 0) ? 0 : Integer.parseInt(points.get(i).getText()), i);
                    lb_pointTotal.setText(Integer.toString(ud.getRound2Points().getLast()));
                    writeToClient(Constants.S2C_R2L1_SCR);
                }
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
            case Constants.C2S_R2L1_ANS:
                for (int i = 0; i < answers.size(); i++)
                    answers.get(i).setText(level2Users.getFirst().getRound2Answers().get(i));
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
                super.writeToClient(Constants.DIS_R2L1_EXP);
                break;
            }
        }
        thread.interrupt();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        questions = new LinkedList<>();

        questions_st = new LinkedList<>();

        answers = new LinkedList<>();

        points = new LinkedList<>();

        questions.add(lb_question1);
        questions.add(lb_question2);
        questions.add(lb_question3);
        questions.add(lb_question4);
        questions.add(lb_question5);

        questions_st.add(lb_question1_st);
        questions_st.add(lb_question2_st);
        questions_st.add(lb_question3_st);
        questions_st.add(lb_question4_st);
        questions_st.add(lb_question5_st);

        answers.add(lb_answer1);
        answers.add(lb_answer2);
        answers.add(lb_answer3);
        answers.add(lb_answer4);
        answers.add(lb_answer5);

        points.add(tf_point1);
        points.add(tf_point2);
        points.add(tf_point3);
        points.add(tf_point4);
        points.add(tf_point5);

        setData();
        tp_mainTab.setVisible(true);
        lb_timer.setVisible(false);
        for (Label question : questions)
            question.setVisible(false);
        bt_startTimer.setVisible(true);
    }

}


