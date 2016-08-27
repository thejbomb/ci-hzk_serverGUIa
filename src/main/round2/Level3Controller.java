package main.round2;

import data.UserDataLevel3;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
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
    private Label lb_answer6;
    @FXML
    private Label lb_answer7;
    @FXML
    private Label lb_answer8;
    @FXML
    private Label lb_answer9;
    @FXML
    private Label lb_answer10;
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
    @FXML
    private Line line1;
    @FXML
    private Line line2;
    @FXML
    private Line line3;
    @FXML
    private Line line4;
    @FXML
    private Line line5;
    @FXML
    private Line line6;
    @FXML
    private Line line7;
    @FXML
    private Line line8;
    @FXML
    private Line line9;
    @FXML
    private Line line10;
    @FXML
    private GridPane gp_instruction;
    @FXML
    private GridPane gp_example;
    @FXML
    private Button bt_instructionHome;
    @FXML
    private Button bt_exampleHome;

    private LinkedList<Label> questions;
    private LinkedList<Label> choices;
    private LinkedList<Label> questions_st;
    private LinkedList<Label> choices_st;
    private LinkedList<TextField> points;
    private LinkedList<Label> answers;
    private LinkedList<Line> lines;

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
        gp_instruction.setVisible(true);
    }

    public void hideInstruction() {
        gp_instruction.setVisible(false);
    }

    public void showExample() {
        ap_root.setVisible(true);
        tp_mainTab.setVisible(false);
        gp_instruction.setVisible(false);
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
                    for (int i = 0; i < points.size(); i++)
                        ud.setPointRound2((points.get(i).getText().compareTo("") == 0) ? 0 : Integer.parseInt(points.get(i).getText()), i);
                    lb_pointTotal.setText(Integer.toString(ud.getRound2Points().getLast()));
                    writeToClient(Constants.S2C_R2L3_SCR);
                }
            }
    }

    @FXML
    private void handleMouseClick(MouseEvent e) {
        if (e.getSource() == bt_startTimer) {
            bt_startTimer.setVisible(false);
            bp_start.setVisible(false);
            super.writeToClient(Constants.DIS_R2L3_QST);
            lb_timer.setVisible(true);
            for (Label q : questions)
                q.setVisible(true);
            for (Label c : choices)
                c.setVisible(true);
        } else if (e.getSource() == gp_score && cb_users.getValue() != null) {
            LinkedList<String> choices = new LinkedList<>(Main.R2L3_DATA.ANSWERS);
            for (UserDataLevel3 ud : level3Users) {
                if (ud.getUSER_NAME() != null && ud.getUSER_NAME().compareTo((String) cb_users.getValue()) == 0) {
                    Collections.shuffle(choices, new Random(ud.getSeed()));
                    for (int i = 0; i < answers.size(); i++) {
                        choices_st.get(i).setText((char) (i + 0x41) + "." + choices.get(i));
                        answers.get(i).setText(ud.getRound2Answers().get(i));
                        points.get(i).setText((ud.getRound2Points() == null) ? "0" : Integer.toString(ud.getRound2Points().get(i)));
                    }
                    drawLine();
                    lb_pointTotal.setText((ud.getRound2Points() == null) ? "0" : Integer.toString(ud.getRound2Points().getLast()));
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

    private void drawLine() {
        for (int j = 0; j < answers.size(); j++) {
            Label answer = answers.get(j);
            String input = answer.getText();
            if (input.compareTo("") != 0) {
                input = input.toUpperCase();
                if (input.length() > 1)
                    answer.setText(input.substring(0, 1));
                else if (input.charAt(0) >= answers.size() + 0x41)
                    answer.setText("");
                else
                    answer.setText(input);

                int _input = input.charAt(0);

                for (int i = 0; i < answers.size(); i++) {
                    if (_input == i + 0x41) {
                        // ONLY WORK IN MAXIMIZED SCREEN MODE RIGHT NOW
                        Scene scene = choices_st.get(i).getScene();
                        Point2D windowC = new Point2D(scene.getWindow().getX(), scene.getWindow().getY());
                        Point2D sceneC = new Point2D(scene.getX(), scene.getY());
                        Point2D node = choices_st.get(i).localToScene(0.0, 0.0);
                        double x = windowC.getX() + sceneC.getX() + node.getX();
                        double y = windowC.getY() + sceneC.getY() + node.getY();
                        lines.get(j).setStartX(x);
                        lines.get(j).setStartY(y);
                        scene = questions_st.get(j).getScene();
                        windowC = new Point2D(scene.getWindow().getX(), scene.getWindow().getY());
                        sceneC = new Point2D(scene.getX(), scene.getY());
                        node = questions_st.get(j).localToScene(0.0, 0.0);
                        x = windowC.getX() + sceneC.getX() + node.getX();
                        y = windowC.getY() + sceneC.getY() + node.getY();
                        lines.get(j).setEndX(x + questions_st.get(i).getWidth());
                        lines.get(j).setEndY(y);

                        lines.get(j).setVisible(true);
                    }
                }
            }
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
            choices.get(i).setText((char) (i + 0x41) + "." + randomizedAnswers.get(i));

            questions_st.get(i).setText(Main.R2L3_DATA.QUESTIONS.get(i));

            points.get(i).setText("0");
            lb_pointTotal.setText("0");
        }
    }

    @Override
    public void handleClientData(int command, LinkedList<String> data) {
        switch (command) {
            case Constants.C2S_R2L3_SEED:
                LinkedList<String> choices = new LinkedList<>(Main.R2L3_DATA.ANSWERS);
                Collections.shuffle(choices, new Random(level3Users.getFirst().getSeed()));
                for (int i = 0; i < choices.size(); i++)
                    choices_st.get(i).setText((char) (i + 0x41) + "." + choices.get(i));
                break;
            case Constants.C2S_R2L3_ANS:
                for (int i = 0; i < answers.size(); i++)
                    answers.get(i).setText(level3Users.getFirst().getRound2Answers().get(i));
                drawLine();
                System.out.println(level3Users.getFirst().getRound2Answers());
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

        answers = new LinkedList<>();
        answers.add(lb_answer1);
        answers.add(lb_answer2);
        answers.add(lb_answer3);
        answers.add(lb_answer4);
        answers.add(lb_answer5);
        answers.add(lb_answer6);
        answers.add(lb_answer7);
        answers.add(lb_answer8);
        answers.add(lb_answer9);
        answers.add(lb_answer10);

        lines = new LinkedList<>();
        lines.add(line1);
        lines.add(line2);
        lines.add(line3);
        lines.add(line4);
        lines.add(line5);
        lines.add(line6);
        lines.add(line7);
        lines.add(line8);
        lines.add(line9);
        lines.add(line10);

        randomizedAnswers = randomizeAnswers(Main.R2L3_DATA.ANSWERS);
        System.out.println("Randomized answer order: " + randomizedAnswers);

        setData();

        tp_mainTab.setVisible(true);
        lb_timer.setVisible(false);
        for (Label question : questions)
            question.setVisible(false);
        for (Label choice : choices)
            choice.setVisible(false);
        bt_startTimer.setVisible(true);
    }
}
