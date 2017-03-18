package main;

import data.UserData;
import data.UserDataLevel1;
import data.UserDataLevel2;
import data.UserDataLevel3;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

/**
 * Created by quang on 08/26/16.
 */
public class ScoreboardController implements Initializable {
    @FXML
    private AnchorPane ap_root;
    @FXML
    private GridPane gp_table1;
    @FXML
    private GridPane gp_table2;
    @FXML
    private GridPane gp_table3;
    @FXML
    private Label lb_L1Name;
    @FXML
    private Label lb_L1Round;
    @FXML
    private Label lb_R1L1;
    @FXML
    private Label lb_R2L1;
    @FXML
    private Label lb_R3L1;
    @FXML
    private Label lb_R4L1;
    @FXML
    private Label lb_R5L1;
    @FXML
    private Label lb_L2Name;
    @FXML
    private Label lb_L2Round;
    @FXML
    private Label lb_R1L2;
    @FXML
    private Label lb_R2L2;
    @FXML
    private Label lb_R3L2;
    @FXML
    private Label lb_R4L2;
    @FXML
    private Label lb_R5L2;
    @FXML
    private Label lb_L3Name;
    @FXML
    private Label lb_L3Round;
    @FXML
    private Label lb_R1L3;
    @FXML
    private Label lb_R2L3;
    @FXML
    private Label lb_R3L3;
    @FXML
    private Label lb_R4L3;
    @FXML
    private Label lb_R5L3;
    @FXML
    private BorderPane bp_start;
    @FXML
    private TableView tb_level1Name;
    @FXML
    private TableView tb_level1Score;
    @FXML
    private TableView tb_level2Name;
    @FXML
    private TableView tb_level2Score;
    @FXML
    private TableView tb_level3Name;
    @FXML
    private TableView tb_level3Score;

    private LinkedList<UserDataLevel1> level1Users;
    private LinkedList<UserDataLevel2> level2Users;
    private LinkedList<UserDataLevel3> level3Users;

    private int currentRound = 0;

    private MainController controller;

    public void setCurrentRound(int current) {
        currentRound = current;
        setupStartButton();
    }

    public void init(MainController controller) {
        this.controller = controller;

        Pane header = (Pane) tb_level1Name.lookup("TableHeaderRow");
        if (header.isVisible()) {
            header.setMaxHeight(0);
            header.setMinHeight(0);
            header.setPrefHeight(0);
            header.setVisible(false);
        }
        header = (Pane) tb_level1Score.lookup("TableHeaderRow");
        if (header.isVisible()) {
            header.setMaxHeight(0);
            header.setMinHeight(0);
            header.setPrefHeight(0);
            header.setVisible(false);
        }

        header = (Pane) tb_level2Name.lookup("TableHeaderRow");
        if (header.isVisible()) {
            header.setMaxHeight(0);
            header.setMinHeight(0);
            header.setPrefHeight(0);
            header.setVisible(false);
        }
        header = (Pane) tb_level2Score.lookup("TableHeaderRow");
        if (header.isVisible()) {
            header.setMaxHeight(0);
            header.setMinHeight(0);
            header.setPrefHeight(0);
            header.setVisible(false);
        }

        header = (Pane) tb_level3Name.lookup("TableHeaderRow");
        if (header.isVisible()) {
            header.setMaxHeight(0);
            header.setMinHeight(0);
            header.setPrefHeight(0);
            header.setVisible(false);
        }
        header = (Pane) tb_level3Score.lookup("TableHeaderRow");
        if (header.isVisible()) {
            header.setMaxHeight(0);
            header.setMinHeight(0);
            header.setPrefHeight(0);
            header.setVisible(false);
        }
    }

    private void addRectangle(Label label) {
        Bounds rectBounds = label.localToScene(label.getBoundsInLocal());
        double rectX = rectBounds.getMinX();
        double rectY = rectBounds.getMinY();
        double rectWidth = rectBounds.getWidth();
        double rectHeight = rectBounds.getHeight();
        Rectangle rect = new Rectangle(rectX, rectY, rectWidth, rectHeight);
        rect.setFill(new Color(1, 1, 1, 0));
        rect.toBack();
        rect.setStroke(Color.BLACK);
        ap_root.getChildren().add(rect);
    }


    private void setupStartButton() {
        Button button = new Button("Begin Round " + currentRound);
        button.setOnMouseClicked(e -> {
            hide();
            controller.startRound(currentRound);
        });
        bp_start.setCenter(button);
    }

    public void initUserData(LinkedList<UserDataLevel1> level1, LinkedList<UserDataLevel2> level2, LinkedList<UserDataLevel3> level3) {
        level1Users = level1;
        level2Users = level2;
        level3Users = level3;
    }

