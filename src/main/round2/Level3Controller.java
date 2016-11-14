package main.round2;

import data.UserDataLevel3;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import main.Main;
import tool.Constants;

import java.net.URL;
import java.util.*;

public class Level3Controller extends Round2Controller implements Initializable {
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
    private BorderPane bp_start;
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
    private Label lb_total;
    @FXML
    private GridPane gp_instruction;
    @FXML
    private GridPane gp_example;
    @FXML
    private Button bt_instructionHome;
    @FXML
    private Button bt_exampleHome;
    @FXML
    private FlowPane fp_answer1;
    @FXML
    private FlowPane fp_answer2;
    @FXML
    private FlowPane fp_answer3;
    @FXML
    private FlowPane fp_answer4;
    @FXML
    private FlowPane fp_answer5;
    @FXML
    private FlowPane fp_answer6;
    @FXML
    private FlowPane fp_answer7;
    @FXML
    private FlowPane fp_answer8;
    @FXML
    private FlowPane fp_answer9;
    @FXML
    private FlowPane fp_answer10;
    @FXML
    private TextField tf_totalPoints;

    private LinkedList<Label> questions;
    private LinkedList<Label> choices;
    private LinkedList<Label> questions_st;
    private LinkedList<Label> choices_st;
    private LinkedList<TextField> points;
    private LinkedList<FlowPane> answers;

    private LinkedList<String> randomizedAnswers;

    private Round2Controller round2Controller;


    public void init(LinkedList<UserDataLevel3> level3Users, Round2Controller controller) {
        this.level3Users = level3Users;
        round2Controller = controller;

        initComboBox();
    }


    public void setActiveThread(long threadId) {
        activeThreadId = threadId;
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

    public void showInstruction() {
        ap_root.setVisible(true);
        tp_mainTab.setVisible(false);
        gp_example.setVisible(false);
        gp_score.setVisible(false);
        gp_instruction.setVisible(true);
    }

    public void hideInstruction() {
        gp_instruction.setVisible(false);
    }

    public void showExample() {
        ap_root.setVisible(true);
        tp_mainTab.setVisible(false);
        gp_instruction.setVisible(false);
        gp_score.setVisible(false);
        gp_example.setVisible(true);
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

    private LinkedList<String> randomizeAnswers(ArrayList<String> data) {
        LinkedList<String> result = new LinkedList<>(data);

        long seed = System.nanoTime();

        Collections.shuffle(result, new Random(seed));

        return result;
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
            for (UserDataLevel3 ud : level3Users) {
                if (ud.getUSER_NAME() != null && ud.getUSER_NAME().compareTo((String) cb_users.getValue()) == 0) {
                    try {
                        ud.setPointRound2(Integer.parseInt(tf_totalPoints.getText()));
                    } catch (NumberFormatException ex) {
                        System.out.println("Only numeric characters allowed.");
                    }
                    writeToClient(Constants.S2C_R2L3_SCR, packageData(ud.getround2Points()), ud.getThreadId());
                }
            }
    }

    @FXML
    private void handleMouseClick(MouseEvent e) {
        if (e.getSource() == bt_startTimer) {
            bt_startTimer.setVisible(false);
            bp_start.setVisible(false);
            super.writeToClient(Constants.DIS_R2L3_QST, Constants.ROUND3);
            lb_timer.setVisible(true);
            for (Label q : questions)
                q.setVisible(true);
            for (Label c : choices)
                c.setVisible(true);
        } else if (e.getSource() == gp_score && cb_users.getValue() != null) {
            LinkedList<String> choices = new LinkedList<>(Main.R2L3_DATA.ANSWERS);
            for (UserDataLevel3 ud : level3Users) {
                if (ud.isOnline() && ud.getUSER_NAME() != null && ud.getUSER_NAME().compareTo((String) cb_users.getValue()) == 0) {
                    Collections.shuffle(choices, new Random(ud.getSeed()));
                    for (int i = 0; i < answers.size(); i++) {
                        answers.get(i).getChildren().clear();
                        Pane pane = new Pane();
                        pane.getChildren().addAll(ud.getRound2Answers().get(i));
                        answers.get(i).getChildren().addAll(pane);
                        tf_totalPoints.setText(ud.getround2Points() + "");
                    }
                }
            }
        } else if (e.getSource() == bt_home) {
            hide();
            round2Controller.show();
        } else if (e.getSource() == bt_instructionHome) {
            hideInstruction();
            hide();
            round2Controller.show();
        } else if (e.getSource() == bt_exampleHome) {
            hideExample();
            hide();
            round2Controller.show();
        }
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

            questions_st.get(i).setText(Main.R2L3_DATA.QUESTIONS.get(i));

        }
    }

    @Override
    public void handleClientData(int command, LinkedList<String> data) {
        System.out.println("R2L3 FROM: command = " + command + " | data = " + data);
        switch (command) {
            case Constants.C2S_R2L3_SEED:
                LinkedList<String> choices = new LinkedList<>(Main.R2L3_DATA.ANSWERS);
                Collections.shuffle(choices, new Random(level3Users.getFirst().getSeed()));
                for (int i = 0; i < choices.size(); i++)
                    choices_st.get(i).setText((char) (i + 0x41) + "." + choices.get(i));
                break;
            case Constants.C2S_R2L3_ANS:
                for (UserDataLevel3 ud : level3Users)
                    if (ud.getThreadId() == activeThreadId)
                        ud.setRound2Answers(data);
                try {
                    for (int i = 0; i < answers.size(); i++) {
                        Pane pane = new Pane();
                        pane.getChildren().addAll(level3Users.getFirst().getRound2Answers().get(i));
                        answers.get(i).getChildren().addAll(pane);
                    }
                } catch (NullPointerException ex) {

                }
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

        choices_st = new LinkedList<>();


        points = new LinkedList<>();

        answers = new LinkedList<>();
        answers.add(fp_answer1);
        answers.add(fp_answer2);
        answers.add(fp_answer3);
        answers.add(fp_answer4);
        answers.add(fp_answer5);
        answers.add(fp_answer6);
        answers.add(fp_answer7);
        answers.add(fp_answer8);
        answers.add(fp_answer9);
        answers.add(fp_answer10);


        randomizedAnswers = randomizeAnswers(Main.R2L3_DATA.ANSWERS);
        System.out.println("Randomized answer order: " + randomizedAnswers);

        setData();

        tp_mainTab.setVisible(true);
        lb_timer.setVisible(false);
        for (Label question : questions)
            question.setVisible(false);
        bt_startTimer.setVisible(true);
    }
}
