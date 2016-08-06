package main.round2;

import data.UserDataLevel1;
import data.UserDataLevel2;
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

public class Level2Controller extends Round2Controller implements Initializable, Runnable {

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
    private Label lb_answer1;
    @FXML
    private Label lb_answer2;
    @FXML
    private Label lb_answer3;
    @FXML
    private Label lb_answer4;
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
    private Label lb_pointTotal;
    @FXML
    private Button bt_home;

    private Round2Controller round2Controller;

    private Thread thread;

    public void init(LinkedList<UserDataLevel2> level2Users, Round2Controller controller) {
        this.level2Users = level2Users;
        round2Controller = controller;
        thread = new Thread(this);
        thread.start();
        initComboBox();
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

    private void setStyleId() {
        lb_instructionHeader.setId(StyleId.INSTRUCTION_HEADER);
        lb_instructionBody_zh.setId(StyleId.INSTRUCTION_BODY_ZH);
        lb_instructionBody_en.setId(StyleId.INSTRUCTION_BODY_EN);
        lb_instructionTime_zh.setId(StyleId.INSTRUCTION_TIME_ZH);
        lb_instructionTime_en.setId(StyleId.INSTRUCTION_TIME_EN);

        lb_exampleHeader.setId(StyleId.EXAMPLE_HEADER);
        lb_exampleBody.setId(StyleId.EXAMPLE_BODY);
        lb_exampleWarning.setId(StyleId.EXAMPLE_WARNING);

        lb_timer.setId(StyleId.ROUND_TIMER);
        lb_question1.setId(StyleId.ROUND_QUESTIONS);
        lb_question2.setId(StyleId.ROUND_QUESTIONS);
        lb_question3.setId(StyleId.ROUND_QUESTIONS);
        lb_question4.setId(StyleId.ROUND_QUESTIONS);
    }

    private void setData() {
        lb_instructionBody_zh.setText(Main.R2L2_DATA.INSTRUCTION_ZH);
        lb_instructionBody_en.setText(Main.R2L2_DATA.INSTRUCTION_EN);
        String time_zh = "限时" + Main.R2L2_DATA.TIME_LIMIT + "分钟";
        String time_en = "Time Limit: " + Main.R2L2_DATA.TIME_LIMIT + ((Main.R2L2_DATA.TIME_LIMIT > 1) ? " minutes" : " minute");
        lb_instructionTime_zh.setText(time_zh);
        lb_instructionTime_en.setText(time_en);

        String cnExample = "";
        for (String data : Main.R2L2_DATA.EXAMPLES) {
            cnExample += data + "\n";
        }
        lb_exampleBody.setText(cnExample);

        lb_question1.setText(Main.R2L2_DATA.QUESTIONS.get(0));
        lb_question2.setText(Main.R2L2_DATA.QUESTIONS.get(1));
        lb_question3.setText(Main.R2L2_DATA.QUESTIONS.get(2));
        lb_question4.setText(Main.R2L2_DATA.QUESTIONS.get(3));

        lb_question1_st.setText(lb_question1.getText());
        lb_question2_st.setText(lb_question2.getText());
        lb_question3_st.setText(lb_question3.getText());
        lb_question4_st.setText(lb_question4.getText());

        lb_pointTotal.setText("0"); // the initial total point is 0
    }

    @FXML
    private void handleMouseClick(MouseEvent e) {
        if (e.getSource() == bt_startTimer) {
            bt_startTimer.setVisible(false);
            super.writeToClient(Constants.DIS_R2L2_QST);
            lb_timer.setVisible(true);
            lb_question1.setVisible(true);
            lb_question2.setVisible(true);
            lb_question3.setVisible(true);
            lb_question4.setVisible(true);
        } else if (e.getSource() == gp_score && cb_users.getValue() != null) {
            for (UserDataLevel2 ud : level2Users) {
                if (ud.getUSER_NAME() != null && ud.getUSER_NAME().compareTo((String) cb_users.getValue()) == 0) {
                    lb_answer1.setText(ud.getRound2Answers().get(0));
                    lb_answer2.setText(ud.getRound2Answers().get(1));
                    lb_answer3.setText(ud.getRound2Answers().get(2));
                    lb_answer4.setText(ud.getRound2Answers().get(3));

                    tf_point1.setText((ud.getPoints() == null) ? "0" : Integer.toString(ud.getPoints().get(0)));
                    tf_point2.setText((ud.getPoints() == null) ? "0" : Integer.toString(ud.getPoints().get(1)));
                    tf_point3.setText((ud.getPoints() == null) ? "0" : Integer.toString(ud.getPoints().get(2)));
                    tf_point4.setText((ud.getPoints() == null) ? "0" : Integer.toString(ud.getPoints().get(3)));
                    lb_pointTotal.setText((ud.getPoints() == null) ? "0" : Integer.toString(ud.getPoints().getLast()));
                }
            }
        }else if(e.getSource() == bt_home){
            hide();
            round2Controller.show();
        }
    }

    public void show(){
        ap_root.setVisible(true);
    }

    private void hide(){
        ap_root.setVisible(false);
    }

    @FXML
    private void handleKeyboard(KeyEvent e) {
        if (cb_users != null)
            for (UserDataLevel2 ud : level2Users) {
                if (ud.getUSER_NAME() != null && ud.getUSER_NAME().compareTo((String) cb_users.getValue()) == 0) {
                    ud.setPointRound2((tf_point1.getText().compareTo("") == 0) ? 0 : Integer.parseInt(tf_point1.getText()), 0);
                    ud.setPointRound2((tf_point2.getText().compareTo("") == 0) ? 0 : Integer.parseInt(tf_point2.getText()), 1);
                    ud.setPointRound2((tf_point3.getText().compareTo("") == 0) ? 0 : Integer.parseInt(tf_point3.getText()), 2);
                    ud.setPointRound2((tf_point4.getText().compareTo("") == 0) ? 0 : Integer.parseInt(tf_point4.getText()), 3);
                    lb_pointTotal.setText(Integer.toString(ud.getPoints().getLast()));
                    writeToClient(Constants.S2C_R2L2_SCR);
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
            case Constants.C2S_R2L2_ANS:
                lb_answer1.setText(level2Users.getFirst().getRound2Answers().get(0));
                lb_answer2.setText(level2Users.getFirst().getRound2Answers().get(1));
                lb_answer3.setText(level2Users.getFirst().getRound2Answers().get(2));
                lb_answer4.setText(level2Users.getFirst().getRound2Answers().get(3));
                System.out.println(level2Users.getFirst().getRound2Answers());
                break;
            default:
                break;
        }
    }

    @Override
    public void run() {
        while (true) {
            if (tp_mainTab.getTabs().get(1).isSelected()) {
                super.writeToClient(Constants.DIS_R2L2_EXP);
                break;
            }
        }
        thread.interrupt();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleId();
        setData();
        tp_mainTab.setVisible(true);
        lb_timer.setVisible(false);
        lb_question1.setVisible(false);
        lb_question2.setVisible(false);
        lb_question3.setVisible(false);
        lb_question4.setVisible(false);
        bt_startTimer.setVisible(true);
    }

}


