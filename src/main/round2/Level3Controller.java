package main.round2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import main.Main;
import tool.Constants;

import java.net.URL;
import java.util.ResourceBundle;

public class Level3Controller implements Initializable{
    @FXML
    private AnchorPane ap_root;
    @FXML
    private TabPane tp_mainTab;
    @FXML
    private Label lb_cnInstructionHeader;
    @FXML
    private Label lb_cnInstruction;
    @FXML
    private Label lb_enInstruction;
    @FXML
    private Label lb_cnTimeLimit;
    @FXML
    private Label lb_enTimeLimit;
    @FXML
    private Label lb_enExampleHeader;
    @FXML
    private Label lb_cnExample;
    @FXML
    private Label lb_enWarning;

    private void setStyleId() {
        lb_cnInstructionHeader.setId(Constants.CN_FONT_HEADER);
        lb_cnInstruction.setId(Constants.CN_FONT_TEXT);
        lb_enInstruction.setId(Constants.EN_FONT_TEXT);
        lb_cnTimeLimit.setId(Constants.CN_FONT_TIME_LIMIT);
        lb_enTimeLimit.setId(Constants.EN_FONT_TIME_LIMIT);

        lb_enExampleHeader.setId(Constants.EN_FONT_EXAMPLE_HEADER);
        lb_cnExample.setId(Constants.CN_FONT_EXAMPLE);
        lb_enWarning.setId(Constants.EN_FONT_EXAMPLE_WARNING);
    }

    private void setData(){
        lb_cnInstruction.setText(Main.R2L3_DATA.CN_INSTRUCTION);
        lb_enInstruction.setText(Main.R2L3_DATA.EN_INSTRUCTION);
        String cnTimeLimit = "限时" + Main.R2L3_DATA.TIME_LIMIT + "分钟";
        String enTimeLimit = "Time Limit: " + Main.R2L3_DATA.TIME_LIMIT + ((Main.R2L3_DATA.TIME_LIMIT > 1) ? " minutes" : " minute");
        lb_cnTimeLimit.setText(cnTimeLimit);
        lb_enTimeLimit.setText(enTimeLimit);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setStyleId();
        //setData();
    }
}