    public void display() {
        displayUserName();
    }

    private void displayUserName() {
        TableColumn nameColumn = (TableColumn) tb_level1Name.getColumns().get(0);
        TableColumn scoreColumn1 = (TableColumn) tb_level1Score.getColumns().get(0);
        TableColumn scoreColumn2 = (TableColumn) tb_level1Score.getColumns().get(1);
        TableColumn scoreColumn3 = (TableColumn) tb_level1Score.getColumns().get(2);
        TableColumn scoreColumn4 = (TableColumn) tb_level1Score.getColumns().get(3);
        TableColumn scoreColumn5 = (TableColumn) tb_level1Score.getColumns().get(4);
        nameColumn.setCellValueFactory(new PropertyValueFactory<UserDataLevel1, String>("USER_NAME"));
        scoreColumn1.setCellValueFactory(new PropertyValueFactory<UserDataLevel1, Integer>("round1Points"));
        scoreColumn2.setCellValueFactory(new PropertyValueFactory<UserDataLevel1, Integer>("round2TotalPoints"));
        scoreColumn3.setCellValueFactory(new PropertyValueFactory<UserDataLevel1, Integer>("round3TotalPoints"));
        scoreColumn4.setCellValueFactory(new PropertyValueFactory<UserDataLevel1, Integer>("round4Points"));
        scoreColumn5.setCellValueFactory(new PropertyValueFactory<UserDataLevel1, Integer>("round5Points"));
        ObservableList<UserDataLevel1> userList1 = FXCollections.observableArrayList(level1Users);
        tb_level1Name.setItems(userList1);
        tb_level1Score.setItems(userList1);

        nameColumn = (TableColumn) tb_level2Name.getColumns().get(0);
        scoreColumn1 = (TableColumn) tb_level2Score.getColumns().get(0);
        scoreColumn2 = (TableColumn) tb_level2Score.getColumns().get(1);
        scoreColumn3 = (TableColumn) tb_level2Score.getColumns().get(2);
        scoreColumn4 = (TableColumn) tb_level2Score.getColumns().get(3);
        scoreColumn5 = (TableColumn) tb_level2Score.getColumns().get(4);
        nameColumn.setCellValueFactory(new PropertyValueFactory<UserDataLevel2, String>("USER_NAME"));
        scoreColumn1.setCellValueFactory(new PropertyValueFactory<UserDataLevel2, Integer>("round1Points"));
        scoreColumn2.setCellValueFactory(new PropertyValueFactory<UserDataLevel2, Integer>("round2TotalPoints"));
        scoreColumn3.setCellValueFactory(new PropertyValueFactory<UserDataLevel2, Integer>("round3TotalPoints"));
        scoreColumn4.setCellValueFactory(new PropertyValueFactory<UserDataLevel2, Integer>("round4Points"));
        scoreColumn5.setCellValueFactory(new PropertyValueFactory<UserDataLevel2, Integer>("round5Points"));
        ObservableList<UserDataLevel2> userList2 = FXCollections.observableArrayList(level2Users);
        tb_level2Name.setItems(userList2);
        tb_level2Score.setItems(userList2);

        nameColumn = (TableColumn) tb_level3Name.getColumns().get(0);
        scoreColumn1 = (TableColumn) tb_level3Score.getColumns().get(0);
        scoreColumn2 = (TableColumn) tb_level3Score.getColumns().get(1);
        scoreColumn3 = (TableColumn) tb_level3Score.getColumns().get(2);
        scoreColumn4 = (TableColumn) tb_level3Score.getColumns().get(3);
        scoreColumn5 = (TableColumn) tb_level3Score.getColumns().get(4);
        nameColumn.setCellValueFactory(new PropertyValueFactory<UserDataLevel3, String>("USER_NAME"));
        scoreColumn1.setCellValueFactory(new PropertyValueFactory<UserDataLevel3, Integer>("round1Points"));
        scoreColumn2.setCellValueFactory(new PropertyValueFactory<UserDataLevel3, Integer>("round2TotalPoints"));
        scoreColumn3.setCellValueFactory(new PropertyValueFactory<UserDataLevel3, Integer>("round3TotalPoints"));
        scoreColumn4.setCellValueFactory(new PropertyValueFactory<UserDataLevel3, Integer>("round4Points"));
        scoreColumn5.setCellValueFactory(new PropertyValueFactory<UserDataLevel3, Integer>("round5Points"));
        ObservableList<UserDataLevel3> userList3 = FXCollections.observableArrayList(level3Users);
        tb_level3Name.setItems(userList3);
        tb_level3Score.setItems(userList3);

    }

    public void show() {
        ap_root.setVisible(true);
    }

    private void hide() {
        ap_root.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ap_root.setVisible(false);
    }
}
