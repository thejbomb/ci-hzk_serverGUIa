package main.round2;

import data.UserDataLevel1;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Polyline;
import main.Main;
import tool.Constants;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class Level1Controller extends Round2Controller implements Initializable {

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

    private Round2Controller round2Controller;

    private LinkedList<Label> questions;
    private LinkedList<Label> questions_st;
    private LinkedList<FlowPane> answers;
    private LinkedList<TextField> points;

    public void init(LinkedList<UserDataLevel1> level1Users, Round2Controller controller) {
        this.level1Users = level1Users;
        round2Controller = controller;
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
            super.writeToClient(Constants.DIS_R2L1_QST, Constants.LEVEL1);
            lb_timer.setVisible(true);
            for (Label question : questions) question.setVisible(true);
        } else if (e.getSource() == gp_score && cb_users.getValue() != null) {
            for (UserDataLevel1 ud : level1Users) {
                if (ud.isOnline() && ud.getUSER_NAME() != null && ud.getUSER_NAME().compareTo((String) cb_users.getValue()) == 0) {
                    for (int i = 0; i < answers.size(); i++) {
                        answers.get(i).getChildren().clear();
                        for (LinkedList<Polyline> pl : ud.getRound2Answers()[i]) {
                            Pane pane = new Pane();
                            pane.getChildren().addAll(pl);
                            answers.get(i).getChildren().addAll(pane);
                        }
                        points.get(i).setText((ud.getRound2Points() == null) ? "0" : Integer.toString(ud.getRound2Points().get(i)));
                    }
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

    public void show() {
        ap_root.setVisible(true);
    }

    private void hide() {
        ap_root.setVisible(false);
    }

    @FXML
    private void handleKeyboard() {
        if (cb_users != null)
            for (UserDataLevel1 ud : level1Users) {
                if (ud.getUSER_NAME() != null && ud.getUSER_NAME().compareTo((String) cb_users.getValue()) == 0) {
                    for (int i = 0; i < points.size(); i++)
                        ud.setPointRound2((points.get(i).getText().compareTo("") == 0) ? 0 : Integer.parseInt(points.get(i).getText()), i);
                    lb_pointTotal.setText(Integer.toString(ud.getRound2Points().getLast()));
                    writeToClient(Constants.S2C_R2L1_SCR, packageData(ud.getRound2Points().getLast()), ud.getThreadId());
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
                for (UserDataLevel1 ud : level1Users) {
                    if (ud.getThreadId() == activeThreadId)
                        ud.setRound2Answers(data);
                }
                try {
                    for (int i = 0; i < answers.size(); i++) {
                        for (LinkedList<Polyline> pl : level1Users.getFirst().getRound2Answers()[i]) {
                            Pane pane = new Pane();
                            pane.getChildren().addAll(pl);
                            answers.get(i).getChildren().addAll(pane);
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

        questions_st = new LinkedList<>();

        answers = new LinkedList<>();

        points = new LinkedList<>();

        questions.add(lb_question1);
        questions.add(lb_question2);
        questions.add(lb_question3);
        questions.add(lb_question4);
        questions.add(lb_question5);

        for(int i = 0; i < Main.R2L1_DATA.QUESTIONS.size(); i++) {
            HBox hbox = new HBox();
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            Label l = new Label(Main.R2L1_DATA.QUESTIONS.get(i));
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


