package main.round4;

import data.UserDataLevel1;
import data.UserDataLevel3;
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

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class Level3Controller extends Round4Controller implements Initializable, Runnable {

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

    @SuppressWarnings(value = "unchecked")
    public void init(LinkedList<UserDataLevel3> users, Round4Controller controller) {

        level3Users = users;

        round4Controller = controller;

        writeToClient(Constants.S2C_R4L3_SEED, packageData(Main.R4L3_DATA.getSeed()));

        thread = new Thread(this);
        thread.start();
        // initComboBox();
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

    private LinkedList<String> packageData(Object data) {
        LinkedList<String> result = new LinkedList<>();

        if (data.getClass() == Integer.class) {
            result.add(Integer.toString((int) data));
        } else if (data.getClass() == Long.class)
            result.add(Long.toString((long) data));

        return result;
    }

    private void displayInstruction(int questionNumber) throws Exception {
        if (questionNumber < 0 || questionNumber >= Main.R4L3_DATA.getQuestions().size())
            throw new Exception("R4L1: No such question exist");
        String instruction = "";
        if (Main.R4L3_DATA.getQuestionType(questionNumber) == 0) {
            instruction += Main.R4L3_DATA.QUESTION_INSTRUCTION1_ZH + "\n" + Main.R4L3_DATA.QUESTION_INSTRUCTION1_EN;
            lb_instruction.setText(instruction);
        } else if (Main.R4L3_DATA.getQuestionType(questionNumber) == 1) {
            instruction += Main.R4L3_DATA.QUESTION_INSTRUCTION2_ZH + "\n" + Main.R4L3_DATA.QUESTION_INSTRUCTION2_EN;
            lb_instruction.setText(instruction);
        }
    }

    private void displayQuestion(int questionNumber) throws Exception {
        if (questionNumber < 0 || questionNumber >= Main.R4L3_DATA.getQuestions().size())
            throw new Exception("R4L1: No such question exist");
        if (Main.R4L3_DATA.getQuestionType(questionNumber) == 0 || Main.R4L3_DATA.getQuestionType(questionNumber) == 1) {
            String question = (questionNumber + 1) + ". " + Main.R4L3_DATA.getQuestions(questionNumber);
            Label label = new Label(question);
            label.getStyleClass().set(0, "label-questionsQuestion");
            AnchorPane.setBottomAnchor(label, 0.0);
            AnchorPane.setTopAnchor(label, 0.0);
            AnchorPane.setLeftAnchor(label, 0.0);
            AnchorPane.setRightAnchor(label, 0.0);
            label.setAlignment(Pos.CENTER);
            ap_question.getChildren().clear();
            ap_question.getChildren().add(label);
        } else if (Main.R4L3_DATA.getQuestionType(questionNumber) == 2) {
            Image image = new Image("file:src/data/round4/" + Main.R4L3_DATA.getQuestions(questionNumber));
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
        Button[] choices = new Button[Main.R4L3_DATA.getChoices(questionNumber).size()];
        for (int i = 0; i < choices.length; i++) {
            choices[i] = new Button((char) (0x41 + i) + "." + Main.R4L3_DATA.getChoices(questionNumber).get(i));
            choices[i].getStyleClass().set(0, "button-questionsChoice");
            int finalI = i;
            choices[i].setOnMouseClicked(e -> {
                if (Main.R4L3_DATA.isCorrect(choices[finalI].getText().substring(2), questionNumber))
                    System.out.println("R4L1: Correct!");
                else
                    System.out.println("R4L1: Incorrect!");
            });
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
                writeToClient(Constants.DIS_R4L3_QST, packageData(currentQuestion));
                System.out.println("R4L1: Current question: " + currentQuestion + 1);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == bt_next) {
            if (currentQuestion == Main.R4L3_DATA.getQuestions().size() - 2)
                bt_next.setVisible(false);
            else if (currentQuestion == 0)
                bt_back.setVisible(true);
            try {
                displayInstruction(++currentQuestion);
                displayQuestion(currentQuestion);
                displayChoices(currentQuestion);
                writeToClient(Constants.DIS_R4L3_QST, packageData(currentQuestion));
                System.out.println("R4L1: Current question: " + currentQuestion + 1);
            } catch (Exception ex) {
                currentQuestion = Main.R4L3_DATA.getQuestions().size() - 2;
                ex.printStackTrace();
            }

        } else if (e.getSource() == bt_back) {
            if (currentQuestion == 1)
                bt_back.setVisible(false);
            else if (currentQuestion == Main.R4L3_DATA.getQuestions().size() - 1)
                bt_next.setVisible(true);
            try {
                displayInstruction(--currentQuestion);
                displayQuestion(currentQuestion);
                displayChoices(currentQuestion);
                writeToClient(Constants.DIS_R4L3_QST, packageData(currentQuestion));
                System.out.println("R4L1: Current question: " + currentQuestion + 1);
            } catch (Exception ex) {
                currentQuestion = 0;
                ex.printStackTrace();
            }

        }
    }


    public void show() {
        ap_root.setVisible(true);
    }

    private void hide() {
        ap_root.setVisible(false);
    }

    @Override
    public void writeToClient(int command) {
        System.out.println("R4L1 TO: command = " + Integer.toHexString(command) + " | data = ");
        super.writeToClient(command);
    }

    @Override
    public void writeToClient(int command, LinkedList<String> data) {
        System.out.println("R4L1 TO: command = " + Integer.toHexString(command) + " | data = " + data);
        super.writeToClient(command, data);
    }

    @Override
    public void handleClientData(int command, LinkedList<String> data) {
        System.out.println("R4L1 FROM: command = " + Integer.toHexString(command) + " | data = " + data);
        switch (command) {
            case Constants.C2S_R4L3_SEED:
                writeToClient(Constants.S2C_R4L3_SEED, packageData(Main.R4L3_DATA.getSeed()));
                break;
            case Constants.C2S_R4L3_ANS:
                displayUserAnswer(Integer.parseInt(data.getFirst()));
                break;
            case Constants.C2S_R4L3_BUZZ:
                writeToClient(Constants.S2C_R4L3_BUZZ, activeThreadId);
                break;
        }
    }

    @Override
    public void run() {
        while (true) {
            if (tp_mainTab.getTabs().get(1).isSelected()) {
                super.writeToClient(Constants.DIS_R4L3_EXP);
                break;
            }
        }
        thread.interrupt();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.R4L3_DATA.init();

        tp_mainTab.setVisible(true);
        lb_timer.setVisible(false);
        lb_instruction.setVisible(false);
        bt_startTimer.setVisible(true);

        bt_next.setVisible(false);
        bt_back.setVisible(false);

        fp_choices.setHgap(50);
        fp_choices.setVgap(10);
    }

}

