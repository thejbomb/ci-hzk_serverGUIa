package main.round1;

import data.UserDataLevel2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import main.Main;
import tool.Constants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    private VBox question;
    @FXML
    private Label lb_total;
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
        lb_instructionBody_zh.setText(Main.R1L2_DATA.INSTRUCTION_ZH);
        lb_instructionBody_en.setText(Main.R1L2_DATA.INSTRUCTION_EN);
        String time_zh = "限时" + Main.R1L1_DATA.TIME_LIMIT + "分钟";
        String time_en = "Time Limit: " + Main.R1L2_DATA.TIME_LIMIT + ((Main.R1L2_DATA.TIME_LIMIT > 1) ? " minutes" : " minute");
        lb_instructionTime_zh.setText(time_zh);
        lb_instructionTime_en.setText(time_en);

        lb_exampleBody.setText(Main.R1L2_DATA.EXAMPLES.get(0));

        for (int i = 0; i < questions.size(); i++) {
            questions.get(i).setText(Main.R1L2_DATA.QUESTIONS.get(i));
            questions_st.get(i).setText(questions.get(i).getText());
        }

        lb_pointTotal.setText("0");

    }

    public void record(String name) {
        File file = new File("round1lvl2.txt");
        FileWriter writer = null;
        try{
            writer = new FileWriter(file);
            writer.write(name);
            for(int i = 0; i < questions_st.size(); i++) {
                String s;
                String l = "";
                s = questions_st.get(i).getText() + ": ";
                for(int j = 0; j < answers.get(i).getChildren().size(); j++) {
                    l = l + ((Label)answers.get(i).getChildren().get(j)).getText();
                }
                l = l.replaceAll("[, ]","");
                s = s + l + "  ";
                writer.write(s);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMouseClick(MouseEvent e) {
        if (e.getSource() == bt_startTimer) {
            bt_startTimer.setVisible(false);
            bp_start.setVisible(false);
            super.writeToClient(Constants.DIS_R1L2_QST);
            lb_timer.setVisible(true);
            for (Label q : questions)
                q.setVisible(true);
        } else if (e.getSource() == gp_score && cb_users.getValue() != null) {
            for (UserDataLevel2 ud : level2Users) {
                if (ud.getUSER_NAME() != null && ud.getUSER_NAME().compareTo((String) cb_users.getValue()) == 0) {
                    for (int i = 0; i < answers.size(); i++) {
                        answers.get(i).getChildren().clear();
                        for (String l : ud.getRound1Answers()[i]) {
                            Label label = new Label(l);
                            label.setStyle("-fx-font: bold 20pt KaiTi; -fx-text-fill: rgb(0,0,150)");
                            answers.get(i).getChildren().addAll(label);
                        }
                        points.get(i).setText((ud.getRound1Points() == null) ? "0" : Integer.toString(ud.getRound1Points().get(i)));
                    }
                    lb_pointTotal.setText((ud.getRound1Points() == null) ? "0" : Integer.toString(ud.getRound1Points().getLast()));
                    record(ud.getUSER_NAME());
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
    private void handleKeyboard() {
        if (cb_users != null)
            for (UserDataLevel2 ud : level2Users) {
                if (ud.getUSER_NAME() != null && ud.getUSER_NAME().compareTo((String) cb_users.getValue()) == 0) {
                    for (int i = 0; i < points.size(); i++)
                        ud.setPointRound1((points.get(i).getText().compareTo("") == 0) ? 0 : Integer.parseInt(points.get(i).getText()), i);
                    lb_pointTotal.setText(Integer.toString(ud.getRound1Points().getLast()));
                    writeToClient(Constants.S2C_R1L2_SCR, packageData(ud.getRound1Points().getLast()), ud.getThreadId());
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
            case Constants.C2S_R1L2_ANS:
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

        answers = new LinkedList<>();

        points = new LinkedList<>();

        for(int i = 0; i < Main.R1L1_DATA.QUESTIONS.size(); i++) {
            HBox hbox = new HBox();
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            Label l = new Label(Main.R1L2_DATA.QUESTIONS.get(i));
            questions_st.add(l);
            l.setStyle("-fx-font: bold 30pt KaiTi; -fx-text-fill: rgb(150,0,250)");
            TextField text = new TextField();
            text.setOnAction(e -> handleKeyboard());
            points.add(text);
            text.setPrefWidth(120);
            FlowPane flow = new FlowPane();
            answers.add(flow);
            flow.setPrefWidth(800);
            vbox.getChildren().add(l);
            hbox.getChildren().addAll(vbox, flow, text);
            question.getChildren().add(hbox);
        }

        setData();

        lb_timer.setVisible(false);
        for (Label question : questions)
            question.setVisible(false);
        bt_startTimer.setVisible(true);
    }
}
