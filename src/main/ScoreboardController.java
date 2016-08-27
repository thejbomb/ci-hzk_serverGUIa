package main;

import data.UserDataLevel1;
import data.UserDataLevel2;
import data.UserDataLevel3;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
    private VBox vb_level1Name;
    @FXML
    private VBox vb_level2Name;
    @FXML
    private VBox vb_level3Name;
    @FXML
    private VBox vb_R1L1Points;
    @FXML
    private VBox vb_R2L1Points;
    @FXML
    private VBox vb_R3L1Points;
    @FXML
    private VBox vb_R4L1Points;
    @FXML
    private VBox vb_R5L1Points;
    @FXML
    private VBox vb_R1L2Points;
    @FXML
    private VBox vb_R2L2Points;
    @FXML
    private VBox vb_R3L2Points;
    @FXML
    private VBox vb_R4L2Points;
    @FXML
    private VBox vb_R5L2Points;
    @FXML
    private VBox vb_R1L3Points;
    @FXML
    private VBox vb_R2L3Points;
    @FXML
    private VBox vb_R3L3Points;
    @FXML
    private VBox vb_R4L3Points;
    @FXML
    private VBox vb_R5L3Points;
    @FXML
    private BorderPane bp_start;

    private LinkedList<UserDataLevel1> level1Users;
    private LinkedList<UserDataLevel2> level2Users;
    private LinkedList<UserDataLevel3> level3Users;

    private LinkedList<VBox> vBoxes;

    private int currentRound = 0;

    private MainController controller;

    public void setCurrentRound(int current) {
        currentRound = current;
        setupStartButton();
    }

    public void init(MainController controller) {
        this.controller = controller;
        Bounds rectBounds = gp_table1.localToScene(gp_table1.getBoundsInLocal());

        double rectX = rectBounds.getMinX();
        double rectY = rectBounds.getMinY();
        double rectWidth = rectBounds.getWidth();
        double rectHeight = rectBounds.getHeight();
        for (VBox vb : vBoxes)
            vb.setSpacing(rectBounds.getHeight() / 95);

        Rectangle rect = new Rectangle(rectX, rectY, rectWidth, rectHeight);
        rect.setFill(null);
        rect.setStroke(Color.BLACK);
        ap_root.getChildren().add(rect);
        double height = rectBounds.getHeight() / 20;
        rectBounds = lb_L1Name.localToScene(lb_L1Name.getBoundsInLocal());
        double y = rectBounds.getMaxY();
        for (int i = 0; i < 18; i++) {
            addRectangle(rectBounds.getMinX(), y, rectBounds.getWidth(), height);
            y += height;
        }
        y = rectBounds.getMaxY();
        rectBounds = lb_L1Round.localToScene(lb_L1Round.getBoundsInLocal());
        double width = rectBounds.getWidth() / 5;
        for (int i = 0; i < 18; i++) {
            double x = rectBounds.getMinX();
            for (int j = 0; j < 5; j++) {
                addRectangle(x, y, width, height);
                x += width;
            }
            y += height;
        }

        addRectangle(lb_L1Name);
        addRectangle(lb_L1Round);
        addRectangle(lb_R1L1);
        addRectangle(lb_R2L1);
        addRectangle(lb_R3L1);
        addRectangle(lb_R4L1);
        addRectangle(lb_R5L1);

        rectBounds = gp_table2.localToScene(gp_table2.getBoundsInLocal());

        rectX = rectBounds.getMinX();
        rectY = rectBounds.getMinY();
        rectWidth = rectBounds.getWidth();
        rectHeight = rectBounds.getHeight();
        rect = new Rectangle(rectX, rectY, rectWidth, rectHeight);
        rect.setFill(null);
        rect.setStroke(Color.BLACK);
        ap_root.getChildren().add(rect);
        rectBounds = lb_L2Name.localToScene(lb_L2Name.getBoundsInLocal());
        y = rectBounds.getMaxY();
        for (int i = 0; i < 18; i++) {
            addRectangle(rectBounds.getMinX(), y, rectBounds.getWidth(), height);
            y += height;
        }
        y = rectBounds.getMaxY();
        rectBounds = lb_L2Round.localToScene(lb_L2Round.getBoundsInLocal());
        width = rectBounds.getWidth() / 5;
        for (int i = 0; i < 18; i++) {
            double x = rectBounds.getMinX();
            for (int j = 0; j < 5; j++) {
                addRectangle(x, y, width, height);
                x += width;
            }
            y += height;
        }

        addRectangle(lb_L2Name);
        addRectangle(lb_L2Round);
        addRectangle(lb_R1L2);
        addRectangle(lb_R2L2);
        addRectangle(lb_R3L2);
        addRectangle(lb_R4L2);
        addRectangle(lb_R5L2);

        rectBounds = gp_table3.localToScene(gp_table3.getBoundsInLocal());

        rectX = rectBounds.getMinX();
        rectY = rectBounds.getMinY();
        rectWidth = rectBounds.getWidth();
        rectHeight = rectBounds.getHeight();
        rect = new Rectangle(rectX, rectY, rectWidth, rectHeight);
        rect.setFill(null);
        rect.setStroke(Color.BLACK);
        ap_root.getChildren().add(rect);
        rectBounds = lb_L3Name.localToScene(lb_L3Name.getBoundsInLocal());
        y = rectBounds.getMaxY();
        for (int i = 0; i < 18; i++) {
            addRectangle(rectBounds.getMinX(), y, rectBounds.getWidth(), height);
            y += height;
        }
        y = rectBounds.getMaxY();
        rectBounds = lb_L3Round.localToScene(lb_L3Round.getBoundsInLocal());
        width = rectBounds.getWidth() / 5;
        for (int i = 0; i < 18; i++) {
            double x = rectBounds.getMinX();
            for (int j = 0; j < 5; j++) {
                addRectangle(x, y, width, height);
                x += width;
            }
            y += height;
        }

        addRectangle(lb_L3Name);
        addRectangle(lb_L3Round);
        addRectangle(lb_R1L3);
        addRectangle(lb_R2L3);
        addRectangle(lb_R3L3);
        addRectangle(lb_R4L3);
        addRectangle(lb_R5L3);
    }

    private void addRectangle(Label label) {
        Bounds rectBounds = label.localToScene(label.getBoundsInLocal());
        double rectX = rectBounds.getMinX();
        double rectY = rectBounds.getMinY();
        double rectWidth = rectBounds.getWidth();
        double rectHeight = rectBounds.getHeight();
        Rectangle rect = new Rectangle(rectX, rectY, rectWidth, rectHeight);
        rect.setFill(null);
        rect.setStroke(Color.BLACK);
        ap_root.getChildren().add(rect);
    }

    private void addRectangle(double x, double y, double width, double height) {
        Rectangle rect = new Rectangle(x, y, width, height);
        rect.setFill(null);
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
        for (UserDataLevel1 ud : level1Users) {
            Label label = new Label();
            label.setText(ud.getUSER_NAME());

            vb_level1Name.getChildren().add(label);

            label = new Label();
            label.setText(Integer.toString(ud.getRound1Points()));
            vb_R1L1Points.getChildren().add(label);
            label = new Label();
            try {
                label.setText(Integer.toString(ud.getRound2Points().getLast()));
            } catch (NullPointerException ex) {
                label.setText("0");
            }
            vb_R2L1Points.getChildren().add(label);
            label = new Label();
            label.setText(Integer.toString(ud.getRound3Points()));
            vb_R3L1Points.getChildren().add(label);
            label = new Label();
            label.setText(Integer.toString(ud.getRound4Points()));
            vb_R4L1Points.getChildren().add(label);
            label = new Label();
            label.setText(Integer.toString(ud.getRound5Points()));
            vb_R5L1Points.getChildren().add(label);

        }

        for (UserDataLevel2 ud : level2Users) {
            Label label = new Label();
            label.setText(ud.getUSER_NAME());
            vb_level2Name.getChildren().add(label);

            label = new Label();
            label.setText(Integer.toString(ud.getRound1Points()));
            vb_R1L2Points.getChildren().add(label);
            label = new Label();
            try {
                label.setText(Integer.toString(ud.getRound2Points().getLast()));
            } catch (NullPointerException ex) {
                label.setText("0");
            }
            vb_R2L2Points.getChildren().add(label);
            label = new Label();
            label.setText(Integer.toString(ud.getRound3Points()));
            vb_R3L2Points.getChildren().add(label);
            label = new Label();
            label.setText(Integer.toString(ud.getRound4Points()));
            vb_R4L2Points.getChildren().add(label);
            label = new Label();
            label.setText(Integer.toString(ud.getRound5Points()));
            vb_R5L2Points.getChildren().add(label);
        }

        for (UserDataLevel3 ud : level3Users) {
            Label label = new Label();
            label.setText(ud.getUSER_NAME());
            vb_level3Name.getChildren().add(label);

            label = new Label();
            label.setText(Integer.toString(ud.getRound1Points()));
            vb_R1L3Points.getChildren().add(label);
            label = new Label();
            try {
                label.setText(Integer.toString(ud.getRound2Points().getLast()));
            } catch (NullPointerException ex) {
                label.setText("0");
            }
            vb_R2L3Points.getChildren().add(label);
            label = new Label();
            label.setText(Integer.toString(ud.getRound3Points()));
            vb_R3L3Points.getChildren().add(label);
            label = new Label();
            label.setText(Integer.toString(ud.getRound4Points()));
            vb_R4L3Points.getChildren().add(label);
            label = new Label();
            label.setText(Integer.toString(ud.getRound5Points()));
            vb_R5L3Points.getChildren().add(label);
        }
    }

    public void update() {
        for (int i = 0; i < level1Users.size(); i++) {
            updateNode(vb_R1L1Points.getChildren().get(i), level1Users.get(i).getRound1Points());
            try {
                updateNode(vb_R2L1Points.getChildren().get(i), level1Users.get(i).getRound2Points().getLast());
            } catch (NullPointerException ex) {
                updateNode(vb_R2L1Points.getChildren().get(i), 0);
            }
            updateNode(vb_R3L1Points.getChildren().get(i), level1Users.get(i).getRound3Points());
            updateNode(vb_R4L1Points.getChildren().get(i), level1Users.get(i).getRound4Points());
            updateNode(vb_R5L1Points.getChildren().get(i), level1Users.get(i).getRound5Points());
        }

        for (int i = 0; i < level2Users.size(); i++) {
            updateNode(vb_R1L2Points.getChildren().get(i), level2Users.get(i).getRound1Points());
            try {
                updateNode(vb_R2L2Points.getChildren().get(i), level2Users.get(i).getRound2Points().getLast());
            } catch (NullPointerException ex) {
                updateNode(vb_R2L2Points.getChildren().get(i), 0);
            }
            updateNode(vb_R3L2Points.getChildren().get(i), level2Users.get(i).getRound3Points());
            updateNode(vb_R4L2Points.getChildren().get(i), level2Users.get(i).getRound4Points());
            updateNode(vb_R5L2Points.getChildren().get(i), level2Users.get(i).getRound5Points());
        }

        for (int i = 0; i < level3Users.size(); i++) {
            updateNode(vb_R1L3Points.getChildren().get(i), level3Users.get(i).getRound1Points());
            try {
                updateNode(vb_R2L3Points.getChildren().get(i), level3Users.get(i).getRound2Points().getLast());
            } catch (NullPointerException ex) {
                updateNode(vb_R2L3Points.getChildren().get(i), 0);
            }
            updateNode(vb_R3L3Points.getChildren().get(i), level3Users.get(i).getRound3Points());
            updateNode(vb_R4L3Points.getChildren().get(i), level3Users.get(i).getRound4Points());
            updateNode(vb_R5L3Points.getChildren().get(i), level3Users.get(i).getRound5Points());
        }
    }

    private void updateNode(Node node, int point) {
        Label label = (Label) node;
        String l = Integer.toString(point);
        label.setText(l);
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
        vBoxes = new LinkedList<>();
        vBoxes.add(vb_level1Name);
        vBoxes.add(vb_level2Name);
        vBoxes.add(vb_level3Name);
        vBoxes.add(vb_R1L1Points);
        vBoxes.add(vb_R2L1Points);
        vBoxes.add(vb_R3L1Points);
        vBoxes.add(vb_R4L1Points);
        vBoxes.add(vb_R5L1Points);
        vBoxes.add(vb_R1L2Points);
        vBoxes.add(vb_R2L2Points);
        vBoxes.add(vb_R3L2Points);
        vBoxes.add(vb_R4L2Points);
        vBoxes.add(vb_R5L2Points);
        vBoxes.add(vb_R1L3Points);
        vBoxes.add(vb_R2L3Points);
        vBoxes.add(vb_R3L3Points);
        vBoxes.add(vb_R4L3Points);
        vBoxes.add(vb_R5L3Points);
    }
}
