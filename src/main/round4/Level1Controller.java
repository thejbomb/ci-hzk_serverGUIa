package main.round4;

import data.UserDataLevel1;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import main.Main;
import tool.Constants;
import tool.Timer;
import tool.TimerInterface;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class Level1Controller extends Round4Controller implements Initializable, Runnable, TimerInterface {

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
    private AnchorPane ap_question;
    @FXML
    private FlowPane fp_choices;
    @FXML
    private BorderPane bp_start;
    @FXML
    private Button bt_startTimer;
    @FXML
    private Label lb_timer;
    @FXML
    private Label lb_instruction;
    @FXML
    private Button bt_back;
    @FXML
    private Button bt_next;
    @FXML
    private ComboBox<String> cb_users;
    @FXML
    private GridPane gp_score;
    @FXML
    private Label lb_total;
    @FXML
    private Label lb_pointTotal;
    @FXML
    private Button bt_home;

    private Round4Controller round4Controller;

    private Thread thread;

    private int currentQuestion = 0;

    private Timer timer;

    public void init(LinkedList<UserDataLevel1> users, Round4Controller controller) {

        this.level1Users = users;

        round4Controller = controller;

        writeToClient(Constants.S2C_R4L1_SEED, packageData(Main.R4L1_DATA.getSeed()));

        thread = new Thread(this);
        thread.start();
        // initComboBox();
    }


    public void setActiveThread(long threadId) {
        activeThreadId = threadId;
    }

    private void setData() {
        lb_instructionBody_zh.setText(Main.R4L1_DATA.INSTRUCTION_ZH);
        lb_instructionBody_en.setText(Main.R4L1_DATA.INSTRUCTION_EN);
        String time_zh = "限时" + Main.R4L1_DATA.TIME_LIMIT + "分钟";
        String time_en = "Time Limit: " + Main.R4L1_DATA.TIME_LIMIT + ((Main.R4L1_DATA.TIME_LIMIT > 1) ? " minutes" : " minute");
        lb_instructionTime_zh.setText(time_zh);
        lb_instructionTime_en.setText(time_en);

        lb_exampleBody.setText(Main.R4L1_DATA.EXAMPLES.get(0));


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

    private void displayInstruction(int questionNumber) throws Exception {
        if (questionNumber < 0 || questionNumber >= Main.R4L1_DATA.getQuestions().size())
            throw new Exception("R4L1: No such question exist");
        String instruction = "";
        if (Main.R4L1_DATA.getQuestionType(questionNumber) == 0) {
            instruction += Main.R4L1_DATA.QUESTION_INSTRUCTION1_ZH + "\n" + Main.R4L1_DATA.QUESTION_INSTRUCTION1_EN;
            lb_instruction.setText(instruction);
        } else if (Main.R4L1_DATA.getQuestionType(questionNumber) == 1) {
            instruction += Main.R4L1_DATA.QUESTION_INSTRUCTION2_ZH + "\n" + Main.R4L1_DATA.QUESTION_INSTRUCTION2_EN;
            lb_instruction.setText(instruction);
        }
    }

    private void displayQuestion(int questionNumber) throws Exception {
        if (questionNumber < 0 || questionNumber >= Main.R4L1_DATA.getQuestions().size())
            throw new Exception("R4L1: No such question exist");
        if (Main.R4L1_DATA.getQuestionType(questionNumber) == 0 || Main.R4L1_DATA.getQuestionType(questionNumber) == 1) {
            String question = (questionNumber + 1) + ". " + Main.R4L1_DATA.getQuestions(questionNumber);
            Label label = new Label(question);
            label.getStyleClass().set(0, "label-questionsQuestion");
            AnchorPane.setBottomAnchor(label, 0.0);
            AnchorPane.setTopAnchor(label, 0.0);
            AnchorPane.setLeftAnchor(label, 0.0);
            AnchorPane.setRightAnchor(label, 0.0);
            label.setAlignment(Pos.CENTER);
            ap_question.getChildren().clear();
            ap_question.getChildren().add(label);
        } else if (Main.R4L1_DATA.getQuestionType(questionNumber) == 2) {
            Image image = new Image("file:src/data/round4/" + Main.R4L1_DATA.getQuestions(questionNumber));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(400.0);
            Label l = new Label();
            l.setGraphic(imageView);
            l.setAlignment(Pos.CENTER);
            AnchorPane.setBottomAnchor(l, 0.0);
            AnchorPane.setTopAnchor(l, 0.0);
            AnchorPane.setLeftAnchor(l, 0.0);
            AnchorPane.setRightAnchor(l, 0.0);
            ap_question.getChildren().clear();
            ap_question.getChildren().add(l);
        }
    }

    private void displayChoices(int questionNumber) throws Exception {
        if (questionNumber < 0 || questionNumber >= Main.R4L1_DATA.getQuestions().size())
            throw new Exception("No such question exist");
        Button[] choices;
        if (Main.R4L1_DATA.getChoices(questionNumber).size() > 0)
            choices = new Button[Main.R4L1_DATA.getChoices(questionNumber).size()];
        else
            choices = new Button[2];
        for (int i = 0; i < choices.length; i++) {

            if (Main.R4L1_DATA.getChoices(questionNumber).size() > 0) {
                String label = Main.R4L1_DATA.getChoices(questionNumber).get(i);
                choices[i] = new Button((char) (0x41 + i) + "." + label);

            } else {
                if (i == 0)
                    choices[i] = new Button("\uD83D\uDC4D");

                else if (i == 1)
                    choices[i] = new Button("\uD83D\uDC4E");
            }
            choices[i].getStyleClass().set(0, "button-questionsChoice");
            int finalI = i;
            if (Main.R4L1_DATA.getChoices(questionNumber).size() > 0) {
                choices[i].setOnMouseClicked(e -> {
                    if (Main.R4L1_DATA.isCorrect(choices[finalI].getText().substring(2), questionNumber)) {
                        getLevel1User(activeThreadId).setRound4Points(1);
                        writeToClient(Constants.S2C_R4LX_CRCT);
                        writeToClient(Constants.S2C_R4L1_SCR, packageData(getLevel1User(activeThreadId).getRound4Points()), activeThreadId);
                        System.out.println("R4L1: Correct!");
                    }
                    else {
                        getLevel1User(activeThreadId).setRound4Points(-1);
                        writeToClient(Constants.S2C_R4LX_WRNG);
                        writeToClient(Constants.S2C_R4L1_SCR, packageData(getLevel1User(activeThreadId).getRound4Points()), activeThreadId);
                        System.out.println("R4L1: Incorrect!");
                    }
                });
            } else {
                choices[i].setOnMouseClicked(e -> {
                    if (finalI == 0) {
                        choices[finalI].getStyleClass().set(0, "button-questionsUserChoice");
                        choices[1].getStyleClass().set(0,"button-questionsChoice");
                        getLevel1User(activeThreadId).setRound4Points(1);
                        writeToClient(Constants.S2C_R4LX_CRCT);
                        writeToClient(Constants.S2C_R4L1_SCR, packageData(getLevel1User(activeThreadId).getRound4Points()), activeThreadId);
                        System.out.println("R4L1: Correct!");
                    } else if (finalI == 1) {
                        choices[finalI].getStyleClass().set(0, "button-questionsUserChoice");
                        choices[0].getStyleClass().set(0,"button-questionsChoice");
                        getLevel1User(activeThreadId).setRound4Points(-1);
                        writeToClient(Constants.S2C_R4LX_WRNG);
                        writeToClient(Constants.S2C_R4L1_SCR, packageData(getLevel1User(activeThreadId).getRound4Points()), activeThreadId);
                        System.out.println("R4L1: Incorrect!");
                    }
                });
            }
        }
        fp_choices.getChildren().clear();
        fp_choices.getChildren().addAll(choices);
    }

    private void displayUserAnswer(int answer) {
        for (int i = 0; i < fp_choices.getChildren().size(); i++) {
            fp_choices.getChildren().get(i).getStyleClass().set(0, "button-questionsChoice");
        }
        fp_choices.getChildren().get(answer).getStyleClass().set(0, "button-questionsUserChoice");
    }

    private void checkUserAnswer(int answer) {
        System.out.println("R4L1 User Answer: " + answer);
        displayUserAnswer(answer);
        if (Main.R4L1_DATA.isCorrect(((Button) fp_choices.getChildren().get(answer)).getText().substring(2), currentQuestion)) {
            writeToClient(Constants.S2C_R4LX_CRCT);
            getLevel1User(activeThreadId).setRound4Points(1);
            System.out.println("R4L1: Correct");

        } else {
            writeToClient(Constants.S2C_R4LX_WRNG);
            getLevel1User(activeThreadId).setRound4Points(-1);
            System.out.println("R4L1: Wrong");
        }
        writeToClient(Constants.S2C_R4L1_SCR, packageData(getLevel1User(activeThreadId).getRound4Points()), activeThreadId);
    }


    @FXML
    private void handleMouseClick(MouseEvent e) {
        if (e.getSource() == bt_startTimer) {
            bp_start.setVisible(false);
            bt_next.setVisible(true);
            lb_timer.setVisible(true);
            lb_instruction.setVisible(true);
            try {
                displayInstruction(currentQuestion);
                displayQuestion(currentQuestion);
                displayChoices(currentQuestion);
                writeToClient(Constants.DIS_R4L1_QST, packageData(currentQuestion));
                System.out.println("R4L1: Current question: " + currentQuestion + 1);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == bt_next) {
            if (currentQuestion == Main.R4L1_DATA.getQuestions().size() - 2)
                bt_next.setVisible(false);
            else if (currentQuestion == 0)
                bt_back.setVisible(true);
            try {
                displayInstruction(++currentQuestion);
                displayQuestion(currentQuestion);
                displayChoices(currentQuestion);
                writeToClient(Constants.DIS_R4L1_QST, packageData(currentQuestion));
                System.out.println("R4L1: Current question: " + currentQuestion + 1);
            } catch (Exception ex) {
                currentQuestion = Main.R4L1_DATA.getQuestions().size() - 2;
                ex.printStackTrace();
            }

        } else if (e.getSource() == bt_back) {
            if (currentQuestion == 1)
                bt_back.setVisible(false);
            else if (currentQuestion == Main.R4L1_DATA.getQuestions().size() - 1)
                bt_next.setVisible(true);
            try {
                displayInstruction(--currentQuestion);
                displayQuestion(currentQuestion);
                displayChoices(currentQuestion);
                writeToClient(Constants.DIS_R4L1_QST, packageData(currentQuestion));
                System.out.println("R4L1: Current question: " + currentQuestion + 1);
            } catch (Exception ex) {
                currentQuestion = 0;
                ex.printStackTrace();
            }

        } else if (e.getSource() == bt_home){
            hide();
            round4Controller.show();
        }
    }


    public void show() {
        ap_root.setVisible(true);
    }

    private void hide() {
        ap_root.setVisible(false);
    }

    @Override
    public void handleClientData(int command, LinkedList<String> data) {
        System.out.println("R4L1 FROM: command = " + Integer.toHexString(command) + " | data = " + data);
        switch (command) {
            case Constants.C2S_R4L1_ANS:
                checkUserAnswer(Integer.parseInt(data.getFirst()));
                break;
            case Constants.C2S_R4L1_BUZZ:
                writeToAllClientsExcept(Constants.S2C_R4L1_BUZZ, activeThreadId);
                int time = 10; // 5 seconds
                timer = new Timer(lb_timer,time,this,1);
                break;
            case Constants.C2S_R4LX_TMUP:
                for (UserDataLevel1 ud : level1Users)
                    if (ud.getThreadId() == activeThreadId)
                        ud.setRound4Points(-1);
                writeToClient(Constants.S2C_R4L1_SCR, packageData(getLevel1User(activeThreadId).getRound4Points()), activeThreadId);
                break;
        }
    }

    @Override
    public void run() {
        while (true) {
            if (tp_mainTab.getTabs().get(1).isSelected()) {
                super.writeToClient(Constants.DIS_R4L1_EXP);
                break;
            }
        }
        thread.interrupt();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.R4L1_DATA.init();

        setData();

        tp_mainTab.setVisible(true);
        lb_timer.setText("10");
        lb_timer.setVisible(false);
        lb_instruction.setVisible(false);
        bt_startTimer.setVisible(true);

        bt_next.setVisible(false);
        bt_back.setVisible(false);

        fp_choices.setHgap(50);
        fp_choices.setVgap(10);
    }

    @Override
    public void takeNotice() {

    }
}