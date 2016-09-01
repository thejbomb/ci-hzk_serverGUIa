package main.round5;

import data.UserDataLevel1;
import data.UserDataLevel3;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import main.Main;
import main.round2.Round2Controller;
import tool.Constants;

import java.net.URL;
import java.util.*;

public class Level3Controller extends Round5Controller implements Initializable {
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
    private ComboBox cb_users;
    @FXML
    private Button bt_home;
    @FXML
    private Label lb_total;
    @FXML
    private Label lb_pointTotal;
    @FXML
    private FlowPane fp_text;
    @FXML
    private FlowPane fp_answers;
    @FXML
    private GridPane gp_instruction;
    @FXML
    private GridPane gp_example;
    @FXML
    private GridPane gp_score;
    @FXML
    private Button bt_instructionHome;
    @FXML
    private Button bt_exampleHome;

    private Round5Controller round5Controller;

    public void init(LinkedList<UserDataLevel3> level3Users, Round5Controller controller) {
        this.level3Users = level3Users;
        round5Controller = controller;
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

    public void show() {
        ap_root.setVisible(true);
    }

    private void hide() {
        ap_root.setVisible(false);
    }

    private void updateAnswerLabels(UserDataLevel3 user) {
        fp_answers.getChildren().clear();
        LinkedList<Character> text = Main.R5L3_DATA.parseText(Main.R5L3_DATA.QUESTIONS);
        for (int i = 0; i < user.getRound5Answers().size(); i++) {
            String labelString = text.get(user.getRound5AnswerIndices().get(i)).toString();
            Label label1 = new Label(labelString + ".");
            label1.getStyleClass().set(0, "label-scoreWrongText");
            Label label2 = new Label(user.getRound5Answers().get(i));
            label2.getStyleClass().set(0, "label-scoreCorrectedText");
            Image image = new Image("file:src/asset/green_v.png");
            Image image2 = new Image("file:src/asset/red_x.png");
            ImageView imageView = new ImageView(image);
            ImageView imageView2 = new ImageView(image2);
            imageView.setFitHeight(label2.getFont().getSize());
            imageView.setFitWidth(label2.getFont().getSize());
            imageView.setPreserveRatio(true);
            imageView.setVisible(false);
            imageView2.setFitHeight(label2.getFont().getSize());
            imageView2.setFitWidth(label2.getFont().getSize());
            imageView2.setPreserveRatio(true);
            imageView2.setVisible(false);
            label2.setContentDisplay(ContentDisplay.RIGHT);
            label2.setGraphic(imageView);
            if (user.getRound5PointState()[i] == 1) {
                imageView.setVisible(true);
                lb_pointTotal.setText(Integer.toString(user.getRound5Points()));
            } else if (user.getRound5PointState()[i] == -1) {
                label2.setGraphic(imageView2);
                imageView2.setVisible(true);
                lb_pointTotal.setText(Integer.toString(user.getRound5Points()));
            }

            int finalI = i;
            label2.setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    if (!imageView.isVisible())
                        user.setRound5Points(1);
                    user.setRound5PointState(finalI, 1); // 1 = correct answer
                    lb_pointTotal.setText(Integer.toString(user.getRound5Points()));
                    System.out.println("Current total points: " + user.getRound5Points());
                    label2.setGraphic(imageView);
                    imageView.setVisible(true);
                    String userName = (String) cb_users.getValue();
                    for (UserDataLevel3 ud : level3Users)
                        if (ud.getUSER_NAME().compareTo(userName) == 0)
                            writeToClient(Constants.S2C_R5L1_SCR, packageData(ud.getRound5Points()), ud.getThreadId());
                } else if (e.getButton() == MouseButton.SECONDARY) {
                    if (imageView.isVisible())
                        user.setRound5Points(-1);
                    user.setRound5PointState(finalI, -1); // - 1 = incorrect answer
                    imageView.setVisible(false);
                    label2.setGraphic(imageView2);
                    imageView2.setVisible(true);
                    lb_pointTotal.setText(Integer.toString(user.getRound5Points()));
                    System.out.println("Current total points: " + user.getRound5Points());
                    String userName = (String) cb_users.getValue();
                    for (UserDataLevel3 ud : level3Users)
                        if (ud.getUSER_NAME().compareTo(userName) == 0)
                            writeToClient(Constants.S2C_R5L1_SCR, packageData(ud.getRound5Points()), ud.getThreadId());

                }
            });
            fp_answers.getChildren().addAll(label1, label2);
        }
    }

    @FXML
    private void handleMouseClick(MouseEvent e) {
        if (e.getSource() == bt_startTimer) {
            bt_startTimer.setVisible(false);
            bp_start.setVisible(false);
            fp_text.setVisible(true);
            super.writeToClient(Constants.DIS_R5L3_QST);
            lb_timer.setVisible(true);
        } else if (e.getSource() == gp_score && cb_users.getValue() != null) {
            for (UserDataLevel3 ud : level3Users) {
                if (ud.getUSER_NAME() != null && ud.getUSER_NAME().compareTo((String) cb_users.getValue()) == 0) {
                    updateAnswerLabels(ud);
                }
            }
        } else if (e.getSource() == bt_home) {
            hide();
            round5Controller.show();
        }else if (e.getSource() == bt_instructionHome) {
            hideInstruction();
            hide();
            round5Controller.show();
        } else if (e.getSource() == bt_exampleHome) {
            hideExample();
            hide();
            round5Controller.show();
        }
    }


    private void setData() {
        lb_instructionBody_zh.setText(Main.R5L3_DATA.INSTRUCTION_ZH);
        lb_instructionBody_en.setText(Main.R5L3_DATA.INSTRUCTION_EN);
        String time_zh = "限时" + Main.R5L3_DATA.TIME_LIMIT + "分钟";
        String time_en = "Time Limit: " + Main.R5L3_DATA.TIME_LIMIT + ((Main.R5L3_DATA.TIME_LIMIT > 1) ? " minutes" : " minute");
        lb_instructionTime_zh.setText(time_zh);
        lb_instructionTime_en.setText(time_en);

        for (Character c : Main.R5L3_DATA.parseText(Main.R5L3_DATA.QUESTIONS)) {
            Label label = new Label(c.toString());
            label.getStyleClass().set(0, "label-questionsText");
            fp_text.getChildren().add(label);
        }
        lb_pointTotal.setText("0"); // set the initial total points to 0

    }

    @Override
    public void handleClientData(int command, LinkedList<String> data) {
        switch (command) {
            case Constants.C2S_R5L3_ANS:
                for (UserDataLevel3 ud : level3Users) {
                    if (ud.getThreadId() == activeThreadId)
                        ud.setRound5Answers(data);
                }
                updateAnswerLabels(level3Users.getFirst());
                break;
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setData();
        fp_text.setVisible(false);
        tp_mainTab.setVisible(true);
        lb_timer.setVisible(false);
        bt_startTimer.setVisible(true);
        fp_answers.setHgap(21);
        fp_answers.setVgap(20);
    }
}
