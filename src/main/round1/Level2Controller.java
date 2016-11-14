package main.round1;

import data.UserDataLevel2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import main.Main;
import tool.Constants;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class Level2Controller extends Round1Controller implements Initializable, Runnable {

    @FXML
    private AnchorPane ap_root;
    @FXML
    private TabPane tp_mainTab;
    @FXML
    private GridPane gp_instruction;
    @FXML
    private GridPane gp_example;
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
    private Label lb_question6;
    @FXML
    private Label lb_question7;
    @FXML
    private Label lb_question8;
    @FXML
    private Label lb_question9;
    @FXML
    private Label lb_question10;

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
    private Label lb_question6_st;
    @FXML
    private Label lb_question7_st;
    @FXML
    private Label lb_question8_st;
    @FXML
    private Label lb_question9_st;
    @FXML
    private Label lb_question10_st;
    @FXML
    private FlowPane fp_answers1;
    @FXML
    private FlowPane fp_answers2;
    @FXML
    private FlowPane fp_answers3;
    @FXML
    private FlowPane fp_answers4;
    @FXML
    private FlowPane fp_answers5;
    @FXML
    private FlowPane fp_answers6;
    @FXML
    private FlowPane fp_answers7;
    @FXML
    private FlowPane fp_answers8;
    @FXML
    private FlowPane fp_answers9;
    @FXML
    private FlowPane fp_answers10;
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
    private TextField tf_point6;
    @FXML
    private TextField tf_point7;
    @FXML
    private TextField tf_point8;
    @FXML
    private TextField tf_point9;
    @FXML
    private TextField tf_point10;
    @FXML
    private Label lb_pointTotal;
    @FXML
    private Button bt_home;
    @FXML
    private Button bt_instructionHome;
    @FXML
    private Button bt_exampleHome;

    private LinkedList<Label> questions;
    private LinkedList<Label> questions_st;
    private LinkedList<Label> choices_st;
    private LinkedList<TextField> points;
    private LinkedList<FlowPane> answers;

    private Round1Controller round1Controller;

    public void init(LinkedList<UserDataLevel2> level2Users, Round1Controller controller) {
        this.level2Users = level2Users;
        round1Controller = controller;
        initComboBox();
    }

    public void showInstruction() {
        ap_root.setVisible(true);
        tp_mainTab.setVisible(false);
        gp_example.setVisible(false);
        gp_instruction.setVisible(true);
        gp_score.setVisible(false);
    }

    public void hideInstruction() {
        gp_instruction.setVisible(false);
    }

    public void showExample() {
        ap_root.setVisible(true);
        tp_mainTab.setVisible(false);
        gp_instruction.setVisible(false);
        gp_example.setVisible(true);
        gp_score.setVisible(false);
    }

    public void hideExample() {
        gp_example.setVisible(false);
    }

    public void showScoring() {
        ap_root.setVisible(true);
        tp_mainTab.setVisible(false);
        gp_instruction.setVisible(false);
        gp_example.setVisible(false);
        gp_score.setVisible(true);
    }

    public void hideScoring() {
        gp_score.setVisible(false);
    }

    public void setActiveThread(long threadId) {
        activeThreadId = threadId;
    }

    private void initComboBox() {
        LinkedList<String> studentName = new LinkedList<>();
        for (UserDataLevel2 ud : level2Users) {
            studentName.add(ud.getUSER_NAME());
        }
        ObservableList<String> name = FXCollections.observableArrayList(studentName);
        cb_users.setItems(name);
        cb_users.setValue(cb_users.getItems().get(0));
    }

    private void setData() {
        lb_instructionBody_zh.setText(Main.R1L1_DATA.INSTRUCTION_ZH);
        lb_instructionBody_en.setText(Main.R1L1_DATA.INSTRUCTION_EN);
        String time_zh = "限时" + Main.R1L1_DATA.TIME_LIMIT + "分钟";
        String time_en = "Time Limit: " + Main.R1L1_DATA.TIME_LIMIT + ((Main.R1L1_DATA.TIME_LIMIT > 1) ? " minutes" : " minute");
        lb_instructionTime_zh.setText(time_zh);
        lb_instructionTime_en.setText(time_en);

        lb_exampleBody.setText(Main.R1L1_DATA.EXAMPLES.get(0));

        for (int i = 0; i < questions.size(); i++) {
            questions.get(i).setText(Main.R1L1_DATA.QUESTIONS.get(i));
            questions_st.get(i).setText(questions.get(i).getText());
        }

        lb_pointTotal.setText("0");

    }



    @FXML
    private void handleMouseClick(MouseEvent e) {
        if (e.getSource() == bt_startTimer) {
            bt_startTimer.setVisible(false);
            bp_start.setVisible(false);
            super.writeToClient(Constants.DIS_R1L1_QST);
            lb_timer.setVisible(true);
            for (Label q : questions)
                q.setVisible(true);
        } else if (e.getSource() == gp_score && cb_users.getValue() != null) {
            LinkedList<String> choices = new LinkedList<>(Main.R1L2_DATA.ANSWERS);
            for (UserDataLevel2 ud : level2Users) {
                if (ud.getUSER_NAME() != null && ud.getUSER_NAME().compareTo((String) cb_users.getValue()) == 0) {
                    //Collections.shuffle(choices, new Random(ud.getSeed()));
                    for (int i = 0; i < answers.size(); i++) {
                        answers.get(i).getChildren().clear();
                        for (String pl : ud.getRound1Answers()[i]) {
                            Label label = new Label(pl);
                            answers.get(i).getChildren().addAll(label);
                            //choices_st.get(i).setText((char) (i + 0x41) + "." + choices.get(i));
                        }
                        points.get(i).setText((ud.getRound1Points() == null) ? "0" : Integer.toString(ud.getRound1Points().get(i)));
                    }
                    lb_pointTotal.setText((ud.getRound1Points() == null) ? "0" : Integer.toString(ud.getRound1Points().getLast()));
                }
            }
        } else if (e.getSource() == bt_home) {
            hide();
            round1Controller.show();
        } else if (e.getSource() == bt_instructionHome) {
            hideInstruction();
            hide();
            round1Controller.show();
        } else if (e.getSource() == bt_exampleHome) {
            hideExample();
            hide();
            round1Controller.show();
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
            for (UserDataLevel2 ud : level2Users) {
                if (ud.getUSER_NAME() != null && ud.getUSER_NAME().compareTo((String) cb_users.getValue()) == 0) {
                    for (int i = 0; i < points.size(); i++)
                        ud.setPointRound1((points.get(i).getText().compareTo("") == 0) ? 0 : Integer.parseInt(points.get(i).getText()), i);
                    lb_pointTotal.setText(Integer.toString(ud.getRound1Points().getLast()));
                    writeToClient(Constants.S2C_R1L1_SCR);
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
            case Constants.C2S_R1L1_ANS:
                for (UserDataLevel2 ud : level2Users) {
                    if (ud.getThreadId() == activeThreadId)
                        ud.setRound1Answers(data);
                }
                try {
                    for (int i = 0; i < answers.size(); i++) {
                        for (String pl : level2Users.getFirst().getRound1Answers()[i]) {
                            Label label = new Label(pl);
                            answers.get(i).getChildren().addAll(label);
                        }
                    }
                } catch (NullPointerException ex) {

                }
                break;
            default:
                break;
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        questions = new LinkedList<>();
        questions.add(lb_question1);
        questions.add(lb_question2);
        questions.add(lb_question3);
        questions.add(lb_question4);
        questions.add(lb_question5);
        questions.add(lb_question6);
        questions.add(lb_question7);
        questions.add(lb_question8);
        questions.add(lb_question9);
        questions.add(lb_question10);

        questions_st = new LinkedList<>();
        questions_st.add(lb_question1_st);
        questions_st.add(lb_question2_st);
        questions_st.add(lb_question3_st);
        questions_st.add(lb_question4_st);
        questions_st.add(lb_question5_st);
        questions_st.add(lb_question6_st);
        questions_st.add(lb_question7_st);
        questions_st.add(lb_question8_st);
        questions_st.add(lb_question9_st);
        questions_st.add(lb_question10_st);

        answers = new LinkedList<>();
        answers.add(fp_answers1);
        answers.add(fp_answers2);
        answers.add(fp_answers3);
        answers.add(fp_answers4);
        answers.add(fp_answers5);
        answers.add(fp_answers6);
        answers.add(fp_answers7);
        answers.add(fp_answers8);
        answers.add(fp_answers9);
        answers.add(fp_answers10);

        points = new LinkedList<>();
        points.add(tf_point1);
        points.add(tf_point2);
        points.add(tf_point3);
        points.add(tf_point4);
        points.add(tf_point5);
        points.add(tf_point6);
        points.add(tf_point7);
        points.add(tf_point8);
        points.add(tf_point9);
        points.add(tf_point10);


        setData();

        lb_timer.setVisible(false);
        for (Label question : questions)
            question.setVisible(false);
        bt_startTimer.setVisible(true);
    }
}
