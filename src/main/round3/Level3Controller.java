package main.round3;

import data.UserDataLevel3;
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
import java.util.*;

public class Level3Controller extends Round3Controller implements Initializable, Runnable {
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
    private Label lb_total;
    @FXML
    private Label lb_pointTotal;
    @FXML
    private Button bt_home;
    @FXML
    private Button bt_instructionHome;
    @FXML
    private Button bt_exampleHome;
    @FXML
    private TextField tf_point3;
    @FXML
    private FlowPane fp_answers;
    @FXML
    private FlowPane fp_roots;

    private LinkedList<Label> roots;
    private LinkedList<TextField> points;
    private LinkedList<Label> answers;

    private Round3Controller round3Controller;

    private Thread thread;

    public void init(LinkedList<UserDataLevel3> level3Users, Round3Controller controller) {
        this.level3Users = level3Users;
        round3Controller = controller;
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
        for (UserDataLevel3 ud : level3Users) {
            studentName.add(ud.getUSER_NAME());
        }
        ObservableList<String> name = FXCollections.observableArrayList(studentName);
        cb_users.setItems(name);
        cb_users.setValue(cb_users.getItems().get(0));
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
                    ud.setPointRound3((tf_point3.getText().compareTo("") == 0) ? 0 : Integer.parseInt(tf_point3.getText()), 1);
                    lb_pointTotal.setText(Integer.toString(ud.getRound3Points().getLast()));
                    writeToClient(Constants.S2C_R3L3_SCR, packageData(ud.getRound3Points().getLast()), ud.getThreadId());
                }
            }
    }

    @FXML
    private void handleMouseClick(MouseEvent e) {
        if (e.getSource() == bt_startTimer) {
            bt_startTimer.setVisible(false);
            bp_start.setVisible(false);
            super.writeToClient(Constants.DIS_R3L3_QST);
            lb_timer.setVisible(true);
            for (Label q : roots)
                q.setVisible(true);
        } else if (e.getSource() == gp_score && cb_users.getValue() != null) {
            //LinkedList<String> choices = new LinkedList<>(Main.R3L3_DATA.ANSWERS);
            for (UserDataLevel3 ud : level3Users) {
                if (ud.getUSER_NAME() != null && ud.getUSER_NAME().compareTo((String) cb_users.getValue()) == 0) {
                    Label label = new Label(ud.getRound3Answers().toString());
                    label.setStyle("-fx-font: bold 30pt KaiTi; -fx-text-fill: rgb(0,255,0)");
                    fp_answers.getChildren().addAll(label);
                    tf_point3.setText((ud.getRound3Points() == null) ? "0" : Integer.toString(ud.getRound3Points().getLast()));
                    lb_pointTotal.setText((ud.getRound3Points() == null) ? "0" : Integer.toString(ud.getRound3Points().getLast()));
                }
            }
        } else if (e.getSource() == bt_home) {
            hide();
            round3Controller.show();
        } else if (e.getSource() == bt_instructionHome) {
            hideInstruction();
            hide();
            round3Controller.show();
        } else if (e.getSource() == bt_exampleHome) {
            hideExample();
            hide();
            round3Controller.show();
        }
    }

    private void setData() {
        lb_instructionBody_zh.setText(Main.R3L3_DATA.INSTRUCTION_ZH);
        lb_instructionBody_en.setText(Main.R3L3_DATA.INSTRUCTION_EN);
        String time_zh = "限时" + Main.R3L3_DATA.TIME_LIMIT + "分钟";
        String time_en = "Time Limit: " + Main.R3L3_DATA.TIME_LIMIT + ((Main.R3L3_DATA.TIME_LIMIT > 1) ? " minutes" : " minute");
        lb_instructionTime_zh.setText(time_zh);
        lb_instructionTime_en.setText(time_en);

        for (int i = 0; i < roots.size(); i++) {
            roots.get(i).setText(Main.R3L3_DATA.ROOTS.get(i));
            Label label = new Label();
            label.setText(Main.R3L3_DATA.ROOTS.get(i));
            label.setStyle("-fx-font: bold 30pt KaiTi; -fx-text-fill: rgb(0,255,0)");
            fp_roots.getChildren().add(label);
        }
        lb_pointTotal.setText("0");
    }

    @Override
    public void handleClientData(int command, LinkedList<String> data) {
        switch (command) {
            case Constants.C2S_R3L3_ANS:
                for (UserDataLevel3 ud : level3Users) {
                    if (ud.getThreadId() == activeThreadId)
                        ud.setRound3Answers(data);
                }
                System.out.println(level3Users.getFirst().getRound3Answers());
                break;
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        roots = new LinkedList<>();
        for(int i = 0; i < Main.R3L3_DATA.ROOTS.size(); i++) {
            Label label = new Label();
            roots.add(label);
        }

        answers = new LinkedList<>();

        points = new LinkedList<>();
        points.add(tf_point3);


//        System.out.println("Randomized answer order: " + randomizedAnswers);

        setData();

        tp_mainTab.setVisible(true);
        lb_timer.setVisible(false);
        for (Label question : roots)
            question.setVisible(false);
        bt_startTimer.setVisible(true);
    }
}
