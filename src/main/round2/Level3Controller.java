package main.round2;

import data.UserDataLevel3;
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
import tool.StyleId;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class Level3Controller extends Round2Controller implements Initializable, Runnable {
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
    private Button bt_startTimer;
    @FXML
    private Label lb_timer;
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
    private Label lb_choice1;
    @FXML
    private Label lb_choice2;
    @FXML
    private Label lb_choice3;
    @FXML
    private Label lb_choice4;
    @FXML
    private Label lb_choice5;
    @FXML
    private Label lb_choice6;
    @FXML
    private Label lb_choice7;
    @FXML
    private Label lb_choice8;
    @FXML
    private Label lb_choice9;
    @FXML
    private Label lb_choice10;
    @FXML
    private GridPane gp_score;
    @FXML
    private ComboBox cb_users;
    @FXML
    private Button bt_home;
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
    private Label lb_choice1_st;
    @FXML
    private Label lb_choice2_st;
    @FXML
    private Label lb_choice3_st;
    @FXML
    private Label lb_choice4_st;
    @FXML
    private Label lb_choice5_st;
    @FXML
    private Label lb_choice6_st;
    @FXML
    private Label lb_choice7_st;
    @FXML
    private Label lb_choice8_st;
    @FXML
    private Label lb_choice9_st;
    @FXML
    private Label lb_choice10_st;
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
    private Label lb_total;
    @FXML
    private Label lb_pointTotal;

    private LinkedList<Label> questions;
    private LinkedList<Label> choices;
    private LinkedList<Label> questions_st;
    private LinkedList<Label> choices_st;
    private LinkedList<TextField> points;
    private LinkedList<Label> answers;

    private Round2Controller round2Controller;

    private Thread thread;

    public void init(LinkedList<UserDataLevel3> level3Users, Round2Controller controller) {
        this.level3Users = level3Users;
        round2Controller = controller;
        thread = new Thread(this);
        thread.start();
        initComboBox();
    }

    private void initComboBox() {
        LinkedList<String> studentName = new LinkedList<>();
        for (UserDataLevel3 ud : level3Users) {
            studentName.add(ud.getUSER_NAME());
        }
        ObservableList<String> name = FXCollections.observableArrayList(studentName);
        cb_users.setItems(name);
        cb_users.setValue(cb_users.getItems().get(0));
    }

    public void show() {
        ap_root.setVisible(true);
    }

    private void hide() {
        ap_root.setVisible(false);
    }

    @FXML
    private void handleKeyboard(KeyEvent e){
        if (cb_users != null)
            for (UserDataLevel3 ud : level3Users) {
                if (ud.getUSER_NAME() != null && ud.getUSER_NAME().compareTo((String) cb_users.getValue()) == 0) {
                    for (int i = 0; i < points.size(); i++)
                        ud.setPointRound2((points.get(i).getText().compareTo("") == 0) ? 0 : Integer.parseInt(points.get(i).getText()), i);
                    lb_pointTotal.setText(Integer.toString(ud.getPoints().getLast()));
                    writeToClient(Constants.S2C_R2L3_SCR);
                }
            }
    }

    @FXML
    private void handleMouseClick(MouseEvent e){
        if (e.getSource() == bt_startTimer) {
            bt_startTimer.setVisible(false);
            super.writeToClient(Constants.DIS_R2L3_QST);
            lb_timer.setVisible(true);
            for(Label q : questions)
                q.setVisible(true);
            for(Label c : choices)
                c.setVisible(true);
        } else if (e.getSource() == gp_score && cb_users.getValue() != null) {
            for (UserDataLevel3 ud : level3Users) {
                if (ud.getUSER_NAME() != null && ud.getUSER_NAME().compareTo((String) cb_users.getValue()) == 0) {
                    for (int i = 0; i < answers.size(); i++) {
                        answers.get(i).setText(ud.getRound2Answers().get(i));
                        points.get(i).setText((ud.getPoints() == null) ? "0" : Integer.toString(ud.getPoints().get(i)));
                    }
                    lb_pointTotal.setText((ud.getPoints() == null) ? "0" : Integer.toString(ud.getPoints().getLast()));
                }
            }
        } else if (e.getSource() == bt_home) {
            hide();
            round2Controller.show();
        }
    }

    private void setStyleId() {
        lb_instructionHeader.setId(StyleId.INSTRUCTION_HEADER);
        lb_instructionBody_zh.setId(StyleId.INSTRUCTION_BODY_ZH);
        lb_instructionBody_en.setId(StyleId.INSTRUCTION_BODY_EN);
        lb_instructionTime_zh.setId(StyleId.INSTRUCTION_TIME_ZH);
        lb_instructionTime_en.setId(StyleId.INSTRUCTION_TIME_EN);

        lb_exampleHeader.setId(StyleId.EXAMPLE_HEADER);
        lb_exampleBody.setId(StyleId.EXAMPLE_BODY);
        lb_exampleWarning.setId(StyleId.EXAMPLE_WARNING);

        for (int i = 0; i < questions.size(); i++) {
            questions.get(i).setId(StyleId.ROUND_QUESTIONS);
            choices.get(i).setId(StyleId.ROUND_QUESTIONS);
            questions_st.get(i).setId(StyleId.ROUND_QUESTIONS);
            choices_st.get(i).setId(StyleId.ROUND_QUESTIONS);
            points.get(i).setId(StyleId.SCORE_POINT);
        }

        lb_total.setId(StyleId.SCORE_TOTAL_LABEL);
        lb_pointTotal.setId(StyleId.SCORE_TOTAL_POINT);
    }

    private void setData() {
        lb_instructionBody_zh.setText(Main.R2L3_DATA.INSTRUCTION_ZH);
        lb_instructionBody_en.setText(Main.R2L3_DATA.INSTRUCTION_EN);
        String time_zh = "限时" + Main.R2L3_DATA.TIME_LIMIT + "分钟";
        String time_en = "Time Limit: " + Main.R2L3_DATA.TIME_LIMIT + ((Main.R2L3_DATA.TIME_LIMIT > 1) ? " minutes" : " minute");
        lb_instructionTime_zh.setText(time_zh);
        lb_instructionTime_en.setText(time_en);

        for (int i = 0; i < questions.size(); i++) {
            questions.get(i).setText((i + 1 > 9) ?
                    (i + 1) + "." + Main.R2L3_DATA.QUESTIONS.get(i) :
                    " " + (i + 1) + "." + Main.R2L3_DATA.QUESTIONS.get(i));
            choices.get(i).setText((char) (i + 0x41) + "." + Main.R2L3_DATA.ANSWERS.get(i));

            questions_st.get(i).setText(questions.get(i).getText());
            choices_st.get(i).setText(choices.get(i).getText());

            points.get(i).setText("0");
            lb_pointTotal.setText("0");
        }
    }

    @Override
    public void handleClientData(int command, LinkedList<String> data) {


    }

    @Override
    public void run() {
        while (true) {
            if (tp_mainTab.getTabs().get(1).isSelected()) {
                super.writeToClient(Constants.DIS_R2L3_EXP);
                break;
            }
        }
        thread.interrupt();
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

        choices = new LinkedList<>();
        choices.add(lb_choice1);
        choices.add(lb_choice2);
        choices.add(lb_choice3);
        choices.add(lb_choice4);
        choices.add(lb_choice5);
        choices.add(lb_choice6);
        choices.add(lb_choice7);
        choices.add(lb_choice8);
        choices.add(lb_choice9);
        choices.add(lb_choice10);

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

        choices_st = new LinkedList<>();
        choices_st.add(lb_choice1_st);
        choices_st.add(lb_choice2_st);
        choices_st.add(lb_choice3_st);
        choices_st.add(lb_choice4_st);
        choices_st.add(lb_choice5_st);
        choices_st.add(lb_choice6_st);
        choices_st.add(lb_choice7_st);
        choices_st.add(lb_choice8_st);
        choices_st.add(lb_choice9_st);
        choices_st.add(lb_choice10_st);

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

        setStyleId();
        setData();

        tp_mainTab.setVisible(true);
        lb_timer.setVisible(false);
        for(Label q : questions)
            q.setVisible(false);
        for(Label c : choices)
            c.setVisible(false);
        bt_startTimer.setVisible(true);
    }
}
